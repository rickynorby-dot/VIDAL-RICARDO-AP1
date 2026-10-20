package sitracmet.modelo;

public class Responsable {
    private final int id;
    private final String legajo;
    private final String nombre;
    private final String email;
    private final Sector sector;

    public Responsable(int id, String legajo, String nombre, String email, Sector sector) {
        if (id <= 0 || legajo == null || legajo.isBlank() || nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Datos de responsable inválidos");
        }
        this.id = id;
        this.legajo = legajo.trim();
        this.nombre = nombre.trim();
        this.email = email == null ? "" : email.trim();
        this.sector = sector;
    }

    public int getId() { return id; }
    public String getLegajo() { return legajo; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public Sector getSector() { return sector; }
}
