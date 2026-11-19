package uce.edu.ec.dominio.modelo;

import java.util.Objects;
import uce.edu.ec.dominio.util.Validador;

import java.io.Serializable;

public class Evidencia implements Serializable {
    private int idEvidencia;
    private String tipoEvidencia;
    private String urlArchivo;

    public Evidencia() {
        this.idEvidencia = 0;
        this.tipoEvidencia = "";
        this.urlArchivo = "";
    }

    public Evidencia(int idEvidencia, String tipoEvidencia, String urlArchivo) {
        this();
        setIdEvidencia(idEvidencia);
        setTipoEvidencia(tipoEvidencia);
        setUrlArchivo(urlArchivo);
    }

    public int getIdEvidencia() {
        return idEvidencia;
    }

    public void setIdEvidencia(int idEvidencia) {
        this.idEvidencia = Validador.exigirId(idEvidencia, "El id de evidencia");
    }

    public String getTipoEvidencia() {
        return tipoEvidencia;
    }

    public void setTipoEvidencia(String tipoEvidencia) {
        this.tipoEvidencia = Validador.exigirDeLista(tipoEvidencia, Validador.TIPOS_EVIDENCIA, "El tipo de evidencia");
    }

    public String getUrlArchivo() {
        return urlArchivo;
    }

    public void setUrlArchivo(String urlArchivo) {
        Validador.exigirEvidencia(urlArchivo, this.tipoEvidencia);
        this.urlArchivo = Validador.exigirUrlArchivo(urlArchivo, "La URL del archivo");
    }

    @Override
    public String toString() {
        return "Evidencia{idEvidencia=" + idEvidencia + ", tipoEvidencia='" + tipoEvidencia
                + "', urlArchivo='" + urlArchivo + "'}";
    }
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (!(objeto instanceof Evidencia)) return false;
        Evidencia otro = (Evidencia) objeto;
        return idEvidencia == otro.idEvidencia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEvidencia);
    }

}