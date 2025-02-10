package com.oficinadobrito.api.services;

import com.oficinadobrito.api.entities.Imovel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ConfiabilidateCalculatorService {
    private double pesoArea;
    private double pesoDimensoes;
    private double pesoTexto;
    private double pesoExato;
    private double confiabilidade;
        
    public ConfiabilidateCalculatorService(){
            this.pesoArea = 0.3;
            this.pesoDimensoes = 0.15;
            this.pesoTexto = 0.3;
            this.pesoExato = 0.25;
            this.confiabilidade = 0;
        }
    
    public double calcularConfiabilidade(Imovel imovelOriginal, Imovel imovelAnalisado) {
        
        this.confiabilidade += this.pesoArea * compararNumeros(imovelOriginal.getAreaCalculada(), imovelAnalisado.getAreaCalculada());
        this.confiabilidade += this.pesoDimensoes * compararNumeros(imovelOriginal.getComprimento(), imovelAnalisado.getComprimento());
        this.confiabilidade += this.pesoDimensoes * compararNumeros(imovelOriginal.getLargura(), imovelAnalisado.getLargura());
        
        this.confiabilidade += this.pesoExato * compararTextosExatos(imovelOriginal.getMatriculaCartorio(), imovelAnalisado.getMatriculaCartorio());
        this.confiabilidade += this.pesoExato * compararTextosExatos(imovelOriginal.getNomeProprietario(), imovelAnalisado.getNomeProprietario());
        this.confiabilidade += this.pesoExato * compararTextosExatos(imovelOriginal.getCpfCnpjProprietario(), imovelAnalisado.getCpfCnpjProprietario());
        this.confiabilidade += this.pesoExato * compararTextosExatos(imovelOriginal.getRgProprietario(), imovelAnalisado.getRgProprietario());
        
        this.confiabilidade += this.pesoTexto * calcularSimilaridade(imovelOriginal.getLocalizacao(), imovelAnalisado.getLocalizacao());
        
        return BigDecimal.valueOf(this.confiabilidade * 100).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public int calcularQuantidadeAcertos(Imovel imovelOriginal, Imovel imovelAnalisado) {
        int acertos = 0;

        if (compararNumeros(imovelOriginal.getAreaCalculada(), imovelAnalisado.getAreaCalculada()) > 0.9) acertos++;
        if (compararNumeros(imovelOriginal.getComprimento(), imovelAnalisado.getComprimento()) > 0.9) acertos++;
        if (compararNumeros(imovelOriginal.getLargura(), imovelAnalisado.getLargura()) > 0.9) acertos++;

        if (compararTextosExatos(imovelOriginal.getMatriculaCartorio(), imovelAnalisado.getMatriculaCartorio()) == 1) acertos++;
        if (compararTextosExatos(imovelOriginal.getNomeProprietario(), imovelAnalisado.getNomeProprietario()) == 1) acertos++;
        if (compararTextosExatos(imovelOriginal.getCpfCnpjProprietario(), imovelAnalisado.getCpfCnpjProprietario()) == 1) acertos++;
        if (compararTextosExatos(imovelOriginal.getRgProprietario(), imovelAnalisado.getRgProprietario()) == 1) acertos++;

        if (calcularSimilaridade(imovelOriginal.getLocalizacao(), imovelAnalisado.getLocalizacao()) > 0.9) acertos++;

        return acertos;
    }

    public int calcularQuantidadeVerificados(Imovel imovelOriginal, Imovel imovelAnalisado) {
        int verificados = 0;

        if (imovelOriginal.getAreaCalculada() != null && imovelAnalisado.getAreaCalculada() != null) verificados++;
        if (imovelOriginal.getComprimento() != null && imovelAnalisado.getComprimento() != null) verificados++;
        if (imovelOriginal.getLargura() != null && imovelAnalisado.getLargura() != null) verificados++;

        if (imovelOriginal.getMatriculaCartorio() != null && imovelAnalisado.getMatriculaCartorio() != null) verificados++;
        if (imovelOriginal.getNomeProprietario() != null && imovelAnalisado.getNomeProprietario() != null) verificados++;
        if (imovelOriginal.getCpfCnpjProprietario() != null && imovelAnalisado.getCpfCnpjProprietario() != null) verificados++;
        if (imovelOriginal.getRgProprietario() != null && imovelAnalisado.getRgProprietario() != null) verificados++;

        if (imovelOriginal.getLocalizacao() != null && imovelAnalisado.getLocalizacao() != null) verificados++;

        return verificados;
    }

    private static double compararNumeros(Double original, Double analisado) {
        if (original == null || analisado == null) return 0;
        double diferenca = Math.abs(original - analisado);
        double percentual = (1 - (diferenca / Math.max(original, analisado)));
        return Math.max(0, percentual);
    }

    private static double compararTextosExatos(String original, String analisado) {
        if (original == null || analisado == null) return 0;
        return original.equalsIgnoreCase(analisado) ? 1.0 : 0.0;
    }

    private static double calcularSimilaridade(String original, String analisado) {
        if (original == null || analisado == null) return 0;
        int distancia = calcularDistanciaLevenshtein(original, analisado);
        int maiorTamanho = Math.max(original.length(), analisado.length());
        return 1 - ((double) distancia / maiorTamanho);
    }

    private static int calcularDistanciaLevenshtein(String str1, String str2) {
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 0; i <= str1.length(); i++) {
            for (int j = 0; j <= str2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(
                        dp[i - 1][j - 1] + (str1.charAt(i - 1) == str2.charAt(j - 1) ? 0 : 1),
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1)
                    );
                }
            }
        }
        return dp[str1.length()][str2.length()];
    }
}
