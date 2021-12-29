<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Vulnerable Site</title>
    <style>

        * {
            margin: 0;
            padding: 0;
        }

        #wrap form {
            margin: 0 auto;
            width: 50%;
            position: relative;
            border: 1px solid #ccc;
        }

        #wrap form div {
            overflow: hidden;
            padding-left: 5px;
        }

        #wrap form div label {
            display: inline-block;
            float: left;
            width: 75px;
            font-size: 15px;
            margin: 5px 0 5px;
        }

        #wrap form div input {
            display: inline-block;
            float: left;
            height: 15px;
            margin: 5px 0 5px;
        }
    </style>
</head>
<body>
<div id="wrap">
    <form action="/login" method="post">
        <div>
            <label for="memberId">
                ID :
            </label>
            <input type="text" name="id" id="memberId"/>
        </div>
        <div>
            <label for="memberPassword">
                비밀번호 :
            </label>
            <input type="password" name="password" id="memberPassword"/>
        </div>
        <input type="submit" value="전송"/>
    </form>
</div>
</body>
</html>