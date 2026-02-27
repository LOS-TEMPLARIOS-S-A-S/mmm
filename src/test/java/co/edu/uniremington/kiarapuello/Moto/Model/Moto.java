package co.edu.uniremington.kiarapuello.Moto.Model;

import java.time.LocalDate;

// Modelo de la Moto Robada
public class Moto {
    private String placa;
    private String propietario;
    private String cedulaPropietario;
    private String marca;
    private String modelo;
    private String color;
    private int anio;
    private String estado; // "normal" o "robada"
    private LocalDate fechaReporte;
    private String descripcion;

    public Moto(String placa, String propietario, String cedulaPropietario,
                String marca, String modelo, String color, int anio,
                String estado, LocalDate fechaReporte, String descripcion) {
        this.placa = placa;
        this.propietario = propietario;
        this.cedulaPropietario = cedulaPropietario;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.anio = anio;
        this.estado = estado;
        this.fechaReporte = fechaReporte;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getPropietario() { return propietario; }
    public void setPropietario(String propietario) { this.propietario = propietario; }

    public String getCedulaPropietario() { return cedulaPropietario; }
    public void setCedulaPropietario(String cedulaPropietario) { this.cedulaPropietario = cedulaPropietario; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getFechaReporte() { return fechaReporte; }
    public void setFechaReporte(LocalDate fechaReporte) { this.fechaReporte = fechaReporte; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
