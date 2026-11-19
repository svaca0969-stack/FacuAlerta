package uce.edu.ec.dominio.consola;

import org.junit.Test;
import uce.edu.ec.dominio.modelo.Categoria;
import uce.edu.ec.dominio.modelo.Evidencia;
import uce.edu.ec.dominio.modelo.Incidente;
import uce.edu.ec.dominio.modelo.Usuario;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.*;

public class IngresoIncidenteTest {

    private IngresoIncidente crear(String entrada) {
        return new IngresoIncidente(LectorTest.crear(entrada, new ByteArrayOutputStream()));
    }

    @Test
    public void testIngresarCategoria() {
        Categoria c = crear("Robo\nRobos y hurtos\n").ingresarCategoria(1);
        assertEquals(1, c.getIdCategoria());
        assertEquals("Robo", c.getNombreCategoria());
        assertEquals("Robos y hurtos", c.getDescripcion());
    }

    @Test
    public void testIngresarEvidencia() {
        Evidencia e = crear("imagen\nhttps://uce.edu.ec/foto.jpg\n").ingresarEvidencia(1);
        assertEquals("IMAGEN", e.getTipoEvidencia());
        assertEquals("https://uce.edu.ec/foto.jpg", e.getUrlArchivo());
    }

    @Test
    public void testIngresarIncidente() {
        Usuario usuario = new Usuario(1, "Carlos", "carlos@uce.edu.ec", "clave123", "ESTUDIANTE");
        Categoria categoria = new Categoria(1, "Robo", "Robos y hurtos");
        String facultad = "Ingeniería y Ciencias Aplicadas";
        Incidente i = crear("Robo de mochila\nSe sustrajo una mochila\n")
                .ingresarIncidente(1, usuario, categoria, facultad, "ALTO");
        assertEquals(1, i.getIdIncidente());
        assertEquals("REPORTADO", i.getEstado());
        assertEquals("ALTO", i.getNivelGravedad());
        assertSame(usuario, i.getUsuarioReporta());
        assertSame(categoria, i.getCategoria());
        assertEquals(facultad, i.getFacultad());
        assertTrue(uce.edu.ec.dominio.util.Validador.esFechaHora(i.getFechaHoraReporte()));
    }
}