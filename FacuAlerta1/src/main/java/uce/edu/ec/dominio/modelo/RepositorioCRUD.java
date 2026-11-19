package uce.edu.ec.dominio.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Repositorio generico en memoria que implementa CRUD.
 * La validacion de duplicados se apoya en equals/hashCode de cada entidad.
 */
public class RepositorioCRUD<T> {
    private final List<T> registros;

    public RepositorioCRUD() {
        this.registros = new ArrayList<>();
    }

    public RepositorioCRUD(List<T> iniciales) {
        this();
        if (iniciales != null) {
            for (T elemento : iniciales) crear(elemento);
        }
    }

    // CREATE: crea si no existe un objeto equivalente.
    public boolean crear(T objeto) {
        exigirObjeto(objeto);
        if (registros.contains(objeto)) return false;
        registros.add(objeto);
        return true;
    }

    // READ: consulta todos los objetos.
    public List<T> consultar() {
        return Collections.unmodifiableList(new ArrayList<>(registros));
    }

    // READ: consulta por posicion.
    public T consultar(int indice) {
        validarIndice(indice);
        return registros.get(indice);
    }

    // Sobrecarga de consulta usando una referencia de objeto como criterio.
    public T consultar(T referencia) {
        exigirObjeto(referencia);
        int indice = registros.indexOf(referencia);
        return indice < 0 ? null : registros.get(indice);
    }

    // UPDATE: reemplaza el objeto de una posicion, evitando duplicados.
    public boolean editar(int indice, T actualizado) {
        validarIndice(indice);
        exigirObjeto(actualizado);
        for (int i = 0; i < registros.size(); i++) {
            if (i != indice && registros.get(i).equals(actualizado)) return false;
        }
        registros.set(indice, actualizado);
        return true;
    }

    // Sobrecarga UPDATE: busca el objeto original por equals y lo reemplaza.
    public boolean editar(T original, T actualizado) {
        exigirObjeto(original);
        int indice = registros.indexOf(original);
        return indice >= 0 && editar(indice, actualizado);
    }

    // DELETE: elimina por posicion.
    public boolean eliminar(int indice) {
        if (indice < 0 || indice >= registros.size()) return false;
        registros.remove(indice);
        return true;
    }

    // Sobrecarga DELETE: elimina usando la referencia del objeto.
    public boolean eliminar(T objeto) {
        exigirObjeto(objeto);
        return registros.remove(objeto);
    }

    public boolean existe(T objeto) {
        exigirObjeto(objeto);
        return registros.contains(objeto);
    }

    public int cantidad() {
        return registros.size();
    }

    private void validarIndice(int indice) {
        if (indice < 0 || indice >= registros.size()) {
            throw new IndexOutOfBoundsException("No existe un registro en la posicion " + indice);
        }
    }

    private void exigirObjeto(T objeto) {
        if (objeto == null) throw new IllegalArgumentException("El objeto no puede ser nulo");
    }
}