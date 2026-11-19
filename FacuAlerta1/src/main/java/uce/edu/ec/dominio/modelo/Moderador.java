package uce.edu.ec.dominio.modelo;

import java.util.Objects;
import uce.edu.ec.dominio.util.Validador;

public class Moderador extends Usuario {
    private int idModerador;
    private String permisos;

    public Moderador() {
        super();
        setTipoUsuario("MODERADOR");
        this.idModerador = 0;
        this.permisos = "";
    }

    public Moderador(int idUsuario, String nombreUsuario, String correo, String contrasenaHash, int idModerador,
                     String permisos) {
        this();
        setIdUsuario(idUsuario);
        setNombreUsuario(nombreUsuario);
        setCorreo(correo);
        setContrasenaHash(contrasenaHash);
        setIdModerador(idModerador);
        setPermisos(permisos);
    }

    public int getIdModerador() {
        return idModerador;
    }

    public void setIdModerador(int idModerador) {
        this.idModerador = Validador.exigirId(idModerador, "El id de moderador");
    }

    public String getPermisos() {
        return permisos;
    }

    public void setPermisos(String permisos) {
        this.permisos = Validador.exigirTexto(permisos, "Los permisos");
    }

    // Validacion de fondo: el tipo de usuario de esta clase no puede cambiarse
    @Override
    public void setTipoUsuario(String tipoUsuario) {
        Validador.exigirDeLista(tipoUsuario, new String[]{"MODERADOR"}, "El tipo de usuario de Moderador");
        super.setTipoUsuario(tipoUsuario);
    }

    @Override
    public String toString() {
        return super.toString() + " Moderador{idModerador=" + idModerador + ", permisos='" + permisos + "'}";
    }
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (!(objeto instanceof Moderador)) return false;
        Moderador otro = (Moderador) objeto;
        return getIdUsuario() == otro.getIdUsuario()
                && idModerador == otro.idModerador;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdUsuario(), idModerador);
    }

}