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
    <script type="text/javascript">
        function moveTeamDetail(id) {
            const element = document.createElement('a')
            element.href = "http://localhost:8080/team/detail/" + id
            element.click()
        }
    </script>
    <meta charset="UTF-8">
    <title>팀 등록</title>
</head>

<body>
<div class="form">
    <form action="/team" method="post">
        <div class="form__input">
            <div>
                <span>팀 이름</span>
                <input type="text" name="teamName"/>
            </div>
        </div>
        <input class="form__button" type="submit" value="전송"/>
    </form>
</div>
`
<div class="container">
    <div class="container__cards">
        <c:forEach items="${teamList}" var="team">
            <div class="container__card" onclick="moveTeamDetail(${team.getId()})">
                <div>
                    <span>팀 이름</span>
                    <span>${team.getTeamName()}</span>
                </div>
                <div>
                    <span>팀 멤버 수</span>
                    <span>${team.getMembersCount()}</span>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>