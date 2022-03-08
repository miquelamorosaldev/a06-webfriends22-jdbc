/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;
import model.persist.DBConnectionException;
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
            throws ServletException, IOException, ClassNotFoundException, DBConnectionException {
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
        } catch (DBConnectionException ex) {
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
        } catch (DBConnectionException ex) {
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
            throws ServletException, IOException, ClassNotFoundException, DBConnectionException {

        //Llegir els camps usuari i password que venen de la vista.
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        //Enviar els camps a un nou mètode del UserDAO que permeti fer login. El password l’enviem desencriptat perquè el mètode SQL ja l’encripta.
        usersDAO = new UserDAO(ruta);
        User userLogin = usersDAO.login(username,password);
        
        //Si el login i el password coincideixen amb el de la base de dades
        // ens retorna les dades de l'usuari (username, role).
        if(userLogin.getUsername() !=null && userLogin.getUsername().equals(username)) {
           //Crear una sessió per gestionar l’usuari.
           
            // Per prevenir el robatori de sessió o hickjacking: hijacking
            // Consultat de: https://www.youtube.com/watch?v=IZ-lnQ4G_uI
            HttpSession session=request.getSession();
            session.setAttribute("user", username);
            // Setting session to expiry in 30 mins
            session.setMaxInactiveInterval(30*60);
                   
            // Afegim una cookie per registrar a l'usuari.
            Cookie userName = new Cookie("user", username);
            //Anem directe a la pantalla de Pacients. 
            RequestDispatcher dispatcherOK = request.getRequestDispatcher("patient.jsp");
            dispatcherOK.forward(request, response);
        } else {
            //Si el login o el password no coincideixen amb el de la base de dades (INCORRECTE):
            //Enviar un missatge d’error a l’usuari: “Usuario o contraseña incorrecta”.
            request.setAttribute("error", "Usuario o contraseña incorrecta");
            RequestDispatcher dispatcherError = request.getRequestDispatcher("index.jsp");
            dispatcherError.forward(request, response);
        }
        
    }
    
    private void logout(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.setContentType("text/html");
        // Invalidate the session if exists
        HttpSession session = request.getSession(false);
        session.invalidate();
        // closeUserSession(response, request);
    	  // System.out.println("User="+session.getAttribute("user"));
    	  /* Codi redundant.
        if(session != null){
            session.removeAttribute("user");
            session.removeAttribute("role");
            session.invalidate();
    	   }
        */
        response.sendRedirect("index.jsp");
    }

    private void closeUserSession(HttpServletResponse response, HttpServletRequest request) {
        // https://kodejava.org/how-do-i-delete-a-cookie-in-servlet/
        Cookie cookieUser = new Cookie("user", "");
        cookieUser.setMaxAge(0);
        response.addCookie(cookieUser);
        Cookie cookieJSESSIONID = new Cookie("JSESSIONID","");
        cookieJSESSIONID.setMaxAge(0);
        response.addCookie(cookieJSESSIONID);
        // Invalidate the session if exists
        HttpSession session = request.getSession();
        // System.out.println("User="+session.getAttribute("user"));
        session.invalidate();
    }
    
}
