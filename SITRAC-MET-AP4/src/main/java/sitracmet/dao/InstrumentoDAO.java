package sitracmet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import sitracmet.dto.InstrumentoPersistido;
import sitracmet.excepcion.PersistenciaException;
import sitracmet.modelo.InstrumentoMedicion;

public class InstrumentoDAO implements RepositorioInstrumentos {

    @Override
    public void insertar(InstrumentoMedicion instrumento, int idTipo, int idSector)
            throws PersistenciaException {
        String sql = """
            INSERT INTO instrumento
            (id_tipo, id_sector_referencia, codigo, marca, modelo, nro_serie, estado, activo)
            VALUES (?, ?, ?, ?, ?, ?, ?, TRUE)
            """;
        try (Connection cn = ConexionBD.abrir();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idTipo);
            ps.setInt(2, idSector);
            ps.setString(3, instrumento.getCodigo());
            ps.setString(4, instrumento.getMarca());
            ps.setString(5, instrumento.getModelo());
            ps.setString(6, instrumento.getNroSerie());
            ps.setString(7, instrumento.getEstado().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException(
                    "No fue posible insertar el instrumento en MySQL", e);
        }
    }

    @Override
    public void actualizarEstado(InstrumentoMedicion instrumento)
            throws PersistenciaException {
        String sql = "UPDATE instrumento SET estado=? WHERE codigo=?";
        try (Connection cn = ConexionBD.abrir();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, instrumento.getEstado().name());
            ps.setString(2, instrumento.getCodigo());
            if (ps.executeUpdate() == 0) {
                throw new PersistenciaException(
                        "El instrumento no existe en la base de datos", null);
            }
        } catch (SQLException e) {
            throw new PersistenciaException(
                    "No fue posible actualizar el estado del instrumento", e);
        }
    }

    @Override
    public List<InstrumentoPersistido> listarTodos() throws PersistenciaException {
        String sql = """
            SELECT i.id_instrumento, i.codigo, ti.nombre AS tipo,
                   i.marca, i.modelo, i.estado,
                   MAX(c.proximo_vencimiento) AS proximo_vencimiento
              FROM instrumento i
              JOIN tipo_instrumento ti ON ti.id_tipo = i.id_tipo
              LEFT JOIN calibracion c ON c.id_instrumento = i.id_instrumento
             WHERE i.activo = TRUE
             GROUP BY i.id_instrumento, i.codigo, ti.nombre,
                      i.marca, i.modelo, i.estado
             ORDER BY i.codigo
            """;
        List<InstrumentoPersistido> resultado = new ArrayList<>();
        try (Connection cn = ConexionBD.abrir();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                java.sql.Date fecha = rs.getDate("proximo_vencimiento");
                LocalDate vencimiento = fecha == null ? null : fecha.toLocalDate();
                resultado.add(new InstrumentoPersistido(
                        rs.getInt("id_instrumento"),
                        rs.getString("codigo"),
                        rs.getString("tipo"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("estado"),
                        vencimiento));
            }
            return resultado;
        } catch (SQLException e) {
            throw new PersistenciaException(
                    "No fue posible consultar los instrumentos en MySQL", e);
        }
    }
}
