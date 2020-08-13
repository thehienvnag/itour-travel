<%-- 
    Document   : admin
    Created on : Jun 10, 2020, 7:45:36 PM
    Author     : USER
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Tour Page</title>
        <style>
            .create-tour{
                margin-top: 30px;
                width: 700px !important;
                padding: 2% 5%;
            }
            .preview-image{
                margin: 0 auto;
                height: 180px;
                width: 230px;
                overflow: hidden;
            }
            .img{
                max-width: 100%;
            }
            .error{
                padding: 4px 6px;
                background: rgb(248,215,218);
                border-radius: 5px;
                display: block;
                color: gray;
            }
        </style>

    </head>
    <body>
        <jsp:include page="components/header.jsp"/>
        <c:set var="imgLink" value="${requestScope.IMG_LINK}"/>
        <c:set var="location" value="${requestScope.LOCATION}"/>
        <c:set var="tourName" value="${requestScope.TOUR_NAME}"/>
        <c:set var="quota" value="${requestScope.QUOTA}"/>
        <c:set var="price" value="${requestScope.PRICE}"/>
        <c:set var="dateStart" value="${requestScope.DATE_START}"/>
        <c:set var="dateEnd" value="${requestScope.DATE_END}"/>

        <div class="border bg-light create-tour container rounded">
            <form id="formCreateTour" action="create-tour-controller" method="POST" enctype="multipart/form-data" >
                <c:if test="${not empty imgLink}">
                    <span id="msgSuccess">
                        <input type="hidden" name="txtHasCreated" value="true" />
                        <p class="alert alert-success">Successfully created tour!</p>
                    </span>

                </c:if>
                <c:if test="${not empty param.txtLocation && empty imgLink}">
                    <p class="alert alert-danger">Failed to create tour, try again later!</p>
                </c:if>
                <h2 class="m-0 mb-3">Create Tour</h2>

                <div class="form-row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="inputTourName"><span class="text-danger">(*) </span>Tour Name: </label>
                            <input name="txtTourName" type="text" class="form-control" id="inputTourName" value="${tourName}">
                        </div>
                        <div class="form-group">
                            <label for="inputToLocation"><span class="text-danger">(*) </span>To Location:</label>
                            <input name="txtLocation" type="text" class="form-control" id="inputToLocation" placeholder="Ha Noi, etc.." value="${location}">
                        </div>
                    </div>
                    <div class="col-md-6 p-2">
                        <div class="border py-3 rounded bg-white">
                            <div class="preview-image text-center">
                                <img id="previewImage" src="${imgLink}" class="rounded img">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-4">
                        <label for="inputPrice"><span class="text-danger">(*) </span>Price:</label>
                        <input name="txtPrice" type="text" class="form-control" id="inputPrice" value="${price}">
                    </div>
                    <div class="form-group col-md-2">
                        <label for="inputQuota"><span class="text-danger">(*) </span>Quota:</label>
                        <input name="txtQuota" type="text" class="form-control" id="inputQuota" value="${quota}">
                    </div>
                    <div class="form-group col-md-6">
                        <label for="inputImage"><span class="text-danger">(*) </span>Image:</label>
                        <div class="input-group">
                            <div class="custom-file mb-0">
                                <input name="fileImage" type="file" accept="image/*" class="custom-file-input" id="inputImage" aria-describedby="inputGroupFileAddon04">
                                <label class="custom-file-label" for="inputImage">Choose file</label>
                            </div>
                        </div>
                        <input id="fileNameInput" type="hidden" name="txtFileInput" value="" />
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-6">
                        <div class="docs-datepicker mb-3">

                            <div class="form-group">
                                <label for="dateStart"><span class="text-danger">(*) </span>Date Start:</label>
                                <input id="dateStart" type="text" name="txtDateStart" value="${dateStart}" class="form-control docs-date" name="date" autocomplete="off">
                            </div>
                        </div>
                        <div class="docs-datepicker-container"></div>
                    </div>
                    <div class="col-md-6">
                        <div class="docs-datepicker mb-3">

                            <div class="form-group">
                                <label for="dateEnd"><span class="text-danger">(*) </span>Date End:</label>
                                <input id="dateEnd" name="txtDateEnd" value="${dateEnd}" type="text" class="form-control docs-date" name="date" autocomplete="off">
                            </div>
                            <div class="docs-datepicker-container"></div>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Create Tour</button>
                <input type="reset" class="btn btn-secondary" id="btnReset" value="Reset">
            </form>
        </div>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/datepicker/1.0.9/datepicker.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.2/dist/jquery.validate.min.js"></script>
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

                const inputEle = document.getElementById("inputImage");
                const imgEle = document.getElementById("previewImage");
                const form = document.getElementById("formCreateTour");
                const fileNameInput = document.getElementById("fileNameInput");

                const inputImgLabel = document.getElementsByClassName("custom-file-label")[0];

                const validateImageInput = (fileName) => {
                    return /\.(jpg|png|gif)$/i.test(fileName);
                }
                if (/TravelTour\/(create-tour|create-tour-controller)$/.test(imgEle.src)) {
                    imgEle.src = "img-global/default.png";
                }
                const loadImage = (e) => {

                    const file = e.target.files[0];
                    if (validateImageInput(file.name)) {
                        const reader = new FileReader();
                        reader.onload = () => {
                            imgEle.src = reader.result;
                            inputImgLabel.innerHTML = file.name;
                            fileNameInput.value = file.name;
                        };

                        reader.readAsDataURL(file);
                    } else {
                        form.reset();
                        imgEle.src = "img-global/default.png";
                        inputImgLabel.innerHTML = "";
                        fileNameInput.value = "";
                    }

                }
                inputEle.addEventListener('change', loadImage);

                const validator = $('#formCreateTour').validate({
                    ignore: [], // initialize the plugin
                    rules: {
                        txtLocation: {
                            required: true,
                            minlength: 3,
                            maxlength: 50
                        },
                        txtTourName: {
                            required: true,
                            minlength: 10,
                            maxlength: 235,
                        },
                        txtPrice: {
                            required: true,
                            min: 100000,
                            max: 200000000,
                            number: true
                        },
                        txtQuota: {
                            required: true,
                            min: 5,
                            max: 200,
                            digits: true
                        },
                        txtDateStart: {
                            required: true
                        },
                        txtDateEnd: {
                            required: true
                        },
                        txtFileInput: {
                            required: true
                        },
                    },
                    messages: {
                        txtLocation: {
                            required: "This field is required",
                            minlength: "This field requires at least 3 chars",
                            maxlength: "This field requires at most 50 chars"
                        },
                        txtTourName: {
                            required: "This field is required",
                            minlength: "This field requires at least 10 chars",
                            maxlength: "This field requires at most 230 chars"
                        },
                        txtPrice: {
                            required: "This field is required",
                            number: "Require number input",
                            min: "Must be at least 100,000 (vnd)",
                            max: "Must be at most 200,000,000 (vnd)"
                        },
                        txtQuota: {
                            required: "Required",
                            digits: "Require number input",
                            min: "Must be at least 5 people",
                            max: "Must be at most 200 people"
                        },
                        txtDateStart: {
                            required: "This field is required"
                        },
                        txtDateEnd: {
                            required: "This field is required"
                        },
                        txtFileInput: {
                            required: "This field is required"
                        },
                    }
                });
                const resetWarning = () => {
                    validator.resetForm();
                    imgEle.src = "img-global/default.png";
                    inputImgLabel.innerHTML = "";
                    document.getElementById("msgSuccess").innerHTML = "";
                }
                document.getElementById("btnReset").addEventListener('click', resetWarning);
            });


        </script>
    </body>
</html>
