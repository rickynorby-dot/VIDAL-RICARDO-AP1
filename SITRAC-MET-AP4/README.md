# SITRAC-MET - AP4

**Alumno:** Ricardo Vidal  
**Materia:** Seminario de Práctica de Informática  
**Universidad:** Universidad Siglo 21

Versión final integradora del sistema de gestión y trazabilidad de instrumentos de medición y calibraciones.

## Alcance AP4

La cuarta entrega integra lo desarrollado en AP1, AP2 y AP3 e incorpora:

- patrón **MVC**;
- persistencia MySQL mediante **JDBC**;
- `PreparedStatement` y `try-with-resources`;
- excepciones de dominio y `PersistenciaException`;
- clase abstracta `InstrumentoMedicion`;
- interfaz `RepositorioInstrumentos`;
- uso complementario de `String[]` y `ArrayList`;
- archivo de auditoría `data/auditoria-sitrac.log`;
- menú de consola y presentación de resultados;
- scripts SQL y evidencias de compilación/ejecución.

## Estructura

```text
src/main/java/sitracmet/
  app/            punto de entrada
  controlador/    controlador MVC
  vista/          vista de consola
  modelo/         clases de dominio
  servicio/       reglas y colecciones
  dao/            JDBC / MySQL
  dto/            datos recuperados de persistencia
  excepcion/      excepciones propias
  util/           auditoría mediante archivo
sql/              definición y consultas MySQL
diagramas/        arquitectura y clases
evidencias/       compilación y ejecución
docs/             informe y guion del video
```

## Base de datos

1. Ejecutar `sql/01_schema.sql`.
2. Ejecutar `sql/02_datos_prueba.sql`.
3. Crear/usar un usuario con permisos sobre `sitrac_met`.
4. Configurar las variables de entorno:

```text
SITRAC_DB_URL=jdbc:mysql://localhost:3306/sitrac_met?serverTimezone=America/Argentina/Buenos_Aires
SITRAC_DB_USER=sitrac_app
SITRAC_DB_PASSWORD=<clave>
```

El proyecto utiliza MySQL Connector/J, declarado en `pom.xml`.

## Ejecución

En NetBeans se puede abrir como proyecto Maven. También puede compilarse con JDK 21.

Modo normal (con MySQL):

```bash
java sitracmet.app.SitracMetApp
```

Modo demostración (sin conexión MySQL, para validar lógica MVC, POO, colecciones y archivo):

```bash
java sitracmet.app.SitracMetApp --demo
```

## Repositorio académico

https://github.com/rickynorby-dot/VIDAL-RICARDO-AP1
