package uce.edu.ec.dominio.modelo;

import org.junit.Test;

import static org.junit.Assert.*;

public class GestorDeRiesgoTest {

    @Test
    public void testConstructorVacio() {
        GestorDeRiesgo gestor = new GestorDeRiesgo();
        assertEquals(0, gestor.getIdGestorRiesgo());
        assertEquals("", gestor.getDepartamento());
        assertEquals("", gestor.getCargoInstitucional());
        assertEquals("", gestor.getMatrizEvaluacion());
        assertEquals("GESTOR_RIESGO", gestor.getTipoUsuario());
    }

    @Test
    public void testConstructorParametros() {
        GestorDeRiesgo gestor = new GestorDeRiesgo(3, "Sofia Vega", "sofia@uce.edu.ec", "hash123", 4,
                "Seguridad", "Directora", "Matriz 5x5 de probabilidad e impacto");
        assertEquals(3, gestor.getIdUsuario());
        assertEquals(4, gestor.getIdGestorRiesgo());
        assertEquals("Seguridad", gestor.getDepartamento());
        assertEquals("Directora", gestor.getCargoInstitucional());
        assertEquals("Matriz 5x5 de probabilidad e impacto", gestor.getMatrizEvaluacion());
        assertEquals("GESTOR_RIESGO", gestor.getTipoUsuario());
    }

    @Test
    public void testGettersSetters() {
        GestorDeRiesgo gestor = new GestorDeRiesgo();
        gestor.setIdGestorRiesgo(8);
        gestor.setDepartamento("Salud");
        gestor.setCargoInstitucional("Coordinador");
        gestor.setMatrizEvaluacion("Matriz basica");
        assertEquals(8, gestor.getIdGestorRiesgo());
        assertEquals("Salud", gestor.getDepartamento());
        assertEquals("Coordinador", gestor.getCargoInstitucional());
        assertEquals("Matriz basica", gestor.getMatrizEvaluacion());
    }

    @Test
    public void testToString() {
        GestorDeRiesgo gestor = new GestorDeRiesgo(3, "Sofia", "sofia@uce.edu.ec", "hash123", 4,
                "Seguridad", "Directora", "Matriz basica");
        String resultado = gestor.toString();
        assertTrue(resultado.contains("Seguridad"));
        assertTrue(resultado.contains("Directora"));
        assertTrue(resultado.contains("Matriz basica"));
        assertFalse(resultado.contains("hash123"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDepartamentoInvalido() {
        new GestorDeRiesgo().setDepartamento("D3pto");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTipoUsuarioNoPuedeCambiar() {
        new GestorDeRiesgo().setTipoUsuario("ESTUDIANTE");
    }
}