USE sitrac_met;

-- Consultas utilizadas para verificar la integración AP4.
SELECT i.codigo, ti.nombre AS tipo, i.marca, i.modelo, i.estado,
       MAX(c.proximo_vencimiento) AS proximo_vencimiento
FROM instrumento i
JOIN tipo_instrumento ti ON ti.id_tipo = i.id_tipo
LEFT JOIN calibracion c ON c.id_instrumento = i.id_instrumento
WHERE i.activo = TRUE
GROUP BY i.id_instrumento, i.codigo, ti.nombre, i.marca, i.modelo, i.estado
ORDER BY i.codigo;

SELECT a.id_asignacion, i.codigo, r.legajo, r.nombre,
       a.fecha_entrega, a.fecha_devolucion,
       a.condicion_entrega, a.condicion_devolucion
FROM asignacion a
JOIN instrumento i ON i.id_instrumento = a.id_instrumento
JOIN responsable r ON r.id_responsable = a.id_responsable
ORDER BY a.id_asignacion DESC;
