package uce.edu.ec.dominio.modelo;

import org.junit.Test;

import static org.junit.Assert.*;

public class ModeradorTest {

    @Test
    public void testConstructorVacio() {
        Moderador moderador = new Moderador();
        assertEquals(0, moderador.getIdModerador());
        assertEquals("", moderador.getPermisos());
        assertEquals("MODERADOR", moderador.getTipoUsuario());
    }

    @Test
    public void testConstructorParametros() {
        Moderador moderador = new Moderador(2, "Luis Mora", "luis@uce.edu.ec", "hash123", 7, "Revisar y cerrar incidentes");
        assertEquals(2, moderador.getIdUsuario());
        assertEquals("Luis Mora", moderador.getNombreUsuario());
        assertEquals(7, moderador.getIdModerador());
        assertEquals("Revisar y cerrar incidentes", moderador.getPermisos());
        assertEquals("MODERADOR", moderador.getTipoUsuario());
    }

    @Test
    public void testGettersSetters() {
        Moderador moderador = new Moderador();
        moderador.setIdModerador(9);
        moderador.setPermisos("Solo lectura");
        assertEquals(9, moderador.getIdModerador());
        assertEquals("Solo lectura", moderador.getPermisos());
    }

    @Test
    public void testToString() {
        Moderador moderador = new Moderador(2, "Luis", "luis@uce.edu.ec", "hash123", 7, "Solo lectura");
        String resultado = moderador.toString();
        assertTrue(resultado.contains("Luis"));
        assertTrue(resultado.contains("7"));
        assertTrue(resultado.contains("Solo lectura"));
        assertFalse(resultado.contains("hash123"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPermisosInvalidos() {
        new Moderador().setPermisos("##");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTipoUsuarioNoPuedeCambiar() {
        new Moderador().setTipoUsuario("TRABAJADOR");
    }
}