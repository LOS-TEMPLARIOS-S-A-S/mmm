package co.edu.uniremington.kiarapuello.Moto.Service;

import co.edu.uniremington.kiarapuello.Moto.Model.ArbolABB;
import co.edu.uniremington.kiarapuello.Moto.Model.Moto;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class MotoService {

    // Delega toda la gestión estructural al ArbolABB
    private final ArbolABB arbol;

    public MotoService() {
        this.arbol = new ArbolABB();
    }

    // ─── CRUD básico ─────────────────────────────────────────────

    public void insertar(Moto moto) {
        arbol.insertar(moto);
    }

    public Moto buscar(String placa) {
        return arbol.buscar(placa.toUpperCase());
    }

    public List<Moto> listar() {
        return arbol.listarInorden();
    }

    public void eliminar(String placa) {
        arbol.eliminar(placa.toUpperCase());
    }

    // ─── Operaciones de estado ───────────────────────────────────

    public boolean marcarComoRobada(String placa) {
        Moto moto = arbol.buscar(placa.toUpperCase());
        if (moto != null) {
            moto.setEstado("robada");
            return true;
        }
        return false;
    }

    public boolean marcarComoRecuperada(String placa) {
        Moto moto = arbol.buscar(placa.toUpperCase());
        if (moto != null) {
            moto.setEstado("recuperada");
            return true;
        }
        return false;
    }

    // ─── Consultas avanzadas ─────────────────────────────────────

    public List<Moto> listarRobadas() {
        return filtrarPorEstado("robada");
    }

    public List<Moto> filtrarPorEstado(String estado) {
        List<Moto> resultado = new ArrayList<>();
        for (Moto m : arbol.listarInorden()) {
            if (m.getEstado().equalsIgnoreCase(estado)) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    public Map<String, Integer> obtenerEstadisticas() {
        List<Moto> todas = arbol.listarInorden();
        int robadas = 0, normales = 0, recuperadas = 0;
        for (Moto m : todas) {
            switch (m.getEstado().toLowerCase()) {
                case "robada":     robadas++;     break;
                case "recuperada": recuperadas++; break;
                default:           normales++;    break;
            }
        }
        Map<String, Integer> stats = new HashMap<>();
        stats.put("total",       todas.size());
        stats.put("robadas",     robadas);
        stats.put("normales",    normales);
        stats.put("recuperadas", recuperadas);
        return stats;
    }
}
