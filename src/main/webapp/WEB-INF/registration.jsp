<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>RegistrationPage</title>
</head>
<body>
<form action="<%= request.getContextPath()%>/reg" method="post" >
    <input name="email" id="email" type="email" placeholder="Почта"/>
    <input name="password" id="password" type="password" placeholder="Пароль"/>
    <input name="rePassword" id="rePassword" type="password" placeholder="Повторите пароль"/>
    <button type="submit">Зарегистрироваться</button>
    <%--    onsubmit="return validatePasswords()--%>
    <c:if test="${not empty  requestScope.errorMessage }">
        <div> ${ requestScope.errorMessage}</div>
    </c:if>
</form>

</body>
<script src="${pageContext.request.contextPath}/js/scripts.js" defer></script>

</html>






