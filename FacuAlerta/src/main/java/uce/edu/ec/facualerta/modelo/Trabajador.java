package uce.edu.ec.dominio.modelo;

public class Trabajador extends Usuario {
    private int idTrabajador;
    private String departamento;
    private String cargo;

    public Trabajador() {
        super();
    }

    public Trabajador(int idUsuario, String nombreUsuario, String correo, String contrasenaHash, int idTrabajador,
            String departamento, String cargo) {
        super(idUsuario, nombreUsuario, correo, contrasenaHash, "TRABAJADOR");
        this.idTrabajador = idTrabajador;
        this.departamento = departamento;
        this.cargo = cargo;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override public String toString() { return super.toString() + " Trabajador{" + "idTrabajador=" + idTrabajador + ", cargo='" + cargo + '\'' + '}'; }
}
