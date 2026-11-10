package sitracmet.modelo;

import java.time.LocalDate;

public class InstrumentoMecanico extends InstrumentoMedicion {
    private final String rangoMecanico;

    public InstrumentoMecanico(int id, String codigo, String marca, String modelo,
                               String nroSerie, EstadoInstrumento estado,
                               LocalDate fechaProximaCalibracion, String rangoMecanico) {
        super(id, codigo, marca, modelo, nroSerie, estado, fechaProximaCalibracion);
        if (rangoMecanico == null || rangoMecanico.isBlank()) {
            throw new IllegalArgumentException("El rango mecánico es obligatorio");
        }
        this.rangoMecanico = rangoMecanico.trim();
    }

    @Override
    public String descripcionTipo() {
        return "Instrumento mecánico [" + rangoMecanico + "]";
    }

    public String getRangoMecanico() { return rangoMecanico; }
}
