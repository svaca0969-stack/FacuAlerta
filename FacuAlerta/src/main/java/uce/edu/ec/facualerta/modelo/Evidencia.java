package uce.edu.ec.facualerta.modelo;

public class Evidencia {
    private int idEvidencia;
    private int idIncidente;
    private String tipoEvidencia;
    private String urlArchivo;

    public Evidencia() {}

    public Evidencia(int idEvidencia, int idIncidente, String tipoEvidencia, String urlArchivo) {
        this.idEvidencia = idEvidencia;
        this.idIncidente = idIncidente;
        this.tipoEvidencia = tipoEvidencia;
        this.urlArchivo = urlArchivo;
    }

    public int getIdEvidencia() {
        return idEvidencia;
    }

    public void setIdEvidencia(int idEvidencia) {
        this.idEvidencia = idEvidencia;
    }

    public int getIdIncidente() {
        return idIncidente;
    }

    public void setIdIncidente(int idIncidente) {
        this.idIncidente = idIncidente;
    }

    public String getTipoEvidencia() {
        return tipoEvidencia;
    }

    public void setTipoEvidencia(String tipoEvidencia) {
        this.tipoEvidencia = tipoEvidencia;
    }

    public String getUrlArchivo() {
        return urlArchivo;
    }

    public void setUrlArchivo(String urlArchivo) {
        this.urlArchivo = urlArchivo;
    }

    @Override
    public String toString() {
        return "Evidencia{" + "id=" + idEvidencia + ", tipo='" + tipoEvidencia + "'}";
    }
}
