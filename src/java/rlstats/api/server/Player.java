package rlstats.api.server;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import rlstats.api.classes.Statistic;

/**
 *
 * @author Adrian
 */
@WebServlet(name = "Player", urlPatterns = {"/Player"})
public class Player extends HttpServlet {

    Statistic s = new Statistic();

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/javascript");
        PrintWriter out = response.getWriter();
        out.print(s.getStatistics("adriansaenz15995", "Steam", 7, 11));
        out.flush();
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/javascript");
        String json = "";
        PrintWriter out = response.getWriter();
        try {
            json = this.parseJson(request.getReader());
        }
        catch (IOException e) {
            System.err.println("Error: " + e);
        }
        JsonElement e = new JsonParser().parse(json);
        JsonObject o = e.getAsJsonObject();
        out.print(s.getStatistics(o.get("nickname").getAsString(), o.get("platform").getAsString()));
        out.flush();
    }
    
    private String parseJson(BufferedReader br) throws IOException{
        String aux = "", line = "";
        while ((aux = br.readLine()) != null) { // while loop begins here
                line = line + aux;
        }
        return line;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
