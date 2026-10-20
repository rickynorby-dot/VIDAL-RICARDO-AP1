package sitracmet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import sitracmet.modelo.InstrumentoMedicion;

public class InstrumentoDAO {
    public void insertar(InstrumentoMedicion instrumento, int idTipo, int idSector) throws SQLException {
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
        }
    }

    public void actualizarEstado(InstrumentoMedicion instrumento) throws SQLException {
        String sql = "UPDATE instrumento SET estado=? WHERE codigo=?";
        try (Connection cn = ConexionBD.abrir();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, instrumento.getEstado().name());
            ps.setString(2, instrumento.getCodigo());
            ps.executeUpdate();
        }
    }
}
