<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <style>
        .form {
            border: #323232 solid 1px;
            margin: auto;
            padding: 10px;
            width: 70vw;
            height: 100%;
        }

        .form__input {
            display: flex;
            flex-direction: column;
            gap: 1rem;
        }

        .form__input > div {
            display: flex;
            justify-content: space-between;
        }

        .form__button {
            margin-top: 10px;
            width: 100%;
        }

        .container {
            position: relative;
            overflow-x: auto;
            border: #323232 solid 1px;
            margin: 10px auto;
            padding: 10px;
            width: 70vw;
            height: 300px;
        }

        .container__header {
            position: absolute;
            top: 1rem;
            right: 1rem;
        }
    </style>
    <meta charset="UTF-8">
    <title>Reflected XSS 공격</title>
</head>

<body>
<h1>Reflected XSS 공격</h1>

<div class="form">
    <form action="/reflected" method="get">
        <div class="form__input">
            <div>
                <span>검색어</span>
                <input type="text" name="keyword"/>
            </div>
        </div>
        <input class="form__button" type="submit" value="검색"/>
    </form>
</div>

<div class="container">
    <div class="container__header">검색어 ${keyword}</div>
</div>

</body>
</html>