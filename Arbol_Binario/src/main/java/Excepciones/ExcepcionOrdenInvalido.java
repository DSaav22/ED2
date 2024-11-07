/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *
 * @author Usuario
 */
public class ExcepcionOrdenInvalido extends Exception {
    public ExcepcionOrdenInvalido() {
        super("El Orden del arbol no debe ser menor a 3.");
    }

    public ExcepcionOrdenInvalido(String message) {
        super(message);
    }
}
