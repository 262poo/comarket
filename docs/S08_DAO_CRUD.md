# Sesión 8: Patrón DAO y operaciones CRUD

## Objetivo

Desacoplar el acceso a datos del resto de la aplicación mediante el patrón DAO e implementar operaciones CRUD persistentes.

## Preparación en IntelliJ

- Crear interfaces DAO y sus implementaciones.
- Mantener separadas las clases del dominio respecto de SQL y JDBC.

## Contenidos

- Responsabilidad del DAO.
- Mapeo objeto-relacional básico.
- Consultas `insert`, `select`, `update` y `delete`.
- Manejo inicial de excepciones.

## Práctica guiada

- Implementar un DAO para `Producto`.
- Probar altas, consultas, actualización y eliminación.
- Comparar la versión en memoria con la persistente.

## Reto de sesión

Agregar una segunda entidad o una búsqueda específica por criterio de negocio.

## Entregable mínimo

Al menos un DAO completamente funcional con operaciones CRUD sobre base de datos relacional.
