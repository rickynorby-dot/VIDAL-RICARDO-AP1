package sitracmet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import sitracmet.excepcion.PersistenciaException;

public class AsignacionDAO {
    public void insertar(int idInstrumento, int idResponsable, String condicion)
            throws PersistenciaException {
        String sql = """
            INSERT INTO asignacion
            (id_instrumento, id_responsable, condicion_entrega)
            VALUES (?, ?, ?)
            """;
        try (Connection cn = ConexionBD.abrir();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idInstrumento);
            ps.setInt(2, idResponsable);
            ps.setString(3, condicion);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException(
                    "No fue posible registrar la asignación en MySQL", e);
        }
    }

    public void cerrarAsignacionAbierta(int idInstrumento, String condicionDevolucion)
            throws PersistenciaException {
        String sql = """
            UPDATE asignacion
               SET fecha_devolucion = NOW(), condicion_devolucion = ?
             WHERE id_instrumento = ? AND fecha_devolucion IS NULL
            """;
        try (Connection cn = ConexionBD.abrir();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, condicionDevolucion);
            ps.setInt(2, idInstrumento);
            if (ps.executeUpdate() == 0) {
                throw new PersistenciaException(
                        "No existe una asignación abierta para cerrar", null);
            }
        } catch (SQLException e) {
            throw new PersistenciaException(
                    "No fue posible registrar la devolución en MySQL", e);
        }
    }
}
