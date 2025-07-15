<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MainPage</title>
</head>
<body>
<div>
    <%@ page import="jakarta.servlet.http.HttpSession" %>

    <% HttpSession session1 = request.getSession();
        Integer count = (Integer) session1.getAttribute("count");
        if (count == null) {
            count = 1;
            session1.setAttribute("count", count);
        } else {
            session1.setAttribute("count", ++count);
        }
    %>
    <div>Входов на сайт с: <%=session1.getAttribute("count")%>
    </div>

    <%= request.getContextPath()%>
    <form action="<%= request.getContextPath()%>/auth" method="get">
        <button name="authButton" type="submit">Войти</button>
    </form>
    <form action="<%= request.getContextPath()%>/reg" method="get">
        <button name="regButton" type="submit">Зарегистрироваться</button>
    </form>
</div>
</body>
</html>
