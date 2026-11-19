package uce.edu.ec.dominio.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validaciones de forma usando Pattern y Matcher.
 * Los metodos es...() devuelven boolean; los metodos exigir...() lanzan
 * IllegalArgumentException y se usan en los setters de los POJO.
 */
public final class Validador {

    public static final String[] TIPOS_USUARIO = {"ESTUDIANTE", "TRABAJADOR", "MODERADOR", "GESTOR_RIESGO"};
    public static final String[] ESTADOS_INCIDENTE = {"REPORTADO", "REVISION", "RESUELTO"};
    public static final String[] NIVELES_GRAVEDAD = {"BAJO", "MEDIO", "ALTO", "CRITICO"};
    public static final String[] TIPOS_EVIDENCIA = {"IMAGEN", "VIDEO"};

    private static final Set<String> FACULTADES_ADICIONALES = new LinkedHashSet<>();

    public static final String[] FACULTADES = {
            "Artes",
            "Arquitectura y Urbanismo",
            "Ciencias",
            "Ciencias Administrativas",
            "Ciencias Agrícolas",
            "Ciencias Biológicas",
            "Ciencias de la Discapacidad, Atención Prehospitalaria",
            "Ciencias Económicas",
            "Ciencias Médicas",
            "Ciencias Psicológicas",
            "Ciencias Químicas",
            "Ciencias Sociales y Humanas",
            "Comunicación Social",
            "Cultura Física",
            "Ingeniería y Ciencias Aplicadas",
            "Filosofía, Letras y Ciencias de la Educación",
            "Ingeniería en Geología, Minas, Petróleo y Ambiental",
            "Ingeniería Química",
            "Jurisprudencia, Ciencias Políticas y Sociales",
            "Medicina Veterinaria y Zootecnia",
            "Odontología"
    };

    // ---- Patrones ----
    private static final Pattern ENTERO = Pattern.compile("^\\d{1,9}$");
    private static final Pattern DECIMAL = Pattern.compile("^-?\\d{1,3}(\\.\\d{1,8})?$");
    private static final Pattern LETRAS = Pattern.compile("^(?=.{2,60}$)\\p{L}+( \\p{L}+)*$");
    private static final Pattern TEXTO = Pattern.compile("^(?=.{3,500}$)[\\p{L}\\p{N}][\\p{L}\\p{N} .,;:()¿?¡!_/\\-]*$");
    private static final Pattern CORREO = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$");
    private static final Pattern CONTRASENA = Pattern.compile("^(?=.{6,100}$)(?=.*[\\p{L}])(?=.*[0-9])\\S+$");
    private static final Pattern FECHA_HORA = Pattern.compile(
            "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]) ([01]\\d|2[0-3]):[0-5]\\d$");
    private static final Pattern URL_ARCHIVO = Pattern.compile(
            "^(https?://[\\w.-]+(:\\d+)?(/\\S*)?|[\\w:./\\\\ -]+\\.[A-Za-z0-9]{2,5})$");
    private static final Pattern CODIGO = Pattern.compile("^[A-Za-z0-9_-]{1,20}$");

    private static final DateTimeFormatter FORMATO_FECHA_HORA =
            DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm").withResolverStyle(ResolverStyle.STRICT);

    private Validador() {
    }

    private static String limpio(String valor) {
        return valor == null ? null : valor.trim();
    }

    private static boolean coincide(Pattern patron, String valor) {
        if (valor == null) {
            return false;
        }
        Matcher matcher = patron.matcher(valor.trim());
        return matcher.matches();
    }

    // ---------------- Validaciones booleanas ----------------

    public static boolean esEnteroPositivo(String valor) {
        return coincide(ENTERO, valor) && Integer.parseInt(valor.trim()) > 0;
    }

    public static boolean esEnteroEnRango(String valor, int min, int max) {
        if (!coincide(ENTERO, valor)) {
            return false;
        }
        int numero = Integer.parseInt(valor.trim());
        return numero >= min && numero <= max;
    }

    public static boolean esDecimal(String valor) {
        return coincide(DECIMAL, valor);
    }

    public static boolean esLetras(String valor) {
        return coincide(LETRAS, valor);
    }

    public static boolean esTexto(String valor) {
        return coincide(TEXTO, valor);
    }

    public static boolean esCorreo(String valor) {
        return coincide(CORREO, valor) && valor.trim().toLowerCase(java.util.Locale.ROOT).endsWith("@uce.edu.ec");
    }

    public static boolean esContrasena(String valor) {
        return coincide(CONTRASENA, valor);
    }

    public static boolean esFechaHora(String valor) {
        return coincide(FECHA_HORA, valor);
    }

    public static boolean esUrlArchivo(String valor) {
        return coincide(URL_ARCHIVO, valor);
    }

    public static boolean esFacultad(String valor) {
        if (esDeLista(valor, FACULTADES)) return true;
        if (valor == null) return false;
        return FACULTADES_ADICIONALES.stream().anyMatch(f -> f.equalsIgnoreCase(valor.trim()));
    }

    public static void registrarFacultad(String valor) {
        String nueva = exigirLetras(valor, "La facultad");
        if (esFacultad(nueva)) throw new IllegalArgumentException("La facultad ya se encuentra registrada");
        FACULTADES_ADICIONALES.add(nueva);
    }

    public static String exigirFacultad(String valor, String campo) {
        if (!esFacultad(valor)) throw new IllegalArgumentException(campo + " debe ser una facultad valida de la UCE");
        for (String facultad : FACULTADES) if (facultad.equalsIgnoreCase(valor.trim())) return facultad;
        for (String facultad : FACULTADES_ADICIONALES) if (facultad.equalsIgnoreCase(valor.trim())) return facultad;
        throw new IllegalArgumentException("Facultad invalida");
    }

    public static boolean esTextoSinNumeros(String valor) {
        return esTexto(valor) && !valor.matches(".*[0-9].*");
    }

    public static boolean esTitulo(String valor) {
        return esTexto(valor) && valor.matches("[\\p{L}\\p{N}][\\p{L}\\p{N} .,;:()¿?¡!_-]*") && valor.matches(".*\\p{L}.*");
    }

    public static boolean esDestinatarios(String valor) {
        if (valor == null || valor.isBlank()) return false;
        if ("@Todos".equalsIgnoreCase(valor.trim())) return true;
        for (String correo : valor.split(",", -1)) if (!esCorreo(correo.trim())) return false;
        return true;
    }

    public static boolean esEvidencia(String valor, String tipo) {
        if (!esUrlArchivo(valor) || !esDeLista(tipo, TIPOS_EVIDENCIA)) return false;
        String sinParametros = valor.trim().split("[?#]", 2)[0].toLowerCase(java.util.Locale.ROOT);
        return "IMAGEN".equalsIgnoreCase(tipo) ? sinParametros.matches(".*\\.(png|jpg|jpeg|webp)")
                : sinParametros.matches(".*\\.(mp4|webm|mov)");
    }

    public static boolean esCodigo(String valor) {
        return coincide(CODIGO, valor);
    }

    public static boolean esDeLista(String valor, String[] permitidos) {
        if (valor == null) {
            return false;
        }
        for (String permitido : permitidos) {
            if (permitido.equalsIgnoreCase(valor.trim())) {
                return true;
            }
        }
        return false;
    }

    // ---------------- Validaciones que lanzan excepcion (para setters) ----------------

    public static int exigirId(int valor, String campo) {
        if (valor <= 0) {
            throw new IllegalArgumentException(campo + " debe ser un entero mayor que 0");
        }
        return valor;
    }

    public static int exigirRango(int valor, int min, int max, String campo) {
        if (valor < min || valor > max) {
            throw new IllegalArgumentException(campo + " debe estar entre " + min + " y " + max);
        }
        return valor;
    }

    public static double exigirRango(double valor, double min, double max, String campo) {
        if (Double.isNaN(valor) || valor < min || valor > max) {
            throw new IllegalArgumentException(campo + " debe estar entre " + min + " y " + max);
        }
        return valor;
    }

    public static String exigirLetras(String valor, String campo) {
        if (!esLetras(valor)) {
            throw new IllegalArgumentException(campo + " debe contener solo letras (2 a 60 caracteres)");
        }
        return limpio(valor);
    }

    public static String exigirTexto(String valor, String campo) {
        if (!esTexto(valor)) {
            throw new IllegalArgumentException(campo + " debe tener entre 3 y 500 caracteres validos");
        }
        return limpio(valor);
    }

    public static String exigirCorreo(String valor, String campo) {
        if (!esCorreo(valor)) {
            throw new IllegalArgumentException(campo + " debe terminar exactamente en @uce.edu.ec");
        }
        return limpio(valor);
    }

    public static String exigirTitulo(String valor, String campo) {
        if (!esTitulo(valor)) throw new IllegalArgumentException(campo + " contiene caracteres no permitidos o esta vacio");
        return limpio(valor);
    }

    public static String exigirTextoSinNumeros(String valor, String campo) {
        if (!esTextoSinNumeros(valor)) throw new IllegalArgumentException(campo + " no debe contener numeros");
        return limpio(valor);
    }

    public static String exigirDestinatarios(String valor, String campo) {
        if (!esDestinatarios(valor)) throw new IllegalArgumentException(campo + " debe ser @Todos o correos institucionales separados por coma");
        return limpio(valor);
    }

    public static void exigirEvidencia(String archivo, String tipo) {
        if (!esEvidencia(archivo, tipo)) throw new IllegalArgumentException("Formato de evidencia no permitido para " + tipo);
    }

    public static String exigirContrasena(String valor, String campo) {
        if (!esContrasena(valor)) {
            throw new IllegalArgumentException(campo + " debe contener letras y numeros, tener entre 6 y 100 caracteres y no incluir espacios");
        }
        return limpio(valor);
    }

    /**
     * Valida la forma (Pattern) y que la fecha exista de verdad (rechaza 2026-02-31).
     */
    public static LocalDateTime aFechaHora(String valor, String campo) {
        if (!esFechaHora(valor)) {
            throw new IllegalArgumentException(campo + " debe tener el formato yyyy-MM-dd HH:mm");
        }
        try {
            return LocalDateTime.parse(valor.trim(), FORMATO_FECHA_HORA);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(campo + " no es una fecha y hora real");
        }
    }

    public static String exigirFechaHora(String valor, String campo) {
        aFechaHora(valor, campo);
        return limpio(valor);
    }

    /**
     * Validacion de fondo: la fecha existe y no esta en el futuro.
     */
    public static String exigirFechaHoraNoFutura(String valor, String campo) {
        LocalDateTime fecha = aFechaHora(valor, campo);
        if (fecha.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException(campo + " no puede estar en el futuro");
        }
        return limpio(valor);
    }

    /**
     * Validacion de fondo: la fecha no puede ser anterior a la de referencia.
     * Si la referencia esta vacia o es nula todavia, no se compara.
     */
    public static void exigirNoAnterior(String valor, String referencia, String campo, String campoReferencia) {
        if (referencia == null || referencia.isEmpty()) {
            return;
        }
        if (aFechaHora(valor, campo).isBefore(aFechaHora(referencia, campoReferencia))) {
            throw new IllegalArgumentException(campo + " no puede ser anterior a " + campoReferencia);
        }
    }

    public static LocalDateTime exigirFechaNoFutura(LocalDateTime valor, String campo) {
        exigirNoNulo(valor, campo);
        if (valor.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException(campo + " no puede estar en el futuro");
        }
        return valor;
    }

    public static String exigirUrlArchivo(String valor, String campo) {
        if (!esUrlArchivo(valor)) {
            throw new IllegalArgumentException(campo + " debe ser una URL http(s) o una ruta de archivo valida");
        }
        return limpio(valor);
    }

    public static String exigirCodigo(String valor, String campo) {
        if (!esCodigo(valor)) {
            throw new IllegalArgumentException(campo + " debe ser alfanumerico (hasta 20 caracteres)");
        }
        return limpio(valor);
    }

    public static String exigirDeLista(String valor, String[] permitidos, String campo) {
        if (!esDeLista(valor, permitidos)) {
            throw new IllegalArgumentException(campo + " debe ser uno de: " + String.join(", ", permitidos));
        }
        return valor.trim().toUpperCase();
    }

    public static <T> T exigirNoNulo(T valor, String campo) {
        if (valor == null) {
            throw new IllegalArgumentException(campo + " no puede ser nulo");
        }
        return valor;
    }
}