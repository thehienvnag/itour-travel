<%-- 
    Document   : login.jsp
    Created on : Jun 12, 2020, 6:50:25 PM
    Author     : thehien
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <style type="text/css">
            .modal-login {
                color: #636363;
                max-width: 340px !important;
            }
            .form-control {
                box-shadow: none;		
                font-weight: normal;
                font-size: 13px;
            }
            .form-control:focus {
                border-color: #33cabb;
                box-shadow: 0 0 8px rgba(0,0,0,0.1);
            }
            .or-seperator {
                margin-top: 32px;
                text-align: center;
                border-top: 1px solid #e0e0e0;
            }
            .or-seperator b {
                color: #666;
                padding: 0 8px;
                width: 30px;
                height: 30px;
                font-size: 13px;
                text-align: center;
                line-height: 26px;
                background: #fff;
                display: inline-block;
                border: 1px solid #e0e0e0;
                border-radius: 50%;
                position: relative;
                top: -15px;
                z-index: 1;
            }
            .navbar .checkbox-inline {
                font-size: 13px;
            }
            .navbar .navbar-right .dropdown-toggle::after {
                display: none;
            }
            @media (min-width: 1200px){
            }
            @media (max-width: 768px){

            }
            .modal-content{
                padding: 7%;
            }
        </style>
    </head>
    <body>
        <jsp:include page="components/header.jsp"/>
        <jsp:useBean id="faceBookUtil" class="hienht.fb.FacebookUtil"/>

        <div class="modal-dialog modal-login">
            <div class="modal-content">

                <form action="login" method="POST">
                    <c:if test="${not empty requestScope.ERR}">
                        <p class="alert alert-danger">${requestScope.ERR}</p>
                    </c:if>

                    <h2 class="text-center">Member Login</h2>
                    <p class="hint-text text-center">Sign in with your social media account</p>
                    <div class="form-group social-btn clearfix text-center">
                        <a href="${faceBookUtil.linkOpenLoginDialog}" class="btn btn-primary"><i class="fa fa-facebook"></i> Facebook</a>
                    </div>
                    <div class="or-seperator"><b>or</b></div>

                    <div class="form-group">
                        <input name="txtUsername" type="text" class="form-control" placeholder="Username" required="required">
                    </div>
                    <div class="form-group">
                        <input name="txtPassword" type="password" class="form-control" placeholder="Password" required="required">
                    </div>
                    <input type="submit" class="btn btn-primary btn-block" value="Login">
                    <c:if test="${not empty requestScope.ERR_LOGIN}">
                        <div class="form-footer text-center alert alert-danger" style="margin-top: 15px">
                            ${requestScope.ERR_LOGIN}
                        </div>
                    </c:if>

                </form>
            </div>
        </div>


    </body>
</html>
