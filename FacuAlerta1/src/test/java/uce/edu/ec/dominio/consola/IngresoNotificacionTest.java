package uce.edu.ec.dominio.consola;

import org.junit.Test;
import uce.edu.ec.dominio.modelo.Incidente;
import uce.edu.ec.dominio.modelo.Notificacion;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.*;

public class IngresoNotificacionTest {

    @Test
    public void testIngresarNotificacion() {
        IngresoNotificacion ingreso = new IngresoNotificacion(
                LectorTest.crear("Aviso\nSe reporto un incidente\ngestor@uce.edu.ec\n", new ByteArrayOutputStream()));
        Incidente incidente = new Incidente();
        Notificacion n = ingreso.ingresarNotificacion("N1", incidente);
        assertEquals("N1", n.getIdNotificacion());
        assertEquals("Aviso", n.getTitulo());
        assertEquals("Se reporto un incidente", n.getMensaje());
        assertEquals("gestor@uce.edu.ec", n.getDestinatarioCorreo());
        assertSame(incidente, n.getIncidente());
        assertFalse(n.isLeida());
    }
}