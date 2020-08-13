<%-- 
    Document   : success-booking
    Created on : Jun 20, 2020, 6:30:04 AM
    Author     : thehien
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Success Booking Page</title>
        <style type="text/css">
            .img-preview{
                width: 90px;
                border-radius: 4px;
            }
            .modal-confirm {	
                box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.1);
                color: #434e65;
                max-width: 700px !important;
            }
            .modal-confirm .modal-content {
                padding: 20px;
                font-size: 16px;
                border-radius: 5px;
                border: none;
            }
            .modal-confirm .modal-header {
                background: #47c9a2;
                border-bottom: none;   
                position: relative;
                text-align: center;
                margin: -20px -20px 0;
                border-radius: 5px 5px 0 0;
            }
            .modal-confirm h4 {
                text-align: center;
                font-size: 36px;
                margin: 10px 0;
            }
            .modal-confirm .form-control, .modal-confirm .btn {
                min-height: 40px;
                border-radius: 3px; 
            }
            .modal-confirm .close {
                position: absolute;
                top: 15px;
                right: 15px;
                color: #fff;
                text-shadow: none;
                opacity: 0.5;
            }
            .modal-confirm .close:hover {
                opacity: 0.8;
            }

            .modal-confirm.modal-dialog {
                margin-top: 80px;
            }
            .modal-confirm .btn {
                color: #fff;
                border-radius: 4px;
                background: #eeb711;
                text-decoration: none;
                transition: all 0.4s;
                line-height: normal;
                border-radius: 30px;
                margin-top: 10px;
                padding: 6px 20px;
                border: none;
            }
            .modal-confirm .btn:hover, .modal-confirm .btn:focus {
                background: #eda645;
                outline: none;
            }
            .modal-confirm .btn span {
                margin: 1px 3px 0;
                float: left;
            }
            .modal-confirm .btn i {
                margin-left: 1px;
                font-size: 20px;
                float: right;
            }
            .trigger-btn {
                display: inline-block;
                margin: 100px auto;
            }
            .table{
                font-size: 0.9em;
            }
            td{
                vertical-align: middle !important;
            }
        </style>
    </head>
    <body>
        <jsp:include page="components/header.jsp"/>
        <c:set var="status" value="${requestScope.BOOKING_STATUS}"/>
        <c:set var="booking" value="${requestScope.BOOKING_CONFIRM}"/>
        <c:set var="paymentType" value="${booking.paymentType}"/>
        <c:if test="${status eq 'Failed'}">
            <c:set var="statusDisplay" value="FAILED"/>
            <c:set var="bgDisplay" value="bg-danger"/>
            <c:set var="iconDisplay" value="close"/>
        </c:if>
        <c:if test="${booking.statusId eq 1}">
            <c:set var="statusDisplay" value="SUCCESS"/>
            <c:set var="bgDisplay" value="bg-success"/>
            <c:set var="iconDisplay" value="check"/>
        </c:if>
        <c:if test="${booking.statusId eq 2}">
            <c:set var="statusDisplay" value="PENDING"/>
            <c:set var="bgDisplay" value="bg-warning"/>
            <c:set var="iconDisplay" value="cached"/>
        </c:if>

        <div class="modal-dialog modal-confirm">
            <div class="modal-content">
                <div class="modal-header d-block text-white ${bgDisplay}">
                    <div class="d-flex justify-content-between">
                        <h5 class="text-white d-flex align-items-center m-0">
                            <span class="material-icons mr-2" style="font-size: 1.4em;">
                                ${iconDisplay}
                            </span>
                            ${statusDisplay}
                        </h5>
                        <h5 class="text-white m-0 text-right">
                            ${booking.totalPriceDisplay} <small>(vnd)</small>
                        </h5>
                    </div>
                    <div class="d-flex justify-content-between align-items-center">

                        <p style="margin: 0">${booking.importDateDisplay}</p>

                        <p style="margin: 0"><b>Booking code:</b> ${booking.bookingCode}</p>

                        <small>${paymentType}<b> (${statusDisplay})</b></small>
                    </div>
                </div>
                <div class="modal-body text-center">
                    <c:if test="${status eq 'Failed'}">
                        <h4>Something went wrong!</h4>	
                        <p>Please try again later!</p>
                        <a href="view-cart" class="btn btn-warning"><span>Back to cart</span> <i class="material-icons">sentiment_dissatisfied</i></a>
                    </c:if>
                    <c:if test="${status ne 'Failed'}">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Your Order</th>
                                    <th scope="col">QNTY</th>
                                    <th scope="col">UNIT (vnd)</th>
                                    <th scope="col">PRICE (vnd)</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach varStatus="counter" var="dto" items="${booking.items}">
                                    <tr>
                                        <td>${counter.count}</th>
                                        <td>
                                            <div class="d-flex align-items-center" style="width: 220px">
                                                <img src="${dto.travelTour.imageLink}" class="img-preview mr-3" >
                                                <span class="text-left">${dto.travelTour.tourName}</span>
                                            </div>

                                        </td>
                                        <td>
                                            ${dto.amount}
                                        </td>
                                        <td>
                                            ${dto.travelTour.priceDisplay}
                                        </td>
                                        <td>
                                            ${dto.priceDisplay}
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${not empty booking.discount}">
                                    <tr>
                                        <td colspan="2"></td>
                                        <td>
                                            <span class="d-flex">
                                                <span><b>DISCOUNT:</b></span>
                                            </span>
                                        </td>
                                        <td>
                                            <b>${booking.discount.valueDisplay}</b>
                                        </td>
                                        <td>
                                            <b>${booking.discountPriceDisplay}</b>
                                        </td>
                                    </tr>
                                </c:if>

                                <tr>
                                    <td colspan="2"></td>
                                    <td>
                                        <span class="d-flex">
                                            <span><b>TOTAL:</b></span>
                                        </span>
                                    </td>
                                    <td>
                                    </td>
                                    <td>
                                        <b>${booking.totalPriceDisplay}</b>
                                    </td>
                                </tr>

                            </tbody>
                        </table>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
