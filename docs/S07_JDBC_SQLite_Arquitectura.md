# Sesión 7: Arquitectura por capas y persistencia relacional

## Objetivo

Preparar la transición de memoria a persistencia definiendo una arquitectura simple por capas y una conexión estable a una base de datos relacional mediante JDBC.

## Preparación en IntelliJ

- Agregar dependencias JDBC y SQLite en `pom.xml`.
- Crear paquetes `app.dao`, `app.config` y `app.service`.

## Contenidos

- Organización por capas.
- Rol de la clase de conexión.
- Fundamentos de JDBC.
- Base de datos relacional embebida en el proyecto.

## Práctica guiada

- Configurar la conexión.
- Crear la base de datos y tablas iniciales.
- Verificar conexión con una consulta simple.

## Reto de sesión

Diseñar un esquema relacional mínimo alineado con las clases principales del dominio.

## Entregable mínimo

Proyecto con conexión funcional a una base de datos relacional y estructura base de paquetes para persistencia.
