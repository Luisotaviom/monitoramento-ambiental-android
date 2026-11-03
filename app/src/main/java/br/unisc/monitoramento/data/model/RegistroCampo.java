package br.unisc.monitoramento.data.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Registro de campo preenchido pelo usuário (formulário + GPS + foto).
 * Contrato compartilhado: a persistência em Room e a sincronização são da Parte 1.
 */
public class RegistroCampo {

    /** Estados da fila de sincronização (AM-04). */
    public enum Status {
        PENDENTE,
        ENVIANDO,
        SINCRONIZADO,
        CONFLITO
    }

    /** Chave de idempotência: a API usa para não duplicar o registro. */
    private final String uuidLocal;
    private final String pontoId;
    private final Map<String, String> campos;
    private final double latitude;
    private final double longitude;
    private final String fotoPath;
    private final long criadoEm;

    private Status status;
    private long atualizadoEm;
    private Long sincronizadoEm;

    public RegistroCampo(String uuidLocal, String pontoId, Map<String, String> campos,
                         double latitude, double longitude, String fotoPath,
                         long criadoEm) {
        this.uuidLocal = uuidLocal;
        this.pontoId = pontoId;
        this.campos = campos == null
                ? Collections.emptyMap()
                : Collections.unmodifiableMap(new HashMap<>(campos));
        this.latitude = latitude;
        this.longitude = longitude;
        this.fotoPath = fotoPath;
        this.criadoEm = criadoEm;
        this.status = Status.PENDENTE;
        this.atualizadoEm = criadoEm;
        this.sincronizadoEm = null;
    }

    public String getUuidLocal() {
        return uuidLocal;
    }

    public String getPontoId() {
        return pontoId;
    }

    /** Valores do formulário, pelo identificador do campo. Não modificável. */
    public Map<String, String> getCampos() {
        return campos;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getFotoPath() {
        return fotoPath;
    }

    public long getCriadoEm() {
        return criadoEm;
    }

    public Status getStatus() {
        return status;
    }

    public long getAtualizadoEm() {
        return atualizadoEm;
    }

    /** Null enquanto o registro não foi sincronizado. */
    public Long getSincronizadoEm() {
        return sincronizadoEm;
    }

    /** Muda o status e registra a hora da mudança. */
    public void mudarStatus(Status novoStatus, long agora) {
        this.status = novoStatus;
        this.atualizadoEm = agora;
        if (novoStatus == Status.SINCRONIZADO) {
            this.sincronizadoEm = agora;
        }
    }
}
