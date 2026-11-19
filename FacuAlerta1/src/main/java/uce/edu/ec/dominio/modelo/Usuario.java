package uce.edu.ec.dominio.modelo;

import java.util.Objects;
import uce.edu.ec.dominio.util.Validador;

import java.io.Serializable;

public class Usuario implements Serializable {
    protected int idUsuario;
    protected String nombreUsuario;
    protected String correo;
    protected String contrasenaHash;
    protected String tipoUsuario;

    public Usuario() {
        this.idUsuario = 0;
        this.nombreUsuario = "";
        this.correo = "";
        this.contrasenaHash = "";
        this.tipoUsuario = "";
    }

    public Usuario(int idUsuario, String nombreUsuario, String correo, String contrasenaHash, String tipoUsuario) {
        this();
        setIdUsuario(idUsuario);
        setNombreUsuario(nombreUsuario);
        setCorreo(correo);
        setContrasenaHash(contrasenaHash);
        setTipoUsuario(tipoUsuario);
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = Validador.exigirId(idUsuario, "El id de usuario");
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = Validador.exigirLetras(nombreUsuario, "El nombre de usuario");
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = Validador.exigirCorreo(correo, "El correo");
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = Validador.exigirContrasena(contrasenaHash, "La contrasena");
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = Validador.exigirDeLista(tipoUsuario, Validador.TIPOS_USUARIO, "El tipo de usuario");
    }

    @Override
    public String toString() {
        return "Usuario{idUsuario=" + idUsuario + ", nombreUsuario='" + nombreUsuario + "', correo='" + correo
                + "', tipoUsuario='" + tipoUsuario + "'}";
    }
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (!(objeto instanceof Usuario)) return false;
        Usuario otro = (Usuario) objeto;
        return idUsuario == otro.idUsuario;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario);
    }

}