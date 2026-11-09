package uce.edu.ec.facualerta.modelo;

public class Notificacion {
    private int idNotificacion;
    private String mensajeAlerta;
    private String fechaHoraEnvio;
    private String tipoNotificacion;
    private int idIncidente;

    public Notificacion() {}

    public Notificacion(
            int idNotificacion,
            String mensajeAlerta,
            String fechaHoraEnvio,
            String tipoNotificacion,
            int idIncidente) {
        this.idNotificacion = idNotificacion;
        this.mensajeAlerta = mensajeAlerta;
        this.fechaHoraEnvio = fechaHoraEnvio;
        this.tipoNotificacion = tipoNotificacion;
        this.idIncidente = idIncidente;
    }

    public int getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(int idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getMensajeAlerta() {
        return mensajeAlerta;
    }

    public void setMensajeAlerta(String mensajeAlerta) {
        this.mensajeAlerta = mensajeAlerta;
    }

    public String getFechaHoraEnvio() {
        return fechaHoraEnvio;
    }

    public void setFechaHoraEnvio(String fechaHoraEnvio) {
        this.fechaHoraEnvio = fechaHoraEnvio;
    }

    public String getTipoNotificacion() {
        return tipoNotificacion;
    }

    public void setTipoNotificacion(String tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }

    public int getIdIncidente() {
        return idIncidente;
    }

    public void setIdIncidente(int idIncidente) {
        this.idIncidente = idIncidente;
    }

    @Override
    public String toString() {
        return "Notificacion{" + "id=" + idNotificacion + ", tipo='" + tipoNotificacion + "'}";
    }
}
