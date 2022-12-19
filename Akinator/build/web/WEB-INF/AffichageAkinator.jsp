
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%= request.getAttribute("question") %>
         
        <form action="${pageContext.request.contextPath}/akinator" method="post">
            <input type="submit" value="Oui" name="oui">
            <input type="submit" value="Non" name="non">
        </form>
        
    </body>
</html>
