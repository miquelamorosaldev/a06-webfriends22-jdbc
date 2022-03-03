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

@WebServlet(name = "userController", urlPatterns = {"/userController"})
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
        String action = request.getParameter("actionUser");
        ruta = getServletContext().getRealPath("/WEB-INF/resources");

        if (action != null) {
            switch (action) {
                case "List all Users":
                    listAll(request, response);
                    break;
                case "New Users":
                    response.sendRedirect("friend.jsp?showFormAdd=1");
                    break;
                case "Add Users":
                    insertUser(request, response);
                    break;
                case "Delete a Users":
                    response.sendRedirect("friend.jsp?showFormDelete=1");
                    break;
                case "deleteUsers":
                    deleteUser(request, response);
                    break;
                case "Modify a Users":
                    response.sendRedirect("friend.jsp?choseToModify=1");
                    break;
                case "modifyUsers":
                    modifyUsersForm(request, response);
                    break;
                case "modify":
                    modify(request, response);
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

    private void listAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {

        usersDAO = new UserDAO(ruta);
        ArrayList<User> users = usersDAO.findAll();
        request.setAttribute("users", users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user.jsp");
        dispatcher.forward(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO
        if (request.getParameter("friendName") != null) {
//            String friendPhone = request.getParameter("friendPhone");
//            String friendName = request.getParameter("friendName");
//            String fAge = request.getParameter("friendAge");
//            int friendAge = Integer.parseInt(fAge);
//            String fCatId = request.getParameter("friendCategoryId");
//            int friendCategoryId = Integer.parseInt(fCatId);

            usersDAO = new UserDAO(ruta);
//            User newUser = new User(username,friendName,friendAge);
//
//            if (usersDAO.insert(newUser) == 1) {
//                request.setAttribute("success", "User " + newUser + "Successfully inserted :) !");
//            } else {
//                request.setAttribute("error", "User not inserted :( !");
//            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("friend.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("friend.jsp");
        }

    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO
    }

    private void modifyUsersForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO

    }

    private void modify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO
    }

}
