USE sitrac_met;

INSERT INTO sector (nombre, descripcion) VALUES
('Mantenimiento', 'Mantenimiento electromecánico'),
('Calidad', 'Metrología y aseguramiento de calidad'),
('Producción', 'Líneas de producción');

INSERT INTO responsable (id_sector, legajo, nombre, email) VALUES
(1, 'L-1001', 'Juan Pérez', 'juan.perez@empresa.local'),
(2, 'L-1002', 'María López', 'maria.lopez@empresa.local');

INSERT INTO tipo_instrumento (nombre, requiere_calibracion) VALUES
('Multímetro', TRUE),
('Manómetro', TRUE),
('Torquímetro', TRUE);

INSERT INTO instrumento (id_tipo, id_sector_referencia, codigo, marca, modelo, nro_serie, estado) VALUES
(1, 1, 'INS-001', 'Fluke', '179', 'FL179-001', 'DISPONIBLE'),
(2, 3, 'INS-002', 'Wika', '232.50', 'WK232-015', 'DISPONIBLE'),
(3, 1, 'INS-003', 'Bahco', '74W9', 'BH74-091', 'EN_CALIBRACION');

INSERT INTO laboratorio (razon_social, identificacion, email) VALUES
('Laboratorio Metrológico Centro', 'LAB-001', 'contacto@labcentro.local');

INSERT INTO calibracion (id_instrumento, id_laboratorio, fecha_calibracion, resultado, nro_certificado, proximo_vencimiento, observaciones) VALUES
(1, 1, '2026-04-15', 'APTO', 'CERT-2026-001', '2027-04-15', 'Sin observaciones'),
(2, 1, '2025-10-10', 'APTO', 'CERT-2025-077', '2026-10-10', 'Control anual');

INSERT INTO asignacion (id_instrumento, id_responsable, fecha_entrega, condicion_entrega)
VALUES (1, 1, NOW(), 'Equipo en condiciones de uso');

UPDATE instrumento SET estado = 'ASIGNADO' WHERE id_instrumento = 1;
