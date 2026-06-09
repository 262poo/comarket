# Sesión 1: Clases, objetos y responsabilidad de clase

## Objetivo

Comprender cómo representar un problema del dominio mediante clases y objetos, definiendo atributos, métodos y responsabilidades básicas.

## Preparación en IntelliJ

- Crear un proyecto Maven en Java.
- Definir el paquete base `app.model`.
- Configurar una clase `Main` para pruebas rápidas.

## Contenidos

- Diferencia entre clase y objeto.
- Atributos, métodos y estado.
- Responsabilidad de clase.
- Primeras entidades del dominio con baja complejidad.
- Relación simple entre `Producto` y `Categoria`, o modelado base de `Cliente`, `Proveedor` o `Usuario` como clases independientes.

## Práctica guiada

- Modelar la clase `Producto` con nombre, precio y stock.
- Agregar una `Categoria` simple o una segunda clase base del dominio con atributos claros.
- Crear objetos de prueba desde `Main`.
- Mostrar estado del objeto con un método descriptivo.

## Reto de sesión

Construir una clase adicional del dominio y justificar qué responsabilidad le corresponde sin usar todavía herencia ni relaciones complejas.

## Entregable mínimo

Proyecto en IntelliJ con una o dos entidades base del dominio, sus atributos y métodos principales, y un programa de prueba que cree objetos y muestre su estado.