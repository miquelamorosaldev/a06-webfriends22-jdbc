package controllers;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Category;
import model.persist.CategoryDAO;

@WebServlet(name = "categoryController", urlPatterns = {"/categoryController"})
public class categoryController extends HttpServlet {

    private CategoryDAO catAdo;
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
        String action = request.getParameter("actionCategory");
        ruta = getServletContext().getRealPath("/WEB-INF/resources");

        if (action != null) {
            switch (action) {
                case "List all categories":
                    listAll(request, response);
                    break;
                case "Insert a category":
                    insertForm(request, response);
                    break;
                case "Add Category":
                    addCategory(request, response);
                    break;
                // 1 - Load the category list with the Edit button in each row.
                case "Modify Categories":
                    modifyCategoriesForm(request, response);
                    break;
                // 2 - Select the category to edit and load the category data
                // in a form.
                case "categoryToEdit":
                    modifyCategory(request, response);
                    break;
                // 3 - Finally, the user press the submit button with the new data.
                // This operation updates the category data in database.  
                case "modifyCategory":
                    modifyThatCategory(request, response);
                    break;
                case "Delete Categories":
                    deleteCategoryForm(request, response);
                    break;
                case "categoryToDelete":
                    deleteCategory(request, response);
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
        catAdo = new CategoryDAO(ruta);
        ArrayList<Category> cats = catAdo.findAll();
        request.setAttribute("categories", cats);
        RequestDispatcher dispatcher = request.getRequestDispatcher("category.jsp");
        dispatcher.forward(request, response);
    }

    private void insertForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("category.jsp?showFormAdd");
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("catName") != null) {
            String categoryName = request.getParameter("catName");

            catAdo = new CategoryDAO(ruta);
            Category newCat = new Category(0, categoryName);

            if (catAdo.insert(newCat) == 1) {
                request.setAttribute("success", "Category " + categoryName + "Successfully inserted :) !");
            } else {
                request.setAttribute("error", "Category not inserted :( !");
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("category.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("category.jsp");
        }

    }

    private void modifyCategoriesForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        catAdo = new CategoryDAO(ruta);
        ArrayList<Category> cats = catAdo.findAll();

        if (cats.isEmpty()) {
            request.setAttribute("error", "There aren't categories");
        }

        request.setAttribute("categories", cats);
        RequestDispatcher dispatcher = request.getRequestDispatcher("category.jsp");
        dispatcher.forward(request, response);
    }

    private void modifyCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String category = request.getParameter("category");
        String[] categoryParams = category.split(";");
        Category newCat = new Category(Integer.parseInt(categoryParams[0]), categoryParams[1]);
        request.setAttribute("categorymodify", newCat);
        RequestDispatcher dispatcher = request.getRequestDispatcher("category.jsp");
        dispatcher.forward(request, response);
    }

    private void modifyThatCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("categoryName") != null && request.getParameter("categoryID") != null) {
            String catName = request.getParameter("categoryName");
            int catId = Integer.parseInt(request.getParameter("categoryID"));
            Category newCat = new Category(catId, catName);

            this.catAdo = new CategoryDAO(ruta);
            int rowsAffected = catAdo.modify(newCat);

            if (rowsAffected > 0) {
                request.setAttribute("success", "Category " + newCat.getDescription() + " Successfully modified :) !");
            } else {
                request.setAttribute("error", "Category not modified :( !");
            }
        } else {
            response.sendRedirect("category.jsp");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("category.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteCategoryForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        catAdo = new CategoryDAO(ruta);
        ArrayList<Category> cats = catAdo.findAll();

        if (cats.isEmpty()) {
            request.setAttribute("error", "There aren't categories");
        }

        request.setAttribute("categories", cats);
        RequestDispatcher dispatcher = request.getRequestDispatcher("category.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String category = request.getParameter("category");
        String[] categoryParams = category.split(";");
        Category newCat = new Category(Integer.parseInt(categoryParams[0]), categoryParams[1]);

        catAdo = new CategoryDAO(ruta);

        int rowsAffected = catAdo.delete(newCat);

        if (rowsAffected > 0) {
            request.setAttribute("success", "Category " + newCat.getDescription() + " DELETED ;) !");
        } else {
            switch (rowsAffected) {
                case -1:
                    request.setAttribute("error", "Category not deleted due to a Constraint fail.");
                    break;
                case -2:
                    request.setAttribute("error", "Category not deleted due to an Error, contact administrator.");
                    break;
                default:
                    response.sendRedirect("category.jsp");
            }
        }

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
