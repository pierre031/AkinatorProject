<%-- 
    Document   : Victoire
    Created on : 19 déc. 2022, 15:50:04
    Author     : stag
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Félicitations !!!!</h1>
        
        <form action="${pageContext.request.contextPath}">
            <input type="submit" value="Retour à l'accueil">
        </form>
    </body>
</html>
