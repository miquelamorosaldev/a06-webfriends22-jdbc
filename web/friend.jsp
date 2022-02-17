<%-- 
    Document   : friends.jsp
    Created on : 06-abr-2021, 19:16:15
    Author     : miquel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="html/menu.html"/>

<!-- Find Friends by filters. -->
<c:if test="${friendfilter != null}" >
    <form action="friendController" method="POST">
        <div class="form-filter form-inline">
            <div class="form-group col-md-6">
                <label for="categoryId">Category:</label>
                <select class="form-control form-select" name="categoryId" aria-label="Category select example">
                    <c:forEach items="${categories}" var="category">
                        <option value="${category.id}">
                            ${category.description}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group col-md-6">
                <button type="submit" class="btn btn-primary" value="FilterFriendsByCategory" name="actionFriend">Find Friends By Category</button>
            </div>
        </div>
    </form>         
</c:if>

<!-- List All Friends with modify and delete buttons -->
<c:if test="${friends != null}">
    <form action="friendController" method="POST">
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">Friend ID</th>
                    <th scope="col">Name</th>
                    <th scope="col">Age</th>
                    <th scope="col">Phone</th>
                    <th scope="col">Category Desc</th>

                    <c:if test="${param.actionFriend == 'loadFriendsListToModify'}">
                        <th scope="col">Modify Friend</th>
                    </c:if>

                    <c:if test="${param.actionFriend == 'Delete a Friend'}">
                        <th scope="col">Delete Friend</th>
                    </c:if>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${friends}" var="friend">
                    <tr>
                        <th scope="row">${friend.friendID}</th>
                        <td>${friend.name}</td>
                        <td>${friend.age}</td>
                        <td>${friend.phone}</td>
                        <td>${friend.categoryDesc}</td>
                        <c:if test="${param.actionFriend == 'loadFriendsListToModify'}">
                            <td scope="col">
                                <button class="btn btn-success" type="submit" value="${friend.friendID}" name="friend">Modify</button>
                                <input type="hidden" name="actionFriend" value="loadFriendFormToModify"/>
                            </td>
                        </c:if>
                        <c:if test="${param.actionFriend == 'Delete a Friend'}">
                            <td scope="col">
                                <button class="btn btn-danger" type="submit" value="${friend.friendID}" name="friend">Delete</button>
                                <input type="hidden" name="actionFriend" value="friendToDelete"/>
                            </td>                                        
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </form>
</c:if>

<!-- Add New Friends -->
<c:if test="${param.showFormAdd != null}" >
    <jsp:include page = "html/addFriendForm.html"/>                
</c:if>

<!-- Edit A Friend -->
<c:if test="${friendmodify != null}" >
    <form action="friendController" method="POST">
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="inputFriend">Friend Name:</label>
                <input type="text" value="${friendmodify.name}" class="form-control" id="friendName" name="friendName" placeholder="Friend Name">
            </div>
            <div class="form-group col-md-6">
                <label for="categoryId">Category:</label>
                <select class="form-control form-select" name="categoryId" aria-label="Category select example">
                    <c:forEach items="${categories}" var="category">
                        <option value="${category.id}">
                            ${category.description}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <!-- 
            <div class="form-group col">
                <label for="inputFriend">Category ID:</label>
                <input type="text" value="${friendmodify.categoryId}" class="form-control" id="friendCategoryId" name="friendCategoryId" placeholder="Category Id">
            </div>
            <div class="form-group col">
                <label for="inputFriend">Category Desc:</label>
                <input type="text" disabled="disabled" value="${friendmodify.categoryDesc}" class="form-control" id="friendCategoryDesc" name="friendCategoryDesc" placeholder="Category Description">
            </div>
            -->
        </div>
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="inputFriend">Friend Age:</label>
                <input type="text" class="form-control" value="${friendmodify.age}" id="friendAge" name="friendAge" placeholder="Friend Age">
             </div>
             <div class="form-group col-md-6">
                <label for="inputFriend">Friend Phone:</label>
                <input type="text" class="form-control" value="${friendmodify.phone}" id="friendPhone" name="friendPhone" placeholder="Friend Phone">
             </div>
        </div>
        <input type="hidden" value="${friendmodify.friendID}" id="id_friends" name="id_friends"/>
        <button type="submit" class="btn btn-primary" value="updateFriend" name="actionFriend">Save Friend</button>
    </form>         
</c:if>

<!-- Success message -->
<c:if test="${success != null}">
    <div class="alert alert-success" role="alert">${success}</div>
</c:if>
    
<!-- Error message -->
<c:if test="${error != null}">
    <div class="alert alert-danger" role="alert">${error}</div>
</c:if>

<jsp:include page="html/footer.html"/>
