USE sitrac_met;

-- 1. Instrumentos disponibles y con calibración vigente
SELECT i.codigo, ti.nombre AS tipo, i.marca, i.modelo,
       MAX(c.proximo_vencimiento) AS vencimiento
FROM instrumento i
JOIN tipo_instrumento ti ON ti.id_tipo = i.id_tipo
LEFT JOIN calibracion c ON c.id_instrumento = i.id_instrumento
WHERE i.estado = 'DISPONIBLE' AND i.activo = TRUE
GROUP BY i.id_instrumento, i.codigo, ti.nombre, i.marca, i.modelo
HAVING vencimiento IS NULL OR vencimiento >= CURDATE();

-- 2. Instrumentos próximos a vencer en los siguientes 30 días
SELECT i.codigo, ti.nombre AS tipo, c.proximo_vencimiento,
       DATEDIFF(c.proximo_vencimiento, CURDATE()) AS dias_restantes
FROM calibracion c
JOIN instrumento i ON i.id_instrumento = c.id_instrumento
JOIN tipo_instrumento ti ON ti.id_tipo = i.id_tipo
WHERE c.proximo_vencimiento BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 30 DAY)
ORDER BY c.proximo_vencimiento;

-- 3. Historial de asignaciones de un instrumento
SELECT i.codigo, r.nombre AS responsable, s.nombre AS sector,
       a.fecha_entrega, a.fecha_devolucion,
       a.condicion_entrega, a.condicion_devolucion
FROM asignacion a
JOIN instrumento i ON i.id_instrumento = a.id_instrumento
JOIN responsable r ON r.id_responsable = a.id_responsable
JOIN sector s ON s.id_sector = r.id_sector
WHERE i.codigo = 'INS-001'
ORDER BY a.fecha_entrega DESC;

-- 4. Borrado físico solo de un registro de prueba sin dependencias
DELETE FROM asignacion
WHERE id_asignacion = 9999;

-- 5. Baja lógica recomendada para información trazable
UPDATE instrumento
SET activo = FALSE
WHERE codigo = 'INS-003';
