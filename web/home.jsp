<%-- 
    Document   : home.jsp
    Created on : Jun 12, 2020, 7:11:10 PM
    Author     : thehien
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-slider/11.0.2/css/bootstrap-slider.min.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
              crossorigin="anonymous">

        <style>
            .tooltip{
                background: black;
            }
            .bg-white {
                box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.1);
            }
            .paging-bar{
                width: 100%;
                position: fixed;
                bottom: 0;
                right: 0;
            }
        </style>
    </head>
    <body>
        <jsp:include page="components/header.jsp"/>
        <c:set var="listTour" value="${requestScope.LIST_TOUR}"/>
        <c:set var="userInfo" value="${sessionScope.USER}"/>

        <div class="container mt-5">
            <form action="search-tour" autocomplete="off">
                <div class="bg-white p-3 mb-3 text-center" style="max-width: 760px; margin: 0 auto">
                    <div class="row">

                        <div class="col-md-6">
                            <div class="input-group mb-3">
                                <input type="text" name="txtLocation" value="${param.txtLocation}" class="form-control" placeholder="Location" >
                                <div class="input-group-append">
                                    <span class="input-group-text" id="basiaddon2">
                                        <span class="material-icons">
                                            pin_drop
                                        </span>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="text-center mb-3 text-center">
                                <input type="hidden" id="hasSearched" value="${not empty param.txtPriceRange}" />
                                <div id="viewPriceRange" class="d-none">
                                    <span>
                                        Price from <span id="fromPrice" style="font-weight: bold"></span>
                                        to <span id="toPrice" style="font-weight: bold"></span> (VND)
                                    </span>
                                    <input style="width: 90%" id="ex12c" type="text" name="txtPriceRange"/>
                                    <input id="priceRange" type="hidden" value="${param.txtPriceRange}" />
                                </div>
                                <div id="viewBtnPriceRange" class="<c:if test="${not empty param.txtPriceRange}">d-none</c:if>">
                                        <button id="btnViewPriceRange" type="button" class="btn btn-info px-3 d-flex align-items-center">
                                            <span class="material-icons mr-2">
                                                filter_alt
                                            </span>
                                            Filter by price
                                        </button>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="docs-datepicker mb-3">
                                    <div class="input-group">
                                        <input id="dateStart" type="text" name="txtDateStart" value="${param.txtDateStart}" class="form-control docs-date" name="date" placeholder="Start date" autocomplete="off">
                                    <div class="input-group-append">
                                        <span class="input-group-text" id="basiaddon2">
                                            <span class="material-icons">
                                                flight_takeoff
                                            </span>
                                        </span>
                                    </div>
                                </div>
                                <div class="docs-datepicker-container"></div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="docs-datepicker mb-3">
                                <div class="input-group">
                                    <input id="dateEnd" name="txtDateEnd" value="${param.txtDateEnd}" type="text" class="form-control docs-date" name="date" placeholder="End date" autocomplete="off">

                                    <div class="input-group-append">
                                        <span class="input-group-text" id="basiaddon2">

                                            <span class="material-icons">
                                                flight_land
                                            </span>
                                        </span>
                                    </div>
                                </div>
                                <div class="docs-datepicker-container"></div>
                            </div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-info px-5">Search</button>
                </div>
            </form>
            <c:if test="${empty listTour && (not empty param.txtLocation || not empty param.txtPriceRange || not empty param.txtDateStart || not empty param.txtDateEnd)}">
                <div class="container py-3 bg-white rounded text-center d-flex align-items-center justify-content-center text-secondary" style="max-width: 700px; margin: 0 auto">
                    <img src="img-global/no-data.png" width="100"/>

                    <span class="ml-1" style="font-size: 2em">
                        It seems like there's not related data!!
                    </span>
                </div>
            </c:if>
            <c:if test="${not empty listTour}">
                <c:forEach varStatus="counter" var="dto" items="${listTour}">
                    <form action="add-to-cart" method="POST">
                        <div class="card <c:if test="${counter.count != 20}">mb-3</c:if> bg-white"  style="min-width: 380px; margin: 0 auto; <c:if test="${counter.count == 20}">margin-bottom: 80px</c:if>">
                                <div class="row no-gutters">
                                    <div class="col-md-3 p-3">
                                            <img src="${dto.imageLink}" width="100%" height="100%">
                                </div>
                                <div class="col-md-9">

                                    <div class="card-body row">
                                        <div class="col-md-9">
                                            <h5 class="card-title">${dto.tourName}</h5>
                                            <p class="card-text text-primary">${dto.priceDisplay} VNĐ</p>
                                            <p class="card-text text-warn">${dto.fromDateDisplay} - ${dto.toDateDisplay}</p>
                                            <p class="card-text"><small class="text-muted">Imported date: ${dto.importedDateDisplay}</small></p>
                                        </div>
                                        <div class="col-md-3 text-right">
                                            <input id="scrollTop" type="hidden" name="txtScrollTop" value="" />
                                            <input type="hidden" name="txtTourId" value="${dto.tourId}" />
                                            <input type="hidden" name="txtLocation" value="${param.txtLocation}" />
                                            <input type="hidden" name="txtPriceRange" value="${param.txtPriceRange}" />
                                            <input type="hidden" name="txtDateStart" value="${param.txtDateStart}" />
                                            <input type="hidden" name="txtDateEnd" value="${param.txtDateEnd}" />
                                            <input type="hidden" name="page" value="${param.page}" />
                                            <c:if test="${empty userInfo || userInfo.roleId eq 2}">
                                                <button class="btn btn-success mb-3 mt-2 mr-2" type="submit" 
                                                        <c:if test="${dto.leftQuota - userInfo.getCurrentBookingAmount(dto.tourId) <= 0}">
                                                            disabled
                                                        </c:if>
                                                        >
                                                    <span class="material-icons">
                                                        add_shopping_cart
                                                    </span>
                                                    Add to cart
                                                </button>
                                                <button class="btn">
                                                    <span class="material-icons">
                                                        favorite_border
                                                    </span>
                                                </button>
                                            </c:if>

                                            <c:if test="${dto.leftQuota - userInfo.getCurrentBookingAmount(dto.tourId) <= 0}">
                                                <p class="alert alert-danger text-center p-1" style="max-width: fit-content; display: inline-block">Out of stock</p>
                                            </c:if>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>

                </c:forEach>
                
                <input id="scrollTopLastValue" type="hidden" value="${param.txtScrollTop}" />

                <c:set var="totalPages" value="${requestScope.TOTAL_PAGES}"/>
                <c:set var="page" value="${param.page}"/>
                <c:if test="${empty page}">
                    <c:set var="page" value="${1}"/>
                </c:if>
                <c:set var="startPage" value="${page - 2}"/>

                <c:if test="${startPage + 4 > totalPages}">
                    <c:set var="startPage" value="${totalPages - 4}"/>
                </c:if>
                <c:if test="${startPage < 1}">
                    <c:set var="startPage" value="${1}"/>
                </c:if>

                <c:set var="lastSearchUrl" value="${requestScope.LAST_SEARCH_URL}"/>

                <c:if test="${totalPages >= 2}">
                    <div class="paging-bar bg-light pt-3">
                        <nav class="container d-flex align-items-center justify-content-between">
                            <div class="hint-text">Found <b>${requestScope.TOTAL_ROWS}</b> entries</div>
                            <ul class="pagination justify-content-end">
                                <li class="page-item <c:if test="${page eq 1}">disabled</c:if>">
                                    <a class="page-link" href="${lastSearchUrl}&page=${page - 1}" >Previous</a>
                                </li>
                                <li class="page-item <c:if test="${page eq startPage}">active</c:if>">
                                    <a class="page-link" href="${lastSearchUrl}&page=${startPage}">${startPage}</a>
                                </li>
                                <li class="page-item <c:if test="${page eq startPage + 1}">active</c:if>">
                                    <a class="page-link" href="${lastSearchUrl}&page=${startPage + 1}">${startPage + 1}</a>
                                </li>
                                <c:if test="${totalPages >= 3}">
                                    <li class="page-item <c:if test="${page eq startPage + 2}">active</c:if>">
                                        <a class="page-link" href="${lastSearchUrl}&page=${startPage + 2}">${startPage + 2}</a>
                                    </li>
                                </c:if>
                                <c:if test="${totalPages >= 4}">
                                    <li class="page-item <c:if test="${page eq startPage + 3}">active</c:if>">
                                        <a class="page-link" href="${lastSearchUrl}&page=${startPage + 3}">${startPage + 3}</a>
                                    </li>
                                </c:if>
                                <c:if test="${totalPages >= 5}">
                                    <li class="page-item <c:if test="${page eq startPage + 4}">active</c:if>">
                                        <a class="page-link" href="${lastSearchUrl}&page=${startPage + 4}">${startPage + 4}</a>
                                    </li>
                                </c:if>
                                <li class="page-item <c:if test="${page eq totalPages}">disabled</c:if>">
                                    <a class="page-link" href="${lastSearchUrl}&page=${page + 1}">Next</a>
                                </li>
                            </ul>
                        </nav>
                    </div>



                </c:if>

            </c:if>

        </div>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/datepicker/1.0.9/datepicker.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-slider/11.0.2/bootstrap-slider.min.js"></script>

        <script>
            $(function () {
                const convertDate = (dateStr) => {
                    try {
                        const dateArr = dateStr.split("-");
                        return new Date(dateArr[2], dateArr[1] * 1 - 1, dateArr[0]);
                    } catch (e) {
                        return null;
                    }
                    
                    return null;
                }
                
                'use strict';
                const dateStartEle = document.getElementById("dateStart");
                const dateEndEle = document.getElementById("dateEnd");
                var $date = $('.docs-date');
                var options = {
                    show: function (e) {
                        console.log(e.type, e.namespace);
                    },
                    hide: function (e) {
                        const target = e.target;
                        const date = convertDate(target.value);
                        if (date == "Invalid Date") {
                            target.value = "";
                            return;
                        }
                        const id = target.id;
                        if (id == "dateStart") {
                            const dateEnd = convertDate(dateEndEle.value);
                            if (date > dateEnd) {
                                target.value = dateEndEle.value;
                            }
                        }
                        if (id == "dateEnd") {
                            const dateStart = convertDate(dateStartEle.value);
                            if (date < dateStart) {
                                target.value = dateStartEle.value;
                            }
                        }
                    },
                    pick: function (e) {
                        
                    },
                    format: "dd-mm-yyyy"
                };
                $date.datepicker(options);
                
                const mySlider = $("#ex12c");
                const displayFromPrice = document.getElementById("fromPrice");
                const displayToPrice = document.getElementById("toPrice");
                const max = 30000000;
                
                const formatPrice = (price, min) => {
                    let priceFormat = (price * 1).toLocaleString('vi-VN');
                    if (!min) {
                        if (price == max) {
                            priceFormat += "+";
                        }
                    }
                    return priceFormat;
                    
                }
                const onChangeSlider = function () {
                    const sliderValue = mySlider[0].value;
                    const sliderValueArr = sliderValue.split(",");
                    let minValue = sliderValueArr[0];
                    let maxValue = sliderValueArr[1];
                    if (minValue === maxValue && minValue === "0") {
                        maxValue = "1000000";
                        mySlider.slider("setValue", [0, maxValue * 1]);
                    }
                    displayFromPrice.innerHTML = formatPrice(minValue, "min");
                    displayToPrice.innerHTML = formatPrice(maxValue);
                }
                
                const setSliderValue = () => {
                    const priceRange = document.getElementById("priceRange").value;
                    if (priceRange) {
                        const priceValues = priceRange.split(",").map(ele => ele * 1);
                        mySlider.slider("setValue", priceValues);
                    }
                    onChangeSlider();
                }
                
                const initSlider = (e) => {
                    $("#viewPriceRange").removeClass("d-none");
                    $("#viewBtnPriceRange").addClass("d-none");
                    
                    mySlider.slider({
                        id: "slider12c",
                        min: 0,
                        max: max,
                        range: true,
                        step: 100000,
                        tooltip: 'hide',
                        value: [0, max]
                    });
                    
                    displayFromPrice.innerHTML = "0";
                    displayToPrice.innerHTML = "30.000.000+";
                    mySlider.change(onChangeSlider);
                    setSliderValue();
                    
                }
                
                $("#btnViewPriceRange").click(initSlider);
                
                
                const determineShowPreviousSearchPrice = () => {
                    const hasSearch = $("#hasSearched").val();
                    if (hasSearch === "true") {
                        initSlider();
                    }
                    const lastScrollTop = document.getElementById("scrollTopLastValue");
                    if(lastScrollTop.value){
                        window.scrollTo(0, lastScrollTop.value);
                    }
                }
                determineShowPreviousSearchPrice();
                
                const filerReader = new FileReader();
                filerReader.onerror = (e) => {
                    console.log(e);
                }
            });
            window.onscroll = () => {
                const scrollTop = document.documentElement.scrollTop;
                const txtScrollTopEle = document.getElementsByName("txtScrollTop");
                for (var i = 0; i < txtScrollTopEle.length; i++) {
                    txtScrollTopEle[i].value = scrollTop;
                }
            }
        </script>
    </body>
</html>
