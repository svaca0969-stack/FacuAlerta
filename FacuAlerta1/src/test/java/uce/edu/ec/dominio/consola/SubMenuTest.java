package uce.edu.ec.dominio.consola;

import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.*;

public class SubMenuTest {

    private SubMenu crear(String entrada, ByteArrayOutputStream salida) {
        return new SubMenu(LectorTest.crear(entrada, salida));
    }

    @Test
    public void testModeradorYCuatroCategoriasPrecargadas() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        SubMenu sub = crear("moderador@uce.edu.ec\nFacu2026\n", salida);
        assertEquals(1, sub.getUsuarios().size());
        assertEquals(4, sub.getCategorias().size());
        assertEquals("Bajo", sub.getCategorias().get(0).getNombreCategoria());
        assertEquals("Medio", sub.getCategorias().get(1).getNombreCategoria());
        assertEquals("Alto", sub.getCategorias().get(2).getNombreCategoria());
        assertEquals("Critico", sub.getCategorias().get(3).getNombreCategoria());
        sub.iniciarSesion();
        assertEquals("MODERADOR", sub.getSesionActual().getTipoUsuario());
    }

    @Test
    public void testRegistroEInicioDeSesion() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        SubMenu sub = crear("1\nMaria\nmaria@uce.edu.ec\nclave123\n15\n5\n0\n"
                + "maria@uce.edu.ec\nclave123\n", salida);
        sub.menuUsuarios();
        sub.iniciarSesion();
        assertEquals(2, sub.getUsuarios().size());
        assertEquals("Maria", sub.getSesionActual().getNombreUsuario());
    }

    @Test
    public void testCorreoDuplicado() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        SubMenu sub = crear("2\nCarlos\ncarlos@uce.edu.ec\nclave123\nSistemas\nTecnico\n"
                + "2\nCarla\ncarlos@uce.edu.ec\nclave123\nSistemas\nTecnico\n0\n", salida);
        sub.menuUsuarios();
        assertEquals(2, sub.getUsuarios().size());
    }

    @Test
    public void testSoloModeradorPuedeCrearCategorias() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        SubMenu sub = crear("1\n0\n", salida);
        sub.menuCategorias();
        assertEquals(4, sub.getCategorias().size());
        assertTrue(salida.toString().contains("iniciar sesion"));
    }

    @Test
    public void testSinSesionNoPuedeReportar() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        SubMenu sub = crear("1\n0\n", salida);
        sub.menuIncidentes();
        assertTrue(sub.getIncidentes().isEmpty());
    }

    @Test
    public void testFlujoConRolesYPrivacidad() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        String entrada = "1\n2\n1\nMaria\nmaria@uce.edu.ec\nclave123\n15\n5\n"
                + "maria@uce.edu.ec\nclave123\n"
                + "1\n1\n3\n15\nRobo de mochila\nSe sustrajo una mochila\n"
                + "IMAGEN\nhttps://uce.edu.ec/foto.png\n0\n0\n"
                + "3\nmoderador@uce.edu.ec\nFacu2026\n"
                + "3\n1\nAviso\nIncidente registrado\n@Todos\n0\n0\n";
        Menu menu = new Menu(LectorTest.crear(entrada, salida));
        menu.ejecutar();
        SubMenu sub = menu.getSubMenu();
        assertEquals(1, sub.getIncidentes().size());
        assertEquals(1, sub.getIncidentes().get(0).getEvidencias().length);
        assertEquals(1, sub.getNotificaciones().size());
        assertEquals("@Todos", sub.getNotificaciones().get(0).getDestinatarioCorreo());
        assertFalse(salida.toString().contains("Usuario{idUsuario="));
    }
}