package uce.edu.ec.facualerta.modelo;

public class Estudiante extends Usuario {
    private int idEstudiante;
    private String carrera;
    private int semestre;

    public Estudiante() {
        super();
    }

    public Estudiante(int idUsuario, String nombreUsuario, String correo, String contrasenaHash, int idEstudiante,
            String carrera, int semestre) {
        super(idUsuario, nombreUsuario, correo, contrasenaHash, "ESTUDIANTE");
        this.idEstudiante = idEstudiante;
        this.carrera = carrera;
        this.semestre = semestre;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    @Override public String toString() { return super.toString() + " Estudiante{" + "idEstudiante=" + idEstudiante + ", carrera='" + carrera + '\'' + '}'; 
                                       }
}
