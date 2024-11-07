/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Excepciones.ExcepcionOrdenInvalido;

/**
 *
 * @author Usuario
 */
public class ArbolB<T extends Comparable<T>> extends ArbolMV<T> {
    private final int nroMaximoDeDatos;
    private final int nroMinimoDeDatos;
    private final int nroMinimoDeHijos;

    public ArbolB() {
        this.nroMaximoDeDatos = 2;
        this.nroMinimoDeDatos = 1;
        this.nroMinimoDeHijos = 2;
    }

    public ArbolB(int orden) throws ExcepcionOrdenInvalido {
        super(orden);
        this.nroMaximoDeDatos = super.orden - 1;
        this.nroMinimoDeDatos = this.nroMaximoDeDatos / 2;
        this.nroMinimoDeHijos = this.nroMinimoDeDatos + 1;
    }
}
