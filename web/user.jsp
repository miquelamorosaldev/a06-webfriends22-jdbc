<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="html/menu.jsp"/>

<!-- List All Users with modify and delete buttons -->
<c:if test="${users != null}">
    <form action="userController" method="POST">
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">User Name</th>
                    <th scope="col">Password</th>
                    <th scope="col">Role</th>

                    <c:if test="${param.actionUser == 'Modify Users'}">
                        <th scope="col">Modify User</th>
                    </c:if>

                    <c:if test="${param.actionUser == 'Delete Users'}">
                        <th scope="col">Delete User</th>
                    </c:if>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <th scope="row">${user.username}</th>
                        <td>${user.password}</td>
                        <td>${user.role}</td>
                        <c:if test="${param.actionUser == 'Modify Users'}">
                            <td scope="col">
                                <button class="btn btn-success" type="submit" value="${user.username};${user.password}" name="user">Modify</button>
                                <input type="hidden" name="actionUser" value="userToEdit"/>
                            </td>
                        </c:if>
                        <c:if test="${param.actionUser == 'Delete Users'}">
                            <td scope="col">
                                <button class="btn btn-danger" type="submit" value="${user.username};${user.password}" name="user">Delete</button>
                                <input type="hidden" name="actionUser" value="userToDelete"/>
                            </td>                                        
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </form>
</c:if>

<!-- Add New User -->
<c:if test="${param.showFormAdd != null}" >
    <jsp:include page = "html/addUserForm.html"/>                
</c:if>

<!-- Modify User -->

<c:if test="${usermodify != null}">
    <form action="userController" method="POST">
        <div class="form-group">
            <label for="inputCat">New User Description or Name:</label>
            <input type="text" class="form-control" id="inputCat" value="${usermodify.username}" name="userName" required>
        </div>
        <div class="form-group">
            <label for="inputCat">New User Description or Name:</label>
            <input type="text" class="form-control" id="inputCat" value="${usermodify.password}" name="userName" required>
        </div>
        <input type="hidden" value="${usermodify.id}" name="userID"/>
        <button type="submit" class="btn btn-primary" name="actionUser" value="modifyUser">Modify</button>
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