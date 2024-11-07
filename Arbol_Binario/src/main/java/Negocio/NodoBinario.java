/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

/**
 *
 * @author Usuario
 */
public class NodoBinario<T> {
    private T dato;
    private NodoBinario<T> hijoIzquierdo;
    private NodoBinario<T> hijoDerecho;

    public NodoBinario() {
    }

    public NodoBinario(T dato) {
        this.dato = dato;
    }

    public static NodoBinario nodoVacio() {
        return null;
    }

    public static boolean esNodoVacio(NodoBinario nodo) {
        return nodo == nodoVacio();
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public T getDato() {
        return this.dato;
    }

    public void setHijoIzquierdo(NodoBinario<T> nodo) {
        this.hijoIzquierdo = nodo;
    }

    public NodoBinario<T> getHijoIzquierdo() {
        return this.hijoIzquierdo;
    }

    public void setHijoDerecho(NodoBinario<T> nodo) {
        this.hijoDerecho = nodo;
    }

    public NodoBinario<T> getHijoDerecho() {
        return this.hijoDerecho;
    }

    public boolean esVacioHijoIzquierdo() {
        return esNodoVacio(this.hijoIzquierdo);
    }

    public boolean esVacioHijoDerecho() {
        return esNodoVacio(this.hijoDerecho);
    }

    public boolean esHoja() {
        return this.esVacioHijoIzquierdo() && this.esVacioHijoDerecho();
    }
}
