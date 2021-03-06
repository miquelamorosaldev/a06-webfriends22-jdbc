<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Friends</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" crossorigin="anonymous">
    <link rel="stylesheet" href="https://getbootstrap.com/docs/4.0/examples/starter-template/starter-template.css" />
    <link rel="stylesheet" href="css/general.css" />
</head>
<body>
    <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
        <a class="navbar-brand" href="index.jsp">Friends</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarsExampleDefault">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="index.jsp">Home</a>
                </li>
                <% if(session.getAttribute("user")!=null) { %>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Friends</a>
                        <div class="dropdown-menu" aria-labelledby="dropdown01">
                            <a class="dropdown-item" href="friendController?actionFriend=List all Friends">List all Friends</a>
                            <a class="dropdown-item" href="friendController?actionFriend=Find Friends">Find Friends</a>
                            <a class="dropdown-item" href="friendController?actionFriend=New Friend">New Friend</a>
                            <a class="dropdown-item" href="friendController?actionFriend=loadFriendsListToModify">Modify a Friend</a>
                            <a class="dropdown-item" href="friendController?actionFriend=Delete a Friend">Delete a Friend</a>
                        </div>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="dropdown02" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Categories</a>
                        <div class="dropdown-menu" aria-labelledby="dropdown02">
                            <a class="dropdown-item" href="categoryController?actionCategory=List all categories">List All Categories</a>
                            <a class="dropdown-item" href="categoryController?actionCategory=Insert a category">Insert a Category</a>
                            <a class="dropdown-item" href="categoryController?actionCategory=Modify Categories">Modify Categories</a>
                            <a class="dropdown-item" href="categoryController?actionCategory=Delete Categories">Delete Categories</a>
                        </div>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="dropdown03" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Patients</a>
                        <div class="dropdown-menu" aria-labelledby="dropdown03">
                            <a class="dropdown-item" href="patientController?actionPatient=List Patients">List Patients</a>
                            <a class="dropdown-item" href="patientController?actionPatient=Filter Patients By Status">Filter Patients By Status</a>
                        </div>
                    </li>

                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="dropdown04" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Users</a>
                        <div class="dropdown-menu" aria-labelledby="dropdown04">
                            <a class="dropdown-item" href="userController?actionUser=List all Users">List Users</a>
                        </div>
                    </li>
                    
                    <li class="nav-item active">
                        <a class="nav-link" href="authenticationController?actionUser=Logout">Logout</a>
                    </li>
                <% } %>
            </ul>
        </div>
    </nav>
    <main class="container" role="main">
        <!-- <div class="starter-template"> -->
        <div>