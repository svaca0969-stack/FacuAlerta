package uce.edu.ec.dominio.consola;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.*;

public class LectorTest {
    public static Lector crear(String entrada, ByteArrayOutputStream salida) {
        return new Lector(new Scanner(entrada), new PrintStream(salida));
    }

    @Test
    public void testLeerOpcionRepiteHastaSerValida() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        Lector lector = crear("x\n9\n2\n", salida);
        assertEquals(2, lector.leerOpcion("Opcion", 0, 4));
        assertTrue(salida.toString().contains("Dato invalido"));
    }

    @Test
    public void testLeerLetrasYCorreo() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        Lector lector = crear("Ana1\nAna\nmal\nana@uce.edu.ec\n", salida);
        assertEquals("Ana", lector.leerLetras("Nombre"));
        assertEquals("ana@uce.edu.ec", lector.leerCorreo("Correo"));
    }

    @Test
    public void testFacultadYLista() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        Lector lector = crear("100\n15\nextremo\nalto\n", salida);
        assertEquals("Ingeniería y Ciencias Aplicadas", lector.leerFacultad("Facultad"));
        assertEquals("ALTO", lector.leerDeLista("Gravedad", new String[]{"BAJO", "ALTO"}));
    }
}