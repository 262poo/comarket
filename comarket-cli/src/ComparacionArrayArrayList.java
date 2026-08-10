import java.util.ArrayList;

public class ComparacionArrayArrayList {
    public static void main(String[] args) {
        String[] nombresArray = new String[3];
        nombresArray[0] = "Teclado";
        nombresArray[1] = "Mouse";

        ArrayList<String> nombres = new ArrayList<>();
        nombres.add("Teclado");
        nombres.add("Mouse");
        nombres.add("Monitor");
        nombres.add("Audifonos");

        System.out.println("Array (tamano fijo " + nombresArray.length + "):");
        for (String nombre : nombresArray) {
            System.out.println(nombre);
        }

        System.out.println("ArrayList (tamano dinamico " + nombres.size() + "):");
        for (String nombre : nombres) {
            System.out.println(nombre);
        }
    }
}
