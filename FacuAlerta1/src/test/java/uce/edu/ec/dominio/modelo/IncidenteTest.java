package uce.edu.ec.dominio.modelo;

import org.junit.Test;

import static org.junit.Assert.*;

public class IncidenteTest {

    private Incidente crearIncidente() {
        Usuario usuario = new Usuario(1, "Carlos", "carlos@uce.edu.ec", "hash123", "ESTUDIANTE");
        Categoria categoria = new Categoria(1, "Robo", "Robos y hurtos");
        String facultad = "Ingeniería y Ciencias Aplicadas";
        return new Incidente(1, "2026-11-10 08:30", "2026-11-10 09:00", "REPORTADO",
                "Robo de mochila", "Se sustrajo una mochila en el aula", "ALTO", usuario, categoria, facultad);
    }

    @Test
    public void testConstructorVacio() {
        Incidente incidente = new Incidente();
        assertEquals(0, incidente.getIdIncidente());
        assertEquals("", incidente.getFechaHoraReporte());
        assertEquals("", incidente.getFechaHoraActualizacion());
        assertEquals("", incidente.getEstado());
        assertEquals("", incidente.getTitulo());
        assertEquals("", incidente.getDescripcion());
        assertEquals("", incidente.getNivelGravedad());
        assertNotNull(incidente.getUsuarioReporta());
        assertNotNull(incidente.getCategoria());
        assertNotNull(incidente.getFacultad());
        assertEquals(0, incidente.getEvidencias().length);
    }

    @Test
    public void testConstructorParametros() {
        Incidente incidente = crearIncidente();
        assertEquals(1, incidente.getIdIncidente());
        assertEquals("2026-11-10 08:30", incidente.getFechaHoraReporte());
        assertEquals("2026-11-10 09:00", incidente.getFechaHoraActualizacion());
        assertEquals("REPORTADO", incidente.getEstado());
        assertEquals("Robo de mochila", incidente.getTitulo());
        assertEquals("ALTO", incidente.getNivelGravedad());
        assertEquals("Carlos", incidente.getUsuarioReporta().getNombreUsuario());
        assertEquals("Robo", incidente.getCategoria().getNombreCategoria());
        assertEquals("Ingeniería y Ciencias Aplicadas", incidente.getFacultad());
    }

    @Test
    public void testGettersSetters() {
        Incidente incidente = new Incidente();
        incidente.setIdIncidente(9);
        incidente.setFechaHoraReporte("2026-01-02 10:15");
        incidente.setFechaHoraActualizacion("2026-01-02 11:00");
        incidente.setEstado("revision");
        incidente.setTitulo("Pelea en pasillo");
        incidente.setDescripcion("Pelea entre estudiantes");
        incidente.setNivelGravedad("medio");
        Evidencia evidencia = new Evidencia(1, "IMAGEN", "https://uce.edu.ec/f.jpg");
        incidente.agregarEvidencia(evidencia);
        Categoria categoria = new Categoria(2, "Pelea", "Peleas entre estudiantes");
        String facultad = "Ciencias Médicas";
        incidente.setCategoria(categoria);
        incidente.setFacultad(facultad);
        assertEquals(9, incidente.getIdIncidente());
        assertEquals("2026-01-02 10:15", incidente.getFechaHoraReporte());
        assertEquals("REVISION", incidente.getEstado());
        assertEquals("MEDIO", incidente.getNivelGravedad());
        assertEquals(1, incidente.getEvidencias().length);
        assertSame(evidencia, incidente.getEvidencias()[0]);
        assertSame(categoria, incidente.getCategoria());
        assertEquals(facultad, incidente.getFacultad());

        // setEvidencias reemplaza el arreglo completo (relacion uno a muchos)
        Evidencia[] nuevas = {
                new Evidencia(2, "VIDEO", "https://uce.edu.ec/v.mp4"),
                new Evidencia(3, "IMAGEN", "https://uce.edu.ec/a.png")};
        incidente.setEvidencias(nuevas);
        assertEquals(2, incidente.getEvidencias().length);
        assertEquals("VIDEO", incidente.getEvidencias()[0].getTipoEvidencia());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCategoriaNula() {
        new Incidente().setCategoria(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFacultadNula() {
        new Incidente().setFacultad(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEvidenciasNulas() {
        new Incidente().setEvidencias(null);
    }

    @Test
    public void testToString() {
        Incidente incidente = crearIncidente();
        String resultado = incidente.toString();
        assertTrue(resultado.contains("Robo de mochila"));
        assertTrue(resultado.contains("REPORTADO"));
        assertTrue(resultado.contains("ALTO"));
        assertTrue(resultado.contains("Ingeniería y Ciencias Aplicadas"));
        assertTrue(resultado.contains("totalEvidencias=0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFechaInvalida() {
        new Incidente().setFechaHoraReporte("10/11/2026");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEstadoInvalido() {
        new Incidente().setEstado("PERDIDO");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUsuarioNulo() {
        new Incidente().setUsuarioReporta(null);
    }

    @Test
    public void testArregloEvidenciasEsCopia() {
        Incidente incidente = new Incidente();
        incidente.agregarEvidencia(new Evidencia(1, "IMAGEN", "https://uce.edu.ec/f.jpg"));
        incidente.getEvidencias()[0] = null;
        assertNotNull(incidente.getEvidencias()[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEvidenciaDuplicada() {
        Incidente incidente = new Incidente();
        incidente.agregarEvidencia(new Evidencia(1, "IMAGEN", "https://uce.edu.ec/f.jpg"));
        incidente.agregarEvidencia(new Evidencia(1, "VIDEO", "https://uce.edu.ec/v.mp4"));
    }

    @Test
    public void testSobrecargaAgregarBuscarEliminarEvidencia() {
        Incidente incidente = new Incidente();
        Evidencia e = new Evidencia(5, "IMAGEN", "https://uce.edu.ec/f.jpg");
        Object referencia = e;
        incidente.agregarEvidencia(referencia);            // agregarEvidencia(Object)
        assertEquals(1, incidente.getTotalEvidencias());
        assertSame(e, incidente.buscarEvidencia(e));        // por referencia
        assertSame(e, incidente.buscarEvidencia(5));        // por id
        assertTrue(incidente.eliminarEvidencia(e));
        assertEquals(0, incidente.getTotalEvidencias());
        assertFalse(incidente.eliminarEvidencia(e));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarObjetoQueNoEsEvidencia() {
        new Incidente().agregarEvidencia((Object) "texto");
    }

    @Test
    public void testEqualsYHashCode() {
        Incidente a = crearIncidente();
        Incidente b = crearIncidente();
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, new Incidente());
        assertNotEquals(a, "texto");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFechaInexistente() {
        new Incidente().setFechaHoraReporte("2026-02-31 10:00");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFechaFutura() {
        new Incidente().setFechaHoraReporte("2999-01-01 10:00");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testActualizacionAnteriorAlReporte() {
        Incidente incidente = new Incidente();
        incidente.setFechaHoraReporte("2026-01-02 10:00");
        incidente.setFechaHoraActualizacion("2026-01-02 09:00");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncidenteResueltoNoCambiaDeEstado() {
        Incidente incidente = new Incidente();
        incidente.setEstado("RESUELTO");
        incidente.setEstado("REPORTADO");
    }
}