package sitracmet.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConexionBD {
    private ConexionBD() { }

    public static Connection abrir() throws SQLException {
        String url = System.getenv().getOrDefault(
                "SITRAC_DB_URL",
                "jdbc:mysql://localhost:3306/sitrac_met?serverTimezone=America/Argentina/Buenos_Aires");
        String usuario = System.getenv().getOrDefault("SITRAC_DB_USER", "sitrac_app");
        String clave = System.getenv().getOrDefault("SITRAC_DB_PASSWORD", "");
        return DriverManager.getConnection(url, usuario, clave);
    }
}
