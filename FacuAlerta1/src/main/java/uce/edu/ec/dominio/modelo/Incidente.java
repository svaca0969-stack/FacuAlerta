package uce.edu.ec.dominio.modelo;

import java.util.Objects;
import uce.edu.ec.dominio.util.Validador;

import java.io.Serializable;
import java.util.Arrays;

public class Incidente implements Serializable {
    private int idIncidente;
    private String fechaHoraReporte;
    private String fechaHoraActualizacion;
    private String estado;
    private String titulo;
    private String descripcion;
    private String nivelGravedad;

    // Relaciones de asociacion (se representan como atributos de objeto)
    private Usuario usuarioReporta;          // AGREGACION: Incidente o--- 1 Usuario (el usuario existe sin el incidente)
    private Categoria categoria;             // ASOCIACION: Incidente * --- 1 Categoria
    private String facultad;                // Facultad donde se reporta el incidente
    private Evidencia[] evidencias;         // Incidente 1 --- * Evidencia (composicion, arreglo)

    public Incidente() {
        this.idIncidente = 0;
        this.fechaHoraReporte = "";
        this.fechaHoraActualizacion = "";
        this.estado = "";
        this.titulo = "";
        this.descripcion = "";
        this.nivelGravedad = "";
        this.usuarioReporta = new Usuario();
        this.categoria = new Categoria();
        this.facultad = "";
        this.evidencias = new Evidencia[0];
    }

    public Incidente(int idIncidente, String fechaHoraReporte, String fechaHoraActualizacion, String estado,
                     String titulo, String descripcion, String nivelGravedad, Usuario usuarioReporta,
                     Categoria categoria, String facultad) {
        this();
        setIdIncidente(idIncidente);
        setFechaHoraReporte(fechaHoraReporte);
        setFechaHoraActualizacion(fechaHoraActualizacion);
        setEstado(estado);
        setTitulo(titulo);
        setDescripcion(descripcion);
        setNivelGravedad(nivelGravedad);
        setUsuarioReporta(usuarioReporta);
        setCategoria(categoria);
        setFacultad(facultad);
    }

    public int getIdIncidente() {
        return idIncidente;
    }

    public void setIdIncidente(int idIncidente) {
        this.idIncidente = Validador.exigirId(idIncidente, "El id de incidente");
    }

    public String getFechaHoraReporte() {
        return fechaHoraReporte;
    }

    public void setFechaHoraReporte(String fechaHoraReporte) {
        String nueva = Validador.exigirFechaHoraNoFutura(fechaHoraReporte, "La fecha y hora de reporte");
        // Validacion de fondo: el reporte no puede ser posterior a la actualizacion
        if (this.fechaHoraActualizacion != null && !this.fechaHoraActualizacion.isEmpty()) {
            Validador.exigirNoAnterior(this.fechaHoraActualizacion, nueva,
                    "La fecha y hora de actualizacion", "la fecha y hora de reporte");
        }
        this.fechaHoraReporte = nueva;
    }

    public String getFechaHoraActualizacion() {
        return fechaHoraActualizacion;
    }

    public void setFechaHoraActualizacion(String fechaHoraActualizacion) {
        String nueva = Validador.exigirFechaHoraNoFutura(fechaHoraActualizacion,
                "La fecha y hora de actualizacion");
        // Validacion de fondo: la actualizacion no puede ser anterior al reporte
        Validador.exigirNoAnterior(nueva, this.fechaHoraReporte,
                "La fecha y hora de actualizacion", "la fecha y hora de reporte");
        this.fechaHoraActualizacion = nueva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        String nuevo = Validador.exigirDeLista(estado, Validador.ESTADOS_INCIDENTE, "El estado");
        if ("RESUELTO".equals(this.estado) && !"RESUELTO".equals(nuevo))
            throw new IllegalArgumentException("Un incidente resuelto no puede reabrirse");
        this.estado = nuevo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = Validador.exigirTitulo(titulo, "El titulo");
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = Validador.exigirTitulo(descripcion, "La descripcion");
    }

    public String getNivelGravedad() {
        return nivelGravedad;
    }

    public void setNivelGravedad(String nivelGravedad) {
        this.nivelGravedad = Validador.exigirDeLista(nivelGravedad, Validador.NIVELES_GRAVEDAD, "El nivel de gravedad");
    }

    public Usuario getUsuarioReporta() {
        return usuarioReporta;
    }

    public void setUsuarioReporta(Usuario usuarioReporta) {
        this.usuarioReporta = Validador.exigirNoNulo(usuarioReporta, "El usuario que reporta");
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = Validador.exigirNoNulo(categoria, "La categoria");
    }

    public String getFacultad() { return facultad; }

    public void setFacultad(String facultad) {
        this.facultad = Validador.exigirFacultad(facultad, "La facultad");
    }

    // Relacion uno a muchos (Incidente 1 --- * Evidencia) representada con un arreglo de objetos.
    // Composicion: las evidencias forman parte del incidente; se copian al arreglo propio.
    public Evidencia[] getEvidencias() {
        return Arrays.copyOf(evidencias, evidencias.length);
    }

    public void setEvidencias(Evidencia[] evidencias) {
        Evidencia[] recibido = Validador.exigirNoNulo(evidencias, "El arreglo de evidencias");
        Evidencia[] copia = new Evidencia[0];
        for (Evidencia evidencia : recibido) {
            Validador.exigirNoNulo(evidencia, "Cada evidencia del arreglo");
            copia = agregarAlArreglo(copia, evidencia);
        }
        this.evidencias = copia;
    }

    public int getTotalEvidencias() {
        return evidencias.length;
    }

    // Sobrecarga: agrega una evidencia recibida como referencia de objeto.
    public void agregarEvidencia(Object objeto) {
        if (!(objeto instanceof Evidencia)) {
            throw new IllegalArgumentException("El objeto debe ser una Evidencia");
        }
        agregarEvidencia((Evidencia) objeto);
    }

    public void agregarEvidencia(Evidencia evidencia) {
        this.evidencias = agregarAlArreglo(this.evidencias, Validador.exigirNoNulo(evidencia, "La evidencia"));
    }

    // Sobrecarga: busca una evidencia por referencia de objeto (usa equals).
    public Evidencia buscarEvidencia(Evidencia referencia) {
        Validador.exigirNoNulo(referencia, "La evidencia");
        for (Evidencia e : evidencias) {
            if (e.equals(referencia)) return e;
        }
        return null;
    }

    // Sobrecarga: busca una evidencia por su id.
    public Evidencia buscarEvidencia(int idEvidencia) {
        for (Evidencia e : evidencias) {
            if (e.getIdEvidencia() == idEvidencia) return e;
        }
        return null;
    }

    // Sobrecarga: elimina una evidencia por referencia de objeto.
    public boolean eliminarEvidencia(Evidencia referencia) {
        Validador.exigirNoNulo(referencia, "La evidencia");
        int pos = -1;
        for (int i = 0; i < evidencias.length; i++) {
            if (evidencias[i].equals(referencia)) { pos = i; break; }
        }
        if (pos < 0) return false;
        Evidencia[] nuevo = new Evidencia[evidencias.length - 1];
        System.arraycopy(evidencias, 0, nuevo, 0, pos);
        System.arraycopy(evidencias, pos + 1, nuevo, pos, evidencias.length - pos - 1);
        this.evidencias = nuevo;
        return true;
    }

    // Validacion de duplicados: no se permite la misma evidencia (mismo id) dos veces.
    private static Evidencia[] agregarAlArreglo(Evidencia[] actual, Evidencia nueva) {
        for (Evidencia e : actual) {
            if (e.equals(nueva)) {
                throw new IllegalArgumentException("La evidencia ya esta registrada en el incidente");
            }
        }
        Evidencia[] nuevo = Arrays.copyOf(actual, actual.length + 1);
        nuevo[actual.length] = nueva;
        return nuevo;
    }

    @Override
    public String toString() {
        return "Incidente{idIncidente=" + idIncidente + ", fechaHoraReporte='" + fechaHoraReporte
                + "', fechaHoraActualizacion='" + fechaHoraActualizacion + "', estado='" + estado
                + "', titulo='" + titulo + "', descripcion='" + descripcion + "', nivelGravedad='" + nivelGravedad
                + "', idUsuarioReporta=" + usuarioReporta.getIdUsuario()
                + ", categoria='" + categoria.getNombreCategoria()
                + "', facultad='" + facultad
                + "', totalEvidencias=" + evidencias.length + "}";
    }
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (!(objeto instanceof Incidente)) return false;
        Incidente otro = (Incidente) objeto;
        return idIncidente == otro.idIncidente;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idIncidente);
    }

}