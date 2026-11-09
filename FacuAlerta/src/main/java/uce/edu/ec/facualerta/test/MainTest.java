package uce.edu.ec.facualerta.test;

import uce.edu.ec.facualerta.gui.MenuConsolaGUI;

public class MainTest {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   INICIANDO SISTEMA INTEGRADO - FACU-ALERTA UCE  ");
        System.out.println("==================================================");
        
        // Instanciamos y lanzamos el menú principal de la interfaz de consola
        MenuConsolaGUI menuPrincipal = new MenuConsolaGUI();
        menuPrincipal.mostrarMenuNivel0();
    }
}
