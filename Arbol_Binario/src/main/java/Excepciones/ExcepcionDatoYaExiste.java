/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *
 * @author Usuario
 */
public class ExcepcionDatoYaExiste extends RuntimeException {
    public ExcepcionDatoYaExiste() {
        super("El dato ya existe en el arbol.");
    }

    public ExcepcionDatoYaExiste(String message) {
        super(message);
    }
}
