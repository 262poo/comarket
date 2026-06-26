package com.upeu.comarket.app;

import com.upeu.comarket.entity.Cliente;
import com.upeu.comarket.entity.Producto;
import com.upeu.comarket.entity.Usuario;
import com.upeu.comarket.service.ProductoService;
import com.upeu.comarket.service.ProductoServiceImplMemoria;

import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final ProductoService PRODUCTO_SERVICE = new ProductoServiceImplMemoria();

    public static void main(String[] args) {
        cargarDatosIniciales();

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");
            ejecutarOpcion(opcion);
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("=== CoMarket CLI - Producto U1 ===");
        System.out.println("1. Registrar producto");
        System.out.println("2. Listar productos");
        System.out.println("3. Buscar producto");
        System.out.println("4. Actualizar producto");
        System.out.println("5. Eliminar producto");
        System.out.println("6. Ver personas de prueba");
        System.out.println("0. Salir");
    }

    private static void ejecutarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> registrarProducto();
                case 2 -> listarProductos();
                case 3 -> buscarProducto();
                case 4 -> actualizarProducto();
                case 5 -> eliminarProducto();
                case 6 -> verPersonasDePrueba();
                case 0 -> System.out.println("Fin del programa.");
                default -> System.out.println("Opcion no valida.");
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void registrarProducto() {
        Producto producto = leerProducto();
        PRODUCTO_SERVICE.registrar(producto);
        System.out.println("Producto registrado.");
    }

    private static void listarProductos() {
        if (PRODUCTO_SERVICE.listar().isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }

        for (Producto producto : PRODUCTO_SERVICE.listar()) {
            System.out.println(producto);
        }
    }

    private static void buscarProducto() {
        String codigo = leerTexto("Codigo: ");
        Producto producto = PRODUCTO_SERVICE.buscarPorCodigo(codigo);
        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }
        System.out.println(producto);
    }

    private static void actualizarProducto() {
        Producto producto = leerProducto();
        boolean actualizado = PRODUCTO_SERVICE.actualizar(producto);
        System.out.println(actualizado ? "Producto actualizado." : "Producto no encontrado.");
    }

    private static void eliminarProducto() {
        String codigo = leerTexto("Codigo: ");
        boolean eliminado = PRODUCTO_SERVICE.eliminar(codigo);
        System.out.println(eliminado ? "Producto eliminado." : "Producto no encontrado.");
    }

    private static Producto leerProducto() {
        String codigo = leerTexto("Codigo: ");
        String nombre = leerTexto("Nombre: ");
        double precio = leerDecimal("Precio: ");
        int stock = leerEntero("Stock: ");
        return new Producto(codigo, nombre, precio, stock);
    }

    private static void verPersonasDePrueba() {
        Cliente cliente = new Cliente("70000001", "Rosa Quispe", "rosa@comarket.pe", "999111222");
        Usuario usuario = new Usuario("70000002", "Luis Ramos", "luis@comarket.pe", "lramos", "ADMIN");
        System.out.println(cliente.mostrarPerfil());
        System.out.println(usuario.mostrarPerfil());
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return SCANNER.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException ex) {
                System.out.println("Ingrese un numero entero valido.");
            }
        }
    }

    private static double leerDecimal(String mensaje) {
        while (true) {
            try {
                return Double.parseDouble(leerTexto(mensaje));
            } catch (NumberFormatException ex) {
                System.out.println("Ingrese un numero decimal valido.");
            }
        }
    }

    private static void cargarDatosIniciales() {
        PRODUCTO_SERVICE.registrar(new Producto("P001", "Arroz", 4.5, 20));
        PRODUCTO_SERVICE.registrar(new Producto("P002", "Aceite", 9.9, 12));
    }
}
