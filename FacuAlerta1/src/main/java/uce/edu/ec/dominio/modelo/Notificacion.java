package uce.edu.ec.dominio.modelo;

import java.util.Objects;
import uce.edu.ec.dominio.util.Validador;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Notificacion implements Serializable {
    private String idNotificacion;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaEnvio;
    private String destinatarioCorreo;
    private boolean leida;
    private Incidente incidente;   // Relacion uno a uno: cada notificacion referencia un objeto Incidente (asociacion)

    // Constructor por defecto: inicializa todos los atributos
    public Notificacion() {
        this.idNotificacion = "";
        this.titulo = "";
        this.mensaje = "";
        this.fechaEnvio = LocalDateTime.now();
        this.destinatarioCorreo = "";
        this.leida = false;
        this.incidente = new Incidente();
    }

    // Constructor con parametros
    public Notificacion(String idNotificacion, String titulo, String mensaje, String destinatarioCorreo,
                        Incidente incidente) {
        this();
        setIdNotificacion(idNotificacion);
        setTitulo(titulo);
        setMensaje(mensaje);
        setDestinatarioCorreo(destinatarioCorreo);
        setIncidente(incidente);
        this.fechaEnvio = LocalDateTime.now();
        this.leida = false;
    }

    public String getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(String idNotificacion) {
        this.idNotificacion = Validador.exigirCodigo(idNotificacion, "El id de notificacion");
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = Validador.exigirTitulo(titulo, "El titulo");
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = Validador.exigirTextoSinNumeros(mensaje, "El mensaje");
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = Validador.exigirFechaNoFutura(fechaEnvio, "La fecha de envio");
    }

    public String getDestinatarioCorreo() {
        return destinatarioCorreo;
    }

    public void setDestinatarioCorreo(String destinatarioCorreo) {
        this.destinatarioCorreo = Validador.exigirDestinatarios(destinatarioCorreo, "El correo del destinatario");
    }

    public boolean isLeida() {
        return leida;
    }

    public void setLeida(boolean leida) {
        // Validacion de fondo: una notificacion leida no vuelve a "no leida"
        if (this.leida && !leida) {
            throw new IllegalArgumentException("Una notificacion leida no puede volver a no leida");
        }
        this.leida = leida;
    }

    public Incidente getIncidente() {
        return incidente;
    }

    public void setIncidente(Incidente incidente) {
        this.incidente = Validador.exigirNoNulo(incidente, "El incidente");
    }

    @Override
    public String toString() {
        return "Notificacion{idNotificacion='" + idNotificacion + "', titulo='" + titulo + "', mensaje='" + mensaje
                + "', destinatarioCorreo='" + destinatarioCorreo + "', fechaEnvio=" + fechaEnvio
                + ", leida=" + leida + ", idIncidente=" + incidente.getIdIncidente() + "}";
    }
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (!(objeto instanceof Notificacion)) return false;
        Notificacion otro = (Notificacion) objeto;
        return Objects.equals(idNotificacion, otro.idNotificacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNotificacion);
    }

}