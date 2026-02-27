package co.edu.uniremington.kiarapuello.Moto.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Árbol Binario de Búsqueda (ABB) para gestionar motos.
 * Ordenado por placa de forma alfabética (insensible a mayúsculas).
 * Operaciones principales: insertar, buscar, eliminar, listar (inorden).
 */
public class ArbolABB {

    private NodoABB raiz;

    public ArbolABB() {
        this.raiz = null;
    }

    // ─── INSERTAR ────────────────────────────────────────────────
    public void insertar(Moto moto) {
        raiz = insertarRec(raiz, moto);
    }

    private NodoABB insertarRec(NodoABB nodo, Moto moto) {
        if (nodo == null) return new NodoABB(moto);

        int cmp = moto.getPlaca().compareToIgnoreCase(nodo.getMoto().getPlaca());
        if (cmp < 0)
            nodo.setIzquierda(insertarRec(nodo.getIzquierda(), moto));
        else if (cmp > 0)
            nodo.setDerecha(insertarRec(nodo.getDerecha(), moto));
        // Si es igual, actualiza la moto existente
        else
            nodo.setMoto(moto);

        return nodo;
    }

    // ─── BUSCAR ──────────────────────────────────────────────────
    public Moto buscar(String placa) {
        return buscarRec(raiz, placa.toUpperCase());
    }

    private Moto buscarRec(NodoABB nodo, String placa) {
        if (nodo == null) return null;

        int cmp = placa.compareToIgnoreCase(nodo.getMoto().getPlaca());
        if (cmp == 0) return nodo.getMoto();
        if (cmp < 0)  return buscarRec(nodo.getIzquierda(), placa);
        return             buscarRec(nodo.getDerecha(), placa);
    }

    // ─── ELIMINAR ────────────────────────────────────────────────
    public void eliminar(String placa) {
        raiz = eliminarRec(raiz, placa.toUpperCase());
    }

    private NodoABB eliminarRec(NodoABB nodo, String placa) {
        if (nodo == null) return null;

        int cmp = placa.compareToIgnoreCase(nodo.getMoto().getPlaca());
        if (cmp < 0) {
            nodo.setIzquierda(eliminarRec(nodo.getIzquierda(), placa));
        } else if (cmp > 0) {
            nodo.setDerecha(eliminarRec(nodo.getDerecha(), placa));
        } else {
            // Caso 1 y 2: nodo con un solo hijo o sin hijos
            if (nodo.getIzquierda() == null) return nodo.getDerecha();
            if (nodo.getDerecha()   == null) return nodo.getIzquierda();
            // Caso 3: nodo con dos hijos → sucesor inorden (mínimo del subárbol derecho)
            NodoABB sucesor = minimoNodo(nodo.getDerecha());
            nodo.setMoto(sucesor.getMoto());
            nodo.setDerecha(eliminarRec(nodo.getDerecha(), sucesor.getMoto().getPlaca()));
        }
        return nodo;
    }

    private NodoABB minimoNodo(NodoABB nodo) {
        while (nodo.getIzquierda() != null) nodo = nodo.getIzquierda();
        return nodo;
    }

    // ─── LISTAR INORDEN (orden alfabético por placa) ─────────────
    public List<Moto> listarInorden() {
        List<Moto> lista = new ArrayList<>();
        inorden(raiz, lista);
        return lista;
    }

    private void inorden(NodoABB nodo, List<Moto> lista) {
        if (nodo != null) {
            inorden(nodo.getIzquierda(), lista);
            lista.add(nodo.getMoto());
            inorden(nodo.getDerecha(), lista);
        }
    }

    // ─── UTILIDADES ──────────────────────────────────────────────
    public boolean estaVacio() {
        return raiz == null;
    }

    public int contarNodos() {
        return contarRec(raiz);
    }

    private int contarRec(NodoABB nodo) {
        if (nodo == null) return 0;
        return 1 + contarRec(nodo.getIzquierda()) + contarRec(nodo.getDerecha());
    }
}
