package sitracmet.modelo;

public class Sector {
    private final int id;
    private final String nombre;

    public Sector(int id, String nombre) {
        if (id <= 0 || nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Datos de sector inválidos");
        }
        this.id = id;
        this.nombre = nombre.trim();
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
}
