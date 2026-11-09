package uce.edu.ec.facualerta.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validador {
    private static final String REGEX_CORREO_UCE = "^[A-Za-z0-9._%+-]+@uce\\.edu\\.ec$";
    private static final String REGEX_GRAVEDAD = "^(BAJO|MEDIO|ALTO|CRITICO)$";
    private static final String REGEX_FECHA = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$";

    public static boolean esCadenaValida(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    public static boolean esCorreoInstitucionalValido(String correo) {
        if (!esCadenaValida(correo)) return false;
        Pattern pattern = Pattern.compile(REGEX_CORREO_UCE, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(correo.trim());
        return matcher.matches();
    }

    public static boolean esNivelGravedadValido(String gravedad) {
        if (!esCadenaValida(gravedad)) return false;
        Pattern pattern = Pattern.compile(REGEX_GRAVEDAD, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(gravedad.trim().toUpperCase());
        return matcher.matches();
    }

    public static boolean esFechaHoraValida(String fechaHora) {
        if (!esCadenaValida(fechaHora)) return false;
        Pattern pattern = Pattern.compile(REGEX_FECHA);
        Matcher matcher = pattern.matcher(fechaHora.trim());
        return matcher.matches();
    }

    public static boolean esOpcionValida(int opcion, int min, int max) {
        return opcion >= min && opcion <= max;
    }
}
