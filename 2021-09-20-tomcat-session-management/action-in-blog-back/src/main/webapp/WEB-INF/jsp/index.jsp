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

<c:if test="${session == null}">
    <div>
        <h5>세션이 없습니다.</h5>
    </div>
</c:if>

<c:if test="${session != null}">
    <div>
        <h5>필터 접근 횟수</h5>
        <c:out value="${session.getAttribute('filterCount')}"/>
    </div>

    <div>
        <h5>인터셉터 접근 횟수</h5>
        <c:out value="${session.getAttribute('interceptorCount')}"/>
    </div>

    <div>
        <h5>컨트롤러 접근 횟수</h5>
        <c:out value="${session.getAttribute('controllerCount')}"/>
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
</script>

</body>
</html>
