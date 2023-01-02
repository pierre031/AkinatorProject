
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="assets/style/style.css">
        <link rel="shortcut icon" href="assets/genie.jpg" type="image/x-icon">
        <title>Akinator</title>
    </head>
    <body>
        <header>
            <img src="./assets/genie.jpg" alt="génie"/>
            <h1>Bienvenue sur notre <br> <span>Akinator</span></h1>
        </header>
        
        <div>
            <p class="question">
                <%= request.getAttribute("question")%>
            </p>
            
            <form action="${pageContext.request.contextPath}/akinator" method="post">
                <input type="submit" value="Oui" class="yes" name="oui">
                <input type="submit" value="Non" class="no" name="non">
            </form>
        </div>

        <footer>
            <p>&copy; Eve, David et Pierre</p>
            <p>LDNR-CDA-2022/2023</p>
        </footer>
    </body>
</html>
