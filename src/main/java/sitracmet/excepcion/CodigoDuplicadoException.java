package sitracmet.excepcion;

public class CodigoDuplicadoException extends Exception {
    public CodigoDuplicadoException(String codigo) {
        super("Ya existe un instrumento con código " + codigo);
    }
}
