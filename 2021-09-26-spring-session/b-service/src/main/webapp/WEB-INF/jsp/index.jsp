<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
<body>
</head>

<div>
    <h1>메인 화면</h1>
</div>

<div id="timeout"></div>

<div>
    <h5>어플리케이션 이름
        <c:out value="${applicationName}"/>
    </h5>
</div>

<button onclick="onClick()">요청</button>

<c:if test="${session == null}">
    <div>
        <h5>세션이 없습니다.</h5>
    </div>
</c:if>

<c:if test="${session != null}">
    <div>
        <h5>컨트롤러 접근 횟수
            <c:out value="${session.getAttribute('controllerCount')}"/>
        </h5>
    </div>
</c:if>

<script type="text/javascript">

    let time = 0;
    let element = document.getElementById("timeout");
    element.innerHTML = time + " 초";
    setInterval(function () {
        time += 1;
        element.innerHTML = time + " 초";
    }, 1000);

    function onClick() {
        let randomKey = Math.floor(Math.random() * 2);
        let url = 'http://localhost';
        if (randomKey == 0) {
            url += ':8081';
        } else {
            url += ':8082';
        }
        window.location.href = url;
    }
</script>

</body>
</html>
