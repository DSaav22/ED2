/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import Excepciones.ExcepcionDatoYaExiste;
import Excepciones.ExcepcionOrdenInvalido;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.swing.JPanel;

public class ArbolMV<T extends Comparable<T>> implements IArbolBusqueda<T> {
    protected int orden;
    protected NodoMV<T> raiz;
    protected static final byte ORDEN_MINIMO = 3;
    protected static final byte POSICION_INVALIDA = -1;

    public ArbolMV() {
        this.orden = 3;
    }

    public ArbolMV(int orden) throws ExcepcionOrdenInvalido {
        if (orden < -1) {
            throw new ExcepcionOrdenInvalido();
        } else {
            this.orden = orden;
        }
    }

    @Override
    public void insertar(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("El dato no debe ser vacío.");
        } else if (this.esArbolVacio()) {
            this.raiz = new NodoMV(this.orden, dato);
        } else {
            NodoMV<T> nodoAux = this.raiz;

            do {
                int posDeDatoAInsertar = this.buscarPosDeDato(nodoAux, dato);
                if (posDeDatoAInsertar != -1) {
                    throw new ExcepcionDatoYaExiste();
                }

                int posicionPorDondeBajar;
                NodoMV nodoNuevo;
                if (nodoAux.esHoja()) {
                    if (nodoAux.estanDatosLlenos()) {
                        posicionPorDondeBajar = this.buscarPosParaBajar(nodoAux, dato);
                        nodoNuevo = new NodoMV(this.orden, dato);
                        nodoAux.setHijo(posicionPorDondeBajar, nodoNuevo);
                    } else {
                        this.insertarDatoOrdenado(nodoAux, dato);
                    }

                    nodoAux = NodoMV.nodoVacio();
                } else {
                    posicionPorDondeBajar = this.buscarPosParaBajar(nodoAux, dato);
                    if (nodoAux.esHijoVacio(posicionPorDondeBajar)) {
                        nodoNuevo = new NodoMV(this.orden, dato);
                        nodoAux.setHijo(posicionPorDondeBajar, nodoNuevo);
                        nodoAux = NodoMV.nodoVacio();
                    } else {
                        nodoAux = nodoAux.getHijo(posicionPorDondeBajar);
                    }
                }
            } while(!NodoMV.esNodoVacio(nodoAux));

        }
    }

    protected int buscarPosDeDato(NodoMV<T> nodo, T dato) {
        for(int i = 0; i < nodo.nroDeDatosNoVacios(); ++i) {
            if (dato.compareTo(nodo.getDato(i)) == 0) {
                return i;
            }
        }

        return -1;
    }

    protected int buscarPosParaBajar(NodoMV<T> nodo, T dato) {
        for(int i = 0; i < nodo.nroDeDatosNoVacios(); ++i) {
            if (dato.compareTo(nodo.getDato(i)) < 0) {
                return i;
            }
        }

        return nodo.nroDeDatosNoVacios();
    }

    protected void insertarDatoOrdenado(NodoMV<T> nodo, T dato) {
        int i;
        for(i = nodo.nroDeDatosNoVacios() - 1; i >= 0 && dato.compareTo(nodo.getDato(i)) < 0; --i) {
            nodo.setDato(i + 1, nodo.getDato(i));
        }

        nodo.setDato(i + 1, dato);
    }
//hola
    @Override
    public void eliminar(T datoAEliminar) {
        if (datoAEliminar == NodoMV.datoVacio()) {
            throw new IllegalArgumentException("No se permiten datos nulos");
        } else if (!this.contiene(datoAEliminar)) {
            throw new IllegalArgumentException("El dato no existe en el arbol");
        } else {
            this.raiz = this.eliminar(this.raiz, datoAEliminar);
        }
    }

    private NodoMV<T> eliminar(NodoMV<T> nodoActual, T datoAEliminar) {
    for (int i = 0; i < nodoActual.nroDeDatosNoVacios(); ++i) {
        T datoEnTurno = nodoActual.getDato(i);
        if (datoAEliminar.compareTo(datoEnTurno) == 0) {
            if (nodoActual.esHoja()) {
                this.eliminarClaveDeNodoDePosicion(nodoActual, i);
                if (!nodoActual.hayDatosNoVacios()) {
                    return NodoMV.nodoVacio();
                }
                return nodoActual;
            }

            T datoDeReemplazo;
            if (this.hayHijosMasAdelanteDeLaPosicion(nodoActual, i)) {
                datoDeReemplazo = this.obtenerSucesorInOrden(nodoActual, datoAEliminar);
            } else {
                datoDeReemplazo = this.obtenerPredecesorInOrden(nodoActual, datoAEliminar);
            }

            nodoActual = this.eliminar(nodoActual, datoDeReemplazo);
            nodoActual.setDato(i, datoDeReemplazo);
            return nodoActual;
        }

        if (datoAEliminar.compareTo(datoEnTurno) < 0) {
            NodoMV<T> supuestoNuevoHijo = this.eliminar(nodoActual.getHijo(i), datoAEliminar);
            nodoActual.setHijo(i, supuestoNuevoHijo);
            return nodoActual;
        }
    }

    NodoMV<T> supuestoNuevoHijo = this.eliminar(nodoActual.getHijo(nodoActual.nroDeDatosNoVacios()), datoAEliminar);
    nodoActual.setHijo(nodoActual.nroDeDatosNoVacios(), supuestoNuevoHijo);
    return nodoActual;
}


    protected void eliminarClaveDeNodoDePosicion(NodoMV<T> nodoActual, int posDato) {
        nodoActual.setDato(posDato, (T)(Comparable)NodoMV.datoVacio());

        for(int i = posDato; i < nodoActual.nroDeDatosNoVacios(); ++i) {
            if (nodoActual.getDato(i) == (Comparable)NodoMV.datoVacio()) {
                T datoDeLaSiguientePosicion = nodoActual.getDato(i + 1);
                nodoActual.setDato(i, datoDeLaSiguientePosicion);
                nodoActual.setDato(i + 1, (T)(Comparable)NodoMV.datoVacio());
            }
        }

    }

    protected boolean hayHijosMasAdelanteDeLaPosicion(NodoMV<T> nodoActual, int posInicial) {
        for(int i = posInicial + 1; i < this.orden; ++i) {
            if (!nodoActual.esHijoVacio(i)) {
                return true;
            }
        }

        return false;
    }

    protected T obtenerSucesorInOrden(NodoMV<T> nodoActual, T datoABuscar) {
        List<T> listaDeClaves = new ArrayList();
        this.recorridoInOrden(nodoActual, listaDeClaves);
        int posDeLaClave = listaDeClaves.indexOf(datoABuscar) + 1;
        return (T)(Comparable)listaDeClaves.get(posDeLaClave);
    }

    protected T obtenerPredecesorInOrden(NodoMV<T> nodoActual, T claveABuscar) {
        List<T> listaDeClaves = new ArrayList();
        this.recorridoInOrden(nodoActual, listaDeClaves);
        int posDeLaClave = listaDeClaves.indexOf(claveABuscar) - 1;
        return (T)(Comparable)listaDeClaves.get(posDeLaClave);
    }

    @Override
    public boolean esArbolVacio() {
        return NodoMV.esNodoVacio(this.raiz);
    }

    @Override
    public void vaciar() {
        this.raiz = NodoMV.nodoVacio();
    }

    @Override
    public T buscar(T claveABuscar) {
        if (claveABuscar == null) {
            throw new IllegalArgumentException("La clave no debe ser nula");
        } else {
            return this.buscar(claveABuscar, this.raiz);
        }
    }

    private T buscar(T claveABuscar, NodoMV<T> nodo) {
        if (NodoMV.esNodoVacio(nodo)) {
            return null;
        } else {
            for(int i = 0; i < nodo.nroDeDatosNoVacios(); ++i) {
                T claveActual = nodo.getDato(i);
                if (claveABuscar.compareTo(claveActual) == 0) {
                    return nodo.getDato(i);
                }

                if (claveABuscar.compareTo(claveActual) < 0) {
                    return this.buscar(claveABuscar, nodo.getHijo(i));
                }
            }

            return this.buscar(claveABuscar, nodo.getHijo(nodo.nroDeDatosNoVacios()));
        }
    }

    @Override
    public boolean contiene(T dato) {
        return this.buscar(dato) != null;
    }

    @Override
    public int altura() {
        return this.altura(this.raiz);
    }

    private int altura(NodoMV<T> nodo) {
        if (NodoMV.esNodoVacio(nodo)) {
            return 0;
        } else {
            int alturaMaxima = 0;

            for(int i = 0; i <= nodo.nroDeDatosNoVacios(); ++i) {
                if (!nodo.esHijoVacio(i)) {
                    int alturaHijo = this.altura(nodo.getHijo(i));
                    if (alturaHijo > alturaMaxima) {
                        alturaMaxima = alturaHijo;
                    }
                }
            }

            return alturaMaxima + 1;
        }
    }

    @Override
    public int size() {
        return this.size(this.raiz);
    }

    protected int size(NodoMV<T> nodo) {
        if (NodoMV.esNodoVacio(nodo)) {
            return 0;
        } else {
            int ctd = 1;

            for(int i = 0; i <= nodo.nroDeDatosNoVacios(); ++i) {
                if (!nodo.esHijoVacio(i)) {
                    ctd += this.size(nodo.getHijo(i));
                }
            }

            return ctd;
        }
    }

    @Override
    public int nivel() {
        return 0;
    }

    @Override
    public List<T> recorridoPorNiveles() {
        List<T> recorrido = new ArrayList();
        if (this.esArbolVacio()) {
            return recorrido;
        } else {
            Queue<NodoMV<T>> cola = new LinkedList();
            cola.offer(this.raiz);

            do {
                NodoMV<T> nodoASacar = (NodoMV)cola.poll();

                for(int i = 0; i < nodoASacar.nroDeDatosNoVacios(); ++i) {
                    recorrido.add(nodoASacar.getDato(i));
                    if (!nodoASacar.esHijoVacio(i)) {
                        cola.offer(nodoASacar.getHijo(i));
                    }
                }

                if (!nodoASacar.esHijoVacio(nodoASacar.nroDeDatosNoVacios())) {
                    cola.offer(nodoASacar.getHijo(nodoASacar.nroDeDatosNoVacios()));
                }
            } while(!cola.isEmpty());

            return recorrido;
        }
    }

    @Override
    public List<T> recorridoInOrden() {
        List<T> recorrido = new ArrayList();
        this.recorridoInOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoInOrden(NodoMV<T> nodo, List<T> recorrido) {
        if (!NodoMV.esNodoVacio(nodo)) {
            for(int i = 0; i < nodo.nroDeDatosNoVacios(); ++i) {
                this.recorridoInOrden(nodo.getHijo(i), recorrido);
                recorrido.add(nodo.getDato(i));
            }

            this.recorridoInOrden(nodo.getHijo(nodo.nroDeDatosNoVacios()), recorrido);
        }
    }

    @Override
    public List<T> recorridoPreOrden() {
        List<T> recorrido = new ArrayList();
        this.recorridoPreOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoPreOrden(NodoMV<T> nodo, List<T> recorrido) {
        if (!NodoMV.esNodoVacio(nodo)) {
            for(int i = 0; i < nodo.nroDeDatosNoVacios(); ++i) {
                recorrido.add(nodo.getDato(i));
                this.recorridoPreOrden(nodo.getHijo(i), recorrido);
            }

            this.recorridoPreOrden(nodo.getHijo(nodo.nroDeDatosNoVacios()), recorrido);
        }
    }

    @Override
    public List<T> recorridoPostOrden() {
        List<T> recorrido = new ArrayList();
        this.recorridoPostOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoPostOrden(NodoMV<T> nodo, List<T> recorrido) {
        if (!NodoMV.esNodoVacio(nodo)) {
            this.recorridoPostOrden(nodo.getHijo(0), recorrido);

            for(int i = 0; i < nodo.nroDeDatosNoVacios(); ++i) {
                this.recorridoPostOrden(nodo.getHijo(i + 1), recorrido);
                recorrido.add(nodo.getDato(i));
            }

        }
    }

    public int contarHijosVacios() {
        return this.contarHijosVacios(this.raiz);
    }

    private int contarHijosVacios(NodoMV<T> nodo) {
        if (NodoMV.esNodoVacio(nodo)) {
            return 0;
        } else {
            int count = nodo.nroDeDatosVacios();

            for(int i = 0; i <= nodo.nroDeDatosNoVacios(); ++i) {
                if (!nodo.esHijoVacio(i)) {
                    count += this.contarHijosVacios(nodo.getHijo(i));
                }
            }

            return count;
        }
    }

    public int ctdNodosConHijosKNivel(int k, int n) {
        return this.ctdNodosConHijosKNivel(this.raiz, k, 0, n);
    }

    private int ctdNodosConHijosKNivel(NodoMV<T> nodo, int k, int nivelActual, int nivelObjetivo) {
        if (NodoMV.esNodoVacio(nodo)) {
            return 0;
        } else {
            int ctdHijos = 0;
            int ctdNodosK = 0;

            for(int i = 0; i <= nodo.nroDeDatosNoVacios(); ++i) {
                if (nivelActual >= nivelObjetivo && ctdHijos >= k) {
                    ++ctdNodosK;
                }

                if (!nodo.esHijoVacio(i)) {
                    ctdHijos += this.ctdNodosConHijosKNivel(nodo.getHijo(i), k, nivelActual + 1, nivelObjetivo);
                }
            }

            return ctdNodosK;
        }
    }

    public int ctdDatosNoVacios() {
        return this.ctdDatosNoVacios(this.raiz);
    }

    private int ctdDatosNoVacios(NodoMV<T> nodo) {
        if (NodoMV.esNodoVacio(nodo)) {
            return 0;
        } else {
            int ctd = nodo.nroDeDatosNoVacios();

            for(int i = 0; i <= nodo.nroDeDatosNoVacios(); ++i) {
                if (!nodo.esHijoVacio(i)) {
                    ctd += this.ctdDatosNoVacios(nodo.getHijo(i));
                }
            }

            return ctd;
        }
    }
    
    
}

