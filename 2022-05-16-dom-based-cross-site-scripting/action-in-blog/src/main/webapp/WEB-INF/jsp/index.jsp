<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <style>
        .container {
            border: red 1px solid;
            overflow: auto;
            width: 80%;
            padding: 5px;
        }

        .item {
            height: 2rem;
            display: flex;
            align-items: center;

            border: #323232 1px solid;
            border-radius: 5px;
            box-shadow: 1px 1px 1px 1px gray;

            padding: 0.5rem;
            margin-top: 0.5rem;

            color: black;
            text-decoration: none;
        }
    </style>
    <script type="text/javascript">
        const hash = window.location.hash.slice(1)
        if (hash) {
            window.location.href = decodeURIComponent(hash)
        }
        window.addEventListener('hashchange', function () {
            window.location.href = decodeURIComponent(window.location.hash.slice(1))
        });
    </script>
    <meta charset="UTF-8">
    <title>DOM Based XSS 공격</title>
</head>

<body>
<h1>DOM Based XSS 공격</h1>

<div class="container">
    <a id="first" href="#first" class="item">First 바로가기</a>
    <a id="second" href="#second" class="item">Second 바로가기</a>
</div>

</body>
</html>