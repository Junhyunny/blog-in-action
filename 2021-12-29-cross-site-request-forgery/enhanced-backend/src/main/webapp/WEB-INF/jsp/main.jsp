<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Enhanced Site</title>
    <style>
        #wrap {
            margin: 0 auto;
        }
    </style>
    <script>
        function onSubmitHandler() {
            fetch('http://localhost:8081/change?_csrf=' + document.getElementById('csrfToken').value, {
                method: 'POST',
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: new URLSearchParams({
                    memberName: document.getElementById('memberName').value,
                })
            }).then(response => {
                return response.json();
            }).then(data => {
                document.getElementById('pageMemberName').innerHTML = data.memberName;
                document.getElementById('memberName').value = '';
            });
        }
    </script>
</head>
<body>
<div id="wrap">
    <p>
        사용자 <strong id="pageMemberName">${memberName}</strong>님은 인증된 사용자입니다.
    </p>
</div>
<div>
    <input id="memberName" type="text" id="memberName" name="memberName"/>
    <input id="csrfToken" type="hidden" name="_csrf" value="${CSRF_TOKEN}"/>
    <button onclick="onSubmitHandler()">Submit</button>
</div>
</body>
</html>
