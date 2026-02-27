package co.edu.uniremington.kiarapuello.Moto.Model;

public class NodoABB {
    private Moto moto;
    private NodoABB izquierda, derecha;

    public NodoABB(Moto moto) {
        this.moto = moto;
        this.izquierda = null;
        this.derecha = null;
    }

    // Getters y Setters
    public Moto getMoto() {
        return moto;
    }

    public void setMoto(Moto moto) {
        this.moto = moto;
    }

    public NodoABB getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(NodoABB izquierda) {
        this.izquierda = izquierda;
    }

    public NodoABB getDerecha() {
        return derecha;
    }

    public void setDerecha(NodoABB derecha) {
        this.derecha = derecha;
    }
}

