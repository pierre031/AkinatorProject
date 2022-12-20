package akinator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author stag
 */
public class AkinatorServlet extends HttpServlet {

    //Liste des Items (questions et animaux)
    ArrayList<Item> animaux = new ArrayList<>();
    //Vérifie si c'est la dernière question
    Boolean isLastQuestion = false;
    //Retourne l'objet précédent
    Item lastItem = null;
    //Initialise l'objet courant de la prochaine boucle
    Item currentItem = null;
    //Réponse de la dernière question posée (pas l'animal)
    Boolean lastOuiNon = null;

    public AkinatorServlet() {
        super();
        this.animaux.add(new Item("chien"));
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        try {
            Item item;
            /**
             * Vérification de si on est dans la 1° boucle/tour (currentItem == null)
             * Si on est dans la 1° boucle:
             * retourne le 1° élément de la liste
             * sinon
             * récupère l'objet initialisé à la boucle précédente
             */
            if (currentItem == null) {
                item = addFirst();
            } else {
                item = currentItem;
            }

            lastItem = item;
            /**
             * Vérification que l'item n'ai pas d'enfants 
             * pas de rep_oui ni rep_non
             * Si pas d'enfants
             * on définit l'item comme la dernière question
             * proposition de réponse
             * Sinon
             * pose une question pour avancer dans l'arbre
             */
            if (item.rep_oui == null || item.rep_non == null) {
                this.isLastQuestion = true;
                request.setAttribute("question", "Est-ce que c'est un " + item.title + " ?");
            } else {
                request.setAttribute("question", item.title);
            }

        } catch (Exception ex) {
            ex.getMessage();
        }
        //Redirection sur la page du jeu
        getServletContext()
                .getRequestDispatcher("/WEB-INF/AffichageAkinator.jsp")
                .forward(request, response);

    }

    /**
     * Retourne la 1° question de la liste
     * Si la liste ne contient qu'un Item, retourne le 1° objet (1° tour)
     * @return Item
     * @throws Exception 
     */
    private Item addFirst() throws Exception {
        if (animaux.size() > 1) {
            for (Item animal : animaux) {
                if (animal.first == true) {
                    return animal;
                }
            }
        } else if (animaux.size() == 1) {
            return animaux.get(0);
        } else {
            throw new Exception("Erreur");
        }
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Test si on est sur la dernière question
        if (isLastQuestion == true) {
            isLastQuestion = false;
            //Si on clique sur "oui" il a gagné
            if (request.getParameter("oui") != null) {
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/Victoire.jsp")
                        .forward(request, response);
            //Si on clique sur non, il a perdu, redirection sur le formulaire pour qu'il apprenne
            } else if (request.getParameter("non") != null) {
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/Formulaire.jsp")
                        .forward(request, response);
            }
        } else {
            if (request.getParameter("oui") != null) {
                if (lastItem != null && lastItem.rep_oui != null && lastItem.rep_non != null) {
                    lastOuiNon = true;
                }
                //On définit l'id sur celle de la rep_oui
                UUID id = lastItem.rep_oui;
                //Définit l'item de la réponse oui pour la prochaine boucle
                currentItem = findItem(id);
            } else if (request.getParameter("non") != null) {
                if (lastItem != null && lastItem.rep_oui != null && lastItem.rep_non != null) {
                    lastOuiNon = false;
                }
                //On définit l'id sur celle de la rep_non
                UUID id = lastItem.rep_non;
                //Définit l'item de la réponse non pour la prochaine boucle
                currentItem = findItem(id);
            }
        }

        if (request.getParameter("rejouer") != null) {
            //Récupère les entrées du formulaire
            String reponse = request.getParameter("reponse");
            String question = request.getParameter("question");
            String ouinon = request.getParameter("ouinon");
            //Création d'un objet avec la réponse
            Item item = new Item(reponse);
            //Création d'un objet avec la question
            Item itemQuestion = new Item(question);
            if (ouinon.equals("oui")) {
                //Associe la rep_oui de la question à l'id de l'item qu'on vient de créer
                itemQuestion.rep_oui = item.id;
                //Associe la rep_non de la question avec l'id de la réponse précédente
                itemQuestion.rep_non = lastItem.id;
            } else {
                //Associe la rep_oui de la question à l'id de la réponse précédente
                itemQuestion.rep_oui = lastItem.id;
                //Associe la rep_non de la question avec l'id de l'item qu'on vient de créer
                itemQuestion.rep_non = item.id;
            }

            //Si il n'y a pas de first la question qu'on vient de créer le devient
            if (!hasFirst()) {
                itemQuestion.first = true;
            } else {
                //On récupère la dernière question
                Item lastQuestion = findLastQuestion(currentItem.id);
                if (lastQuestion != null) {
                    //On récupère l'index de la dernière question dans l'arbre
                    Integer animauxIndex = animaux.indexOf(lastQuestion);
                    if (lastOuiNon != null && lastOuiNon) {
                        //Modifie le rep_oui de la dernière question dans l'arbre
                        animaux.get(animauxIndex).setRep_oui(itemQuestion.id);
                    } else {
                        //Modifie le rep_non de la dernière question dans l'arbre
                        animaux.get(animauxIndex).setRep_non(itemQuestion.id);
                    }
                }
            }
            currentItem = null;
            lastOuiNon = null;
            //Ajout de l'animal et la question correspondante dans l'arbre
            animaux.add(item);
            animaux.add(itemQuestion);
        }
        
        //Renvoi sur la page d'accueil
        if (request.getParameter("gagner") != null) {
            currentItem = null;
            lastOuiNon = null;
            getServletContext()
                        .getRequestDispatcher("/")
                        .forward(request, response);
        }
        processRequest(request, response);
    }

    /**
     * Cherche si un first existe dans la liste
     * @return boolean
     */
    private Boolean hasFirst() {
        for (Item animal : animaux) {
            if (animal.first == true) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourne l'objet correspondant à l'id dans la liste
     * @param id
     * @return Item
     */
    private Item findItem(UUID id) {
        for (Item animal : animaux) {
            if (animal.id == id) {
                return animal;
            }
        }
        return null;
    }

    /**
     * Retourne la dernière question
     * @param id
     * @return Item
     */
    private Item findLastQuestion(UUID id) {
        for (Item animal : animaux) {
            if (animal.rep_oui == id || animal.rep_non == id) {
                return animal;
            }
        }
        return null;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
