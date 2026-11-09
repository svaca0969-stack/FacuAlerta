package uce.edu.ec.facualerta.modelo;

import java.time.LocalDateTime;

public class Notificacion {
    private String idNotificacion;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaEnvio;
    private String destinatarioCorreo;
    private boolean leida;

    // Constructor vacío
    public Notificacion() {
        this.fechaEnvio = LocalDateTime.now();
        this.leida = false;
    }

    // Constructor con parámetros principales
    public Notificacion(String idNotificacion, String titulo, String mensaje, String destinatarioCorreo) {
        this.idNotificacion = idNotificacion;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.destinatarioCorreo = destinatarioCorreo;
        this.fechaEnvio = LocalDateTime.now();
        this.leida = false;
    }

    // Getters y Setters
    public String getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(String idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getDestinatarioCorreo() {
        return destinatarioCorreo;
    }

    public void setDestinatarioCorreo(String destinatarioCorreo) {
        this.destinatarioCorreo = destinatarioCorreo;
    }

    public boolean isLeida() {
        return leida;
    }

    public void setLeida(boolean leida) {
        this.leida = leida;
    }

    @Override
    public String toString() {
        return "Notificacion{" +
                "idNotificacion='" + idNotificacion + '\'' +
                ", titulo='" + titulo + '\'' +
                ", destinatarioCorreo='" + destinatarioCorreo + '\'' +
                ", fechaEnvio=" + fechaEnvio +
                ", leida=" + leida +
                '}';
    }
}
