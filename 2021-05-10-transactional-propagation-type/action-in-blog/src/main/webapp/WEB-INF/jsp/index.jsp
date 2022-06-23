<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>멤버 등록</title>
</head>
<body>
	<div>
		<form action="/jsp/member/index" method="post">
			<p>
				ID : <input type="text" name="id" />
				비밀번호 : <input type="password" name="password" />
				이름 : <input type="text" name="memberName" />
				E-MAIL : <input type="text" name="memberEmail" />
			</p>
			<p>
				<input type="submit" value="전송" />
		</form>
	</div>
	<div class="container">
		<table class="table table-hover table table-striped">
			<tr>
				<th>ID</th>
				<th>이름</th>
				<th>E-MAIL</th>
			</tr>
			<c:forEach items="${memberList}" var="member">
				<tr>
					<th>${member.getId()}</th>
					<th>${member.getMemberName()}</th>
					<th>${member.getMemberEmail()}</th>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>