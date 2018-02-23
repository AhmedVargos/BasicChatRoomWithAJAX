/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static servlets.chatServlet.messages;
import static servlets.LoginServlet.usersRegistered;

/**
 *
 * @author Vargos
 */
@WebServlet(name = "onlineUsers", urlPatterns = {"/UsersOnlineServlet"})
public class UsersOnlineServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

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
                int size = LoginServlet.usersOnline.size();
                int pos = -1;

                String senderName = (String) httpSession.getAttribute("USER_NAME");
                for (int i = 0; i < size; i++) {
                    if (LoginServlet.usersOnline.get(i).getName().equals(senderName)) {
                        pos = i;
                    }
                }

                if (pos != -1) {
                    LoginServlet.usersOnline.remove(pos);

                    response.sendRedirect("index.html");

                }
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
                //TODO Update the list of online usersRegistered to all clients
                response.setContentType("text/html;charset=UTF-8");
                Gson gson = new Gson();
                response.getWriter().write(gson.toJson(LoginServlet.usersOnline));
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
