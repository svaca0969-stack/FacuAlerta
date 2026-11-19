package uce.edu.ec.dominio.modelo;

import org.junit.Test;

import static org.junit.Assert.*;

public class UsuarioTest {

    @Test
    public void testConstructorVacio() {
        Usuario usuario = new Usuario();
        assertEquals(0, usuario.getIdUsuario());
        assertEquals("", usuario.getNombreUsuario());
        assertEquals("", usuario.getCorreo());
        assertEquals("", usuario.getContrasenaHash());
        assertEquals("", usuario.getTipoUsuario());
    }

    @Test
    public void testConstructorParametros() {
        Usuario usuario = new Usuario(1, "Carlos Perez", "carlos@uce.edu.ec", "hash123", "TRABAJADOR");
        assertEquals(1, usuario.getIdUsuario());
        assertEquals("Carlos Perez", usuario.getNombreUsuario());
        assertEquals("carlos@uce.edu.ec", usuario.getCorreo());
        assertEquals("hash123", usuario.getContrasenaHash());
        assertEquals("TRABAJADOR", usuario.getTipoUsuario());
    }

    @Test
    public void testGettersSetters() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(5);
        usuario.setNombreUsuario("Ana");
        usuario.setCorreo("ana@uce.edu.ec");
        usuario.setContrasenaHash("clave99");
        usuario.setTipoUsuario("estudiante");
        assertEquals(5, usuario.getIdUsuario());
        assertEquals("Ana", usuario.getNombreUsuario());
        assertEquals("ana@uce.edu.ec", usuario.getCorreo());
        assertEquals("clave99", usuario.getContrasenaHash());
        assertEquals("ESTUDIANTE", usuario.getTipoUsuario());
    }

    @Test
    public void testToString() {
        Usuario usuario = new Usuario(1, "Carlos", "carlos@uce.edu.ec", "hash123", "TRABAJADOR");
        String resultado = usuario.toString();
        assertTrue(resultado.contains("Carlos"));
        assertTrue(resultado.contains("carlos@uce.edu.ec"));
        assertFalse(resultado.contains("hash123"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIdInvalido() {
        new Usuario().setIdUsuario(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNombreInvalido() {
        new Usuario().setNombreUsuario("Carlos123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCorreoInvalido() {
        new Usuario().setCorreo("correo-sin-arroba");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testContrasenaCorta() {
        new Usuario().setContrasenaHash("123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTipoInvalido() {
        new Usuario().setTipoUsuario("ADMIN");
    }
}