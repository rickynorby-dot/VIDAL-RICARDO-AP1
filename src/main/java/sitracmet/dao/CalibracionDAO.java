package sitracmet.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import sitracmet.modelo.Calibracion;

public class CalibracionDAO {
    public void insertar(Calibracion calibracion, int idInstrumento, Integer idLaboratorio)
            throws SQLException {
        String sql = """
            INSERT INTO calibracion
            (id_instrumento, id_laboratorio, fecha_calibracion, resultado,
             nro_certificado, proximo_vencimiento, observaciones)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
        try (Connection cn = ConexionBD.abrir();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idInstrumento);
            if (idLaboratorio == null) ps.setNull(2, java.sql.Types.INTEGER);
            else ps.setInt(2, idLaboratorio);
            ps.setDate(3, Date.valueOf(calibracion.getFecha()));
            ps.setString(4, calibracion.getResultado().name());
            ps.setString(5, calibracion.getNroCertificado());
            ps.setDate(6, Date.valueOf(calibracion.getProximoVencimiento()));
            ps.setString(7, "Registro generado por prototipo AP3");
            ps.executeUpdate();
        }
    }
}
