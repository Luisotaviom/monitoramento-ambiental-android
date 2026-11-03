package br.unisc.monitoramento.data.model;

/** Ponto de coleta de campo dentro de uma área (AM-02, AM-05). */
public class PontoColeta {

    private final String id;
    private final String areaId;
    private final String nome;
    private final double latitude;
    private final double longitude;

    /** Caminho local da foto, ou null se não houver. */
    private final String fotoPath;

    public PontoColeta(String id, String areaId, String nome,
                       double latitude, double longitude, String fotoPath) {
        this.id = id;
        this.areaId = areaId;
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fotoPath = fotoPath;
    }

    public String getId() {
        return id;
    }

    public String getAreaId() {
        return areaId;
    }

    public String getNome() {
        return nome;
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
}
