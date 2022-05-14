<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <style>
        .post {
            display: flex;
            flex-direction: column;
            border: #323232 solid 1px;
            margin: 1rem;
            padding: 1rem;
            height: 500px;
        }

        .post__title {
            border: #323232 solid 1px;
            padding: 0.5rem;
        }

        .post__content {
            flex-grow: 1;
            border: #323232 solid 1px;
            padding: 0.5rem;
        }
    </style>
    <meta charset="UTF-8">
    <title>Stored XSS 공격</title>
</head>

<body>
<h1>Stored XSS 공격</h1>

<div class="post">
    <h3 class="post__title">
        <c:out value="${post.title}"/>
        <%-- ${post.title} --%>
    </h3>
    <div class="post__content">
        <pre><c:out value="${post.content}"/></pre>
        <%-- ${post.content} --%>
    </div>
</div>

</body>
</html>