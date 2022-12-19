<%-- 
    Document   : Formulaire
    Created on : 19 déc. 2022, 15:53:36
    Author     : stag
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vous êtes nul !!! :)</title>
    </head>
    <body>
        <h1>Perdu !!!</h1>
        
        <form method="post">
            <label for="reponse">Votre réponse</label>
            <input type="text" name="reponse" placeholder="Votre réponse">
            <label for="reponse">Votre question</label>
            <input type="text" name="question" placeholder="Votre réponse">
            <label for="reponse">Réponse à votre question</label>
            <input type="text" name="ouinon" placeholder="Votre réponse">
            <input type="submit" value="rejouer" name="rejouer">
        </form>
    </body>
</html>
