package uce.edu.ec.dominio.consola;

import uce.edu.ec.dominio.modelo.Estudiante;
import uce.edu.ec.dominio.modelo.GestorDeRiesgo;
import uce.edu.ec.dominio.modelo.Moderador;
import uce.edu.ec.dominio.modelo.Trabajador;
import uce.edu.ec.dominio.modelo.Usuario;

//Ingreso de datos de los usuarios (atributos coherentes con los POJO)
public class IngresoUsuario {
    private final Lector lector;

    public IngresoUsuario(Lector lector) {
        this.lector = lector;
    }

    private void ingresarDatosBase(Usuario usuario, int id) {
        usuario.setIdUsuario(id);
        usuario.setNombreUsuario(lector.leerLetras("Nombre de usuario"));
        usuario.setCorreo(lector.leerCorreo("Correo"));
        usuario.setContrasenaHash(lector.leerContrasena("Contrasena"));
    }

    public Estudiante ingresarEstudiante(int id) {
        Estudiante estudiante = new Estudiante();
        ingresarDatosBase(estudiante, id);
        estudiante.setIdEstudiante(id);
        estudiante.setFacultad(lector.leerFacultad("Facultad"));
        estudiante.setSemestre(lector.leerOpcion("Semestre (1-12)", 1, 12));
        return estudiante;
    }

    public Trabajador ingresarTrabajador(int id) {
        Trabajador trabajador = new Trabajador();
        ingresarDatosBase(trabajador, id);
        trabajador.setIdTrabajador(id);
        trabajador.setDepartamento(lector.leerLetras("Departamento"));
        trabajador.setCargo(lector.leerLetras("Cargo"));
        return trabajador;
    }

    public Moderador ingresarModerador(int id) {
        Moderador moderador = new Moderador();
        ingresarDatosBase(moderador, id);
        moderador.setIdModerador(id);
        moderador.setPermisos("Validar incidentes, cambiar estados y enviar notificaciones");
        return moderador;
    }

    public GestorDeRiesgo ingresarGestorDeRiesgo(int id) {
        GestorDeRiesgo gestor = new GestorDeRiesgo();
        ingresarDatosBase(gestor, id);
        gestor.setIdGestorRiesgo(id);
        gestor.setDepartamento(lector.leerLetras("Departamento"));
        gestor.setCargoInstitucional(lector.leerLetras("Cargo institucional"));
        gestor.setMatrizEvaluacion(lector.leerTexto("Matriz de evaluacion"));
        return gestor;
    }
}