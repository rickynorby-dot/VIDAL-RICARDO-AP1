package sitracmet.app;

import java.util.Arrays;
import java.util.Scanner;
import sitracmet.controlador.SitracMetControlador;
import sitracmet.dao.AsignacionDAO;
import sitracmet.dao.CalibracionDAO;
import sitracmet.dao.InstrumentoDAO;
import sitracmet.servicio.GestorInstrumentos;
import sitracmet.vista.ConsolaVista;

public class SitracMetApp {
    public static void main(String[] args) {
        boolean modoDemo = Arrays.asList(args).contains("--demo");
        ConsolaVista vista = new ConsolaVista(new Scanner(System.in));
        SitracMetControlador controlador = new SitracMetControlador(
                vista,
                new GestorInstrumentos(),
                new InstrumentoDAO(),
                new CalibracionDAO(),
                new AsignacionDAO(),
                modoDemo);
        if (modoDemo) {
            vista.informar("SITRAC-MET iniciado en modo demo (sin conexión MySQL).\n"
                    + "La versión normal utiliza JDBC/MySQL con las variables SITRAC_DB_*.");
        }
        controlador.ejecutar();
    }
}
