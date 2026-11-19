package uce.edu.ec.dominio.modelo;

import org.junit.Test;

import static org.junit.Assert.*;

public class EvidenciaTest {

    @Test
    public void testConstructorVacio() {
        Evidencia evidencia = new Evidencia();
        assertEquals(0, evidencia.getIdEvidencia());
        assertEquals("", evidencia.getTipoEvidencia());
        assertEquals("", evidencia.getUrlArchivo());
    }

    @Test
    public void testConstructorParametros() {
        Evidencia evidencia = new Evidencia(1, "IMAGEN", "https://uce.edu.ec/evidencias/foto1.jpg");
        assertEquals(1, evidencia.getIdEvidencia());
        assertEquals("IMAGEN", evidencia.getTipoEvidencia());
        assertEquals("https://uce.edu.ec/evidencias/foto1.jpg", evidencia.getUrlArchivo());
    }

    @Test
    public void testGettersSetters() {
        Evidencia evidencia = new Evidencia();
        evidencia.setIdEvidencia(3);
        evidencia.setTipoEvidencia("video");
        evidencia.setUrlArchivo("C:/evidencias/video1.mp4");
        assertEquals(3, evidencia.getIdEvidencia());
        assertEquals("VIDEO", evidencia.getTipoEvidencia());
        assertEquals("C:/evidencias/video1.mp4", evidencia.getUrlArchivo());
    }

    @Test
    public void testToString() {
        Evidencia evidencia = new Evidencia(1, "IMAGEN", "https://uce.edu.ec/a.png");
        String resultado = evidencia.toString();
        assertTrue(resultado.contains("1"));
        assertTrue(resultado.contains("IMAGEN"));
        assertTrue(resultado.contains("https://uce.edu.ec/a.png"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTipoInvalido() {
        new Evidencia().setTipoEvidencia("TEXTO");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUrlInvalida() {
        new Evidencia().setUrlArchivo("???");
    }
}