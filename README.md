# SITRAC-MET - AP3

Prototipo Java del Sistema de Gestión y Trazabilidad de Instrumentos de Medición y Calibraciones.

## Alumno
Ricardo Vidal - Seminario de Práctica de Informática - Universidad Siglo 21.

## Contenido
- `src/main/java/`: código Java del prototipo (estructura Maven, compatible con NetBeans).
- `sql/`: scripts MySQL heredados y actualizados desde AP2.
- `diagramas/`: diagramas utilizados en el informe.
- `docs/`: informe AP3 en PDF.
- `evidencias/`: salida de compilación/ejecución.

## Ejecución rápida con JDK
Desde la raíz del proyecto:

```bash
mkdir -p out
javac -encoding UTF-8 -d out $(find src/main/java -name "*.java")
java -cp out sitracmet.app.SitracMetApp
```

En Apache NetBeans puede abrirse directamente la carpeta como proyecto Maven. La clase principal es `sitracmet.app.SitracMetApp`.

## MySQL
La persistencia del proyecto se mantiene en MySQL (`sitrac_met`). Los scripts de creación y datos se encuentran en `sql/`. La capa `dao` utiliza JDBC. Para conexión real debe agregarse MySQL Connector/J a las librerías del proyecto y definir, preferentemente, las variables de entorno:

- `SITRAC_DB_URL`
- `SITRAC_DB_USER`
- `SITRAC_DB_PASSWORD`

No se publican contraseñas reales en el repositorio.

## Repositorio
https://github.com/rickynorby-dot/VIDAL-RICARDO-AP1
