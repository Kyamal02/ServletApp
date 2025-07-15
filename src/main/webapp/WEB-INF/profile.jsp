<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<header>
    <form method="POST" action="${pageContext.request.contextPath}/logout">
        <input type="submit" value="Выход">
        <input type="hidden" name="userId" value="${requestScope.user.getId()}">
    </form>
</header>
<div>
    Ваша почта:
</div>
<div>
    ${requestScope.user.getEmail()}
</div>
<div>
    <h3>Все пользователи: </h3>
    <div>
        <c:forEach var="user" items="${requestScope.users}">
            <p>${user.getEmail()}</p>
            <br>
        </c:forEach>
    </div>


</div>
</body>
</html>
