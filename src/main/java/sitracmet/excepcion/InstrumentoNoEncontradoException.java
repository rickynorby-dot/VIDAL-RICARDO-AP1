package sitracmet.excepcion;

public class InstrumentoNoEncontradoException extends Exception {
    public InstrumentoNoEncontradoException(String codigo) {
        super("No se encontró el instrumento con código " + codigo);
    }
}
