package sitracmet.dto;

import java.time.LocalDate;

public record InstrumentoPersistido(
        int id,
        String codigo,
        String tipo,
        String marca,
        String modelo,
        String estado,
        LocalDate proximoVencimiento) {
}
