<%-- 
    Document   : view-cart.jsp
    Created on : Jun 18, 2020, 3:25:03 PM
    Author     : thehien
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View cart page</title>
        <style type="text/css">

            .btn-cart{
                font-size: 1.05em !important;
            }
            .book-now{
                background-color: #35ff53 !important;
                color: gray !important;
            }
            .book-now:hover{
                background-color: #00ca17 !important;
                color: white !important;
            }
            th{
                text-align: center !important;
            }
            .action{
                font-size: 1.8em !important;
            }
            .form-control{
                width: 60px !important;
            }
            .img-preview{
                width: 120px;
                border-radius: 4px;
            }
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
            .hint-text {
                float: left;
                margin-top: 10px;
                font-size: 13px;
            }
            #removeModal .modal-dialog {
                position: absolute !important;
                top: 200px;
                right: 150px;
                z-index: 10040;
                overflow: auto;
                overflow-y: auto;
            }
        </style>
    </head>
    <body>
        <jsp:include page="components/header.jsp"/>

        <c:set var="currentBooking" value="${sessionScope.USER.currentBooking}"/>
        <c:set var="listDiscounts" value="${requestScope.LIST_DISCOUNTS}" />
        <c:set var="errPayment" value="${requestScope.ERR_PAYMENT_TYPE}" />
        <c:set var="discountErr" value="${requestScope.INVALID_DISCOUNT}"/>
        <c:set var="quotaErr" value="${requestScope.INVALID_QUOTA}"/>
        <c:set var="tourDateErr" value="${requestScope.INVALID_TOUR_DATE}"/>

        <div class="container">
            <div class="table-wrapper">
                <div class="table-title">
                    <div class="row">
                        <div class="col-sm-5">
                            <h2>Booking <b>Management</b>

                            </h2>
                        </div>
                        <div class="col-sm-7">
                            <a href="home" class="btn btn-primary btn-cart">
                                <i class="material-icons"></i> 
                                <span>Add New Tour</span>
                            </a>
                            <a href="view-booking-history" class="btn btn-primary btn-cart">
                                <span class="material-icons">
                                    history_edu
                                </span>
                                <span>Booking History</span>
                            </a>
                            <button class="btn btn-primary book-now btn-cart" data-toggle="modal" data-target="#exampleModal">
                                <i class="material-icons">
                                    event_available
                                </i>
                                <span>Book Now</span>
                            </button>						
                        </div>
                    </div>
                </div>
                <c:if test="${empty currentBooking.items}">
                    <div class="text-center d-flex align-items-center justify-content-center text-secondary">
                        <img src="img-global/no-data.png" width="100"/>

                        <span style="font-size: 2em">
                            Your cart is empty!!
                        </span>
                    </div>
                </c:if>
                <c:if test="${not empty currentBooking.items}">
                    <c:if test="${not empty quotaErr || not empty tourDateErr || not empty discountErr}">
                        <p class="alert alert-danger">
                            There are some errors during booking, please check your cart item! <br/>
                            <span>${discountErr}</span>
                        </p>
                    </c:if>

                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Image</th>						
                                <th>Name</th>						
                                <th>Start Date</th>
                                <th>End Date</th>
                                <th>Amount</th>
                                <th>Price (VNĐ)</th>
                                <th>Total (VNĐ)</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach varStatus="counter" var="dto" items="${currentBooking.items}">

                            <form action="update-item" method="POST">


                                <tr style="<c:if test="${(not empty tourDateErr.get(dto.travelTourId)) || (not empty quotaErr.get(dto.travelTourId))}">
                                    background-color: #f8d7da !important
                                    </c:if>">
                                    <td>${counter.count}</td>
                                    <td>
                                        <img src="${dto.travelTour.imageLink}" class="img-preview" ></td>
                                    <td style="max-width: 220px">
                                        ${dto.travelTour.tourName}
                                    </td>                        
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <span class="mr-1">${dto.travelTour.fromDateDisplay}</span>
                                            <c:if test="${not empty tourDateErr.get(dto.travelTourId)}">
                                                <span data-toggle="tooltip" data-placement="top" title="Tour has expired!">
                                                    <span class="material-icons text-danger">info</span>
                                                </span>
                                            </c:if>
                                        </div>
                                    </td>                        
                                    <td>${dto.travelTour.toDateDisplay}</td>                        
                                    <td>
                                        <span class="form-group d-flex align-items-center">
                                            <input name="txtAmount" type="text" class="form-control mr-1" value="${dto.amount}">
                                            <c:if test="${not empty quotaErr.get(dto.travelTourId)}">
                                                <span data-toggle="tooltip" data-placement="top" title="Has ${quotaErr.get(dto.travelTourId)} left!">
                                                    <span class="material-icons text-danger">info</span>
                                                </span>
                                            </c:if>
                                        </span>
                                    </td>
                                    <td>
                                        ${dto.travelTour.priceDisplay}
                                    </td>
                                    <td>
                                        ${dto.priceDisplay}
                                    </td>
                                    <td class="">
                                        <c:url var="removeItemLink" value="remove-item">
                                            <c:param name="txtTourId" value="${dto.travelTourId}"/>
                                        </c:url>
                                        <span>
                                            <div class="d-flex justify-content-center">
                                                <button type="button" class="btn text-warning btn-light mr-2 border" data-toggle="modal" data-target="#removeModal">
                                                    <span class="material-icons action">
                                                        delete_forever
                                                    </span>
                                                </button>
                                                <div class="modal fade" id="removeModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel2" aria-hidden="true">
                                                    <div class="modal-dialog modal-sm">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title" id="exampleModalLabel">Remove Confirmation</h5>
                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span>
                                                                </button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <p>
                                                                    "${dto.travelTour.tourName}": <br/> Item selected will be removed?
                                                                </p>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                                                <a href="${removeItemLink}" class="btn btn-warning text-white">Continue</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <input type="hidden" name="txtTourId" value="${dto.travelTourId}" />
                                                <button class="btn text-success btn-light border" type="submit">
                                                    <span class="material-icons action">
                                                        update
                                                    </span>
                                                </button>
                                            </div>
                                        </span>
                                    </td>
                                </tr>
                            </form>
                        </c:forEach>
                        <c:if test="${not empty listDiscounts}">
                            <form action="apply-discount" method="POST">
                                <tr>
                                    <td colspan="3"></td>
                                    <td>
                                        <span class="d-flex">
                                            <span class="material-icons ml-2 text-warning">
                                                local_offer
                                            </span>
                                            -
                                            <span>
                                                Coupon:
                                            </span>

                                        </span>
                                    </td>
                                    <td colspan="2">
                                        <div class="d-flex">
                                            <select name="txtDiscountId" class="custom-select" id="inputGroupSelect01">
                                                <option value="">Choose...</option>
                                                <c:forEach var="dto" items="${listDiscounts}">
                                                    <option <c:if test="${dto.id eq currentBooking.discountId}">selected</c:if> value="${dto.id}">${dto.code}</option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                    </td>
                                    <td>
                                        ${currentBooking.discount.valueDisplay}

                                    </td>
                                    <td>
                                        ${currentBooking.discountPriceDisplay}
                                    </td>
                                    <td>
                                        <span class="d-flex">
                                            <button type="submit" class="btn btn-info p-1 mr-2">Apply</button>
                                            <a href="remove-discount" class="btn btn-warning p-1">Remove</a>
                                        </span>

                                    </td>
                                </tr>
                            </form>

                        </c:if>

                        <tr>
                            <td colspan="6"></td>
                            <td colspan="1">
                                <span class="d-flex">
                                    <span class="material-icons text-info">
                                        payments
                                    </span>
                                    -
                                    <span>Total:</span>
                                </span>


                            </td>
                            <td colspan="2"><b>${currentBooking.totalPriceDisplay}</b></td>
                        </tr>

                        </tbody>
                    </table>
                </c:if>

            </div>
            <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form action="confirm-booking" method="POST">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Booking Confirmation</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <c:if test="${not empty errPayment}">
                                    <p class="alert alert-danger">${errPayment}</p>
                                </c:if>
                                <div class="radio">
                                    <label class="d-flex align-items-center">
                                        <input type="radio" name="txtPaymentType" value="Direct payment"> 
                                        <span class="material-icons mx-2">
                                            store
                                        </span>
                                        Direct payment - <small><i>(At 190 Pasteur, P.6, Q.3, Tp. Hồ Chí Minh, Việt Nam)</i></small>
                                    </label>
                                </div>
                                <div class="radio">
                                    <label class="d-flex align-items-center">

                                        <input type="radio" name="txtPaymentType" value="Charged with Momo"> 
                                        <span class="material-icons mx-2">
                                            account_balance_wallet
                                        </span>
                                        Online payment via Momo
                                    </label>
                                </div>


                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                <button type="submit" class="btn btn-primary">Continue</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </div>
        <script>
            $(function () {
                $('[data-toggle="tooltip"]').tooltip();
            })
        </script>
    </body>
</html>
