import java.util.ArrayList;

public class ProductosListasParalelas {
    public static void main(String[] args) {
        ArrayList<String> codigos = new ArrayList<>();
        ArrayList<String> nombres = new ArrayList<>();
        ArrayList<Double> precios = new ArrayList<>();
        ArrayList<Integer> stocks = new ArrayList<>();

        codigos.add("P001");
        nombres.add("Teclado");
        precios.add(80.0);
        stocks.add(10);

        for (int i = 0; i < codigos.size(); i++) {
            System.out.println(
                    codigos.get(i) + " - " +
                    nombres.get(i) + " - S/ " +
                    precios.get(i) + " - Stock: " +
                    stocks.get(i)
            );
        }
    }
}
