package uce.edu.ec.dominio.consola;

import uce.edu.ec.dominio.modelo.*;
import uce.edu.ec.dominio.util.Validador;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Captura datos de categorias, evidencias e incidentes.
public class IngresoIncidente {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final Lector lector;
    public IngresoIncidente(Lector lector) { this.lector = lector; }

    public Categoria ingresarCategoria(int id) {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(id);
        categoria.setNombreCategoria(lector.leerLetras("Nombre de la categoria"));
        categoria.setDescripcion(lector.leerTextoSinNumeros("Descripcion"));
        return categoria;
    }

    public Evidencia ingresarEvidencia(int id) {
        Evidencia evidencia = new Evidencia();
        evidencia.setIdEvidencia(id);
        String tipo = lector.leerDeLista("Tipo de evidencia", Validador.TIPOS_EVIDENCIA);
        evidencia.setTipoEvidencia(tipo);
        evidencia.setUrlArchivo(lector.leerEvidencia("URL o ruta de imagen/video", tipo));
        return evidencia;
    }

    public Incidente ingresarIncidente(int id, Usuario usuario, Categoria categoria, String facultad, String nivelGravedad) {
        String ahora = LocalDateTime.now().format(FORMATO);
        Incidente incidente = new Incidente();
        incidente.setIdIncidente(id);
        incidente.setFechaHoraReporte(ahora);
        incidente.setFechaHoraActualizacion(ahora);
        incidente.setEstado("REPORTADO");
        incidente.setTitulo(lector.leerTitulo("Titulo"));
        incidente.setDescripcion(lector.leerTitulo("Descripcion"));
        // El nivel de gravedad se obtiene de la categoria elegida una unica vez.
        incidente.setNivelGravedad(nivelGravedad);
        incidente.setUsuarioReporta(usuario);
        incidente.setCategoria(categoria);
        incidente.setFacultad(facultad);
        return incidente;
    }
}