package sitracmet.modelo;

import java.time.LocalDate;

public class Calibracion {
    private final int id;
    private final String codigoInstrumento;
    private final LocalDate fecha;
    private final ResultadoCalibracion resultado;
    private final String nroCertificado;
    private final String laboratorio;
    private final LocalDate proximoVencimiento;

    public Calibracion(int id, String codigoInstrumento, LocalDate fecha,
                       ResultadoCalibracion resultado, String nroCertificado,
                       String laboratorio, LocalDate proximoVencimiento) {
        this.id = id;
        this.codigoInstrumento = codigoInstrumento;
        this.fecha = fecha;
        this.resultado = resultado;
        this.nroCertificado = nroCertificado;
        this.laboratorio = laboratorio;
        this.proximoVencimiento = proximoVencimiento;
    }

    public int getId() { return id; }
    public String getCodigoInstrumento() { return codigoInstrumento; }
    public LocalDate getFecha() { return fecha; }
    public ResultadoCalibracion getResultado() { return resultado; }
    public String getNroCertificado() { return nroCertificado; }
    public String getLaboratorio() { return laboratorio; }
    public LocalDate getProximoVencimiento() { return proximoVencimiento; }
}
