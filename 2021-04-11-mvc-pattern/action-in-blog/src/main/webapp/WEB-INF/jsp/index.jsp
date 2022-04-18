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
            overflow: auto;
            border: #323232 solid 1px;
            margin: 10px auto;
            padding: 10px;
            width: 70vw;
            height: 300px;
        }

        .container__cards {
            display: flex;
            flex-direction: column;
            gap: 1rem;
        }

        .container__card {
            padding: 10px;
            border: #323232 solid 1px;
            border-radius: 4px;
            box-shadow: 0 2px 1px 1px gray;
        }
    </style>
    <meta charset="UTF-8">
    <title>멤버 등록</title>
</head>

<body>
<div class="form">
    <form action="/index" method="post">
        <div class="form__input">
            <div>
                <span>ID: </span>
                <input type="text" name="id"/>
            </div>
            <div>
                <span>비밀번호: </span>
                <input type="password" name="password"/>
            </div>
            <div>
                <span>이름: </span>
                <input type="text" name="memberName"/>
            </div>
            <div>
                <span>E-MAIL:</span>
                <input type="text" name="memberEmail"/>
            </div>
        </div>
        <input class="form__button" type="submit" value="전송"/>
    </form>
</div>

<div class="container">
    <div class="container__cards">
        <c:forEach items="${memberList}" var="member">
            <div class="container__card">
                <div>
                    <span>ID</span>
                    <span>${member.getId()}</span>
                </div>
                <div>
                    <span>이름</span>
                    <span>${member.getMemberName()}</span>
                </div>
                <div>
                    <span>E-MAIL</span>
                    <span>${member.getMemberEmail()}</span>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>