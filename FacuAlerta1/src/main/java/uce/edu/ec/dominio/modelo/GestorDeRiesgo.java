package uce.edu.ec.dominio.modelo;

import java.util.Objects;
import uce.edu.ec.dominio.util.Validador;

public class GestorDeRiesgo extends Usuario {
    private int idGestorRiesgo;
    private String departamento;
    private String cargoInstitucional;
    private String matrizEvaluacion;

    public GestorDeRiesgo() {
        super();
        setTipoUsuario("GESTOR_RIESGO");
        this.idGestorRiesgo = 0;
        this.departamento = "";
        this.cargoInstitucional = "";
        this.matrizEvaluacion = "";
    }

    public GestorDeRiesgo(int idUsuario, String nombreUsuario, String correo, String contrasenaHash,
                          int idGestorRiesgo, String departamento, String cargoInstitucional, String matrizEvaluacion) {
        this();
        setIdUsuario(idUsuario);
        setNombreUsuario(nombreUsuario);
        setCorreo(correo);
        setContrasenaHash(contrasenaHash);
        setIdGestorRiesgo(idGestorRiesgo);
        setDepartamento(departamento);
        setCargoInstitucional(cargoInstitucional);
        setMatrizEvaluacion(matrizEvaluacion);
    }

    public int getIdGestorRiesgo() {
        return idGestorRiesgo;
    }

    public void setIdGestorRiesgo(int idGestorRiesgo) {
        this.idGestorRiesgo = Validador.exigirId(idGestorRiesgo, "El id de gestor de riesgo");
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = Validador.exigirLetras(departamento, "El departamento");
    }

    public String getCargoInstitucional() {
        return cargoInstitucional;
    }

    public void setCargoInstitucional(String cargoInstitucional) {
        this.cargoInstitucional = Validador.exigirLetras(cargoInstitucional, "El cargo institucional");
    }

    public String getMatrizEvaluacion() {
        return matrizEvaluacion;
    }

    public void setMatrizEvaluacion(String matrizEvaluacion) {
        this.matrizEvaluacion = Validador.exigirTexto(matrizEvaluacion, "La matriz de evaluacion");
    }

    // Validacion de fondo: el tipo de usuario de esta clase no puede cambiarse
    @Override
    public void setTipoUsuario(String tipoUsuario) {
        Validador.exigirDeLista(tipoUsuario, new String[]{"GESTOR_RIESGO"}, "El tipo de usuario de GestorDeRiesgo");
        super.setTipoUsuario(tipoUsuario);
    }

    @Override
    public String toString() {
        return super.toString() + " GestorDeRiesgo{idGestorRiesgo=" + idGestorRiesgo + ", departamento='"
                + departamento + "', cargoInstitucional='" + cargoInstitucional + "', matrizEvaluacion='"
                + matrizEvaluacion + "'}";
    }
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (!(objeto instanceof GestorDeRiesgo)) return false;
        GestorDeRiesgo otro = (GestorDeRiesgo) objeto;
        return getIdUsuario() == otro.getIdUsuario()
                && idGestorRiesgo == otro.idGestorRiesgo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdUsuario(), idGestorRiesgo);
    }

}