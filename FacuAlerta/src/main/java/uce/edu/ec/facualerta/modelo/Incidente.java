package uce.edu.ec.facualerta.modelo;

import java.io.Serializable;

public class Incidente implements Serializable {
    private int idIncidente;
    private String fechaHoraReporte;
    private String fechaHoraActualizacion;
    private String estado;
    private String titulo;
    private String descripcion;
    private String nivelGravedad;
    private int idUsuarioReporta;

    public Incidente() {
    }

    public Incidente(int idIncidente, String fechaHoraReporte, String fechaHoraActualizacion, String estado,
            String titulo, String descripcion, String nivelGravedad, int idUsuarioReporta) {
        this.idIncidente = idIncidente;
        this.fechaHoraReporte = fechaHoraReporte;
        this.fechaHoraActualizacion = fechaHoraActualizacion;
        this.estado = estado;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.nivelGravedad = nivelGravedad;
        this.idUsuarioReporta = idUsuarioReporta;
    }

    public int getIdIncidente() {
        return idIncidente;
    }

    public void setIdIncidente(int idIncidente) {
        this.idIncidente = idIncidente;
    }

    public String getFechaHoraReporte() {
        return fechaHoraReporte;
    }

    public void setFechaHoraReporte(String fechaHoraReporte) {
        this.fechaHoraReporte = fechaHoraReporte;
    }

    public String getFechaHoraActualizacion() {
        return fechaHoraActualizacion;
    }

    public void setFechaHoraActualizacion(String fechaHoraActualizacion) {
        this.fechaHoraActualizacion = fechaHoraActualizacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNivelGravedad() {
        return nivelGravedad;
    }

    public void setNivelGravedad(String nivelGravedad) {
        this.nivelGravedad = nivelGravedad;
    }

    public int getIdUsuarioReporta() {
        return idUsuarioReporta;
    }

    public void setIdUsuarioReporta(int idUsuarioReporta) {
        this.idUsuarioReporta = idUsuarioReporta;
}

@Override public String toString() { return "Incidente{" + "id=" + idIncidente + ", titulo='" + titulo + '\'' + ", estado='" + estado + '\'' + '}'; } 
}
