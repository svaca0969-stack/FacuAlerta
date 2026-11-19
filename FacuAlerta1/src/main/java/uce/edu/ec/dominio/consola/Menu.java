package uce.edu.ec.dominio.consola;

/** Menu inicial: permite seleccionar el rol antes de autenticarse. */
public class Menu {
    private final Lector lector;
    private final SubMenu subMenu;

    public Menu(Lector lector) {
        this.lector = lector;
        this.subMenu = new SubMenu(lector);
    }

    public SubMenu getSubMenu() {
        return subMenu;
    }

    public void ejecutar() {
        int opcion;
        do {
            lector.mostrar("\n========== FACUALERTA ==========");
            lector.mostrar("Seleccione el rol con el que desea ingresar:");
            lector.mostrar("1. Usuario (Estudiante o Trabajador)");
            lector.mostrar("2. Gestor de riesgo");
            lector.mostrar("3. Moderador");
            lector.mostrar("0. Salir");
            opcion = lector.leerOpcion("Opcion", 0, 3);
            switch (opcion) {
                case 1 -> subMenu.ejecutarMenuPorRol("USUARIO");
                case 2 -> subMenu.ejecutarMenuPorRol("GESTOR_RIESGO");
                case 3 -> subMenu.ejecutarMenuPorRol("MODERADOR");
                case 0 -> lector.mostrar("Hasta pronto.");
                default -> { }
            }
        } while (opcion != 0);
    }

}