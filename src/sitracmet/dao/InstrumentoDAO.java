package sitracmet.dao;
import java.sql.*;
public class InstrumentoDAO {
    public boolean puedeAsignarse(int idInstrumento) throws SQLException {
        String sql = """
          SELECT i.estado,
                 MAX(c.proximo_vencimiento) AS vencimiento
          FROM instrumento i
          LEFT JOIN calibracion c ON c.id_instrumento=i.id_instrumento
          WHERE i.id_instrumento=? AND i.activo=TRUE
          GROUP BY i.id_instrumento, i.estado
        """;
        try (Connection cn = ConexionBD.abrir(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idInstrumento);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return false;
                Date venc = rs.getDate("vencimiento");
                return "DISPONIBLE".equals(rs.getString("estado")) &&
                       (venc == null || !venc.toLocalDate().isBefore(java.time.LocalDate.now()));
            }
        }
    }
}
