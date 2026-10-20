package sitracmet.modelo;

import java.time.LocalDate;

public class InstrumentoElectrico extends InstrumentoMedicion {
    private final String rangoElectrico;

    public InstrumentoElectrico(int id, String codigo, String marca, String modelo,
                                String nroSerie, EstadoInstrumento estado,
                                LocalDate fechaProximaCalibracion, String rangoElectrico) {
        super(id, codigo, marca, modelo, nroSerie, estado, fechaProximaCalibracion);
        if (rangoElectrico == null || rangoElectrico.isBlank()) {
            throw new IllegalArgumentException("El rango eléctrico es obligatorio");
        }
        this.rangoElectrico = rangoElectrico.trim();
    }

    @Override
    public String descripcionTipo() {
        return "Instrumento eléctrico [" + rangoElectrico + "]";
    }

    public String getRangoElectrico() { return rangoElectrico; }
}
