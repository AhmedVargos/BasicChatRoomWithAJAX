package servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.MessageModule;
import models.User;

/**
 *
 * @author Vargos
 */
@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    public static ArrayList<User> usersRegistered = new ArrayList<>();
    public static ArrayList<User> usersOnline = new ArrayList<>();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    Connection connection;
    Statement stmt;

    @Override
    public void init()
            throws ServletException {

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

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

        response.setContentType("text/html;charset=UTF-8");
        String userName = request.getParameter("username");
        String userPass = request.getParameter("userpassword");

        boolean isCreated = isCreated(userName, userPass);
        if (isCreated) {
            HttpSession httpSession = request.getSession(true);
            httpSession.setAttribute("LOGGED", "true");
            httpSession.setAttribute("USER_NAME", userName);
            User user = new User(userName, userPass);
            usersOnline.add(user);
            response.sendRedirect("chatPage.html");
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

        response.setContentType("text/html;charset=UTF-8");
        String userName = request.getParameter("username");
        String userPass = request.getParameter("userpassword");
        String userEmail = request.getParameter("useremail");

        
        boolean isCreated = isAvailable(userName);
        if (isCreated) {
            HttpSession httpSession = request.getSession(true);
            httpSession.setAttribute("LOGGED", "true");
            httpSession.setAttribute("USER_NAME", userName);
            User user = new User(userName, userPass,userEmail);
            usersRegistered.add(user);
            usersOnline.add(user);
            response.sendRedirect("chatPage.html");
        } else {
            response.sendRedirect("index.html");
        }
    }
    
    private boolean isAvailable(String name){
        boolean result = true;
        for(int i = 0; i < usersRegistered.size(); i++){
            if(usersRegistered.get(i).getName().equals(name)){
                result = false;
            }
        }
        return result;
    }

    private boolean isCreated(String userName,String pass){
        boolean result = false;
        for(int i = 0; i < usersRegistered.size(); i++){
            if(usersRegistered.get(i).getName().equals(userName) && usersRegistered.get(i).getPass().equals(pass) ){
                result = true;
            }
        }
        return result;
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
