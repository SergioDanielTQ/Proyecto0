import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;

public class CurrencyConverter {
    private static final String API_KEY = "e64a357c70bc6135a8d4eb5b"; // Reemplaza con tu clave
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            procesarOpcion(opcion, scanner);
        } while (opcion != 7);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("***********");
        System.out.println("* Conversor de Moneda  *");
        System.out.println("***********");
        System.out.println("1) Dólar => Peso argentino");
        System.out.println("2) Peso argentino => Dólar");
        System.out.println("3) Dolar => Real Brasileño");
        System.out.println("4) Real Brasileño => Dólar");
        System.out.println("5) Dólar => Peso Colombiano");
        System.out.println("6) Peso Colombiano => Dólar");
        System.out.println("7) Salir");
        System.out.println("Elija una opción válida:");
    }

    private static void procesarOpcion(int opcion, Scanner scanner) throws IOException, InterruptedException {
        String monedaOrigen = "";
        String monedaDestino = "";
        double cantidad;

        switch (opcion) {
            case 1:
                monedaOrigen = "USD";
                monedaDestino = "ARS";
                break;
            case 2:
                monedaOrigen = "ARS";
                monedaDestino = "USD";
                break;
            case 3:
                monedaOrigen = "USD";
                monedaDestino = "BRL";
                break;
            case 4:
                monedaOrigen = "BRL";
                monedaDestino = "USD";
                break;
            case 5:
                monedaOrigen = "USD";
                monedaDestino = "COP";
                break;
            case 6:
                monedaOrigen = "COP";
                monedaDestino = "USD";
                break;
        }

        System.out.print("Ingrese el valor que deseas convertir: ");
        cantidad = scanner.nextDouble();

        double tasaConversion = obtenerTasaConversion(monedaOrigen, monedaDestino);
        double resultado = cantidad * tasaConversion;

        System.out.println("El valor " + cantidad + " [" + monedaOrigen + "] corresponde al valor final de => " +
                resultado + " [" + monedaDestino + "]");
    }

    private static double obtenerTasaConversion(String monedaOrigen, String monedaDestino) throws IOException, InterruptedException {
        String url = BASE_URL + API_KEY + "/latest/" + monedaOrigen;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        RateResponse rateResponse = gson.fromJson(response.body(), RateResponse.class);
        return rateResponse.getConversion_rates().get(monedaDestino);
    }


}