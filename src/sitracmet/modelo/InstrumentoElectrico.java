package sitracmet.modelo;
import java.time.LocalDate;
public class InstrumentoElectrico extends InstrumentoMedicion {
    private String rangoElectrico;
    public InstrumentoElectrico(int id, String codigo, String marca, String modelo, String nroSerie,
        EstadoInstrumento estado, LocalDate vencimiento, String rangoElectrico) {
        super(id,codigo,marca,modelo,nroSerie,estado,vencimiento); this.rangoElectrico=rangoElectrico;
    }
    @Override public String descripcionTipo() { return "Instrumento eléctrico - " + rangoElectrico; }
}
