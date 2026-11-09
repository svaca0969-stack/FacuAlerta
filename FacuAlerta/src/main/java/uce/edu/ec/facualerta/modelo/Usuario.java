package uce.edu.ec.facualerta.modelo;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int idUsuario;
    private String nombreUsuario;
    private String correo;
    private String contrasenaHash;
    private String tipoUsuario;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombreUsuario, String correo, String contrasenaHash, String tipoUsuario) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.contrasenaHash = contrasenaHash;
        this.tipoUsuario = tipoUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
}

@Override public String toString() { return "Usuario{" + "idUsuario=" + idUsuario + ", nombreUsuario='" + nombreUsuario + '\'' + ", correo='" + correo + '\'' + '}'; 
                                   } 
}
