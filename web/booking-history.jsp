<%-- 
    Document   : booking-history
    Created on : Jun 21, 2020, 2:46:32 PM
    Author     : thehien
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking History Page</title>
        <style type="text/css">
            body {
                color: #566787;
                background: #f5f5f5;
                font-family: 'Varela Round', sans-serif;
                font-size: 13px;
            }
            .table-wrapper {
                background: #fff;
                padding: 20px 25px;
                margin: 30px 0;
                border-radius: 3px;
                box-shadow: 0 1px 1px rgba(0,0,0,.05);
            }
            .table-title {
                padding-bottom: 15px;
                background: #299be4;
                color: #fff;
                padding: 16px 30px;
                margin: -20px -25px 10px;
                border-radius: 3px 3px 0 0;
            }
            .table-title h2 {
                margin: 5px 0 0;
                font-size: 24px;
            }
            .table-title .btn {
                color: #566787;
                float: right;
                font-size: 13px;
                background: #fff;
                border: none;
                min-width: 50px;
                border-radius: 2px;
                border: none;
                outline: none !important;
                margin-left: 10px;
            }
            .table-title .btn:hover, .table-title .btn:focus {
                color: #566787;
                background: #f2f2f2;
            }
            .table-title .btn i {
                float: left;
                font-size: 21px;
                margin-right: 5px;
            }
            .table-title .btn span {
                float: left;
                margin-top: 2px;
            }
            table.table tr th, table.table tr td {
                border-color: #e9e9e9;
                padding: 12px 15px;
                vertical-align: middle;
            }
            table.table tr th:first-child {
                width: 60px;
            }
            table.table tr th:last-child {
                width: 100px;
            }
            table.table-striped tbody tr:nth-of-type(odd) {
                background-color: #fcfcfc;
            }
            table.table-striped.table-hover tbody tr:hover {
                background: #f5f5f5;
            }
            table.table th i {
                font-size: 13px;
                margin: 0 5px;
                cursor: pointer;
            }	
            table.table td:last-child i {
                opacity: 0.9;
                font-size: 22px;
                margin: 0 5px;
            }
            table.table td a {
                font-weight: bold;
                color: #566787;
                display: inline-block;
                text-decoration: none;
            }
            table.table td a:hover {
                color: #2196F3;
            }
            table.table td a.settings {
                color: #2196F3;
            }
            table.table td a.delete {
                color: #F44336;
            }
            table.table td i {
                font-size: 19px;
            }
            table.table .avatar {
                border-radius: 50%;
                vertical-align: middle;
                margin-right: 10px;
            }
            .status {
                font-size: 30px;
                margin: 2px 2px 0 0;
                display: inline-block;
                vertical-align: middle;
                line-height: 10px;
            }
            .text-success {
                color: #10c469;
            }
            .text-info {
                color: #62c9e8;
            }
            .text-warning {
                color: #FFC107;
            }
            .text-danger {
                color: #ff5b5b;
            }
            .pagination {
                float: right;
                margin: 0 0 5px;
            }
            .pagination li a {
                border: none;
                font-size: 13px;
                min-width: 30px;
                min-height: 30px;
                color: #999;
                margin: 0 2px;
                line-height: 30px;
                border-radius: 2px !important;
                text-align: center;
                padding: 0 6px;
            }
            .pagination li a:hover {
                color: #666;
            }	
            .pagination li.active a, .pagination li.active a.page-link {
                background: #03A9F4;
            }
            .pagination li.active a:hover {        
                background: #0397d6;
            }
            .pagination li.disabled i {
                color: #ccc;
            }
            .pagination li i {
                font-size: 16px;
                padding-top: 6px
            }
            .hint-text {
                float: left;
                margin-top: 10px;
                font-size: 13px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="components/header.jsp"/>
        <div class="container mt-5">
            <div class="table-wrapper">
                <div class="table-title bg-info">
                    <div class="row">
                        <div class="col-sm-5">
                            <h2>Booking <b>Management</b></h2>
                        </div>
                    </div>
                </div>
                <c:set var="bookingList" value="${requestScope.BOOKING_LIST}"/>
                <c:if test="${empty bookingList}">
                    <div class="text-center d-flex align-items-center justify-content-center text-secondary">
                        <img src="img-global/no-data.png" width="100"/>

                        <span style="font-size: 2em">
                            It seems like there's not related data!!
                        </span>
                    </div>
                </c:if>
                
                <c:if test="${not empty bookingList}">
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Code</th>						
                                <th>Date Imported</th>
                                <th>Discount (vnd)</th>
                                <th>Total (vnd)</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>



                            <c:forEach varStatus="counter" var="item" items="${bookingList}">
                                <c:url var="viewBookingLink" value="finish-booking">
                                    <c:param name="bookingCode" value="${item.bookingCode}"/>
                                </c:url>
                                <c:if test="${item.statusId eq 1}">
                                    <c:set var="textColor" value="text-success"/>
                                    <c:set var="textStatus" value="SUCCESS"/>
                                </c:if>
                                <c:if test="${item.statusId eq 2}">
                                    <c:set var="textColor" value="text-warning"/>
                                    <c:set var="textStatus" value="PENDING"/>
                                </c:if>
                                <tr>
                                    <td>${counter.count}</td>
                                    <td><a href="${viewBookingLink}">${item.bookingCode}</a></td>
                                    <td>${item.importDateDisplay}</td>                        
                                    <td>${item.discountPriceDisplay}</td>
                                    <td>${item.totalPriceDisplay}</td>
                                    <td>
                                        <div class="d-flex align-items-center ${textColor}">
                                            <span style="font-size: 1.5em" class="material-icons mr-2">hdr_strong</span> <span>${textStatus}</span>
                                        </div>

                                    </td>
                                    <td>
                                        <div class="d-flex">
                                            <a href="${viewBookingLink}" class="settings btn btn-info p-1 mr-2" >
                                                <span class="material-icons text-white">
                                                    search
                                                </span>
                                            </a>
                                            <c:url var="payNowLink" value="pay-now">
                                                <c:param name="bookingId" value="${item.id}"/>
                                            </c:url>
                                            <c:if test="${item.statusId eq 2}">
                                                <a href="${payNowLink}" class="btn btn-success p-1 text-white" >
                                                    PayNow
                                                </a>
                                            </c:if>


                                        </div>

                                    </td>
                                </tr>
                            </c:forEach>


                        </tbody>
                    </table>
                </c:if>

            </div>
        </div>
    </body>
</html>
