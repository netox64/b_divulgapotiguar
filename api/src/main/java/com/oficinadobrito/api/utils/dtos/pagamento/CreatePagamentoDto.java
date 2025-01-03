package com.oficinadobrito.api.utils.dtos.pagamento;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Objects;

public record CreatePagamentoDto(
                @NotBlank(message = "tipo has required") String tipo,

                String cartao,

                String chavePix,

                @Min(value = 0, message = "The value not has negative") double valor,

                @NotBlank(message = "A id for Usuario has required") String usuarioId,

                BigInteger[] planosIds) {
        @Override
        public boolean equals(Object o) {
                if (this == o)
                        return true;
                if (o == null || getClass() != o.getClass())
                        return false;
                CreatePagamentoDto that = (CreatePagamentoDto) o;
                return Double.compare(that.valor, valor) == 0 &&
                                tipo.equals(that.tipo) &&
                                Objects.equals(cartao, that.cartao) &&
                                Objects.equals(chavePix, that.chavePix) &&
                                usuarioId.equals(that.usuarioId) &&
                                Arrays.equals(planosIds, that.planosIds);
        }

        @Override
        public int hashCode() {
                int result = Objects.hash(tipo, cartao, chavePix, valor, usuarioId);
                result = 31 * result + Arrays.hashCode(planosIds);
                return result;
        }

        @Override
        public String toString() {
                return "CreatePagamentoDto{" +
                                "tipo='" + tipo + '\'' +
                                ", cartao='" + cartao + '\'' +
                                ", chavePix='" + chavePix + '\'' +
                                ", valor=" + valor +
                                ", usuarioId='" + usuarioId + '\'' +
                                ", planosIds=" + Arrays.toString(planosIds) +
                                '}';
        }
}
