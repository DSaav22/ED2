/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JPanel;

public class AVL<T extends Comparable<T>> extends ABB<T> {
    private static final byte RANGO_INFERIOR = -1;
    private static final byte RANGO_SUPERIOR = 1;

    public AVL() {
    }

    @Override
    public void insertar(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("");
        } else {
            this.raiz = this.insertar(this.raiz, dato);
        }
    }

    private NodoBinario<T> insertar(NodoBinario<T> nodo, T dato) {
    if (NodoBinario.esNodoVacio(nodo)) {
        return new NodoBinario<>(dato);
    } else {
        NodoBinario<T> supuestoHD;
        if (dato.compareTo(nodo.getDato()) < 0) {
            supuestoHD = this.insertar(nodo.getHijoIzquierdo(), dato);
            nodo.setHijoIzquierdo(supuestoHD);
        } else if (dato.compareTo(nodo.getDato()) > 0) {
            supuestoHD = this.insertar(nodo.getHijoDerecho(), dato);
            nodo.setHijoDerecho(supuestoHD);
        } else {
            throw new RuntimeException("Dato repetido");
        }
        return this.balancear(nodo);
    }
}


    @Override
    public void eliminar(T datoAEliminar) {
        if (datoAEliminar == null) {
            throw new IllegalArgumentException("");
        } else if (!this.contiene(datoAEliminar)) {
            throw new IllegalArgumentException("Dato no existe en el árbol.");
        } else {
            this.raiz = this.eliminar(this.raiz, datoAEliminar);
        }
    }

    private NodoBinario<T> eliminar(NodoBinario<T> nodo, T dato) {
    if (NodoBinario.esNodoVacio(nodo)) {
        return NodoBinario.nodoVacio();
    } else {
        if (dato.compareTo(nodo.getDato()) < 0) {
            nodo.setHijoIzquierdo(this.eliminar(nodo.getHijoIzquierdo(), dato));
        } else if (dato.compareTo(nodo.getDato()) > 0) {
            nodo.setHijoDerecho(this.eliminar(nodo.getHijoDerecho(), dato));
        } else {
            if (nodo.esHoja()) {
                return NodoBinario.nodoVacio();
            }

            if (nodo.esVacioHijoIzquierdo() || nodo.esVacioHijoDerecho()) {
                return nodo.esVacioHijoIzquierdo() ? nodo.getHijoDerecho() : nodo.getHijoIzquierdo();
            }

            NodoBinario<T> sucesor = this.obtenerSucesor(nodo.getHijoDerecho());
            nodo.setDato(sucesor.getDato());
            nodo.setHijoDerecho(this.eliminar(nodo.getHijoDerecho(), sucesor.getDato()));
        }

        return this.balancear(nodo);
    }
}


    @Override
    public boolean contiene(T dato) {
        return this.buscar(dato) != null;
    }

    @Override
    public T buscar(T dato) {
        return this.buscar(this.raiz, dato);
    }

    private T buscar(NodoBinario<T> nodo, T dato) {
    if (NodoBinario.esNodoVacio(nodo)) {
        return null;
    } else if (dato.compareTo(nodo.getDato()) == 0) {
        return nodo.getDato();
    } else {
        return dato.compareTo(nodo.getDato()) < 0 
            ? this.buscar(nodo.getHijoIzquierdo(), dato) 
            : this.buscar(nodo.getHijoDerecho(), dato);
    }
}


    private NodoBinario<T> obtenerSucesor(NodoBinario<T> nodo) {
        NodoBinario sucesor;
        for(sucesor = nodo; !sucesor.esVacioHijoIzquierdo(); sucesor = sucesor.getHijoIzquierdo()) {
        }

        return sucesor;
    }

    private NodoBinario<T> balancear(NodoBinario<T> nodo) {
        int ai = this.altura(nodo.getHijoIzquierdo());
        int ad = this.altura(nodo.getHijoDerecho());
        NodoBinario nodoHD;
        if (ai - ad > 1) {
            nodoHD = nodo.getHijoIzquierdo();
            ai = this.altura(nodoHD.getHijoIzquierdo());
            ad = this.altura(nodoHD.getHijoDerecho());
            return ad > ai ? this.rotacionDobleDerecha(nodo) : this.rotacionSimpleDerecha(nodo);
        } else if (ai - ad < -1) {
            nodoHD = nodo.getHijoDerecho();
            ai = this.altura(nodoHD.getHijoIzquierdo());
            ad = this.altura(nodoHD.getHijoDerecho());
            return ad < ai ? this.rotacionDobleIzquierda(nodo) : this.rotacionSimpleIzquierda(nodo);
        } else {
            return nodo;
        }
    }

    private NodoBinario<T> rotacionSimpleDerecha(NodoBinario<T> nodo) {
        NodoBinario<T> nodoARetornar = nodo.getHijoIzquierdo();
        nodo.setHijoIzquierdo(nodoARetornar.getHijoDerecho());
        nodoARetornar.setHijoDerecho(nodo);
        return nodoARetornar;
    }

    private NodoBinario<T> rotacionSimpleIzquierda(NodoBinario<T> nodo) {
        NodoBinario<T> nodoARetornar = nodo.getHijoDerecho();
        nodo.setHijoDerecho(nodoARetornar.getHijoIzquierdo());
        nodoARetornar.setHijoIzquierdo(nodo);
        return nodoARetornar;
    }

    private NodoBinario<T> rotacionDobleDerecha(NodoBinario<T> nodo) {
        nodo.setHijoIzquierdo(this.rotacionSimpleIzquierda(nodo.getHijoIzquierdo()));
        return this.rotacionSimpleDerecha(nodo);
    }

    private NodoBinario<T> rotacionDobleIzquierda(NodoBinario<T> nodo) {
        nodo.setHijoDerecho(this.rotacionSimpleDerecha(nodo.getHijoDerecho()));
        return this.rotacionSimpleIzquierda(nodo);
    }

    public boolean esAVL() {
        if (this.esArbolVacio()) {
            return false;
        } else {
            Queue<NodoBinario<T>> cola = new LinkedList();
            cola.offer(this.raiz);

            do {
                NodoBinario<T> nodo = (NodoBinario)cola.poll();
                int ai = this.altura(nodo.getHijoIzquierdo());
                int ad = this.altura(nodo.getHijoDerecho());
                if (ai - ad < -1 || ai - ad > 1) {
                    return false;
                }

                if (!nodo.esVacioHijoIzquierdo()) {
                    cola.offer(nodo.getHijoIzquierdo());
                }

                if (!nodo.esVacioHijoDerecho()) {
                    cola.offer(nodo.getHijoDerecho());
                }
            } while(!cola.isEmpty());

            return true;
        }
    }

    public boolean esAVLR() {
        return this.esAVLR(this.raiz);
    }

    private boolean esAVLR(NodoBinario<T> nodo) {
        if (NodoBinario.esNodoVacio(nodo)) {
            return true;
        } else {
            int ai = this.altura(nodo.getHijoIzquierdo());
            int ad = this.altura(nodo.getHijoDerecho());
            if (ai - ad >= -1 && ai - ad <= 1) {
                return this.esAVLR(nodo.getHijoIzquierdo()) && this.esAVLR(nodo.getHijoDerecho());
            } else {
                return false;
            }
        }
    }

    public boolean esAVLANivel(int nivelObjetivo) {
        if (this.esArbolVacio()) {
            return false;
        } else {
            Queue<NodoBinario<T>> cola = new LinkedList();
            cola.offer(this.raiz);
            int nivelActual = 0;

            do {
                int nodosEnCola = cola.size();

                for(int i = 0; i < nodosEnCola; ++i) {
                    NodoBinario<T> nodo = (NodoBinario)cola.poll();
                    if (nivelActual >= nivelObjetivo) {
                        int ai = this.altura(nodo.getHijoIzquierdo());
                        int ad = this.altura(nodo.getHijoDerecho());
                        if (ai - ad < -1 || ai - ad > 1) {
                            return false;
                        }
                    }

                    if (!nodo.esVacioHijoIzquierdo()) {
                        cola.offer(nodo.getHijoIzquierdo());
                    }

                    if (!nodo.esVacioHijoDerecho()) {
                        cola.offer(nodo.getHijoDerecho());
                    }
                }

                ++nivelActual;
            } while(!cola.isEmpty());

            return true;
        }
    }

    public boolean esAVLANivelR(int nivelObjetivo) {
        return this.esAVLANivelR(this.raiz, 0, nivelObjetivo);
    }

    private boolean esAVLANivelR(NodoBinario<T> nodo, int nivelActual, int nivelObjetivo) {
        if (NodoBinario.esNodoVacio(nodo)) {
            return true;
        } else {
            if (nivelActual >= nivelObjetivo) {
                int ai = this.altura(nodo.getHijoIzquierdo());
                int ad = this.altura(nodo.getHijoDerecho());
                if (ai - ad < -1 || ai - ad > 1) {
                    return false;
                }
            }

            return this.esAVLANivelR(nodo.getHijoIzquierdo(), nivelActual + 1, nivelObjetivo) && this.esAVLANivelR(nodo.getHijoDerecho(), nivelActual + 1, nivelObjetivo);
        }
    }
    
    
}
