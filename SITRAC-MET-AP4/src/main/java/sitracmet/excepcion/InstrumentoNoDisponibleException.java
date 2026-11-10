package sitracmet.excepcion;

public class InstrumentoNoDisponibleException extends Exception {
    public InstrumentoNoDisponibleException(String codigo) {
        super("El instrumento " + codigo + " no se encuentra disponible");
    }
}
