package sitracmet.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AuditoriaArchivo {
    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final Path archivo;

    public AuditoriaArchivo(Path archivo) {
        this.archivo = archivo;
    }

    public void registrar(String accion) throws IOException {
        Path padre = archivo.getParent();
        if (padre != null) Files.createDirectories(padre);
        String linea = LocalDateTime.now().format(FORMATO)
                + " | " + accion + System.lineSeparator();
        Files.writeString(archivo, linea, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public List<String> leerUltimos(int cantidad) throws IOException {
        if (!Files.exists(archivo)) return List.of();
        List<String> lineas = Files.readAllLines(archivo, StandardCharsets.UTF_8);
        int inicio = Math.max(0, lineas.size() - cantidad);
        return new ArrayList<>(lineas.subList(inicio, lineas.size()));
    }
}
