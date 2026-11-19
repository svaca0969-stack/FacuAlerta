package uce.edu.ec.dominio.consola;

import org.junit.Test;
import uce.edu.ec.dominio.modelo.Estudiante;
import uce.edu.ec.dominio.modelo.GestorDeRiesgo;
import uce.edu.ec.dominio.modelo.Moderador;
import uce.edu.ec.dominio.modelo.Trabajador;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.*;

public class IngresoUsuarioTest {

    private IngresoUsuario crear(String entrada) {
        return new IngresoUsuario(LectorTest.crear(entrada, new ByteArrayOutputStream()));
    }

    @Test
    public void testIngresarEstudiante() {
        Estudiante e = crear("Maria Lopez\nmaria@uce.edu.ec\nclave123\n15\n5\n").ingresarEstudiante(1);
        assertEquals(1, e.getIdUsuario());
        assertEquals("Maria Lopez", e.getNombreUsuario());
        assertEquals("Ingeniería y Ciencias Aplicadas", e.getFacultad());
        assertEquals(5, e.getSemestre());
        assertEquals("ESTUDIANTE", e.getTipoUsuario());
    }

    @Test
    public void testIngresarTrabajador() {
        Trabajador t = crear("Carlos\ncarlos@uce.edu.ec\nclave123\nSistemas\nTecnico\n").ingresarTrabajador(2);
        assertEquals(2, t.getIdTrabajador());
        assertEquals("Sistemas", t.getDepartamento());
        assertEquals("Tecnico", t.getCargo());
    }

    @Test
    public void testIngresarModerador() {
        Moderador m = crear("Luis\nluis@uce.edu.ec\nclave123\nRevisar incidentes\n").ingresarModerador(3);
        assertEquals(3, m.getIdModerador());
        assertEquals("Validar incidentes, cambiar estados y enviar notificaciones", m.getPermisos());
    }

    @Test
    public void testIngresarGestorDeRiesgo() {
        GestorDeRiesgo g = crear("Sofia\nsofia@uce.edu.ec\nclave123\nSeguridad\nDirectora\nMatriz basica\n")
                .ingresarGestorDeRiesgo(4);
        assertEquals(4, g.getIdGestorRiesgo());
        assertEquals("Directora", g.getCargoInstitucional());
        assertEquals("Matriz basica", g.getMatrizEvaluacion());
    }

    @Test
    public void testReintentaCuandoElDatoEsInvalido() {
        Estudiante e = crear("Ana99\nAna\nmal\nana@uce.edu.ec\n123\nclave123\n15\n20\n5\n").ingresarEstudiante(1);
        assertEquals("Ana", e.getNombreUsuario());
        assertEquals(5, e.getSemestre());
    }
}