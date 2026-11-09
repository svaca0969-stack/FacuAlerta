package uce.edu.ec.facualerta.gui;

import java.util.Scanner;
import uce.edu.ec.facualerta.util.Validador;

public class MenuConsolaGUI {
    private Scanner scanner;

    public MenuConsolaGUI() {
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenuNivel0() {
        int opcion;
        do {
            System.out.println("\n==================================================");
            System.out.println("     SISTEMA FACU-ALERTA (UCE) - AUTENTICACION    ");
            System.out.println("==================================================");
            System.out.println("1. Estudiante / Trabajador");
            System.out.println("2. Moderador");
            System.out.println("3. Gestor de Riesgos");
            System.out.println("0. Salir");
            System.out.print("Seleccione su rol: ");
            
            opcion = leerEntero();

            switch (opcion) {
                case 1:
                case 2:
                case 3:
                    if (autenticarUsuario()) {
                        mostrarMenuNivel1(opcion);
                    }
                    break;
                case 0:
                    System.out.println("\nSaliendo del sistema. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("\n[ERROR] Opcion invalida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    private boolean autenticarUsuario() {
        System.out.println("\n--- CASO [H1]: INICIO DE SESION ---");
        System.out.print("Ingrese correo institucional (@uce.edu.ec): ");
        String correo = scanner.nextLine();
        
        System.out.print("Ingrese contrasena: ");
        String password = scanner.nextLine();

        if (!Validador.esCorreoInstitucionalValido(correo)) {
            System.out.println("\n[ERROR] El correo ingresado no es un correo institucional valido de la UCE.");
            return false;
        }

        System.out.println("\n[EXITO] Autenticacion correcta. Bienvenido.");
        return true;
    }

    public void mostrarMenuNivel1(int rol) {
        int opcion;
        do {
            System.out.println("\n--------------------------------------------------");
            switch (rol) {
                case 1: // Estudiante / Trabajador
                    System.out.println("         MENU - ESTUDIANTE / TRABAJADOR           ");
                    System.out.println("--------------------------------------------------");
                    System.out.println("1. Registrar reporte de incidente ([H3], [H4], [H6])");
                    System.out.println("2. Buscar incidentes por facultad");
                    System.out.println("0. Cerrar sesion");
                    System.out.print("Elija una opcion: ");
                    
                    opcion = leerEntero();
                    switch (opcion) {
                        case 1:
                            registrarIncidenteUI();
                            break;
                        case 2:
                            buscarIncidenteUI();
                            break;
                        case 0:
                            System.out.println("\nCerrando sesion...");
                            break;
                        default:
                            System.out.println("\n[ERROR] Opcion no valida.");
                    }
                    break;

                case 2: // Moderador
                    System.out.println("             MENU - MODERADOR                     ");
                    System.out.println("--------------------------------------------------");
                    System.out.println("1. Gestionar usuarios ([H2])");
                    System.out.println("2. Gestionar categorias ([H5])");
                    System.out.println("3. Gestionar ubicaciones ([H7])");
                    System.out.println("4. Gestionar notificaciones ([H8])");
                    System.out.println("0. Cerrar sesion");
                    System.out.print("Elija una opcion: ");
                    
                    opcion = leerEntero();
                    switch (opcion) {
                        case 1: case 2: case 3: case 4:
                            System.out.println("\n[INFO] Ejecutando modulo de gestion administrativa...");
                            break;
                        case 0:
                            System.out.println("\nCerrando sesion...");
                            break;
                        default:
                            System.out.println("\n[ERROR] Opcion no valida.");
                    }
                    break;

                case 3: // Gestor de Riesgos
                    System.out.println("          MENU - GESTOR DE RIESGOS                ");
                    System.out.println("--------------------------------------------------");
                    System.out.println("1. Clasificar incidentes segun matriz de riesgo ([H9])");
                    System.out.println("2. Emitir recomendaciones de seguridad");
                    System.out.println("0. Cerrar sesion");
                    System.out.print("Elija una opcion: ");
                    
                    opcion = leerEntero();
                    switch (opcion) {
                        case 1: case 2:
                            System.out.println("\n[INFO] Ejecutando modulo de analisis de riesgos...");
                            break;
                        case 0:
                            System.out.println("\nCerrando sesion...");
                            break;
                        default:
                            System.out.println("\n[ERROR] Opcion no valida.");
                    }
                    break;
                default:
                    opcion = 0;
            }
        } while (opcion != 0);
    }

    private void registrarIncidenteUI() {
        System.out.println("\n--- REGISTRO DE INCIDENTE ---");
        System.out.print("Ingrese titulo del incidente: ");
        String titulo = scanner.nextLine();
        
        System.out.print("Ingrese descripcion: ");
        String descripcion = scanner.nextLine();
        
        System.out.print("Ingrese gravedad (BAJO, MEDIO, ALTO, CRITICO): ");
        String gravedad = scanner.nextLine();

        if (!Validador.esNivelGravedadValido(gravedad)) {
            System.out.println("\n[ADVERTENCIA] El nivel de gravedad ingresado no coincide con los parametros estandar.");
        }

        System.out.print("Ingrese la URL o ruta de la evidencia fotografica: ");
        String evidencia = scanner.nextLine();

        System.out.println("\n[EXITO] Incidente registrado correctamente en el sistema.");
    }

    private void buscarIncidenteUI() {
        System.out.println("\n--- BUSQUEDA DE INCIDENTES ---");
        System.out.print("Ingrese el nombre de la facultad: ");
        String facultad = scanner.nextLine();
        System.out.println("\n[RESULTADOS] Mostrando incidentes registrados para: " + facultad);
    }

    private int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
