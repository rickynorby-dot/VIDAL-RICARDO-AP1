package sitracmet.modelo;
import java.time.LocalDate;
public class InstrumentoMecanico extends InstrumentoMedicion {
    private String rangoMecanico;
    public InstrumentoMecanico(int id, String codigo, String marca, String modelo, String nroSerie,
        EstadoInstrumento estado, LocalDate vencimiento, String rangoMecanico) {
        super(id,codigo,marca,modelo,nroSerie,estado,vencimiento); this.rangoMecanico=rangoMecanico;
    }
    @Override public String descripcionTipo() { return "Instrumento mecánico - " + rangoMecanico; }
}
