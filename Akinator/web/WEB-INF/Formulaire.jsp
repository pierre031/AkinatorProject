
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
            <h1>Félicitations <br> 
                Tu as gagné !!!</h1>
        </header>
        
        <form method="post" class="form">
            <div>
                <label for="reponse">Votre réponse</label>
                <input type="text" name="reponse" placeholder="Votre réponse">
            </div>
            <div>
                <label for="reponse">Votre question</label>
                <input type="text" name="question" placeholder="Votre question">
            </div>
            <div>
                <label for="reponse">Réponse à votre question</label>
                <input type="text" name="ouinon" placeholder="La réponse à votre question">
            </div>
            <div id="replay">
                <input type="submit" value="Rejouer" name="rejouer">
            </div>
        </form>
        
        <footer>
            <p>&copy; Eve, David et Pierre</p>
            <p>LDNR-CDA-2022/2023</p>
        </footer>
    </body>
</html>
