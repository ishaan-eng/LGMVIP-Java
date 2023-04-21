import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

public class CurrencyConverterServlet extends HttpServlet {
    private static final String API_URL = "https://api.exchangeratesapi.io/latest";
    private static final String API_KEY = "YOUR_API_KEY";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String from = request.getParameter("from");
            String to = request.getParameter("to");
            double amount = Double.parseDouble(request.getParameter("amount"));

            URL url = new URL(API_URL + "?access_key=" + API_KEY + "&base=" + from + "&symbols=" + to);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer apiResponse = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                apiResponse.append(inputLine);
            }
            in.close();

            JSONObject obj = new JSONObject(apiResponse.toString());
            JSONObject rates = obj.getJSONObject("rates");
            double fromRate = 1.0;
            double toRate = rates.getDouble(to);
            double converted = amount * (toRate / fromRate);

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Currency Converter</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Currency Converter</h1>");
            out.println("<p>" + amount + " " + from + " = " + converted + " " + to + "</p>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Error</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Error</h1>");
        out.println("<p>An error occurred: " + e.getMessage() + "</p>");
        out.println("</body>");
        out.println("</html>");
        e.printStackTrace();
    }
  }
}
