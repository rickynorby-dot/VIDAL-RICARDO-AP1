package sitracmet.servicio;
import java.sql.SQLException;
import sitracmet.dao.InstrumentoDAO;
public class ServicioInstrumento {
    private final InstrumentoDAO dao = new InstrumentoDAO();
    public boolean validarAsignacion(int idInstrumento) throws SQLException {
        return dao.puedeAsignarse(idInstrumento);
    }
}
