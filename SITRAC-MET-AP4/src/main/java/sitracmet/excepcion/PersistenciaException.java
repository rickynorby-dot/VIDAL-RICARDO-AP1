package sitracmet.excepcion;

public class PersistenciaException extends Exception {
    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
