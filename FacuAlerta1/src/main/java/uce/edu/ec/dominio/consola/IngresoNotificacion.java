package uce.edu.ec.dominio.consola;

import uce.edu.ec.dominio.modelo.Incidente;
import uce.edu.ec.dominio.modelo.Notificacion;

// Captura datos de una notificacion
public class IngresoNotificacion {
    private final Lector lector;
    public IngresoNotificacion(Lector lector) { this.lector = lector; }
    public Notificacion ingresarNotificacion(String id, Incidente incidente) {
        Notificacion notificacion = new Notificacion();
        notificacion.setIdNotificacion(id);
        notificacion.setTitulo(lector.leerTitulo("Titulo"));
        notificacion.setMensaje(lector.leerTextoSinNumeros("Mensaje"));
        notificacion.setDestinatarioCorreo(lector.leerDestinatarios("Destinatario (@Todos o correos separados por comas)"));
        notificacion.setIncidente(incidente);
        return notificacion;
    }
}