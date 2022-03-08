/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import model.persist.UserDAO;

@WebServlet(name = "authenticationController", urlPatterns = {"/authenticationController"})
public class authenticationController extends HttpServlet {

    private UserDAO usersDAO;
    // private CategoryDAO categoryAdo;
    private String ruta;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        String action = request.getParameter("action");
        ruta = getServletContext().getRealPath("/WEB-INF/resources");

        if (action != null) {
            switch (action) {
                case "login":
                    login(request, response);
                    break;
                case "logout":
                    logout(request, response);
                    break;
                default:
                    response.sendRedirect("index.jsp");
            }

        } else {
            response.sendRedirect("index.jsp");
        }
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
        try {
            doAction(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(authenticationController.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            doAction(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(authenticationController.class.getName()).log(Level.SEVERE, null, ex);
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

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {

        //Llegir els camps usuari i password que venen de la vista.
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        //Enviar els camps a un nou mètode del UserDAO que permeti fer login. El password l’enviem desencriptat perquè el mètode SQL ja l’encripta.
        usersDAO = new UserDAO(ruta);
        ArrayList<User> users = usersDAO.findAll();
        
        //Si el login i el password coincideixen amb el de la base de dades:
        //Crear una sessió per gestionar l’usuari.
        
        //Anem directe a la pantalla de Pacients. 
        RequestDispatcher dispatcher = request.getRequestDispatcher("user.jsp");
        dispatcher.forward(request, response);
        //Si el login o el password no coincideixen amb el de la base de dades (INCORRECTE):
        //Enviar un missatge d’error a l’usuari: “Usuario o contraseña incorrecta”.

    }
    
    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {

//        usersDAO = new UserDAO(ruta);
//        ArrayList<User> users = usersDAO.findAll();
//        request.setAttribute("users", users);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("user.jsp");
//        dispatcher.forward(request, response);
    }
    
}
