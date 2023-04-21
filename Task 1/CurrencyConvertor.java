import java.net.*;
import java.io.*;
import org.json.*;

public class CurrencyConverter {
    private static final String API_URL = "https://api.exchangeratesapi.io/latest";
    private static final String API_KEY = "YOUR_API_KEY";                                 //replace "YOUR_API_KEY" with your own API key from ExchangeRatesAPI.

    public static void main(String[] args) {
        try {
            URL url = new URL(API_URL + "?access_key=" + API_KEY);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject obj = new JSONObject(response.toString());
            JSONObject rates = obj.getJSONObject("rates");
            double fromRate = rates.getDouble("FROM_CURRENCY_CODE");
            double toRate = rates.getDouble("TO_CURRENCY_CODE");
            double amount = Double.parseDouble(args[0]);
            double converted = amount * (toRate / fromRate);

            System.out.println(amount + " " + "FROM_CURRENCY_CODE" + " = " + converted + " " + "TO_CURRENCY_CODE");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
