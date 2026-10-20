package sitracmet.servicio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import sitracmet.excepcion.CalibracionVencidaException;
import sitracmet.excepcion.CodigoDuplicadoException;
import sitracmet.excepcion.InstrumentoNoDisponibleException;
import sitracmet.excepcion.InstrumentoNoEncontradoException;
import sitracmet.modelo.Asignacion;
import sitracmet.modelo.Calibracion;
import sitracmet.modelo.InstrumentoMedicion;
import sitracmet.modelo.Responsable;
import sitracmet.modelo.ResultadoCalibracion;

public class GestorInstrumentos {
    private final List<InstrumentoMedicion> instrumentos = new ArrayList<>();
    private final List<Calibracion> calibraciones = new ArrayList<>();
    private final List<Asignacion> asignaciones = new ArrayList<>();
    private int siguienteAsignacion = 1;
    private int siguienteCalibracion = 1;

    public void agregarInstrumento(InstrumentoMedicion instrumento) throws CodigoDuplicadoException {
        if (existeCodigo(instrumento.getCodigo())) {
            throw new CodigoDuplicadoException(instrumento.getCodigo());
        }
        instrumentos.add(instrumento);
    }

    private boolean existeCodigo(String codigo) {
        for (InstrumentoMedicion instrumento : instrumentos) {
            if (instrumento.getCodigo().equalsIgnoreCase(codigo)) {
                return true;
            }
        }
        return false;
    }

    // Búsqueda secuencial: recorre la lista hasta encontrar coincidencia.
    public InstrumentoMedicion buscarPorCodigo(String codigo) throws InstrumentoNoEncontradoException {
        for (InstrumentoMedicion instrumento : instrumentos) {
            if (instrumento.getCodigo().equalsIgnoreCase(codigo)) {
                return instrumento;
            }
        }
        throw new InstrumentoNoEncontradoException(codigo);
    }

    // Ordenación por inserción aplicada sobre una copia para no alterar la colección original.
    public List<InstrumentoMedicion> listarOrdenadosPorCodigo() {
        List<InstrumentoMedicion> copia = new ArrayList<>(instrumentos);
        Comparator<InstrumentoMedicion> comparador = Comparator.comparing(InstrumentoMedicion::getCodigo);
        for (int i = 1; i < copia.size(); i++) {
            InstrumentoMedicion actual = copia.get(i);
            int j = i - 1;
            while (j >= 0 && comparador.compare(copia.get(j), actual) > 0) {
                copia.set(j + 1, copia.get(j));
                j--;
            }
            copia.set(j + 1, actual);
        }
        return Collections.unmodifiableList(copia);
    }

    public Calibracion registrarCalibracion(String codigo, ResultadoCalibracion resultado,
                                             String certificado, String laboratorio,
                                             LocalDate proximoVencimiento)
            throws InstrumentoNoEncontradoException {
        InstrumentoMedicion instrumento = buscarPorCodigo(codigo);
        Calibracion calibracion = new Calibracion(siguienteCalibracion++, codigo,
                LocalDate.now(), resultado, certificado, laboratorio, proximoVencimiento);
        calibraciones.add(calibracion);
        if (resultado == ResultadoCalibracion.APTO) {
            instrumento.registrarVencimientoCalibracion(proximoVencimiento);
        } else {
            instrumento.enviarACalibracion();
        }
        return calibracion;
    }

    public Asignacion asignar(String codigo, Responsable responsable, String condicion)
            throws InstrumentoNoEncontradoException, InstrumentoNoDisponibleException,
                   CalibracionVencidaException {
        InstrumentoMedicion instrumento = buscarPorCodigo(codigo);
        if (!instrumento.estaDisponible()) {
            throw new InstrumentoNoDisponibleException(codigo);
        }
        if (!instrumento.calibracionVigente()) {
            throw new CalibracionVencidaException(codigo);
        }
        instrumento.asignar();
        Asignacion asignacion = new Asignacion(siguienteAsignacion++, instrumento, responsable, condicion);
        asignaciones.add(asignacion);
        return asignacion;
    }

    public void devolver(String codigo, String condicion)
            throws InstrumentoNoEncontradoException, InstrumentoNoDisponibleException {
        InstrumentoMedicion instrumento = buscarPorCodigo(codigo);
        for (Asignacion asignacion : asignaciones) {
            if (asignacion.estaAbierta()
                    && asignacion.getInstrumento().getCodigo().equalsIgnoreCase(codigo)) {
                asignacion.cerrar(condicion);
                instrumento.devolver();
                return;
            }
        }
        throw new InstrumentoNoDisponibleException(codigo);
    }

    public List<Asignacion> getAsignaciones() {
        return Collections.unmodifiableList(asignaciones);
    }

    public List<Calibracion> getCalibraciones() {
        return Collections.unmodifiableList(calibraciones);
    }
}
