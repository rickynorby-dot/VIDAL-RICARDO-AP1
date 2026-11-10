package sitracmet.dao;

import java.util.List;
import sitracmet.dto.InstrumentoPersistido;
import sitracmet.excepcion.PersistenciaException;
import sitracmet.modelo.InstrumentoMedicion;

public interface RepositorioInstrumentos {
    void insertar(InstrumentoMedicion instrumento, int idTipo, int idSector)
            throws PersistenciaException;

    void actualizarEstado(InstrumentoMedicion instrumento)
            throws PersistenciaException;

    List<InstrumentoPersistido> listarTodos()
            throws PersistenciaException;
}
