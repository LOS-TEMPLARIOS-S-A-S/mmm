package co.edu.uniremington.kiarapuello.Moto.controller;

import co.edu.uniremington.kiarapuello.Moto.Model.Moto;
import co.edu.uniremington.kiarapuello.Moto.Service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/motos")
public class MotoController {
    private final MotoService motoService;

    @Autowired
    public MotoController(MotoService motoService) {
        this.motoService = motoService;
    }

    // Página principal con formulario
    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("motos", motoService.listar());
        model.addAttribute("stats", motoService.obtenerEstadisticas());
        return "index";
    }

    // Registrar una moto
    @PostMapping("/registrar")
    public String registrarMoto(
            @RequestParam String placa,
            @RequestParam String propietario,
            @RequestParam String cedulaPropietario,
            @RequestParam String marca,
            @RequestParam String modelo,
            @RequestParam String color,
            @RequestParam int anio,
            @RequestParam String estado,
            @RequestParam(required = false) String descripcion,
            RedirectAttributes redirectAttributes
    ) {
        Moto moto = new Moto(
            placa.toUpperCase(), propietario, cedulaPropietario,
            marca, modelo, color, anio, estado,
            LocalDate.now(),
            descripcion != null ? descripcion : ""
        );
        motoService.insertar(moto);
        redirectAttributes.addFlashAttribute("mensaje", "Moto registrada exitosamente");
        redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        return "redirect:/motos";
    }

    // Buscar una moto por placa
    @GetMapping("/buscar")
    public String buscarMoto(@RequestParam String placa, Model model) {
        Moto moto = motoService.buscar(placa.toUpperCase());
        model.addAttribute("moto", moto);
        model.addAttribute("placaBuscada", placa.toUpperCase());
        model.addAttribute("stats", motoService.obtenerEstadisticas());
        return "index";
    }

    // Listar todas las motos
    @GetMapping("/listar")
    public String listarMotos(@RequestParam(required = false) String filtro, Model model) {
        if (filtro != null && !filtro.isEmpty()) {
            model.addAttribute("motos", motoService.filtrarPorEstado(filtro));
            model.addAttribute("filtroActivo", filtro);
        } else {
            model.addAttribute("motos", motoService.listar());
        }
        model.addAttribute("stats", motoService.obtenerEstadisticas());
        return "lista-motos";
    }

    // Eliminar una moto por placa
    @PostMapping("/eliminar")
    public String eliminarMoto(@RequestParam String placa, RedirectAttributes redirectAttributes) {
        Moto moto = motoService.buscar(placa.toUpperCase());
        if (moto != null) {
            motoService.eliminar(placa.toUpperCase());
            redirectAttributes.addFlashAttribute("mensaje", "Moto eliminada del registro");
            redirectAttributes.addFlashAttribute("tipoMensaje", "warning");
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "No se encontró la moto con placa " + placa);
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/motos/listar";
    }

    // Marcar una moto como robada
    @PostMapping("/marcarRobada")
    public String marcarComoRobada(@RequestParam String placa, RedirectAttributes redirectAttributes) {
        boolean ok = motoService.marcarComoRobada(placa.toUpperCase());
        if (ok) {
            redirectAttributes.addFlashAttribute("mensaje", "¡Moto " + placa.toUpperCase() + " marcada como ROBADA!");
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "No se encontró la moto con placa " + placa);
            redirectAttributes.addFlashAttribute("tipoMensaje", "warning");
        }
        return "redirect:/motos/listar";
    }

    // Marcar una moto como recuperada
    @PostMapping("/marcarRecuperada")
    public String marcarComoRecuperada(@RequestParam String placa, RedirectAttributes redirectAttributes) {
        boolean ok = motoService.marcarComoRecuperada(placa.toUpperCase());
        if (ok) {
            redirectAttributes.addFlashAttribute("mensaje", "Moto " + placa.toUpperCase() + " marcada como recuperada.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "No se encontró la moto con placa " + placa);
            redirectAttributes.addFlashAttribute("tipoMensaje", "warning");
        }
        return "redirect:/motos/listar";
    }

    // Vista de estadísticas
    @GetMapping("/estadisticas")
    public String verEstadisticas(Model model) {
        model.addAttribute("stats", motoService.obtenerEstadisticas());
        model.addAttribute("robadas", motoService.listarRobadas());
        return "estadisticas";
    }

    // Ver detalle de una moto
    @GetMapping("/detalle")
    public String verDetalle(@RequestParam String placa, Model model) {
        Moto moto = motoService.buscar(placa.toUpperCase());
        model.addAttribute("moto", moto);
        return "detalle-moto";
    }
}
