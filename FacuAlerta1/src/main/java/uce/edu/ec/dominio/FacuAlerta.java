package uce.edu.ec.dominio;

import uce.edu.ec.dominio.consola.Lector;
import uce.edu.ec.dominio.consola.Menu;

import java.util.Scanner;

/**
 * Clase del proyecto: punto de entrada que llama al Menu.
 */
public class FacuAlerta {
    public static void main(String[] args) {
        Menu menu = new Menu(new Lector(new Scanner(System.in), System.out));
        menu.ejecutar();
    }
}