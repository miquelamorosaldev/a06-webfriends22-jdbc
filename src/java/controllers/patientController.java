package controllers;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Patient;
import model.persist.PatientDAO;

@WebServlet(name = "patientController", urlPatterns = {"/patientController"})
public class patientController extends HttpServlet {

    private PatientDAO patDAO;
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
            throws ServletException, IOException {
        String action = request.getParameter("actionPatient");
        ruta = getServletContext().getRealPath("/WEB-INF/resources");

        if (action != null) {
            switch (action) {
                case "List Patients":
                    listAll(request, response);
                    break;
                case "Filter Patients By Status":
                    filter(request, response);
                    break;
                case "Insert a category":
                    insertForm(request, response);
                    break;
                case "Add Patient":
                    addPatient(request, response);
                    break;
                // 1 - Load the category list with the Edit button in each row.
                case "Modify Categories":
                    modifyCategoriesForm(request, response);
                    break;
                // 2 - Select the category to edit and load the category data
                // in a form.
                case "categoryToEdit":
                    modifyPatient(request, response);
                    break;
                // 3 - Finally, the user press the submit button with the new data.
                // This operation updates the category data in database.  
                case "modifyPatient":
                    modifyThatPatient(request, response);
                    break;
                case "Delete Categories":
                    deletePatientForm(request, response);
                    break;
                case "categoryToDelete":
                    deletePatient(request, response);
                    break;
                default:
                    response.sendRedirect("index.jsp");
            }

        } else {
            response.sendRedirect("mainController");
        }
    }

    private void listAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        patDAO = new PatientDAO(ruta);
        ArrayList<Patient> cats = patDAO.findAll();
        request.setAttribute("patients", cats);
        RequestDispatcher dispatcher = request.getRequestDispatcher("patient.jsp");
        dispatcher.forward(request, response);
    }
    
    private void filter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO 
        patDAO = new PatientDAO(ruta);
        ArrayList<Patient> cats = patDAO.findAll();
        request.setAttribute("categories", cats);
        RequestDispatcher dispatcher = request.getRequestDispatcher("patient.jsp");
        dispatcher.forward(request, response);
    }

    // TODO
    
    private void insertForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("category.jsp?showFormAdd");
    }

    private void addPatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("catName") != null) {
            String categoryName = request.getParameter("catName");

            patDAO = new PatientDAO(ruta);
//            Patient newCat = new Patient(0, categoryName);
//
//            if (patDAO.insert(newCat) == 1) {
//                request.setAttribute("success", "Patient " + categoryName + "Successfully inserted :) !");
//            } else {
//                request.setAttribute("error", "Patient not inserted :( !");
//            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("category.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("category.jsp");
        }

    }

    private void modifyCategoriesForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        patDAO = new PatientDAO(ruta);
        ArrayList<Patient> cats = patDAO.findAll();

        if (cats.isEmpty()) {
            request.setAttribute("error", "There aren't categories");
        }

        request.setAttribute("categories", cats);
        RequestDispatcher dispatcher = request.getRequestDispatcher("category.jsp");
        dispatcher.forward(request, response);
    }

    private void modifyPatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String category = request.getParameter("category");
        String[] categoryParams = category.split(";");
//        Patient newCat = new Patient(Integer.parseInt(categoryParams[0]), categoryParams[1]);
//        request.setAttribute("categorymodify", newCat);
        RequestDispatcher dispatcher = request.getRequestDispatcher("category.jsp");
        dispatcher.forward(request, response);
    }

    private void modifyThatPatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("categoryName") != null && request.getParameter("categoryID") != null) {
            String catName = request.getParameter("categoryName");
            int catId = Integer.parseInt(request.getParameter("categoryID"));
            int rowsAffected = 0;
//            Patient newCat = new Patient(catId, catName);
//
//            this.patDAO = new PatientDAO(ruta);
//            int rowsAffected = patDAO.modify(newCat);

//            if (rowsAffected > 0) {
//                request.setAttribute("success", "Patient " + newCat.getDescription() + " Successfully modified :) !");
//            } else {
//                request.setAttribute("error", "Patient not modified :( !");
//            }
        } else {
            response.sendRedirect("category.jsp");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("category.jsp");
        dispatcher.forward(request, response);
    }

    private void deletePatientForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        patDAO = new PatientDAO(ruta);
        ArrayList<Patient> cats = patDAO.findAll();

        if (cats.isEmpty()) {
            request.setAttribute("error", "There aren't categories");
        }

        request.setAttribute("categories", cats);
        RequestDispatcher dispatcher = request.getRequestDispatcher("category.jsp");
        dispatcher.forward(request, response);
    }

    private void deletePatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String category = request.getParameter("category");
        String[] categoryParams = category.split(";");
//        Patient newCat = new Patient(Integer.parseInt(categoryParams[0]), categoryParams[1]);

        patDAO = new PatientDAO(ruta);

//        int rowsAffected = patDAO.delete(newCat);

//        if (rowsAffected > 0) {
//            request.setAttribute("success", "Patient " + newCat.getDescription() + " DELETED ;) !");
//        } else {
//            switch (rowsAffected) {
//                case -1:
//                    request.setAttribute("error", "Patient not deleted due to a Constraint fail.");
//                    break;
//                case -2:
//                    request.setAttribute("error", "Patient not deleted due to an Error, contact administrator.");
//                    break;
//                default:
//                    response.sendRedirect("category.jsp");
//            }
//        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("category.jsp");
        dispatcher.forward(request, response);
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
        doAction(request, response);
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
        doAction(request, response);
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
