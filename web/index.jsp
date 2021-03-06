<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="html/menu.jsp"/>

<h3>WebFriends Login (Pending)</h3>
<!-- 
<p>Benvingut al nostre portal de bioinformàtica.</p>
<p>On podràs trobar notícies, si estàs registrat, molt més.</p> 
-->
<!-- RWD Form -->
<div class="form-row">
    <form method="post" action="authenticationController">
        <div class="col">
            <label>Username:</label> 
            <input class="form-control" type="text" name="username"/>
        </div>
        <div class="col">
            <label>Password:</label>
            <input class="form-control" type="password" name="password" />
        </div>    
        <div class="col-md-6">
            <button class="buttonSubmit btn btn-primary" 
                    accesskey="" type="submit" name="action" value="login">Log In</button>     
        </div>
    </form>
</div>
<%
    if (request.getAttribute("error") != null) {
        //out.println("Usuario y/o contraseña incorrectas");
        String error = request.getAttribute("error").toString();
        out.println(error);
//        if (error.equals("1")) {
//            out.println("Usuario y/o contraseña incorrectas");
//        }
    }

%>
</main>

<jsp:include page="html/footer.html"/>