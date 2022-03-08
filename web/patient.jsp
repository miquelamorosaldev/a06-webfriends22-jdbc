<%-- 
    Document   : patient.jsp
    Created on : 06-mar-2022, 19:16:15
    Author     : miquel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="html/menu.jsp"/>

<h2>Patients LIST</h2>
<p>Select an option from the menu.</p>
<!-- List All Friends with modify and delete buttons -->
<c:if test="${patients != null}">
    <form action="patientController" method="POST">
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">Patient ID</th>
                    <th scope="col">Age</th>
                    <th scope="col">AgeGroup</th>
                    <th scope="col">IMC</th>
                    <th scope="col">Classificació</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${patients}" var="patient">
                    <tr>
                        <td scope="row">${patient.registerId}</td>
                        <td>${patient.age}</td>
                        <td>${patient.ageGroup}</td>
                        <td>${patient.imc}</td>
                        <td>${patient.classification}</td>
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

<!-- Success message -->
<c:if test="${success != null}">
    <div class="alert alert-success" role="alert">${success}</div>
</c:if>
    
<!-- Error message -->
<c:if test="${error != null}">
    <div class="alert alert-danger" role="alert">${error}</div>
</c:if>

<jsp:include page="html/footer.html"/>
