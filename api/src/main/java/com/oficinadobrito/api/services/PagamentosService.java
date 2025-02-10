package com.oficinadobrito.api.services;

import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;
import com.oficinadobrito.api.entities.Pagamento;
import com.oficinadobrito.api.entities.Usuario;
import com.oficinadobrito.api.repositories.PagamentoRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.nayuki.qrcodegen.QrCode;
import io.nayuki.qrcodegen.QrCode.Ecc;
import java.util.*;

@Service
public class PagamentosService extends GenericService<Pagamento> {
    @Value("${spring.pix.api.client_id}")
    private String clientId;
    @Value("${spring.pix.api.client_secret}")
    private String clientSecret;
    @Value("${spring.pix.api.certificate}")
    private String certificate;
    @Value("${spring.pix.api.sandbox}")
    private String sandbox;
    private EfiPay efi;

    private final PagamentoRepository pagamentoRepository;
    private final UsuariosService usuariosService;

    public PagamentosService(PagamentoRepository repository, PagamentoRepository pagamentoRepository, UsuariosService usuariosService) {
        super(repository);
        this.pagamentoRepository = pagamentoRepository;
        this.usuariosService = usuariosService;
    }

    public Set<Pagamento> getAllPagamentosForUsuario(String usuarioId) {
        Usuario usuario = this.usuariosService.findUsuarioById(usuarioId);
        return usuario == null ? new HashSet<>() : new HashSet<>(this.pagamentoRepository.findByUsuarioId(usuarioId));
    }

    private JSONObject config() {
        JSONObject options = new JSONObject();
        options.put("client_id", this.clientId);
        options.put("client_secret", this.clientSecret);
        options.put("certificate", this.certificate);
        options.put("sandbox", this.sandbox);
        return options;
    }

    private synchronized void initializeEfi() throws Exception {
        if (this.efi == null) {
            this.efi = new EfiPay(this.config());
        }
    }


    public String pixGenerateQRCodeAutoSvg(String username, String cpf, String valorOriginalCobrado) {
        String chaveAleatoria = pegarChaveAleatoriaExistente();
        if(chaveAleatoria == null){
            JSONObject chavePix = pixCreateEVP();
            if (chavePix != null && chavePix.has("chave") && !chavePix.getString("chave").isEmpty()) {
                chaveAleatoria = chavePix.getString("chave");
            } else {
                throw new RuntimeException("Não foi possível obter ou criar uma chave PIX.");
            }
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setCpf(cpf);

        HashMap<String, String> params = new HashMap<>();
        params.put("id", this.pixCriarCobranca(usuario, chaveAleatoria, valorOriginalCobrado));

        try {
            initializeEfi();
            Map<String, Object> response = this.efi.call("pixGenerateQRCode", params, new HashMap<>());

            String qrData = (String) response.get("qrcode");

            QrCode qr = QrCode.encodeText(qrData, Ecc.MEDIUM);
            return generateSvg(qr, 4);

        } catch (EfiPayException e) {
            throw new RuntimeException("Erro ao gerar QR Code: " + e.getError() + " - " + e.getErrorDescription());
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao gerar QR Code: " + e.getMessage());
        }
    }

    public JSONObject pixCreateEVP() {
        try {
            initializeEfi();
            return this.efi.call("pixCreateEvp", new HashMap<>(), new JSONObject());
        } catch (EfiPayException e) {
            throw new RuntimeException("Erro ao criar chave EVP: " + e.getError() + " - " + e.getErrorDescription());
        } catch (Exception ex) {
            throw new RuntimeException("Erro inesperado ao criar chave EVP: " + ex.getMessage());
        }
    }

    public String[] listarChavesPix() {
        try {
            initializeEfi();
            Map<String, Object> response = this.efi.call("pixListEvp", new HashMap<>(), new HashMap<>());

            if (response != null && response.containsKey("chaves")) {
                Object chavesObject = response.get("chaves");
                List<String> chavesList = new ArrayList<>();

                if (chavesObject instanceof JSONArray) {
                    JSONArray chavesArray = (JSONArray) chavesObject;
                    for (int i = 0; i < chavesArray.length(); i++) {
                        chavesList.add(chavesArray.getJSONObject(i).optString("chave", "valor padrão"));
                    }
                } else if (chavesObject instanceof ArrayList) {
                    for (Object item : (ArrayList<?>) chavesObject) {
                        if (item instanceof Map) {
                            JSONObject chaveObject = new JSONObject((Map<?, ?>) item);
                            chavesList.add(chaveObject.optString("chave", "valor padrão"));
                        } else if (item instanceof String) {
                            chavesList.add((String) item);
                        }
                    }
                } else {
                    throw new RuntimeException("Tipo de dado inesperado para chaves PIX: " + chavesObject.getClass().getName());
                }
                return chavesList.toArray(new String[0]);
            } else {
                return new String[0];
            }
        } catch (EfiPayException e) {
            throw new RuntimeException("Erro ao listar chaves PIX: " + e.getError() + " - " + e.getErrorDescription());
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao listar chaves PIX: " + e.getMessage());
        }
    }

    public String pegarChaveAleatoriaExistente() {
        String[] chaves = listarChavesPix();
        if (chaves.length > 0) {
            Random random = new Random();
            return chaves[random.nextInt(chaves.length)];
        }
        return null;
    }

    public String pixCreateLocale(String evp) {
        Map<String, Object> body = new HashMap<>();
        body.put("tipoCob", "cob");
        try {
            initializeEfi();
            Map<String, Object> response = this.efi.call("pixCreateLocation", new HashMap<>(), body);
            return response.get("id").toString();
        } catch (EfiPayException e) {
            throw new RuntimeException("Erro ao criar localização: " + e.getError() + " - " + e.getErrorDescription());
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao criar localização: " + e.getMessage());
        }
    }

    public String pixCriarCobranca(Usuario userCobrado, String chave, String valorOriginalCobrado) {
        Map<String, Object> body = new HashMap<>();

        Map<String, Object> calendario = new HashMap<>();
        Map<String, Object> devedor = new HashMap<>();
        Map<String, Object> valor = new HashMap<>();
        Map<String, Object> loc = new HashMap<>();
        calendario.put("expiracao", 1800);
        devedor.put("nome", userCobrado.getUsername());
        devedor.put("cpf", userCobrado.getCpf());
        valor.put("original", valorOriginalCobrado);
        String localeId = this.pixCreateLocale(chave);
        loc.put("id", Integer.parseInt(localeId));

        body.put("calendario", calendario);
        body.put("devedor", devedor);
        body.put("valor", valor);
        body.put("loc", loc);
        body.put("chave", chave);
        body.put("solicitacaoPagador", "Cobrança dos serviços prestados.");

        try {
            this.efi.call("pixCreateImmediateCharge", new HashMap<>(), body);
            return localeId;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar cobrança PIX: " + e.getMessage());
        }
    }

    private String generateSvg(QrCode qr, int border) {
        StringBuilder sb = new StringBuilder();
        int size = qr.size + border * 2;
        sb.append(String.format(
                "<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 %1$d %1$d\" stroke=\"none\">\n", size));

        for (int y = 0; y < qr.size; y++) {
            for (int x = 0; x < qr.size; x++) {
                if (qr.getModule(x, y)) {
                    sb.append(String.format("<rect x=\"%d\" y=\"%d\" width=\"1\" height=\"1\" fill=\"#000\" />\n",
                            x + border, y + border));
                }
            }
        }

        sb.append("</svg>\n");
        return sb.toString();
    }

    public boolean verificaCartao(String cardNumber) {
        cardNumber = cardNumber.replaceAll("\\s+", "");
        int[] digits = new int[cardNumber.length()];
        for (int i = 0; i < cardNumber.length(); i++) {
            digits[i] = Character.getNumericValue(cardNumber.charAt(i));
        }
        int sum = 0;
        boolean shouldDouble = false;
        for (int i = digits.length - 1; i >= 0; i--) {
            int digit = digits[i];
            if (shouldDouble) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            shouldDouble = !shouldDouble;
        }
        return sum % 10 == 0;
    }
}
