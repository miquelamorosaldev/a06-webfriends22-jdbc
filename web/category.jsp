<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="html/menu.html"/>

<!-- List All Categories with modify and delete buttons -->
<c:if test="${categories != null}">
    <form action="categoryController" method="POST">
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">Category ID</th>
                    <th scope="col">Category Description</th>

                    <c:if test="${param.actionCategory == 'Modify Categories'}">
                        <th scope="col">Modify Category</th>
                    </c:if>

                    <c:if test="${param.actionCategory == 'Delete Categories'}">
                        <th scope="col">Delete Category</th>
                    </c:if>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${categories}" var="category">
                    <tr>
                        <th scope="row">${category.id}</th>
                        <td>${category.description}</td>
                        <c:if test="${param.actionCategory == 'Modify Categories'}">
                            <td scope="col">
                                <button class="btn btn-success" type="submit" value="${category.id};${category.description}" name="category">Modify</button>
                                <input type="hidden" name="actionCategory" value="categoryToEdit"/>
                            </td>
                        </c:if>
                        <c:if test="${param.actionCategory == 'Delete Categories'}">
                            <td scope="col">
                                <button class="btn btn-danger" type="submit" value="${category.id};${category.description}" name="category">Delete</button>
                                <input type="hidden" name="actionCategory" value="categoryToDelete"/>
                            </td>                                        
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </form>
</c:if>

<!-- Add New Category -->
<c:if test="${param.showFormAdd != null}" >
    <jsp:include page = "html/addCategoryForm.html"/>                
</c:if>

<!-- Modify Category -->
<c:if test="${categorymodify != null}">
    <form action="categoryController" method="POST">
        <div class="form-group">
            <label for="inputCat">New Category Description or Name:</label>
            <input type="text" class="form-control" id="inputCat" value="${categorymodify.description}" name="categoryName" required>
        </div>
        <input type="hidden" value="${categorymodify.id}" name="categoryID"/>
        <button type="submit" class="btn btn-primary" name="actionCategory" value="modifyCategory">Modify</button>
    </form>
</c:if>

<!-- Success message -->
<c:if test="${success != null}">
    <div class="alert alert-success" role="alert">${success}</div>
</c:if>

<!-- Error message -->
<c:if test="${danger != null}">
    <div class="alert alert-danger" role="alert">${error}</div>
</c:if>

<jsp:include page="html/footer.html"/>