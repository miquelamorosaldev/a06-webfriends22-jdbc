<%@page import="java.util.List"%>
<%@page import="model.Category"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="html/menu.html"/>

<!-- List All Categories with modify and delete buttons -->
<%
    if(request.getAttribute("categories")!=null){
        List<Category> categories= (List<Category> )request.getAttribute("categories");
%>
        <form action="categoryController" method="POST">
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">Category ID</th>
                    <th scope="col">Category Description</th>
                    
                    
 <%   
                if(request.getParameter("actionCategory")!=null){
                    if(request.getParameter("actionCategory").equals("Modify Categories")){
                                 out.println("<th scope='col'>Modify Category</th>");
                    }
                    if(request.getParameter("actionCategory").equals("Delete Categories")){
                    out.println("<th scope='col'>Delete Category</th>");
                    }
                }
                out.println(" </tr> </thead><thbody>");
                for(Category category: categories){
                    out.println(" <tr> <th scope='row'>"+category.getId()+"</th><td>"+category.getDescription()+"</td>");
                    if(request.getParameter("actionCategory")!=null){
                        if(request.getParameter("actionCategory").equals("Modify Categories")){
                                out.println("<td scope='col'><button class='btn btn-success' type='submit' value='"+category.getId()+";"+category.getDescription()+"' name='category'>Modify</button>");
                                out.println("<input type='hidden' name='actionCategory' value='categoryToEdit'/>");
                                out.println("</td>");
                        }
                        if(request.getParameter("actionCategory").equals("Delete Categories")){
                                out.println("<td scope='col'><button class='btn btn-danger' type='submit' value='"+category.getId()+";"+category.getDescription()+"' name='category'>Delete</button>");
                                out.println("<input type='hidden' name='actionCategory' value='categoryToDelete'/>");
                                out.println("</td>");
                        }
                    }
                   out.println("</tr>");
                
                }//tanca el for
        out.println(" </tbody>  </table>   </form>");
 }

    
 
// Add New Category -->
if (request.getParameter("showFormAdd")!= null){
%>

 <jsp:include page = "html/addCategoryForm.html"/>    

<%
}
               

// Modify Category -->
if(request.getAttribute("categorymodify")!=null){
        Category categorymodify=(Category)request.getAttribute("categorymodify");
%>   

        <form action="categoryController" method="POST">
            <div class="form-group">
            <label for="inputCat">New Category Description or Name:</label>
<%
            out.println("<input type='text' class='form-control' id='inputCat' value='"+categorymodify.getDescription()+"' name='categoryName' required></div>");
            out.println("<input type='hidden' value='"+categorymodify.getId()+"' name='categoryID'/>");
%>
            <button type="submit" class="btn btn-primary" name="actionCategory" value="modifyCategory">Modify</button>
        </form>
<%
}

//Success message -->
if(request.getAttribute("success") != null){
    out.println("<div class='alert alert-success' role='alert'>"+request.getAttribute("success")+"</div>");
}

//Error message -->
if(request.getAttribute("error") != null){
    out.println("<div class='alert alert-success' role='alert'>"+request.getAttribute("error")+"</div>");
}
%>
<jsp:include page="html/footer.html"/>