package uce.edu.ec.dominio.modelo;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class RepositorioCRUDTest {

    private Categoria cat(int id, String nombre) {
        return new Categoria(id, nombre, "Descripcion de " + nombre);
    }

    @Test
    public void testCrearYConsultar() {
        RepositorioCRUD<Categoria> repo = new RepositorioCRUD<>();
        assertTrue(repo.crear(cat(1, "Bajo")));
        assertTrue(repo.crear(cat(2, "Alto")));
        assertEquals(2, repo.cantidad());
        assertEquals(2, repo.consultar().size());
        assertEquals("Alto", repo.consultar(1).getNombreCategoria());
        assertEquals("Bajo", repo.consultar(cat(1, "Otro")).getNombreCategoria()); // por referencia (equals por id)
        assertNull(repo.consultar(cat(9, "Nada")));
    }

    @Test
    public void testValidacionDeDuplicados() {
        RepositorioCRUD<Categoria> repo = new RepositorioCRUD<>();
        assertTrue(repo.crear(cat(1, "Bajo")));
        assertFalse(repo.crear(cat(1, "Repetida")));
        assertEquals(1, repo.cantidad());
        assertTrue(repo.existe(cat(1, "Xxx")));
    }

    @Test
    public void testConstructorConListaInicialIgnoraDuplicados() {
        RepositorioCRUD<Categoria> repo = new RepositorioCRUD<>(Arrays.asList(cat(1, "Aaa"), cat(1, "Bbb"), cat(2, "Ccc")));
        assertEquals(2, repo.cantidad());
    }

    @Test
    public void testEditarPorIndiceYPorReferencia() {
        RepositorioCRUD<Categoria> repo = new RepositorioCRUD<>();
        repo.crear(cat(1, "Bajo"));
        repo.crear(cat(2, "Alto"));
        assertTrue(repo.editar(0, cat(1, "Leve")));
        assertEquals("Leve", repo.consultar(0).getNombreCategoria());
        assertTrue(repo.editar(cat(2, "Zzz"), cat(2, "Grave")));
        assertEquals("Grave", repo.consultar(1).getNombreCategoria());
        assertFalse(repo.editar(0, cat(2, "Choca")));          // duplicaria el id 2
        assertFalse(repo.editar(cat(7, "No existe"), cat(7, "Yyy")));
    }

    @Test
    public void testEliminarPorIndiceYPorReferencia() {
        RepositorioCRUD<Categoria> repo = new RepositorioCRUD<>();
        repo.crear(cat(1, "Bajo"));
        repo.crear(cat(2, "Alto"));
        repo.crear(cat(3, "Medio"));
        assertTrue(repo.eliminar(0));
        assertTrue(repo.eliminar(cat(3, "Medio")));
        assertFalse(repo.eliminar(cat(9, "No")));
        assertFalse(repo.eliminar(5));
        assertEquals(1, repo.cantidad());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCrearNuloLanzaExcepcion() {
        new RepositorioCRUD<Categoria>().crear(null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testConsultarIndiceInvalido() {
        new RepositorioCRUD<Categoria>().consultar(0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testConsultarDevuelveListaNoModificable() {
        new RepositorioCRUD<Categoria>().consultar().add(cat(1, "Aaa"));
    }
}