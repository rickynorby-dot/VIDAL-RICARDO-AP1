package sitracmet.vista;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import sitracmet.dto.InstrumentoPersistido;
import sitracmet.modelo.Asignacion;
import sitracmet.modelo.InstrumentoMedicion;

public class ConsolaVista {
    private final Scanner scanner;

    public ConsolaVista(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrarMenu(String[] opciones) {
        System.out.println("\n========================================");
        System.out.println("       SITRAC-MET - MENÚ PRINCIPAL");
        System.out.println("========================================");
        for (String opcion : opciones) {
            System.out.println(opcion);
        }
    }

    public int solicitarOpcion() {
        return leerEntero("Seleccione una opción: ");
    }

    public int leerEntero(String mensaje) {
        System.out.print(mensaje);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        String valor = scanner.nextLine().trim();
        if (valor.isBlank()) throw new IllegalArgumentException("El valor no puede estar vacío");
        return valor;
    }

    public LocalDate leerFecha(String mensaje) throws DateTimeParseException {
        return LocalDate.parse(leerTexto(mensaje));
    }

    public void mostrarInstrumentos(List<InstrumentoMedicion> instrumentos) {
        System.out.println("\nCódigo | Tipo | Marca/Modelo | Estado | Calibración");
        for (InstrumentoMedicion instrumento : instrumentos) {
            System.out.println(instrumento);
        }
    }

    public void mostrarInstrumentosBD(List<InstrumentoPersistido> instrumentos) {
        System.out.println("\nDatos recuperados desde MySQL:");
        System.out.println("Código | Tipo | Marca/Modelo | Estado | Próx. vencimiento");
        for (InstrumentoPersistido i : instrumentos) {
            System.out.println(i.codigo() + " | " + i.tipo() + " | "
                    + i.marca() + " " + i.modelo() + " | " + i.estado()
                    + " | " + (i.proximoVencimiento() == null ? "sin registrar" : i.proximoVencimiento()));
        }
    }

    public void mostrarAsignaciones(List<Asignacion> asignaciones) {
        if (asignaciones.isEmpty()) {
            System.out.println("No hay asignaciones registradas.");
            return;
        }
        for (Asignacion a : asignaciones) {
            System.out.println("#" + a.getId() + " | " + a.getInstrumento().getCodigo()
                    + " | " + a.getResponsable().getNombre()
                    + " | " + (a.estaAbierta() ? "ABIERTA" : "CERRADA"));
        }
    }

    public void mostrarLineas(String titulo, List<String> lineas) {
        System.out.println("\n" + titulo);
        if (lineas.isEmpty()) System.out.println("Sin registros.");
        else lineas.forEach(System.out::println);
    }

    public void informar(String mensaje) {
        System.out.println(mensaje);
    }

    public void error(String mensaje) {
        System.out.println("Operación no realizada: " + mensaje);
    }
}
