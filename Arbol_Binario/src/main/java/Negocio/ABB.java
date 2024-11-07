

package Negocio;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import javax.swing.JPanel;

public class ABB<T extends Comparable<T>> implements IArbolBusqueda<T> {
    protected NodoBinario<T> raiz;

    public ABB() {
    }

    public ABB(List<T> datosInOrden, List<T> datosNoInOrden, boolean esPreOrden) {
        if (!this.tieneDatoNulo(datosInOrden) && !this.tieneDatoNulo(datosNoInOrden)) {
            if (datosInOrden.size() != datosNoInOrden.size()) {
                throw new IllegalArgumentException("Las listas deben ser del mismo tamaño.");
            } else {
                if (esPreOrden) {
                    this.raiz = this.construirPreOrden(datosInOrden, datosNoInOrden, 0, datosInOrden.size() - 1, 0, datosInOrden.size() - 1);
                } else {
                    this.raiz = this.construirPostOrden(datosInOrden, datosNoInOrden, 0, datosInOrden.size() - 1, 0, datosInOrden.size() - 1);
                }

            }
        } else {
            throw new IllegalArgumentException("Error. Hay datos nulos.");
        }
    }

    public boolean tieneDatoNulo(List<T> lista) {
        Iterator var2 = lista.iterator();

        Object ele;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            ele = var2.next();
        } while(ele != null);

        return true;
    }

    private NodoBinario<T> construirPreOrden(List<T> datosInOrden, List<T> datosPreOrden, int inicioInOrden, int finInOrden, int inicioPreOrden, int finPreOrden) {
        if (inicioInOrden <= finInOrden && inicioPreOrden <= finPreOrden) {
            T datoRaiz = (T)(Comparable)datosPreOrden.get(inicioPreOrden);
            NodoBinario<T> nodo = new NodoBinario(datoRaiz);
            int indexRaiz = datosInOrden.indexOf(datoRaiz);
            int nodosRI = indexRaiz - inicioInOrden;
            nodo.setHijoIzquierdo(this.construirPreOrden(datosInOrden, datosPreOrden, inicioInOrden, indexRaiz - 1, inicioPreOrden + 1, inicioPreOrden + nodosRI));
            nodo.setHijoDerecho(this.construirPreOrden(datosInOrden, datosPreOrden, indexRaiz + 1, finInOrden, inicioPreOrden + 1 + nodosRI, finPreOrden));
            return nodo;
        } else {
            return NodoBinario.nodoVacio();
        }
    }

    private NodoBinario<T> construirPostOrden(List<T> datosInOrden, List<T> datosPostOrden, int inicioInOrden, int finInOrden, int inicioPostOrden, int finPostOrden) {
        if (inicioInOrden <= finInOrden && inicioPostOrden <= finPostOrden) {
            T datoRaiz = (T)(Comparable)datosPostOrden.get(finPostOrden);
            NodoBinario<T> nodo = new NodoBinario(datoRaiz);
            int indexRaiz = datosInOrden.indexOf(datoRaiz);
            int nodosRI = indexRaiz - inicioInOrden;
            nodo.setHijoIzquierdo(this.construirPostOrden(datosInOrden, datosPostOrden, inicioInOrden, indexRaiz - 1, inicioPostOrden, inicioPostOrden + nodosRI - 1));
            nodo.setHijoDerecho(this.construirPostOrden(datosInOrden, datosPostOrden, indexRaiz + 1, finInOrden, inicioPostOrden + nodosRI, finPostOrden - 1));
            return nodo;
        } else {
            return NodoBinario.nodoVacio();
        }
    }

    @Override
    public void insertar(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("Error. Dato nulo.");
        } else {
            this.raiz = this.insertar(this.raiz, dato);
        }
    }

    private NodoBinario<T> insertar(NodoBinario<T> nodo, T dato) {
    if (NodoBinario.esNodoVacio(nodo)) {
        return new NodoBinario<>(dato);
    } else {
        if (dato.compareTo(nodo.getDato()) < 0) {
            nodo.setHijoIzquierdo(this.insertar(nodo.getHijoIzquierdo(), dato));
        } else if (dato.compareTo(nodo.getDato()) > 0) {
            nodo.setHijoDerecho(this.insertar(nodo.getHijoDerecho(), dato));
        } else {
            throw new IllegalArgumentException("Dato repetido");
        }

        return nodo;
    }
}


    @Override
    public void eliminar(T dato) {
    if (dato == null) {
        throw new IllegalArgumentException("Error. Dato nulo.");
    }
    
    if (this.esArbolVacio()) {
        throw new IllegalArgumentException("Dato no existe en el arbol");
    }

    NodoBinario<T> actual = this.raiz;
    NodoBinario<T> padre = NodoBinario.nodoVacio();

    while (!NodoBinario.esNodoVacio(actual) && dato.compareTo(actual.getDato()) != 0) {
        padre = actual;
        if (dato.compareTo(actual.getDato()) < 0) {
            actual = actual.getHijoIzquierdo();
        } else {
            actual = actual.getHijoDerecho();
        }
    }

    if (NodoBinario.esNodoVacio(actual)) {
        throw new IllegalArgumentException("Dato no existe en el arbol");
    }

    if (actual.esHoja()) {
        if (actual == this.raiz) {
            this.vaciar();
        } else if (actual == padre.getHijoIzquierdo()) {
            padre.setHijoIzquierdo(NodoBinario.nodoVacio());
        } else {
            padre.setHijoDerecho(NodoBinario.nodoVacio());
        }
    } else {
        NodoBinario<T> sucesor;
        if (!actual.esVacioHijoIzquierdo() && !actual.esVacioHijoDerecho()) {
            sucesor = actual.getHijoDerecho();
            NodoBinario<T> padreSucesor = actual;
            while (!sucesor.esVacioHijoIzquierdo()) {
                padreSucesor = sucesor;
                sucesor = sucesor.getHijoIzquierdo();
            }

            if (padreSucesor != actual) {
                padreSucesor.setHijoIzquierdo(sucesor.getHijoDerecho());
            } else {
                padreSucesor.setHijoDerecho(sucesor.getHijoDerecho());
            }

            actual.setDato(sucesor.getDato());
        } else {
            sucesor = actual.esVacioHijoIzquierdo() ? actual.getHijoDerecho() : actual.getHijoIzquierdo();
            if (actual == this.raiz) {
                this.raiz = sucesor;
            } else if (actual == padre.getHijoIzquierdo()) {
                padre.setHijoIzquierdo(sucesor);
            } else {
                padre.setHijoDerecho(sucesor);
            }
        }
    }
}


    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }

    @Override
    public void vaciar() {
        this.raiz = NodoBinario.nodoVacio();
    }

    @Override
    public T buscar(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("Error. Dato nulo.");
        } else {
            return this.buscar(this.raiz, dato);
        }
    }

    private T buscar(NodoBinario<T> nodo, T dato) {
    if (NodoBinario.esNodoVacio(nodo)) {
        return null;
    } else if (dato.compareTo(nodo.getDato()) == 0) {
        return nodo.getDato();
    } else {
        if (dato.compareTo(nodo.getDato()) < 0) {
            return this.buscar(nodo.getHijoIzquierdo(), dato);
        } else {
            return this.buscar(nodo.getHijoDerecho(), dato);
        }
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

    protected int altura(NodoBinario<T> nodo) {
        if (NodoBinario.esNodoVacio(nodo)) {
            return 0;
        } else {
            int ai = this.altura(nodo.getHijoIzquierdo());
            int ad = this.altura(nodo.getHijoDerecho());
            return ai > ad ? ai + 1 : ad + 1;
        }
    }

    @Override
    public int size() {
        if (this.esArbolVacio()) {
            return 0;
        } else {
            int size = 0;
            Stack<NodoBinario<T>> pila = new Stack();
            NodoBinario<T> nodo = this.raiz;

            while(true) {
                while(NodoBinario.esNodoVacio(nodo)) {
                    nodo = (NodoBinario)pila.pop();
                    ++size;
                    nodo = nodo.getHijoDerecho();
                    if (NodoBinario.esNodoVacio(nodo) && pila.empty()) {
                        return size;
                    }
                }

                pila.push(nodo);
                nodo = nodo.getHijoIzquierdo();
            }
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
            Queue<NodoBinario<T>> cola = new LinkedList();
            cola.offer(this.raiz);

            do {
                NodoBinario<T> nodo = (NodoBinario)cola.poll();
                recorrido.add((T)(Comparable)nodo.getDato());
                if (!nodo.esVacioHijoIzquierdo()) {
                    cola.offer(nodo.getHijoIzquierdo());
                }

                if (!nodo.esVacioHijoDerecho()) {
                    cola.offer(nodo.getHijoDerecho());
                }
            } while(!cola.isEmpty());

            return recorrido;
        }
    }

    @Override
    public List<T> recorridoInOrden() {
        List<T> recorrido = new ArrayList();
        if (this.esArbolVacio()) {
            return recorrido;
        } else {
            Stack<NodoBinario<T>> pila = new Stack();
            NodoBinario<T> nodo = this.raiz;

            while(true) {
                while(NodoBinario.esNodoVacio(nodo)) {
                    nodo = (NodoBinario)pila.pop();
                    recorrido.add((T)(Comparable)nodo.getDato());
                    nodo = nodo.getHijoDerecho();
                    if (pila.empty() && NodoBinario.esNodoVacio(nodo)) {
                        return recorrido;
                    }
                }

                pila.push(nodo);
                nodo = nodo.getHijoIzquierdo();
            }
        }
    }

    @Override
    public List<T> recorridoPreOrden() {
        List<T> recorrido = new ArrayList();
        if (this.esArbolVacio()) {
            return recorrido;
        } else {
            Stack<NodoBinario<T>> pila = new Stack();
            pila.push(this.raiz);

            do {
                NodoBinario<T> nodo = (NodoBinario)pila.pop();
                recorrido.add((T)(Comparable)nodo.getDato());
                if (!nodo.esVacioHijoDerecho()) {
                    pila.push(nodo.getHijoDerecho());
                }

                if (!nodo.esVacioHijoIzquierdo()) {
                    pila.push(nodo.getHijoIzquierdo());
                }
            } while(!pila.empty());

            return recorrido;
        }
    }

    @Override
    public List<T> recorridoPostOrden() {
        List<T> recorrido = new ArrayList();
        if (this.esArbolVacio()) {
            return recorrido;
        } else {
            Stack<NodoBinario<T>> pilaPostOrden = new Stack();
            Stack<NodoBinario<T>> pilaAux = new Stack();
            pilaAux.push(this.raiz);

            do {
                NodoBinario<T> nodo = (NodoBinario)pilaAux.pop();
                pilaPostOrden.push(nodo);
                if (!nodo.esVacioHijoIzquierdo()) {
                    pilaAux.push(nodo.getHijoIzquierdo());
                }

                if (!nodo.esVacioHijoDerecho()) {
                    pilaAux.push(nodo.getHijoDerecho());
                }
            } while(!pilaAux.empty());

            do {
                recorrido.add((T)(Comparable)((NodoBinario)pilaPostOrden.pop()).getDato());
            } while(!pilaPostOrden.empty());

            return recorrido;
        }
    }

    public int ctdHijosVaciosPostOrden() {
        if (this.esArbolVacio()) {
            return 0;
        } else {
            int ctd = 0;
            Stack<NodoBinario<T>> pilaPostOrden = new Stack();
            Stack<NodoBinario<T>> pilaAux = new Stack();
            pilaAux.push(this.raiz);

            NodoBinario aux;
            do {
                aux = (NodoBinario)pilaAux.pop();
                pilaPostOrden.push(aux);
                if (!aux.esVacioHijoIzquierdo()) {
                    pilaAux.push(aux.getHijoIzquierdo());
                }

                if (!aux.esVacioHijoDerecho()) {
                    pilaAux.push(aux.getHijoDerecho());
                }
            } while(!pilaAux.empty());

            do {
                aux = (NodoBinario)pilaPostOrden.pop();
                if (aux.esVacioHijoIzquierdo()) {
                    ++ctd;
                }

                if (aux.esVacioHijoDerecho()) {
                    ++ctd;
                }
            } while(!pilaPostOrden.empty());

            return ctd;
        }
    }

    public int ctdHijosVaciosInOrden() {
        if (this.esArbolVacio()) {
            return 0;
        } else {
            int ctd = 0;
            Stack<NodoBinario<T>> pila = new Stack();
            NodoBinario<T> nodo = this.raiz;

            while(true) {
                while(NodoBinario.esNodoVacio(nodo)) {
                    nodo = (NodoBinario)pila.pop();
                    if (nodo.esVacioHijoDerecho()) {
                        ++ctd;
                    }

                    if (nodo.esVacioHijoIzquierdo()) {
                        ++ctd;
                    }

                    nodo = nodo.getHijoDerecho();
                    if (pila.empty() && NodoBinario.esNodoVacio(nodo)) {
                        return ctd;
                    }
                }

                pila.push(nodo);
                nodo = nodo.getHijoIzquierdo();
            }
        }
    }

    public T maximo() {
        if (this.esArbolVacio()) {
            return null;
        } else {
            NodoBinario nodo;
            for(nodo = this.raiz; !nodo.esVacioHijoDerecho(); nodo = nodo.getHijoDerecho()) {
            }

            return (T)(Comparable)nodo.getDato();
        }
    }

    public T minimo() {
        if (this.esArbolVacio()) {
            return null;
        } else {
            NodoBinario nodo;
            for(nodo = this.raiz; !nodo.esVacioHijoIzquierdo(); nodo = nodo.getHijoIzquierdo()) {
            }

            return (T)(Comparable)nodo.getDato();
        }
    }

    public NodoBinario<T> getNodo(T dato) {
    NodoBinario<T> nodo = this.raiz;

    while (!NodoBinario.esNodoVacio(nodo)) {
        if (dato.compareTo(nodo.getDato()) == 0) {
            return nodo;
        } else if (dato.compareTo(nodo.getDato()) < 0) {
            nodo = nodo.getHijoIzquierdo();
        } else {
            nodo = nodo.getHijoDerecho();
        }
    }

    return NodoBinario.nodoVacio();
}


    public NodoBinario<T> getNodoPadre(T datoHijo) {
    NodoBinario<T> actual = this.raiz;
    NodoBinario<T> nodoPadre = NodoBinario.nodoVacio();

    while (!NodoBinario.esNodoVacio(actual) && datoHijo.compareTo(actual.getDato()) != 0) {
        nodoPadre = actual;
        if (datoHijo.compareTo(actual.getDato()) < 0) {
            actual = actual.getHijoIzquierdo();
        } else {
            actual = actual.getHijoDerecho();
        }
    }

    return !NodoBinario.esNodoVacio(actual) && !NodoBinario.esNodoVacio(nodoPadre) ? nodoPadre : NodoBinario.nodoVacio();
}


    public boolean esMonticulo() {
        if (this.esArbolVacio()) {
            return false;
        } else {
            Queue<NodoBinario<T>> cola = new LinkedList();
            cola.offer(this.raiz);

            do {
                NodoBinario<T> nodo = (NodoBinario)cola.poll();
                Comparable datoHD;
                if (!nodo.esVacioHijoIzquierdo()) {
                    datoHD = (Comparable)nodo.getHijoIzquierdo().getDato();
                    if (datoHD.compareTo((Comparable)nodo.getDato()) < 0) {
                        return false;
                    }

                    cola.offer(nodo.getHijoIzquierdo());
                }

                if (!nodo.esVacioHijoDerecho()) {
                    datoHD = (Comparable)nodo.getHijoDerecho().getDato();
                    if (datoHD.compareTo((Comparable)nodo.getDato()) < 0) {
                        return false;
                    }

                    cola.offer(nodo.getHijoDerecho());
                }
            } while(!cola.isEmpty());

            return true;
        }
    }

    public List<T> listarNivel(int nivelObjetivo) {
        List<T> listado = new ArrayList();
        if (this.esArbolVacio()) {
            return listado;
        } else {
            int nivelActual = 0;
            Queue<NodoBinario<T>> cola = new LinkedList();
            cola.offer(this.raiz);

            do {
                int nodosEnCola = cola.size();

                for(int i = 0; i < nodosEnCola; ++i) {
                    NodoBinario<T> nodo = (NodoBinario)cola.poll();
                    if (nivelActual == nivelObjetivo && !NodoBinario.esNodoVacio(nodo)) {
                        listado.add((T)(Comparable)nodo.getDato());
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

            return listado;
        }
    }

    public List<T> listarNivelR(int nivelObjetivo) {
        List<T> listado = new ArrayList();
        this.listarNivelR(this.raiz, 0, nivelObjetivo, listado);
        return listado;
    }

    private void listarNivelR(NodoBinario<T> nodo, int nivelActual, int nivelObjetivo, List<T> listado) {
        if (!NodoBinario.esNodoVacio(nodo)) {
            if (nivelActual == nivelObjetivo && !NodoBinario.esNodoVacio(nodo)) {
                listado.add((T)(Comparable)nodo.getDato());
            }

            this.listarNivelR(nodo.getHijoIzquierdo(), nivelActual + 1, nivelObjetivo, listado);
            this.listarNivelR(nodo.getHijoDerecho(), nivelActual + 1, nivelObjetivo, listado);
        }
    }

    public int nodosConSoloUnHijoNivel(int nivel) {
        return this.nodosConSoloUnHijoNivel(this.raiz, 0, nivel);
    }

    private int nodosConSoloUnHijoNivel(NodoBinario<T> nodo, int nivelActual, int nivelObjetivo) {
        if (NodoBinario.esNodoVacio(nodo)) {
            return 0;
        } else {
            int ctd = 0;
            if (nivelActual == nivelObjetivo) {
                if (nodo.esVacioHijoIzquierdo() && !nodo.esVacioHijoDerecho()) {
                    ++ctd;
                }

                if (!nodo.esVacioHijoIzquierdo() && nodo.esVacioHijoDerecho()) {
                    ++ctd;
                }
            }

            this.nodosConSoloUnHijoNivel(nodo.getHijoIzquierdo(), nivelActual++, nivelObjetivo);
            this.nodosConSoloUnHijoNivel(nodo.getHijoDerecho(), nivelActual++, nivelObjetivo);
            return ctd;
        }
    }

    public int nodosParaSerCompleto() {
        if (this.esArbolVacio()) {
            return 0;
        } else {
            int nodosNecesarios = (1 << this.altura()) - 1;
            return nodosNecesarios - this.size();
        }
    }

    public boolean esArbolDegenerado() {
        if (this.esArbolVacio()) {
            return true;
        } else {
            for(NodoBinario<T> nodo = this.raiz; !NodoBinario.esNodoVacio(nodo); nodo = !nodo.esVacioHijoIzquierdo() ? nodo.getHijoIzquierdo() : nodo.getHijoDerecho()) {
                if (!nodo.esVacioHijoIzquierdo() && !nodo.esVacioHijoDerecho()) {
                    return false;
                }

                if (nodo.esHoja()) {
                    return true;
                }
            }

            return true;
        }
    }

    public boolean esArbolDegeneradoR() {
        return this.esArbolDegeneradoR(this.raiz);
    }

    private boolean esArbolDegeneradoR(NodoBinario<T> nodo) {
        if (!NodoBinario.esNodoVacio(nodo) && !nodo.esHoja()) {
            return !nodo.esVacioHijoIzquierdo() && !nodo.esVacioHijoDerecho() ? false : this.esArbolDegeneradoR(!nodo.esVacioHijoIzquierdo() ? nodo.getHijoIzquierdo() : nodo.getHijoDerecho());
        } else {
            return true;
        }
    }

    public boolean esArbolLleno() {
        if (this.esArbolVacio()) {
            return true;
        } else {
            Queue<NodoBinario<T>> cola = new LinkedList();
            cola.offer(this.raiz);

            while(!cola.isEmpty()) {
                NodoBinario<T> nodo = (NodoBinario)cola.poll();
                if (!nodo.esHoja()) {
                    if (nodo.esVacioHijoIzquierdo() != nodo.esVacioHijoDerecho()) {
                        return false;
                    }

                    if (!nodo.esVacioHijoIzquierdo()) {
                        cola.offer(nodo.getHijoIzquierdo());
                        cola.offer(nodo.getHijoDerecho());
                    }
                }
            }

            return true;
        }
    }

    public boolean esArbolLlenoR() {
        return this.esArbolLlenoR(this.raiz);
    }

    private boolean esArbolLlenoR(NodoBinario<T> nodo) {
        if (NodoBinario.esNodoVacio(nodo)) {
            return true;
        } else if (nodo.esHoja()) {
            return true;
        } else if (!nodo.esVacioHijoIzquierdo() && !nodo.esVacioHijoDerecho()) {
            return this.esArbolLlenoR(nodo.getHijoIzquierdo()) && this.esArbolLlenoR(nodo.getHijoDerecho());
        } else {
            return false;
        }
    }

    public int contarNodosEnNivel(int nivelObjetivo) {
        if (this.esArbolVacio()) {
            return 0;
        } else {
            return nivelObjetivo < 0 ? 0 : this.contarNodosEnNivelRecursivo(this.raiz, 0, nivelObjetivo);
        }
    }

    private int contarNodosEnNivelRecursivo(NodoBinario<T> nodo, int nivelActual, int nivelObjetivo) {
        if (NodoBinario.esNodoVacio(nodo)) {
            return 0;
        } else if (nivelActual == nivelObjetivo) {
            return 1;
        } else {
            return nivelActual > nivelObjetivo ? 0 : this.contarNodosEnNivelRecursivo(nodo.getHijoIzquierdo(), nivelActual + 1, nivelObjetivo) + this.contarNodosEnNivelRecursivo(nodo.getHijoDerecho(), nivelActual + 1, nivelObjetivo);
        }
    }
    
    public JPanel getdibujo() {
        return new ArbolExpresionGrafico(this);
    }
}
