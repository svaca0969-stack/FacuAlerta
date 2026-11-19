package uce.edu.ec.dominio.modelo;

import org.junit.Test;

import static org.junit.Assert.*;

public class CategoriaTest {

    @Test
    public void testConstructorVacio() {
        Categoria categoria = new Categoria();
        assertEquals(0, categoria.getIdCategoria());
        assertEquals("", categoria.getNombreCategoria());
        assertEquals("", categoria.getDescripcion());
    }

    @Test
    public void testConstructorParametros() {
        Categoria categoria = new Categoria(1, "Acoso", "Situaciones de acoso dentro del campus");
        assertEquals(1, categoria.getIdCategoria());
        assertEquals("Acoso", categoria.getNombreCategoria());
        assertEquals("Situaciones de acoso dentro del campus", categoria.getDescripcion());
    }

    @Test
    public void testGettersSetters() {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(2);
        categoria.setNombreCategoria("Robo");
        categoria.setDescripcion("Robos y hurtos");
        assertEquals(2, categoria.getIdCategoria());
        assertEquals("Robo", categoria.getNombreCategoria());
        assertEquals("Robos y hurtos", categoria.getDescripcion());
    }

    @Test
    public void testToString() {
        Categoria categoria = new Categoria(1, "Acoso", "Situaciones de acoso");
        String resultado = categoria.toString();
        assertTrue(resultado.contains("1"));
        assertTrue(resultado.contains("Acoso"));
        assertTrue(resultado.contains("Situaciones de acoso"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNombreInvalido() {
        new Categoria().setNombreCategoria("A");
    }
}