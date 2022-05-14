<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <style>
        .container {
            display: flex;
            width: 100%;
            height: 600px;
            padding: 5px;
        }

        .posts {
            flex-grow: 1;
            border: #323232 solid 1px;
            padding: 5px;
            overflow: auto;
            width: 45vw;
            height: 100%;
        }

        .posts__item {
            position: relative;
            border: #323232 1px solid;
            border-radius: 5px;
            box-shadow: 1px 1px 1px 1px gray;
            height: 2rem;
            padding-left: 1rem;
        }

        .posts__item div {
            position: absolute;
            left: 2rem;
            top: 50%;
            transform: translateY(-50%);
        }

        .form {
            flex-grow: 1;
            border: #323232 solid 1px;
            padding: 5px;
            width: 45vw;
            height: 100%;
        }

        .form__title {
            display: flex;
            gap: 1rem;
            margin-bottom: 0.5rem;
        }

        .form__title > input {
            flex-grow: 1;
        }

        .form__text-area {
            width: calc(100% - 10px);
            height: 300px;
        }

        .form__button {
            margin-top: 10px;
            width: 100%;
        }
    </style>
    <script type="text/javascript">
        function postDetail(id) {
            const form = document.createElement("form");
            document.body.appendChild(form)
            form.action = '/post/' + id
            form.method = 'GET'
            form.submit()
        }
    </script>
    <meta charset="UTF-8">
    <title>Stored XSS 공격</title>
</head>

<body>
<h1>Stored XSS 공격</h1>

<div class="container">
    <div class="posts">
        <c:forEach var="post" items="${posts}">
            <div class="posts__item" onclick="postDetail(${post.id})">
                <div>
                    <span>제목</span>
                    <span><c:out value="${post.title}"/></span>
                </div>
            </div>
        </c:forEach>
    </div>
    <div class="form">
        <form action="/post" method="POST">
            <div class="form__title">
                <span>제목</span>
                <input type="text" name="title"/>
            </div>
            <div>
                <span>내용</span>
            </div>
            <textarea class="form__text-area" type="text" name="content"></textarea>
            <input class="form__button" type="submit" value="저장"/>
        </form>
    </div>
</div>

</body>
</html>