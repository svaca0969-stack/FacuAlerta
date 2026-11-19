package uce.edu.ec.dominio.modelo;

import org.junit.Test;

import static org.junit.Assert.*;

/** Herencia, equals/hashCode con instanceof y relaciones entre clases. */
public class RelacionesYEqualsTest {

    @Test
    public void testHerenciaDeUsuario() {
        Usuario m = new Moderador(1, "Ana", "ana@uce.edu.ec", "clave123", 1, "Validar incidentes");
        assertTrue(m instanceof Usuario);
        assertTrue(new Estudiante() instanceof Usuario);
        assertTrue(new Trabajador() instanceof Usuario);
        assertTrue(new GestorDeRiesgo() instanceof Usuario);
        assertTrue(m.toString().contains("Moderador{"));   // toString sobreescrito en la hija
    }

    @Test
    public void testEqualsEntreTiposDistintosEsFalso() {
        Usuario u = new Usuario(1, "Ana", "ana@uce.edu.ec", "clave123", "ESTUDIANTE");
        Moderador m = new Moderador(1, "Ana", "ana@uce.edu.ec", "clave123", 1, "Validar incidentes");
        assertFalse(m.equals(u));
        assertFalse(u.equals(null));
        assertEquals(u, u);
    }

    @Test
    public void testEqualsYHashCodeDeModelo() {
        assertEquals(new Categoria(1, "Alto", "Riesgo alto"), new Categoria(1, "Otro", "Otra"));
        assertEquals(new Categoria(1, "Alto", "Riesgo alto").hashCode(), new Categoria(1, "Otro", "Otra").hashCode());
        assertNotEquals(new Categoria(1, "Alto", "Riesgo alto"), new Categoria(2, "Alto", "Riesgo alto"));
        assertEquals(new Evidencia(1, "IMAGEN", "https://uce.edu.ec/a.jpg"), new Evidencia(1, "VIDEO", "https://uce.edu.ec/v.mp4"));
    }

    @Test
    public void testRelacionNotificacionIncidenteUnoAUno() {
        Incidente i = new Incidente(1, "2026-11-10 08:30", "2026-11-10 09:00", "REPORTADO",
                "Robo de mochila", "Se sustrajo una mochila", "ALTO",
                new Usuario(1, "Carlos", "carlos@uce.edu.ec", "hash123", "ESTUDIANTE"),
                new Categoria(1, "Robo", "Robos"), "Ingeniería y Ciencias Aplicadas");
        Notificacion n = new Notificacion("N1", "Aviso", "Incidente registrado", "@Todos", i);
        assertSame(i, n.getIncidente());
    }
}