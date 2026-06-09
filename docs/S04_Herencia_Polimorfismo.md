# Sesión 4: Herencia, reutilización y polimorfismo

## Objetivo

Identificar cuándo usar herencia y polimorfismo para reutilizar comportamiento sin degradar el diseño.

## Preparación en IntelliJ

- Crear una jerarquía pequeña solo si el dominio la justifica.
- Preparar pruebas manuales desde `Main`.

## Contenidos

- Relación "es un" frente a "tiene un".
- Clase base y subclases.
- Sobrescritura de métodos.
- Polimorfismo aplicado a reglas del negocio.

## Práctica guiada

- Introducir una clase base `Persona` cuando el dominio ya justifique atributos y comportamientos compartidos.
- Derivar clases como `Cliente`, `Proveedor`, `Usuario` o `Trabajador` según el caso trabajado en sesiones anteriores.
- Implementar un comportamiento compartido y especializaciones.
- Probar llamadas polimórficas.

## Reto de sesión

Comparar una solución con herencia frente a otra con composición y justificar la decisión tomada a partir del modelo construido en sesiones previas.

## Entregable mínimo

Jerarquía pequeña y justificada, por ejemplo `Persona` con clases derivadas del dominio, integrada al proyecto con una decisión de diseño argumentada.