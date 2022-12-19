
package akinator;

import java.util.UUID;

/**
 *
 * @author stag
 */
public class Item {
    
    UUID id;
    String title;
    UUID rep_oui = null;
    UUID rep_non = null;
    Boolean first = false;

    public Item(String title) {
        id = UUID.randomUUID();
        this.title = title;
        this.rep_oui = null;
        this.rep_non = null;
    }

    public void setRep_oui(UUID rep_oui) {
        this.rep_oui = rep_oui;
    }

    public void setRep_non(UUID rep_non) {
        this.rep_non = rep_non;
    }
    
    
}
