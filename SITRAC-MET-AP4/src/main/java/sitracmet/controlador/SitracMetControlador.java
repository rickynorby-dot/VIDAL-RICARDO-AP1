package sitracmet.controlador;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import sitracmet.dao.AsignacionDAO;
import sitracmet.dao.CalibracionDAO;
import sitracmet.dao.RepositorioInstrumentos;
import sitracmet.excepcion.CalibracionVencidaException;
import sitracmet.excepcion.CodigoDuplicadoException;
import sitracmet.excepcion.InstrumentoNoDisponibleException;
import sitracmet.excepcion.InstrumentoNoEncontradoException;
import sitracmet.excepcion.PersistenciaException;
import sitracmet.modelo.Asignacion;
import sitracmet.modelo.Calibracion;
import sitracmet.modelo.EstadoInstrumento;
import sitracmet.modelo.InstrumentoElectrico;
import sitracmet.modelo.InstrumentoMecanico;
import sitracmet.modelo.InstrumentoMedicion;
import sitracmet.modelo.Responsable;
import sitracmet.modelo.ResultadoCalibracion;
import sitracmet.modelo.Sector;
import sitracmet.servicio.GestorInstrumentos;
import sitracmet.util.AuditoriaArchivo;
import sitracmet.vista.ConsolaVista;

public class SitracMetControlador {
    // Arreglo: tamaño fijo y conocido para las opciones de menú.
    private static final String[] OPCIONES_MENU = {
        "1. Registrar instrumento eléctrico",
        "2. Registrar instrumento mecánico",
        "3. Listar instrumentos en memoria",
        "4. Buscar instrumento por código",
        "5. Registrar calibración",
        "6. Asignar instrumento",
        "7. Registrar devolución",
        "8. Consultar asignaciones",
        "9. Consultar instrumentos persistidos en MySQL",
        "10. Ver últimas acciones de auditoría",
        "0. Salir"
    };

    private final ConsolaVista vista;
    private final GestorInstrumentos gestor;
    private final RepositorioInstrumentos repositorioInstrumentos;
    private final CalibracionDAO calibracionDAO;
    private final AsignacionDAO asignacionDAO;
    private final AuditoriaArchivo auditoria;
    private final boolean modoDemo;
    private final Responsable responsableDemo;
    private int siguienteId = 10;

    public SitracMetControlador(ConsolaVista vista,
                                GestorInstrumentos gestor,
                                RepositorioInstrumentos repositorioInstrumentos,
                                CalibracionDAO calibracionDAO,
                                AsignacionDAO asignacionDAO,
                                boolean modoDemo) {
        this.vista = vista;
        this.gestor = gestor;
        this.repositorioInstrumentos = repositorioInstrumentos;
        this.calibracionDAO = calibracionDAO;
        this.asignacionDAO = asignacionDAO;
        this.modoDemo = modoDemo;
        this.auditoria = new AuditoriaArchivo(Path.of("data", "auditoria-sitrac.log"));
        this.responsableDemo = new Responsable(
                1, "L-1001", "Juan Pérez", "juan.perez@empresa.local",
                new Sector(1, "Mantenimiento"));
    }

    public void ejecutar() {
        cargarDatosIniciales();
        boolean continuar = true;
        while (continuar) {
            vista.mostrarMenu(OPCIONES_MENU);
            try {
                int opcion = vista.solicitarOpcion();
                switch (opcion) {
                    case 1 -> altaElectrico();
                    case 2 -> altaMecanico();
                    case 3 -> vista.mostrarInstrumentos(gestor.listarOrdenadosPorCodigo());
                    case 4 -> buscarInstrumento();
                    case 5 -> registrarCalibracion();
                    case 6 -> asignarInstrumento();
                    case 7 -> devolverInstrumento();
                    case 8 -> vista.mostrarAsignaciones(gestor.getAsignaciones());
                    case 9 -> consultarMySQL();
                    case 10 -> vista.mostrarLineas("Últimas acciones de auditoría:", auditoria.leerUltimos(10));
                    case 0 -> continuar = false;
                    default -> vista.error("Opción fuera de rango");
                }
            } catch (NumberFormatException | DateTimeParseException e) {
                vista.error("Dato inválido. Revise el formato ingresado");
            } catch (IllegalArgumentException | CodigoDuplicadoException |
                     InstrumentoNoEncontradoException | InstrumentoNoDisponibleException |
                     CalibracionVencidaException | PersistenciaException e) {
                vista.error(e.getMessage());
            } catch (IOException e) {
                vista.error("No fue posible acceder al archivo de auditoría: " + e.getMessage());
            }
        }
        vista.informar("SITRAC-MET finalizado correctamente.");
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

    private void altaElectrico() throws CodigoDuplicadoException, PersistenciaException, IOException {
        InstrumentoElectrico instrumento = new InstrumentoElectrico(
                siguienteId++, vista.leerTexto("Código: "), vista.leerTexto("Marca: "),
                vista.leerTexto("Modelo: "), vista.leerTexto("Nro. de serie: "),
                EstadoInstrumento.DISPONIBLE,
                vista.leerFecha("Próximo vencimiento (AAAA-MM-DD): "),
                vista.leerTexto("Rango eléctrico: "));
        gestor.agregarInstrumento(instrumento);
        if (!modoDemo) repositorioInstrumentos.insertar(instrumento, 1, 1);
        registrarAuditoria("Alta instrumento " + instrumento.getCodigo());
        vista.informar("Instrumento eléctrico registrado.");
    }

    private void altaMecanico() throws CodigoDuplicadoException, PersistenciaException, IOException {
        InstrumentoMecanico instrumento = new InstrumentoMecanico(
                siguienteId++, vista.leerTexto("Código: "), vista.leerTexto("Marca: "),
                vista.leerTexto("Modelo: "), vista.leerTexto("Nro. de serie: "),
                EstadoInstrumento.DISPONIBLE,
                vista.leerFecha("Próximo vencimiento (AAAA-MM-DD): "),
                vista.leerTexto("Rango mecánico: "));
        gestor.agregarInstrumento(instrumento);
        if (!modoDemo) repositorioInstrumentos.insertar(instrumento, 2, 1);
        registrarAuditoria("Alta instrumento " + instrumento.getCodigo());
        vista.informar("Instrumento mecánico registrado.");
    }

    private void buscarInstrumento() throws InstrumentoNoEncontradoException {
        String codigo = vista.leerTexto("Código a buscar: ");
        vista.informar(gestor.buscarPorCodigo(codigo).toString());
    }

    private void registrarCalibracion()
            throws InstrumentoNoEncontradoException, PersistenciaException, IOException {
        String codigo = vista.leerTexto("Código de instrumento: ");
        int opcion = vista.leerEntero("Resultado (1=APTO, 2=NO_APTO): ");
        ResultadoCalibracion resultado = opcion == 1
                ? ResultadoCalibracion.APTO : ResultadoCalibracion.NO_APTO;
        String certificado = vista.leerTexto("Nro. certificado: ");
        String laboratorio = vista.leerTexto("Laboratorio: ");
        LocalDate vencimiento = vista.leerFecha("Próximo vencimiento (AAAA-MM-DD): ");
        Calibracion c = gestor.registrarCalibracion(
                codigo, resultado, certificado, laboratorio, vencimiento);
        InstrumentoMedicion i = gestor.buscarPorCodigo(codigo);
        if (!modoDemo) {
            calibracionDAO.insertar(c, i.getId(), 1);
            repositorioInstrumentos.actualizarEstado(i);
        }
        registrarAuditoria("Calibración " + codigo + " resultado=" + resultado);
        vista.informar("Calibración registrada.");
    }

    private void asignarInstrumento()
            throws InstrumentoNoEncontradoException, InstrumentoNoDisponibleException,
                   CalibracionVencidaException, PersistenciaException, IOException {
        String codigo = vista.leerTexto("Código de instrumento: ");
        String condicion = vista.leerTexto("Condición de entrega: ");
        Asignacion a = gestor.asignar(codigo, responsableDemo, condicion);
        if (!modoDemo) {
            asignacionDAO.insertar(a.getInstrumento().getId(), responsableDemo.getId(), condicion);
            repositorioInstrumentos.actualizarEstado(a.getInstrumento());
        }
        registrarAuditoria("Asignación " + codigo + " a " + responsableDemo.getLegajo());
        vista.informar("Asignación registrada con id " + a.getId());
    }

    private void devolverInstrumento()
            throws InstrumentoNoEncontradoException, InstrumentoNoDisponibleException,
                   PersistenciaException, IOException {
        String codigo = vista.leerTexto("Código de instrumento: ");
        String condicion = vista.leerTexto("Condición de devolución: ");
        InstrumentoMedicion i = gestor.buscarPorCodigo(codigo);
        gestor.devolver(codigo, condicion);
        if (!modoDemo) {
            asignacionDAO.cerrarAsignacionAbierta(i.getId(), condicion);
            repositorioInstrumentos.actualizarEstado(i);
        }
        registrarAuditoria("Devolución " + codigo);
        vista.informar("Devolución registrada.");
    }

    private void consultarMySQL() throws PersistenciaException {
        if (modoDemo) {
            vista.informar("Modo demo: la consulta MySQL está deshabilitada. Ejecute sin --demo para usar JDBC.");
            return;
        }
        vista.mostrarInstrumentosBD(repositorioInstrumentos.listarTodos());
    }

    private void registrarAuditoria(String accion) throws IOException {
        auditoria.registrar(accion);
    }
}
