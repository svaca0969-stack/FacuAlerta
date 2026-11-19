package uce.edu.ec.dominio.modelo;

import org.junit.Test;

import static org.junit.Assert.*;

public class EstudianteTest {

    @Test
    public void testConstructorVacio() {
        Estudiante estudiante = new Estudiante();
        assertEquals(0, estudiante.getIdEstudiante());
        assertEquals("", estudiante.getFacultad());
        assertEquals(0, estudiante.getSemestre());
        assertEquals("ESTUDIANTE", estudiante.getTipoUsuario());
    }

    @Test
    public void testConstructorParametros() {
        Estudiante estudiante = new Estudiante(1, "Maria Lopez", "maria@uce.edu.ec", "hash123", 10, "Ingeniería y Ciencias Aplicadas", 5);
        assertEquals(1, estudiante.getIdUsuario());
        assertEquals("Maria Lopez", estudiante.getNombreUsuario());
        assertEquals("maria@uce.edu.ec", estudiante.getCorreo());
        assertEquals(10, estudiante.getIdEstudiante());
        assertEquals("Ingeniería y Ciencias Aplicadas", estudiante.getFacultad());
        assertEquals(5, estudiante.getSemestre());
        assertEquals("ESTUDIANTE", estudiante.getTipoUsuario());
    }

    @Test
    public void testGettersSetters() {
        Estudiante estudiante = new Estudiante();
        estudiante.setIdEstudiante(20);
        estudiante.setFacultad("Ciencias Médicas");
        estudiante.setSemestre(8);
        assertEquals(20, estudiante.getIdEstudiante());
        assertEquals("Ciencias Médicas", estudiante.getFacultad());
        assertEquals(8, estudiante.getSemestre());
    }

    @Test
    public void testToString() {
        Estudiante estudiante = new Estudiante(1, "Maria", "maria@uce.edu.ec", "hash123", 10, "Ingeniería y Ciencias Aplicadas", 5);
        String resultado = estudiante.toString();
        assertTrue(resultado.contains("10"));
        assertTrue(resultado.contains("Ingeniería y Ciencias Aplicadas"));
        assertTrue(resultado.contains("semestre=5"));
        assertFalse(resultado.contains("hash123"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSemestreFueraDeRango() {
        new Estudiante().setSemestre(13);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCarreraInvalida() {
        new Estudiante().setFacultad("Ingenieria");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTipoUsuarioNoPuedeCambiar() {
        new Estudiante().setTipoUsuario("MODERADOR");
    }
}