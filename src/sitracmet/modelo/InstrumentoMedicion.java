package sitracmet.modelo;

import java.time.LocalDate;

public abstract class InstrumentoMedicion {
    private int id;
    private String codigo;
    private String marca;
    private String modelo;
    private String nroSerie;
    private EstadoInstrumento estado;
    private LocalDate fechaProximaCalibracion;

    protected InstrumentoMedicion(int id, String codigo, String marca, String modelo,
                                  String nroSerie, EstadoInstrumento estado,
                                  LocalDate fechaProximaCalibracion) {
        this.id = id; this.codigo = codigo; this.marca = marca; this.modelo = modelo;
        this.nroSerie = nroSerie; this.estado = estado;
        this.fechaProximaCalibracion = fechaProximaCalibracion;
    }

    public boolean estaDisponible() { return estado == EstadoInstrumento.DISPONIBLE; }
    public boolean calibracionVigente() {
        return fechaProximaCalibracion == null || !fechaProximaCalibracion.isBefore(LocalDate.now());
    }
    public abstract String descripcionTipo();

    public int getId() { return id; }
    public String getCodigo() { return codigo; }
    public EstadoInstrumento getEstado() { return estado; }
    public void setEstado(EstadoInstrumento estado) { this.estado = estado; }
}
