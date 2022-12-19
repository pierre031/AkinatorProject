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

    ArrayList<Item> animaux = new ArrayList<>();
    Boolean isLastQuestion = false;
    Item lastItem = null;
    Item currentItem = null;

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
        response.setContentType("text/html;charset=UTF-8");
        try {
            for (Item animal : animaux) {
                //System.out.println(animal.title + "--" + animal.id + "--" + animal.rep_oui + "--" + animal.rep_non + "\n");
            }
            Item item;
            if (currentItem == null) {
                item = addFirst();
            } else {
                item = currentItem;
            }

            lastItem = item;
            if (item.rep_oui == null || item.rep_non == null) {
                this.isLastQuestion = true;
                request.setAttribute("question", "Est-ce que c'est un " + item.title + " ?");
            } else {
                request.setAttribute("question", item.title);
            }

        } catch (Exception ex) {
            ex.getMessage();
        }
//        request.setAttribute("listeAnimaux", animaux);
        getServletContext()
                .getRequestDispatcher("/WEB-INF/AffichageAkinator.jsp")
                .forward(request, response);

    }

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
        if (isLastQuestion == true) {
            isLastQuestion = false;
            
            if (request.getParameter("oui") != null) {
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/Victoire.jsp")
                        .forward(request, response);
            } else if (request.getParameter("non") != null) {
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/Formulaire.jsp")
                        .forward(request, response);
            }
        } else {
            if (request.getParameter("oui") != null) {
                UUID id = lastItem.rep_oui;
                currentItem = findItem(id);
            } else if (request.getParameter("non") != null) {
                UUID id = lastItem.rep_non;
                currentItem = findItem(id);
            }
        }

        if (request.getParameter("rejouer") != null) {
            System.out.println("rejouer");
            String reponse = request.getParameter("reponse");
            String question = request.getParameter("question");
            String ouinon = request.getParameter("ouinon");
            //System.out.println(reponse + question + ouinon);
            Item item = new Item(reponse);
            Item itemQuestion = new Item(question);
            if (ouinon.equals("oui")) {
                itemQuestion.rep_oui = item.id;
                itemQuestion.rep_non = lastItem.id;
            } else {
                itemQuestion.rep_oui = lastItem.id;
                itemQuestion.rep_non = item.id;
            }
            
            if (!isFirst()) {
                itemQuestion.first = true;

            } else {
                System.out.println(currentItem.title );
                Item lastQuestion = findItemByRep(currentItem.id);
                System.out.println(lastQuestion.title + "--" + lastQuestion.id);
                if (lastQuestion != null) {
                    Integer animauxIndex = animaux.indexOf(lastQuestion);
                    System.out.println(animauxIndex);
                    System.out.println(animaux.get(animauxIndex).title);
                    if (ouinon.equals("oui")) {
                        animaux.get(animauxIndex).setRep_non(itemQuestion.id);
                        //System.out.println(lastItem.title + "--" + lastItem.id + "\n");

                    } else {
                        animaux.get(animauxIndex).setRep_oui(itemQuestion.id);
                    }
                }
            }
            currentItem = null;
            animaux.add(item);
            animaux.add(itemQuestion);
        }
        processRequest(request, response);
    }

    private Boolean isFirst() {
        for (Item animal : animaux) {
            if (animal.first == true) {
                return true;
            }
        }
        return false;
    }

    private Item findItem(UUID id) {
        for (Item animal : animaux) {
            if (animal.id == id) {
                return animal;
            }
        }
        return null;
    }

    private Item findItemByRep(UUID id) {
        for (Item animal : animaux) {
            if (animal.rep_oui == id || animal.rep_non == id) {
                return animal;
            }
            System.out.println(animal.id + "--" + animal.rep_oui + "--" + animal.rep_non + "\n");
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
