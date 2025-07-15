<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>AuthPage</title>
</head>
<body>
<%--<%= request.getContextPath()%>--%>
<form action="${pageContext.request.contextPath}/auth" method="post">
    <input name="email" type="email" placeholder="Почта"/>
    <input name="password" type="password" placeholder="Пароль"/>
    <input name="rememberMe" id="rememberMe" type="checkbox">
    <label for="rememberMe">Запомнить меня</label>
    <button type="submit">Войти</button>


    <div>${ requestScope.errorMessage}</div>

</form>

</body>
</html>
