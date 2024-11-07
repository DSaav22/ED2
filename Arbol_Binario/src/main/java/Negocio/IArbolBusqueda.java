
package Negocio;

import java.util.List;

public interface IArbolBusqueda<T extends Comparable<T>> {
    void insertar(T var1);

    void eliminar(T var1);

    boolean esArbolVacio();

    void vaciar();

    T buscar(T var1);

    boolean contiene(T var1);

    int altura();

    int size();

    int nivel();

    List<T> recorridoPorNiveles();

    List<T> recorridoInOrden();

    List<T> recorridoPreOrden();

    List<T> recorridoPostOrden();
}
