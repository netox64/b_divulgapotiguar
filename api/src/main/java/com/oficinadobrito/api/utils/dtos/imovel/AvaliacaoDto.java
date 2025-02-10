package com.oficinadobrito.api.utils.dtos.imovel;

public class AvaliacaoDto {
    
    private int quantVerificacao;
    private int quantAcerto;
    private double indiceConfiabilidade;

    public AvaliacaoDto(){}

    public int getQuantVerificacao() {
        return quantVerificacao;
    }

    public void setQuantVerificacao(int quantVerificacao) {
        this.quantVerificacao = quantVerificacao;
    }

    public int getQuantAcerto() {
        return quantAcerto;
    }

    public void setQuantAcerto(int quantAcerto) {
        this.quantAcerto = quantAcerto;
    }

    public double getIndiceConfiabilidade() {
        return indiceConfiabilidade;
    }

    public void setIndiceConfiabilidade(double indiceConfiabilidade) {
        this.indiceConfiabilidade = indiceConfiabilidade;
    }
}
