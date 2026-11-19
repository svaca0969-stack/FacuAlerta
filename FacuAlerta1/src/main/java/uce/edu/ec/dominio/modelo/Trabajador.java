package uce.edu.ec.dominio.modelo;

import java.util.Objects;
import uce.edu.ec.dominio.util.Validador;

public class Trabajador extends Usuario {
    private int idTrabajador;
    private String departamento;
    private String cargo;

    public Trabajador() {
        super();
        setTipoUsuario("TRABAJADOR");
        this.idTrabajador = 0;
        this.departamento = "";
        this.cargo = "";
    }

    public Trabajador(int idUsuario, String nombreUsuario, String correo, String contrasenaHash, int idTrabajador,
                      String departamento, String cargo) {
        this();
        setIdUsuario(idUsuario);
        setNombreUsuario(nombreUsuario);
        setCorreo(correo);
        setContrasenaHash(contrasenaHash);
        setIdTrabajador(idTrabajador);
        setDepartamento(departamento);
        setCargo(cargo);
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = Validador.exigirId(idTrabajador, "El id de trabajador");
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = Validador.exigirLetras(departamento, "El departamento");
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = Validador.exigirLetras(cargo, "El cargo");
    }

    // Validacion de fondo: el tipo de usuario de esta clase no puede cambiarse
    @Override
    public void setTipoUsuario(String tipoUsuario) {
        Validador.exigirDeLista(tipoUsuario, new String[]{"TRABAJADOR"}, "El tipo de usuario de Trabajador");
        super.setTipoUsuario(tipoUsuario);
    }

    @Override
    public String toString() {
        return super.toString() + " Trabajador{idTrabajador=" + idTrabajador + ", departamento='" + departamento
                + "', cargo='" + cargo + "'}";
    }
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (!(objeto instanceof Trabajador)) return false;
        Trabajador otro = (Trabajador) objeto;
        return getIdUsuario() == otro.getIdUsuario()
                && idTrabajador == otro.idTrabajador;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdUsuario(), idTrabajador);
    }

}