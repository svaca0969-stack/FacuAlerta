package uce.edu.ec.dominio.modelo;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrabajadorTest {

    @Test
    public void testConstructorVacio() {
        Trabajador trabajador = new Trabajador();
        assertNotNull(trabajador);
        assertEquals(0, trabajador.getIdTrabajador());
        assertEquals("", trabajador.getDepartamento());
        assertEquals("", trabajador.getCargo());
        assertEquals("TRABAJADOR", trabajador.getTipoUsuario());
    }

    @Test
    public void testConstructorParametros() {
        Trabajador trabajador = new Trabajador(1, "Carlos", "carlos@uce.edu.ec", "hash123", 10, "Sistemas", "Tecnico");
        assertEquals(1, trabajador.getIdUsuario());
        assertEquals("Carlos", trabajador.getNombreUsuario());
        assertEquals("carlos@uce.edu.ec", trabajador.getCorreo());
        assertEquals("hash123", trabajador.getContrasenaHash());
        assertEquals(10, trabajador.getIdTrabajador());
        assertEquals("Sistemas", trabajador.getDepartamento());
        assertEquals("Tecnico", trabajador.getCargo());
        assertEquals("TRABAJADOR", trabajador.getTipoUsuario());
    }

    @Test
    public void testGettersSetters() {
        Trabajador trabajador = new Trabajador();
        trabajador.setIdTrabajador(20);
        trabajador.setDepartamento("Administracion");
        trabajador.setCargo("Coordinador");
        assertEquals(20, trabajador.getIdTrabajador());
        assertEquals("Administracion", trabajador.getDepartamento());
        assertEquals("Coordinador", trabajador.getCargo());
    }

    @Test
    public void testToString() {
        Trabajador trabajador = new Trabajador(1, "Carlos", "carlos@uce.edu.ec", "hash123", 10, "Sistemas", "Tecnico");
        String resultado = trabajador.toString();
        assertTrue(resultado.contains("Carlos"));
        assertTrue(resultado.contains("carlos@uce.edu.ec"));
        assertTrue(resultado.contains("10"));
        assertTrue(resultado.contains("Sistemas"));
        assertTrue(resultado.contains("Tecnico"));
        assertFalse(resultado.contains("hash123"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCargoInvalido() {
        new Trabajador().setCargo("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTipoUsuarioNoPuedeCambiar() {
        new Trabajador().setTipoUsuario("ESTUDIANTE");
    }
}