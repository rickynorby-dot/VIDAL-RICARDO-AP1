package sitracmet.app;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import sitracmet.excepcion.CalibracionVencidaException;
import sitracmet.excepcion.CodigoDuplicadoException;
import sitracmet.excepcion.InstrumentoNoDisponibleException;
import sitracmet.excepcion.InstrumentoNoEncontradoException;
import sitracmet.modelo.Asignacion;
import sitracmet.modelo.EstadoInstrumento;
import sitracmet.modelo.InstrumentoElectrico;
import sitracmet.modelo.InstrumentoMecanico;
import sitracmet.modelo.InstrumentoMedicion;
import sitracmet.modelo.Responsable;
import sitracmet.modelo.ResultadoCalibracion;
import sitracmet.modelo.Sector;
import sitracmet.servicio.GestorInstrumentos;

public class SitracMetApp {
    private final Scanner scanner = new Scanner(System.in);
    private final GestorInstrumentos gestor = new GestorInstrumentos();
    private final Responsable responsableDemo = new Responsable(
            1, "L-1001", "Juan Pérez", "juan.perez@empresa.local",
            new Sector(1, "Mantenimiento"));
    private int siguienteId = 10;

    public static void main(String[] args) {
        new SitracMetApp().iniciar();
    }

    private void iniciar() {
        cargarDatosIniciales();
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            try {
                int opcion = leerEntero("Seleccione una opción: ");
                switch (opcion) {
                    case 1 -> altaElectrico();
                    case 2 -> altaMecanico();
                    case 3 -> listarInstrumentos();
                    case 4 -> buscarInstrumento();
                    case 5 -> registrarCalibracion();
                    case 6 -> asignarInstrumento();
                    case 7 -> devolverInstrumento();
                    case 8 -> listarAsignaciones();
                    case 0 -> continuar = false;
                    default -> System.out.println("Opción fuera de rango.");
                }
            } catch (InputMismatchException | NumberFormatException | DateTimeParseException e) {
                System.out.println("Dato inválido. Revise el formato ingresado.");
                scanner.nextLine();
            } catch (IllegalArgumentException | CodigoDuplicadoException |
                     InstrumentoNoEncontradoException | InstrumentoNoDisponibleException |
                     CalibracionVencidaException e) {
                System.out.println("Operación no realizada: " + e.getMessage());
            }
        }
        System.out.println("SITRAC-MET finalizado correctamente.");
    }

    private void cargarDatosIniciales() {
        try {
            gestor.agregarInstrumento(new InstrumentoElectrico(
                    1, "INS-001", "Fluke", "179", "FL179-001",
                    EstadoInstrumento.DISPONIBLE, LocalDate.now().plusMonths(6), "0-1000 V"));
            gestor.agregarInstrumento(new InstrumentoMecanico(
                    2, "INS-002", "Wika", "232.50", "WK232-015",
                    EstadoInstrumento.DISPONIBLE, LocalDate.now().minusDays(1), "0-10 bar"));
        } catch (CodigoDuplicadoException e) {
            throw new IllegalStateException(e);
        }
    }

    private void mostrarMenu() {
        System.out.println("\n========================================");
        System.out.println("       SITRAC-MET - MENÚ PRINCIPAL");
        System.out.println("========================================");
        System.out.println("1. Registrar instrumento eléctrico");
        System.out.println("2. Registrar instrumento mecánico");
        System.out.println("3. Listar instrumentos ordenados por código");
        System.out.println("4. Buscar instrumento por código");
        System.out.println("5. Registrar calibración");
        System.out.println("6. Asignar instrumento");
        System.out.println("7. Registrar devolución");
        System.out.println("8. Consultar asignaciones");
        System.out.println("0. Salir");
    }

    private void altaElectrico() throws CodigoDuplicadoException {
        String codigo = leerTexto("Código: ");
        String marca = leerTexto("Marca: ");
        String modelo = leerTexto("Modelo: ");
        String serie = leerTexto("Nro. de serie: ");
        String rango = leerTexto("Rango eléctrico: ");
        LocalDate vencimiento = leerFecha("Próximo vencimiento (AAAA-MM-DD): ");
        gestor.agregarInstrumento(new InstrumentoElectrico(
                siguienteId++, codigo, marca, modelo, serie,
                EstadoInstrumento.DISPONIBLE, vencimiento, rango));
        System.out.println("Instrumento eléctrico registrado.");
    }

    private void altaMecanico() throws CodigoDuplicadoException {
        String codigo = leerTexto("Código: ");
        String marca = leerTexto("Marca: ");
        String modelo = leerTexto("Modelo: ");
        String serie = leerTexto("Nro. de serie: ");
        String rango = leerTexto("Rango mecánico: ");
        LocalDate vencimiento = leerFecha("Próximo vencimiento (AAAA-MM-DD): ");
        gestor.agregarInstrumento(new InstrumentoMecanico(
                siguienteId++, codigo, marca, modelo, serie,
                EstadoInstrumento.DISPONIBLE, vencimiento, rango));
        System.out.println("Instrumento mecánico registrado.");
    }

    private void listarInstrumentos() {
        List<InstrumentoMedicion> instrumentos = gestor.listarOrdenadosPorCodigo();
        System.out.println("\nCódigo | Tipo | Marca/Modelo | Estado | Calibración");
        for (InstrumentoMedicion instrumento : instrumentos) {
            // Polimorfismo: descripcionTipo() ejecuta la implementación de la subclase real.
            System.out.println(instrumento);
        }
    }

    private void buscarInstrumento() throws InstrumentoNoEncontradoException {
        String codigo = leerTexto("Código a buscar: ");
        System.out.println(gestor.buscarPorCodigo(codigo));
    }

    private void registrarCalibracion() throws InstrumentoNoEncontradoException {
        String codigo = leerTexto("Código de instrumento: ");
        int opcion = leerEntero("Resultado (1=APTO, 2=NO_APTO): ");
        ResultadoCalibracion resultado = opcion == 1
                ? ResultadoCalibracion.APTO : ResultadoCalibracion.NO_APTO;
        String certificado = leerTexto("Nro. certificado: ");
        String laboratorio = leerTexto("Laboratorio: ");
        LocalDate vencimiento = leerFecha("Próximo vencimiento (AAAA-MM-DD): ");
        gestor.registrarCalibracion(codigo, resultado, certificado, laboratorio, vencimiento);
        System.out.println("Calibración registrada.");
    }

    private void asignarInstrumento() throws InstrumentoNoEncontradoException,
            InstrumentoNoDisponibleException, CalibracionVencidaException {
        String codigo = leerTexto("Código de instrumento: ");
        String condicion = leerTexto("Condición de entrega: ");
        Asignacion a = gestor.asignar(codigo, responsableDemo, condicion);
        System.out.println("Asignación registrada con id " + a.getId()
                + " para " + a.getResponsable().getNombre());
    }

    private void devolverInstrumento() throws InstrumentoNoEncontradoException,
            InstrumentoNoDisponibleException {
        String codigo = leerTexto("Código de instrumento: ");
        String condicion = leerTexto("Condición de devolución: ");
        gestor.devolver(codigo, condicion);
        System.out.println("Devolución registrada.");
    }

    private void listarAsignaciones() {
        if (gestor.getAsignaciones().isEmpty()) {
            System.out.println("No hay asignaciones registradas.");
            return;
        }
        for (Asignacion a : gestor.getAsignaciones()) {
            System.out.println("#" + a.getId() + " | " + a.getInstrumento().getCodigo()
                    + " | " + a.getResponsable().getNombre()
                    + " | " + (a.estaAbierta() ? "ABIERTA" : "CERRADA"));
        }
    }

    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        String valor = scanner.nextLine().trim();
        return Integer.parseInt(valor);
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        String valor = scanner.nextLine().trim();
        if (valor.isBlank()) throw new IllegalArgumentException("El valor no puede estar vacío");
        return valor;
    }

    private LocalDate leerFecha(String mensaje) {
        return LocalDate.parse(leerTexto(mensaje));
    }
}
