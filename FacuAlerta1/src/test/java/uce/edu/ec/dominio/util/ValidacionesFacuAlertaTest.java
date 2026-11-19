package uce.edu.ec.dominio.util;

import org.junit.Test;
import uce.edu.ec.dominio.modelo.*;
import static org.junit.Assert.*;

public class ValidacionesFacuAlertaTest {
    @Test public void soloCorreoInstitucional() {
        assertTrue(Validador.esCorreo("estudiante@uce.edu.ec"));
        assertFalse(Validador.esCorreo("estudiante@gmail.com"));
        assertFalse(Validador.esCorreo("estudiante@GMAIL.COM"));
        assertFalse(Validador.esCorreo("estudiante@gmail.com.uce.edu.ec"));
        assertFalse(Validador.esCorreo("estudiante@uce.edu.ec.gmail.com"));
    }
    @Test public void soloFacultadNoCarrera() {
        assertTrue(Validador.esFacultad("Ingeniería y Ciencias Aplicadas"));
        assertFalse(Validador.esFacultad("Computacion"));
        assertFalse(Validador.esFacultad("Facultad 123"));
    }
    @Test public void contrasenaConLetrasYNumeros() {
        assertTrue(Validador.esContrasena("clave123"));
        assertFalse(Validador.esContrasena("sóloLetras"));
        assertFalse(Validador.esContrasena("123456789"));
        assertFalse(Validador.esContrasena("clave 123"));
    }
    @Test public void evidenciaSoloImagenOVideo() {
        assertTrue(Validador.esEvidencia("https://uce.edu.ec/foto.png", "IMAGEN"));
        assertTrue(Validador.esEvidencia("https://uce.edu.ec/video.mp4", "VIDEO"));
        assertFalse(Validador.esEvidencia("https://uce.edu.ec/audio.mp3", "AUDIO"));
        assertFalse(Validador.esEvidencia("https://uce.edu.ec/documento.pdf", "IMAGEN"));
        assertFalse(Validador.esEvidencia("https://uce.edu.ec/video.mp4", "IMAGEN"));
    }
    @Test public void destinatariosYTexto() {
        assertTrue(Validador.esDestinatarios("@Todos"));
        assertTrue(Validador.esDestinatarios("uno@uce.edu.ec, dos@uce.edu.ec"));
        assertFalse(Validador.esDestinatarios("@Todos,uno@uce.edu.ec"));
        assertFalse(Validador.esDestinatarios("uno@gmail.com"));
        assertFalse(Validador.esDestinatarios("   "));
        assertFalse(Validador.esTextoSinNumeros("Texto 2"));
        assertTrue(Validador.esTextoSinNumeros("Aviso general"));
        assertFalse(Validador.esTitulo("    "));
        assertFalse(Validador.esTitulo("12345"));
        assertTrue(Validador.esTitulo("Reporte 2"));
    }
    @Test public void estadosExactos() {
        assertTrue(Validador.esDeLista("revision", Validador.ESTADOS_INCIDENTE));
        assertFalse(Validador.esDeLista("en_atencion", Validador.ESTADOS_INCIDENTE));
        assertFalse(Validador.esDeLista("123", Validador.NIVELES_GRAVEDAD));
    }
}