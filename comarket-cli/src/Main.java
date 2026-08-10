import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> nombres = new ArrayList<>();
        int opcion;

        do {
            System.out.println("1. Registrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar");
            System.out.println("4. Actualizar");
            System.out.println("5. Eliminar");
            System.out.println("6. Salir");
            System.out.print("Opcion: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    registrar(nombres, scanner);
                    break;
                case 2:
                    listar(nombres);
                    break;
                case 3:
                    buscarDesdeMenu(nombres, scanner);
                    break;
                case 4:
                    actualizar(nombres, scanner);
                    break;
                case 5:
                    eliminar(nombres, scanner);
                    break;
                case 6:
                    System.out.println("Fin");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        } while (opcion != 6);

        scanner.close();
    }

    public static void registrar(ArrayList<String> nombres, Scanner scanner) {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        nombres.add(nombre);
    }

    public static void listar(ArrayList<String> nombres) {
        for (int i = 0; i < nombres.size(); i++) {
            System.out.println(i + " - " + nombres.get(i));
        }
    }

    public static int buscar(ArrayList<String> nombres, String nombreBuscado) {
        for (int i = 0; i < nombres.size(); i++) {
            if (nombres.get(i).equalsIgnoreCase(nombreBuscado)) {
                return i;
            }
        }
        return -1;
    }

    public static void buscarDesdeMenu(ArrayList<String> nombres, Scanner scanner) {
        System.out.print("Nombre a buscar: ");
        String nombreBuscado = scanner.nextLine();
        int posicion = buscar(nombres, nombreBuscado);
        if (posicion == -1) {
            System.out.println("No encontrado");
        } else {
            System.out.println("Encontrado en la posicion " + posicion);
        }
    }

    public static void actualizar(ArrayList<String> nombres, Scanner scanner) {
        System.out.print("Nombre actual: ");
        String nombreActual = scanner.nextLine();
        int posicion = buscar(nombres, nombreActual);
        if (posicion == -1) {
            System.out.println("No encontrado");
            return;
        }
        System.out.print("Nuevo nombre: ");
        String nuevoNombre = scanner.nextLine();
        nombres.set(posicion, nuevoNombre);
        System.out.println("Actualizado");
    }

    public static void eliminar(ArrayList<String> nombres, Scanner scanner) {
        System.out.print("Nombre a eliminar: ");
        String nombre = scanner.nextLine();
        int posicion = buscar(nombres, nombre);
        if (posicion == -1) {
            System.out.println("No encontrado");
            return;
        }
        nombres.remove(posicion);
        System.out.println("Eliminado");
    }
}
