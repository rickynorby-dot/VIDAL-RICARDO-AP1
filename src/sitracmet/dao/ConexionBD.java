package sitracmet.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/sitrac_met?serverTimezone=America/Argentina/Buenos_Aires";
    private static final String USUARIO = "sitrac_app";
    private static final String CLAVE = "CAMBIAR_EN_ENTORNO_LOCAL";
    public static Connection abrir() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
