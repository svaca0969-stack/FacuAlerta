package uce.edu.ec.dominio.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidadorTest {

    @Test
    public void testEnteros() {
        assertTrue(Validador.esEnteroPositivo("15"));
        assertFalse(Validador.esEnteroPositivo("0"));
        assertFalse(Validador.esEnteroPositivo("-3"));
        assertFalse(Validador.esEnteroPositivo("abc"));
        assertTrue(Validador.esEnteroEnRango("5", 1, 12));
        assertFalse(Validador.esEnteroEnRango("13", 1, 12));
    }

    @Test
    public void testLetras() {
        assertTrue(Validador.esLetras("Maria Jose"));
        assertTrue(Validador.esLetras("Nu\u00f1ez"));
        assertFalse(Validador.esLetras("Maria2"));
        assertFalse(Validador.esLetras("A"));
        assertFalse(Validador.esLetras(null));
    }

    @Test
    public void testTexto() {
        assertTrue(Validador.esTexto("Bloque 3, planta baja"));
        assertFalse(Validador.esTexto("ab"));
        assertFalse(Validador.esTexto("@@@"));
    }

    @Test
    public void testCorreo() {
        assertTrue(Validador.esCorreo("alguien@uce.edu.ec"));
        assertFalse(Validador.esCorreo("alguien@"));
        assertFalse(Validador.esCorreo("alguien@uce"));
        assertFalse(Validador.esCorreo("alguien uce.edu.ec"));
    }

    @Test
    public void testContrasenaFechaUrlCodigo() {
        assertTrue(Validador.esContrasena("clave123"));
        assertFalse(Validador.esContrasena("12 345"));
        assertTrue(Validador.esFechaHora("2026-11-15 17:09"));
        assertFalse(Validador.esFechaHora("2026-13-15 17:09"));
        assertFalse(Validador.esFechaHora("2026-11-15 25:00"));
        assertTrue(Validador.esUrlArchivo("https://uce.edu.ec/a.png"));
        assertTrue(Validador.esUrlArchivo("C:/evidencias/a.png"));
        assertFalse(Validador.esUrlArchivo("???"));
        assertTrue(Validador.esCodigo("N12"));
        assertFalse(Validador.esCodigo("N 12"));
    }

    @Test
    public void testListas() {
        assertTrue(Validador.esDeLista("alto", Validador.NIVELES_GRAVEDAD));
        assertFalse(Validador.esDeLista("extremo", Validador.NIVELES_GRAVEDAD));
        assertEquals("ALTO", Validador.exigirDeLista(" alto ", Validador.NIVELES_GRAVEDAD, "Nivel"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExigirIdInvalido() {
        Validador.exigirId(-1, "Id");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExigirNoNulo() {
        Validador.exigirNoNulo(null, "Valor");
    }
}