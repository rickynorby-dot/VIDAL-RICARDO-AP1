# SITRAC-MET - AP2
Prototipo académico para la gestión y trazabilidad de instrumentos de medición y calibraciones.

## Tecnología
- Java
- Apache NetBeans
- MySQL
- JDBC / MySQL Connector-J

## Estructura
- `src/`: clases del prototipo
- `sql/01_schema.sql`: creación de base y tablas
- `sql/02_datos_prueba.sql`: datos de prueba
- `sql/03_consultas.sql`: inserción/consulta/borrado y baja lógica

## Ejecución
1. Ejecutar `sql/01_schema.sql` en MySQL.
2. Crear un usuario de aplicación y actualizar las credenciales locales de `ConexionBD`.
3. Ejecutar `sql/02_datos_prueba.sql`.
4. Incorporar MySQL Connector/J al proyecto Java.
5. Abrir el proyecto en Apache NetBeans.

> Para un repositorio público no se recomienda versionar contraseñas reales. Usar variables de entorno o configuración local excluida del repositorio.
