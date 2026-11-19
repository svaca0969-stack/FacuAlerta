package uce.edu.ec.dominio.modelo;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class NotificacionTest {

    @Test
    public void testConstructorVacio() {
        Notificacion notificacion = new Notificacion();
        assertEquals("", notificacion.getIdNotificacion());
        assertEquals("", notificacion.getTitulo());
        assertEquals("", notificacion.getMensaje());
        assertEquals("", notificacion.getDestinatarioCorreo());
        assertNotNull(notificacion.getFechaEnvio());
        assertFalse(notificacion.isLeida());
        assertNotNull(notificacion.getIncidente());
    }

    @Test
    public void testConstructorParametros() {
        Incidente incidente = new Incidente();
        Notificacion notificacion = new Notificacion("N1", "Nuevo incidente", "Se reporto un incidente",
                "gestor@uce.edu.ec", incidente);
        assertEquals("N1", notificacion.getIdNotificacion());
        assertEquals("Nuevo incidente", notificacion.getTitulo());
        assertEquals("Se reporto un incidente", notificacion.getMensaje());
        assertEquals("gestor@uce.edu.ec", notificacion.getDestinatarioCorreo());
        assertSame(incidente, notificacion.getIncidente());
        assertNotNull(notificacion.getFechaEnvio());
        assertFalse(notificacion.isLeida());
    }

    @Test
    public void testGettersSetters() {
        Notificacion notificacion = new Notificacion();
        LocalDateTime fecha = LocalDateTime.of(2026, 1, 15, 10, 30);
        notificacion.setIdNotificacion("N7");
        notificacion.setTitulo("Aviso");
        notificacion.setMensaje("Mensaje de prueba");
        notificacion.setFechaEnvio(fecha);
        notificacion.setDestinatarioCorreo("a@uce.edu.ec");
        notificacion.setLeida(true);
        Incidente incidente = new Incidente();
        notificacion.setIncidente(incidente);
        assertSame(incidente, notificacion.getIncidente());
        assertEquals("N7", notificacion.getIdNotificacion());
        assertEquals("Aviso", notificacion.getTitulo());
        assertEquals("Mensaje de prueba", notificacion.getMensaje());
        assertEquals(fecha, notificacion.getFechaEnvio());
        assertEquals("a@uce.edu.ec", notificacion.getDestinatarioCorreo());
        assertTrue(notificacion.isLeida());
    }

    @Test
    public void testToString() {
        Notificacion notificacion = new Notificacion("N1", "Aviso", "Mensaje de prueba", "a@uce.edu.ec",
                new Incidente());
        String resultado = notificacion.toString();
        assertTrue(resultado.contains("N1"));
        assertTrue(resultado.contains("Aviso"));
        assertTrue(resultado.contains("a@uce.edu.ec"));
        assertTrue(resultado.contains("leida=false"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncidenteNulo() {
        new Notificacion().setIncidente(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCorreoInvalido() {
        new Notificacion().setDestinatarioCorreo("no-es-correo");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIdInvalido() {
        new Notificacion().setIdNotificacion("N 1 !");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFechaEnvioFutura() {
        new Notificacion().setFechaEnvio(LocalDateTime.now().plusDays(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoSePuedeVolverANoLeida() {
        Notificacion notificacion = new Notificacion();
        notificacion.setLeida(true);
        notificacion.setLeida(false);
    }
}