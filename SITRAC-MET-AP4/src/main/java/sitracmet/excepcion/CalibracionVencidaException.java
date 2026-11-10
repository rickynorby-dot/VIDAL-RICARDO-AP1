package sitracmet.excepcion;

public class CalibracionVencidaException extends Exception {
    public CalibracionVencidaException(String codigo) {
        super("El instrumento " + codigo + " no posee una calibración vigente");
    }
}
