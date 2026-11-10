package sitracmet.modelo;

import java.time.LocalDateTime;

public class Asignacion {
    private final int id;
    private final InstrumentoMedicion instrumento;
    private final Responsable responsable;
    private final LocalDateTime fechaEntrega;
    private LocalDateTime fechaDevolucion;
    private final String condicionEntrega;
    private String condicionDevolucion;

    public Asignacion(int id, InstrumentoMedicion instrumento, Responsable responsable,
                      String condicionEntrega) {
        this.id = id;
        this.instrumento = instrumento;
        this.responsable = responsable;
        this.condicionEntrega = condicionEntrega;
        this.fechaEntrega = LocalDateTime.now();
    }

    public boolean estaAbierta() { return fechaDevolucion == null; }

    public void cerrar(String condicionDevolucion) {
        this.condicionDevolucion = condicionDevolucion;
        this.fechaDevolucion = LocalDateTime.now();
    }

    public int getId() { return id; }
    public InstrumentoMedicion getInstrumento() { return instrumento; }
    public Responsable getResponsable() { return responsable; }
    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public LocalDateTime getFechaDevolucion() { return fechaDevolucion; }
    public String getCondicionEntrega() { return condicionEntrega; }
    public String getCondicionDevolucion() { return condicionDevolucion; }
}
