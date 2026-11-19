package uce.edu.ec.dominio.consola;

import uce.edu.ec.dominio.util.Validador;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.Predicate;

//Lee datos de la consola y repite la pregunta hasta que el dato cumpla la validacion de forma.

public class Lector {
    private final Scanner scanner;
    private final PrintStream out;

    public Lector(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    public void mostrar(String mensaje) {
        out.println(mensaje);
    }

    public String leerValido(String etiqueta, Predicate<String> validacion, String ayuda) {
        while (true) {
            out.print(etiqueta + ": ");
            String linea = scanner.nextLine().trim();
            if (validacion.test(linea)) {
                return linea;
            }
            out.println("  Dato invalido. " + ayuda);
        }
    }

    public int leerOpcion(String etiqueta, int min, int max) {
        String texto = leerValido(etiqueta, v -> Validador.esEnteroEnRango(v, min, max),
                "Ingrese un numero entre " + min + " y " + max + ".");
        return Integer.parseInt(texto);
    }

    public int leerEntero(String etiqueta) {
        return Integer.parseInt(leerValido(etiqueta, Validador::esEnteroPositivo,
                "Ingrese un entero mayor que 0."));
    }

    public String leerLetras(String etiqueta) {
        return leerValido(etiqueta, Validador::esLetras, "Solo letras y espacios (2 a 60 caracteres).");
    }

    public String leerTexto(String etiqueta) {
        return leerValido(etiqueta, Validador::esTexto, "Texto de 3 a 500 caracteres validos.");
    }

    public String leerCorreo(String etiqueta) {
        return leerValido(etiqueta, Validador::esCorreo, "Use un correo institucional @uce.edu.ec (no se acepta Gmail u otro dominio).");
    }

    public String leerContrasena(String etiqueta) {
        return leerValido(etiqueta, Validador::esContrasena, "La contrasena debe contener letras y numeros (6 a 100 caracteres, sin espacios).");
    }

    public String leerFacultad(String etiqueta) {
        out.println("Facultades disponibles:");
        for (int i = 0; i < Validador.FACULTADES.length; i++)
            out.println("  " + (i + 1) + ". " + Validador.FACULTADES[i]);
        int indice = leerOpcion(etiqueta + " (numero)", 1, Validador.FACULTADES.length);
        return Validador.FACULTADES[indice - 1];
    }

    public String leerTextoSinNumeros(String etiqueta) {
        return leerValido(etiqueta, Validador::esTextoSinNumeros, "No use numeros ni deje el texto vacio.");
    }

    public String leerTitulo(String etiqueta) {
        return leerValido(etiqueta, Validador::esTitulo, "Ingrese letras o numeros y signos de puntuacion validos; no se admiten espacios vacios.");
    }

    public String leerDestinatarios(String etiqueta) {
        return leerValido(etiqueta, Validador::esDestinatarios,
                "Escriba @Todos o uno o varios correos @uce.edu.ec separados por comas.");
    }

    public String leerEvidencia(String etiqueta, String tipo) {
        return leerValido(etiqueta, v -> Validador.esEvidencia(v, tipo),
                "Use imagen .png/.jpg/.jpeg/.webp o video .mp4/.webm/.mov; audio/documentos no permitidos.");
    }

    public String leerUrlArchivo(String etiqueta) {
        return leerValido(etiqueta, Validador::esUrlArchivo, "Ingrese una URL http(s) o una ruta de archivo.");
    }

    public String leerDeLista(String etiqueta, String[] permitidos) {
        return leerValido(etiqueta + " (" + String.join("/", permitidos) + ")",
                v -> Validador.esDeLista(v, permitidos), "Elija una de las opciones indicadas.").toUpperCase();
    }

}