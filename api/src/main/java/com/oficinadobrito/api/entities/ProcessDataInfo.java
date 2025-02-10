package com.oficinadobrito.api.entities;

import com.oficinadobrito.api.utils.enums.StatusProcessamento;
import jakarta.persistence.*;


import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_processos")
public class ProcessDataInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long processId;
    
    private BigInteger imovelId;

    @Enumerated(EnumType.STRING)
    private StatusProcessamento status;

    @Lob
    private String resultado;

    private LocalDateTime criadoEm = LocalDateTime.now();

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public StatusProcessamento getStatus() {
        return status;
    }

    public void setStatus(StatusProcessamento status) {
        this.status = status;
    }

    public BigInteger getImovelId() {
        return imovelId;
    }

    public void setImovelId(BigInteger imovelId) {
        this.imovelId = imovelId;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }
}
