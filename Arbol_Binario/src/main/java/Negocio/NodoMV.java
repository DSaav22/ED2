/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Usuario
 */
public class NodoMV<T extends Comparable<T>> {
    private List<T> listaDeDatos;
    private List<NodoMV<T>> listaDeHijos;

    public static NodoMV nodoVacio() {
        return null;
    }

    public static Object datoVacio() {
        return null;
    }

    public static boolean esNodoVacio(NodoMV nodo) {
        return nodo == nodoVacio();
    }

    public NodoMV(int orden) {
        this.listaDeDatos = new ArrayList();
        this.listaDeHijos = new ArrayList();

        for(int i = 0; i < orden - 1; ++i) {
            this.listaDeDatos.add((T)(Comparable)datoVacio());
            this.listaDeHijos.add(nodoVacio());
        }

        this.listaDeHijos.add(nodoVacio());
    }

    public NodoMV(int orden, T dato) {
        this(orden);
        this.listaDeDatos.set(0, dato);
    }

    public NodoMV<T> getHijo(int pos) {
        return (NodoMV)this.listaDeHijos.get(pos);
    }

    public void setHijo(int pos, NodoMV<T> nodo) {
        this.listaDeHijos.set(pos, nodo);
    }

    public T getDato(int pos) {
        return (T)(Comparable)this.listaDeDatos.get(pos);
    }

    public void setDato(int pos, T dato) {
        this.listaDeDatos.set(pos, dato);
    }

    public boolean esHijoVacio(int pos) {
        return this.listaDeHijos.get(pos) == nodoVacio();
    }

    public boolean esDatoVacio(int pos) {
        return this.listaDeDatos.get(pos) == datoVacio();
    }

    public boolean esHoja() {
        for(int i = 0; i < this.listaDeHijos.size(); ++i) {
            if (!this.esHijoVacio(i)) {
                return false;
            }
        }

        return true;
    }

    public int cantDeHijosNoVacios() {
        int ctd = 0;

        for(int i = 0; i < this.listaDeHijos.size(); ++i) {
            if (!this.esHijoVacio(i)) {
                ++ctd;
            }
        }

        return ctd;
    }

    public int nroDeDatosNoVacios() {
        int ctd = 0;

        for(int i = 0; i < this.listaDeDatos.size(); ++i) {
            if (!this.esDatoVacio(i)) {
                ++ctd;
            }
        }

        return ctd;
    }

    public int nroDeDatosVacios() {
        int ctd = 0;

        for(int i = 0; i < this.listaDeDatos.size(); ++i) {
            if (this.esDatoVacio(i)) {
                ++ctd;
            }
        }

        return ctd;
    }

    public boolean hayDatosNoVacios() {
        return this.nroDeDatosNoVacios() > 0;
    }

    public boolean estanDatosLlenos() {
        return this.listaDeDatos.size() == this.nroDeDatosNoVacios();
    }
}

