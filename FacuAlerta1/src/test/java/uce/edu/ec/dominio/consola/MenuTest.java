package uce.edu.ec.dominio.consola;

import org.junit.Test;
import java.io.ByteArrayOutputStream;
import static org.junit.Assert.*;

public class MenuTest {
    @Test
    public void testSalirDirectamente() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        Menu menu = new Menu(LectorTest.crear("0\n", salida));
        menu.ejecutar();
        assertTrue(salida.toString().contains("FACUALERTA"));
        assertTrue(salida.toString().contains("Hasta pronto"));
    }

    @Test
    public void testRolModeradorYSubmenusAgrupados() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        Menu menu = new Menu(LectorTest.crear("3\nmoderador@uce.edu.ec\nFacu2026\n"
                + "1\n0\n2\n0\n5\n0\n0\n0\n", salida));
        menu.ejecutar();
        String texto = salida.toString();
        assertTrue(texto.contains("GESTIONAR USUARIOS"));
        assertTrue(texto.contains("GESTIONAR INCIDENTES"));
        assertTrue(texto.contains("GESTIONAR RECOMENDACIONES"));
        assertTrue(texto.contains("Sesion cerrada"));
        assertNotNull(menu.getSubMenu());
    }

    @Test
    public void testUsuarioEligeCategoriaSoloUnaVezYGravedadEsAutomatica() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        String entrada = "1\n2\n1\nAna\nana@uce.edu.ec\nclave123\n15\n5\n"
                + "ana@uce.edu.ec\nclave123\n1\n1\n3\n15\n"
                + "Robo de mochila\nSe sustrajo una mochila\nIMAGEN\nhttps://uce.edu.ec/foto.png\n"
                + "0\n0\n0\n";
        Menu menu = new Menu(LectorTest.crear(entrada, salida));
        menu.ejecutar();
        assertEquals(1, menu.getSubMenu().getIncidentes().size());
        assertEquals("ALTO", menu.getSubMenu().getIncidentes().get(0).getNivelGravedad());
        assertEquals(1, salida.toString().split("Id de la categoria", -1).length - 1);
        assertFalse(salida.toString().contains("Nivel de gravedad ("));
    }
}