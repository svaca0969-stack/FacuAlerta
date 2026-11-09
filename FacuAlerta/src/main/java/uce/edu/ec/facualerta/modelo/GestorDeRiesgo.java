package uce.edu.ec.facualerta.modelo;

public class GestorDeRiesgo extends Usuario {
    private int idGestorRiesgo;
    private String departamento;
    private String cargoInstitucional;
    private String matrizEvaluacion;

    public GestorDeRiesgo() {
        super();
    }

    public GestorDeRiesgo(int idUsuario, String nombreUsuario, String correo, String contrasenaHash, int idGestorRiesgo, String departamento, String cargoInstitucional, String matrizEvaluacion) { 
        super(idUsuario, nombreUsuario, correo, contrasenaHash, "GESTOR_RIESGO"); 
        this.idGestorRiesgo = idGestorRiesgo; 
        this.departamento = departamento; 
        this.cargoInstitucional = cargoInstitucional; 
        this.matrizEvaluacion = matrizEvaluacion; 
    }

    public int getIdGestorRiesgo() {
        return idGestorRiesgo;
    }

    public void setIdGestorRiesgo(int idGestorRiesgo) {
        this.idGestorRiesgo = idGestorRiesgo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCargoInstitucional() {
        return cargoInstitucional;
    }

    public void setCargoInstitucional(String cargoInstitucional) {
        this.cargoInstitucional = cargoInstitucional;
    }

    public String getMatrizEvaluacion() {
        return matrizEvaluacion;
    }

    public void setMatrizEvaluacion(String matrizEvaluacion) {
        this.matrizEvaluacion = matrizEvaluacion;
    }

    @Override public String toString() { return super.toString() + " GestorDeRiesgo{" + "idGestorRiesgo=" + idGestorRiesgo + ", matriz='" + matrizEvaluacion + '\'' + '}'; }
}
