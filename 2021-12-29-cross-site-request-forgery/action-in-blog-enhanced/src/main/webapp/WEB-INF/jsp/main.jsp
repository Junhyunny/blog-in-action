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

        function uuidv4() {
            return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                let r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
                return v.toString(16);
            });
        }

        function doubleSubmitHandler() {
            let uuid = uuidv4();
            document.cookie = 'CSRF_TOKEN=' + uuid + ";path=/";
            fetch('http://localhost:8081/change', {
                method: 'POST',
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                    'X-CSRF-HEADER': uuid
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
<%--    <button onclick="onSubmitHandler()">Submit</button>--%>
    <button onclick="doubleSubmitHandler()">Double Submit Cookie</button>
</div>
</body>
</html>
