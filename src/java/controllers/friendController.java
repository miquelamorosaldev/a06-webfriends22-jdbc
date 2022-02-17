/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Category;
import model.Friend;
import model.persist.CategoryDAO;
import model.persist.FriendDAO;

@WebServlet(name = "friendController", urlPatterns = {"/friendController"})
public class friendController extends HttpServlet {

    private FriendDAO friendAdo;
    private CategoryDAO categoryAdo;
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
        String action = request.getParameter("actionFriend");
        ruta = getServletContext().getRealPath("/WEB-INF/resources");

        if (action != null) {
            switch (action) {
                case "List all Friends":
                    listAll(request, response);
                    break;
                case "New Friend":
                    response.sendRedirect("friend.jsp?showFormAdd=1");
                    break;
                case "Add Friend":
                    insertFriend(request, response);
                    break;
                case "Delete a Friend":
                    //response.sendRedirect("friend.jsp?showFormDelete=1");
                    deleteFriendForm(request, response);
                    break;
                case "friendToDelete":
                    deleteFriend(request, response);
                    break;
//                case "Modify a Friend":
//                    response.sendRedirect("friend.jsp?choseToModify=1");
//                    break;
//                case "modifyFriend":
//                    modifyFriendForm(request, response);
//                    break;
//                case "modify":
//                    modify(request, response);
//                    break;
                // 1 - Load the friends list with the Edit button in each row.
                case "loadFriendsListToModify":
                    loadFriendsListToModify(request, response);
                    break;
                // 2 - Select the category to edit and load the category data
                // in a form.
                case "loadFriendFormToModify":
                    loadFriendFormToModify(request, response);
                    break;
                // 3 - Finally, the user press the submit button with the new data.
                // This operation updates the category data in database.  
                case "updateFriend":
                    updateFriend(request, response);
                    break;
                // Find friends
                case "Find Friends":  
                    findFriends(request, response);
                    break;
                case "FilterFriendsByCategory":  
                    filterFriendsByCategory(request, response);
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
            Logger.getLogger(friendController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(friendController.class.getName()).log(Level.SEVERE, null, ex);
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

        loadAllFriendsAndCategories(request);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("friend.jsp");
        dispatcher.forward(request, response);
    }

    private void insertFriend(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO
        if (request.getParameter("friendName") != null) {
            String friendPhone = request.getParameter("friendPhone");
            String friendName = request.getParameter("friendName");
            String fAge = request.getParameter("friendAge");
            int friendAge = Integer.parseInt(fAge);
            String fCatId = request.getParameter("friendCategoryId");
            int friendCategoryId = Integer.parseInt(fCatId);

            friendAdo = new FriendDAO(ruta);
            Friend newFriend = new Friend(friendPhone,friendName,friendAge,friendCategoryId);

            if (friendAdo.insert(newFriend) == 1) {
                request.setAttribute("success", "Friend " + friendName + "Successfully inserted :) !");
            } else {
                request.setAttribute("error", "Friend not inserted :( !");
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("friend.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("friend.jsp");
        }

    }

    
    private void deleteFriendForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        friendAdo = new FriendDAO(ruta);
        ArrayList<Friend> friends = friendAdo.findAll();

        if (friends.isEmpty()) {
            request.setAttribute("error", "There aren't friends");
        }

        request.setAttribute("friends", friends);
        RequestDispatcher dispatcher = request.getRequestDispatcher("friend.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteFriend(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        System.out.println("aaaaa");
        String friendIdForm = request.getParameter("friend");
        int friendId = Integer.parseInt(friendIdForm);
            
//        String[] friendParams = friend.split(";");
        Friend deleteFriend 
                = new Friend(friendId);

        friendAdo = new FriendDAO(ruta);

        int rowsAffected = friendAdo.remove(deleteFriend);

        if (rowsAffected > 0) {
            request.setAttribute("success", "Friend " + friendId + " DELETED ;) !");
        } else {
            switch (rowsAffected) {
                case -1:
                    request.setAttribute("error", "Friend not deleted due to a Constraint fail.");
                    break;
                case -2:
                    request.setAttribute("error", "Friend not deleted due to an Error, contact administrator.");
                    break;
                default:
                    response.sendRedirect("friend.jsp");
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("category.jsp");
        dispatcher.forward(request, response);
    }
    
    
    private void findFriends(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        friendAdo = new FriendDAO(ruta);
        ArrayList<Friend> friends = friendAdo.findAll();
        request.setAttribute("friends", friends);
        
        ArrayList<Category> cats = loadCategories(request);
        
        // Seleccionem la primera categoria pel camp select
        // de la categoria.
        Friend friend = new Friend();
        friend.setCategoryId(1);
        friend.setCategoryDesc(cats.get(0).getDescription());
        request.setAttribute("friendfilter", friend);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("friend.jsp");
        dispatcher.forward(request, response);
    }
    
    private void filterFriendsByCategory(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Friend> friends;
        ArrayList<Category> cats;
        // 1. Agafem l'id de categoria del select del form.
        String idCatForm = request.getParameter("categoryId");
        int catId = Integer.parseInt(idCatForm);
        if(!idCatForm.equals("0")) {
            // 2. Fem la consulta per a mostrar els friends amb el categoryId
            // que hem recollit del camp anterior.
            friendAdo = new FriendDAO(ruta);
            friends = friendAdo.findByCategory(catId);

            // 3. Seleccionem la primera categoria pel camp select
            // de la categoria.
            categoryAdo = new CategoryDAO(ruta);
            cats = categoryAdo.findAll();

        } else {
            friendAdo = new FriendDAO(ruta);
            categoryAdo = new CategoryDAO(ruta);
            friends = friendAdo.findAll();
            cats = categoryAdo.findAll();
        }
        // 4. Construim el select de categories.
        
        // Afegim el filtre de totes les categories al select.
        cats.add(0,new Category(0,"All categories"));
        
        Friend friend = new Friend();
        friend.setCategoryId(catId);
        // friend.setCategoryDesc(cats.get(catId).getDescription());
        request.setAttribute("friendfilter", friend);
            
        // 5. Pintem la taula de friends, el valor del select de categories 
        // i tornem a la mateixa página.
        
        request.setAttribute("friends", friends);
        request.setAttribute("categories", cats);
        if(friends.isEmpty()) {
            request.setAttribute("warning", 
                "We couldn't find friends in the system.");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("friend.jsp");
        dispatcher.forward(request, response);
    }

    private void loadFriendsListToModify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        loadAllFriendsAndCategories(request);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("friend.jsp");
        dispatcher.forward(request, response);
    }


    private void loadFriendFormToModify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Cargamos la lista de categorias también
        // https://stackoverflow.com/questions/44010103/jstl-for-each-arraylist-with-dropdown-list-in-jsp
        categoryAdo = new CategoryDAO(ruta);
        ArrayList<Category> cats = categoryAdo.findAll();
        request.setAttribute("categories", cats);
        
        // A partir de la PK de Friend vamos a consultar los demás datos de Friend.
        String friendID = request.getParameter("friend");
        int friendPK = friendID!=null?Integer.parseInt(friendID):0;
        
        Friend friend = friendAdo.findOne(friendPK);
        request.setAttribute("friendmodify", friend);
        
        // TODO.
        // Opcional: Cargar la lista de categorias 
        // para que las ponga en un campo select.
        // List<Category> categoriesList = categoryAdo.findAll();
        // request.setAttribute("categoriesListModify", friend);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("friend.jsp");
        dispatcher.forward(request, response);
    }
    
    private void updateFriend(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Friend newFriend = null;
        // Paso 31. Recoger datos del form.
        if (request.getParameter("friendName") != null) {
            String friendPhone = request.getParameter("friendPhone");
            String friendName = request.getParameter("friendName");
            String fAge = request.getParameter("friendAge");
            int friendAge = Integer.parseInt(fAge);
            
            // Novetat: Agafem el id de la categoria des d'un camp select.
            
            // String fCatId = request.getParameter("friendCategoryId");
            String fCatId = request.getParameter("categoryId");
            int categoryId = Integer.parseInt(fCatId);
            
            // String fCatDesc = request.getParameter("friendCategoryDesc");
            String fId = request.getParameter("id_friends");
            int friendId = Integer.parseInt(fId);
       
            // Paso 32. Crear el objeto Friend con estos datos.
            // int idFriend, String phone, String name, int age, int categoryId
            newFriend = 
                new Friend(friendId,friendPhone,
                    friendName,friendAge,categoryId);
        }
        
        // Paso 33. Realizar el update en la BBDD.
        friendAdo = new FriendDAO(ruta);
        int rowsAffected = friendAdo.update(newFriend);
        // Paso 34. Informar al usuario como ha ido el udpate.
        if (rowsAffected > 0) {
            request.setAttribute("success", "Friend " 
                    + newFriend.getName() + " Successfully modified :) !");
        } else {
            request.setAttribute("error", "Friend not updated :( !");
        }
          
        // Paso 35. volver a la jsp.
        RequestDispatcher dispatcher = request.getRequestDispatcher("friend.jsp");
        dispatcher.forward(request, response);
    }
    
    /**
     * Auxiliar method to load categories from DB.
     * @param request
     * @return
     * @throws IOException 
     */
    private ArrayList<Category> loadCategories(HttpServletRequest request) throws IOException {
        // Cargamos la lista de categorias también
        // https://stackoverflow.com/questions/44010103/jstl-for-each-arraylist-with-dropdown-list-in-jsp
        categoryAdo = new CategoryDAO(ruta);
        ArrayList<Category> cats = categoryAdo.findAll();
        cats.add(0,new Category(0,"All categories"));
        request.setAttribute("categories", cats);
        return cats;
    }
    
    
    private void loadAllFriendsAndCategories(HttpServletRequest request) throws IOException {
        friendAdo = new FriendDAO(ruta);
        ArrayList<Friend> friends = friendAdo.findAll();
        request.setAttribute("friends", friends);
        if(friends.isEmpty()) {
            request.setAttribute("warning", 
                "We couldn't find friends in the system.");
        }
        // Cargamos la lista de categorias también
        // https://stackoverflow.com/questions/44010103/jstl-for-each-arraylist-with-dropdown-list-in-jsp
        categoryAdo = new CategoryDAO(ruta);
        ArrayList<Category> cats = categoryAdo.findAll();
        request.setAttribute("categories", cats);
        if(friends.isEmpty()) {
            request.setAttribute("warning", 
                "We couldn't find categories in the system.");
        }
    }

}
