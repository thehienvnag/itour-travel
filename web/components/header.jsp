<%-- 
    Document   : header
    Created on : Feb 14, 2020, 4:48:57 PM
    Author     : thehien
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Header</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
              crossorigin="anonymous">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/datepicker/1.0.9/datepicker.min.css">
        <link rel="shortcut icon" type="image/png" href="../img-global/logo.png"/>
        <style type="text/css">
            body{
                padding-top: 76px;
            }
            #header{
                position: fixed;
                width: 100%;
                top: 0;
                z-index: 8;
            }
            .circle-avatar{
                border-radius: 50%;
                background-color: gray;
                width: 30px;
                height: 30px;
                display: inline-block;
                padding: 2%;
                text-align: center;
                margin-right: 10px;
                color: white;
            }
            .nav-hover{
                font-weight: bold;
            }
            .nav-hover:hover{
                border-radius: 3px;
                background-color: white;
                box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.1);
            }
            .navbar-header.col {
                padding: 0 !important;
            }	
            .navbar {		
                background: #fff;
                padding-left: 16px;
                padding-right: 16px;
                border-bottom: 1px solid #d6d6d6;
                box-shadow: 0 0 4px rgba(0,0,0,.1);
            }
            .nav-link img {
                border-radius: 50%;
                width: 36px;
                height: 36px;
                margin: -8px 0;
                float: left;
                margin-right: 10px;
            }
            .navbar .navbar-brand {
                color: #555;
                padding-left: 0;
                font-family: 'Merienda One', sans-serif;
            }
            .navbar .navbar-brand i {
                font-size: 20px;
                margin-right: 5px;
            }
            .search-box {
                position: relative;
            }	
            .search-box input {
                box-shadow: none;
                padding-right: 35px;
                border-radius: 3px !important;
            }
            .navbar .nav-item i {
                font-size: 18px;
            }
            .navbar .dropdown-item i {
                font-size: 16px;
                min-width: 22px;
            }
            .navbar .nav-item.open > a {
                background: none !important;
            }
            .navbar .dropdown-menu {
                border-radius: 1px;
                border-color: #e5e5e5;
                box-shadow: 0 2px 8px rgba(0,0,0,.05);
                position: absolute;
            }
            .navbar .dropdown-menu li a {
                color: #777;
                padding: 8px 20px;
                line-height: normal;
            }
            .navbar .dropdown-menu li a:hover, .navbar .dropdown-menu li a:active {
                color: #333;
            }	
            .navbar .dropdown-item .material-icons {
                font-size: 21px;
                line-height: 16px;
                vertical-align: middle;
                margin-top: -2px;
            }
            .navbar .badge {
                background: #f44336;
                font-size: 11px;
                border-radius: 20px;
                position: absolute;
                min-width: 10px;
                padding: 4px 6px 0;
                min-height: 18px;
                top: 2px;
            }
            .navbar ul.nav li a.notifications, .navbar ul.nav li a.messages {
                position: relative;
                margin-right: 10px;
            }
            .navbar ul.nav li a.messages {
                margin-right: 20px;
            }
            .navbar a.notifications .badge {
                margin-left: -5px;
            }
            .navbar a.messages .badge {
                margin-left: -4px;
            }	
            .navbar .active a, .navbar .active a:hover, .navbar .active a:focus {
                background: transparent !important;
            }
            .left-8{
                left: 0% !important;
            }
        </style>
    </head>
    <body style="background-color: rgb(26,188,156)">
        <nav id="header" class="navbar navbar-expand-lg navbar-light bg-white">
            <a class="navbar-brand" href="home">
                <img src="img-global/logo.png" width="65"/>
                <img src="img-global/logo-text.png" width="80"/>

            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item pt-2 mx-3">
                        <a class="nav-link nav-hover btn btn-light border" href="home">HOME</a>
                    </li>
                    <c:if test="${empty sessionScope.USER}">
                        <li class="nav-item pt-2 mr-3">
                            <a class="nav-link nav-hover btn btn-light border" href="login-page">LOGIN</a>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.USER.roleId eq 1}">
                        <li class="nav-item pt-2 mr-3">
                            <a class="nav-link nav-hover btn btn-light border" href="create-tour">CREATE TOUR</a>
                        </li>
                        <li class="nav-item pt-2 mr-3">
                            <a class="nav-link nav-hover btn btn-light border" href="manage-booking">MANAGE BOOKING</a>
                        </li>
                    </c:if>


                </ul>
                <div class="row">
                    <c:if test="${not empty sessionScope.USER}">
                        <ul class="nav navbar-nav navbar-right ml-auto d-flex px-3 align-items-center">
                            <c:if test="${sessionScope.USER.roleId eq 2}">
                                <li class="nav-item">
                                    <a href="view-cart" class="notifications btn btn-info border text-white px-3" style="display: flex; align-items: center">
                                        CART
                                        <span class="ml-2">
                                            <span class="material-icons">shopping_cart</span>
                                            <span class="badge">${sessionScope.USER.currentBooking.items.size()}</span>
                                        </span>
                                    </a>
                                </li>
                            </c:if>

                            <!--
                            <li class="nav-item">
                                <a href="#" class="nav-link messages">
                                    <i class="fa fa-envelope-o">

                                    </i>
                                    <span class="badge">10</span>
                                </a>
                            </li>-->
                            <li class="nav-item dropdown d-flex align-items-center" style="width: 200px">
                                <a href="#" data-toggle="dropdown" class="nav-link dropdown-toggle user-action btn btn-light  border">
                                    <span class="circle-avatar">${sessionScope.USER.nameLetter}</span><span class="user-name">${sessionScope.USER.name}</span>
                                    <b class="caret"></b>
                                </a>
                                <ul class="dropdown-menu left-8">
                                    <c:if test="${sessionScope.USER.roleId eq 2}">
                                        <li>
                                            <a href="view-booking-history" class="dropdown-item">
                                                <span class="material-icons">
                                                    history_edu
                                                </span> Booking History
                                            </a>
                                        </li>
                                    </c:if>

                                    <li class="divider dropdown-divider"></li>
                                    <li>
                                        <a href="logout" class="dropdown-item">
                                            <i class="material-icons"></i> Logout
                                        </a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </c:if>
                </div>
            </div>
        </nav>  
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
                crossorigin="anonymous">
        </script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
                crossorigin="anonymous">
        </script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
                crossorigin="anonymous">
        </script>


    </body>
</html>
