package sitracmet.modelo;

import java.time.LocalDate;
import java.util.Objects;

public abstract class InstrumentoMedicion {
    private final int id;
    private final String codigo;
    private final String marca;
    private final String modelo;
    private final String nroSerie;
    private EstadoInstrumento estado;
    private LocalDate fechaProximaCalibracion;

    protected InstrumentoMedicion(int id, String codigo, String marca, String modelo,
                                  String nroSerie, EstadoInstrumento estado,
                                  LocalDate fechaProximaCalibracion) {
        if (id <= 0) {
            throw new IllegalArgumentException("El id debe ser mayor que cero");
        }
        this.id = id;
        this.codigo = validarTexto(codigo, "código");
        this.marca = validarTexto(marca, "marca");
        this.modelo = validarTexto(modelo, "modelo");
        this.nroSerie = validarTexto(nroSerie, "número de serie");
        this.estado = Objects.requireNonNull(estado, "El estado es obligatorio");
        this.fechaProximaCalibracion = fechaProximaCalibracion;
    }

    private static String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El " + campo + " no puede estar vacío");
        }
        return valor.trim();
    }

    public boolean estaDisponible() {
        return estado == EstadoInstrumento.DISPONIBLE;
    }

    public boolean calibracionVigente() {
        return fechaProximaCalibracion != null
                && !fechaProximaCalibracion.isBefore(LocalDate.now());
    }

    public void asignar() {
        this.estado = EstadoInstrumento.ASIGNADO;
    }

    public void devolver() {
        this.estado = EstadoInstrumento.DISPONIBLE;
    }

    public void enviarACalibracion() {
        this.estado = EstadoInstrumento.EN_CALIBRACION;
    }

    public void registrarVencimientoCalibracion(LocalDate nuevaFecha) {
        this.fechaProximaCalibracion = Objects.requireNonNull(nuevaFecha);
        this.estado = EstadoInstrumento.DISPONIBLE;
    }

    public abstract String descripcionTipo();

    public int getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public String getNroSerie() { return nroSerie; }
    public EstadoInstrumento getEstado() { return estado; }
    public LocalDate getFechaProximaCalibracion() { return fechaProximaCalibracion; }

    @Override
    public String toString() {
        return codigo + " | " + descripcionTipo() + " | " + marca + " " + modelo
                + " | " + estado + " | venc. calibración: "
                + (fechaProximaCalibracion == null ? "sin registrar" : fechaProximaCalibracion);
    }
}
