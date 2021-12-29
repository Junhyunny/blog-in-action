<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Attacker Site</title>
</head>
<body>
<div id="wrap">
    <h1>악성 페이지</h1>
    <ul>
        <li>
            <a href="http://127.0.0.1:8080/get">숨겨진 이미지 태크를 이용한 공격</a>
        </li>
        <li>
            <a href="http://127.0.0.1:8080/post">숨겨진 폼(form) 태그를 이용한 공격</a>
        </li>
    </ul>
</div>
</body>
</html>