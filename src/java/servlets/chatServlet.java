package servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.MessageModule;

/**
 *
 * @author Vargos
 */
@WebServlet(urlPatterns = {"/chatServlet"})
public class chatServlet extends HttpServlet {

    public static ArrayList<MessageModule> messages = new ArrayList<>();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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

        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            String loggedString = (String) httpSession.getAttribute("LOGGED");
            if (loggedString.equals("true")) {
                //TODO check the body of the request if new message or cheking the users or updating the table

                MessageModule msg = new MessageModule();
                String senderName = (String) httpSession.getAttribute("USER_NAME");
                msg.setName(senderName);
                msg.setMessage(request.getParameter("message"));
                messages.add(msg);
                response.setContentType("application/json");
                Gson gson = new Gson();
                response.getWriter().write(gson.toJson(messages.get(messages.size() - 1)));
                response.getWriter().close();
            }
        } else {
            response.sendRedirect("index.html");

        }
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

        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            String loggedString = (String) httpSession.getAttribute("LOGGED");
            if (loggedString.equals("true")) {

                response.setContentType("application/json");
                Gson gson = new Gson();
                response.getWriter().write(gson.toJson(messages));
                response.getWriter().close();
            }
        } else {
            response.sendRedirect("index.html");

        }
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
