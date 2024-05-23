import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Génère la vue d'un clavier et associe le contrôleur aux touches
 * le choix ici est d'un faire un héritié d'un TilePane
 */
public class Clavier extends TilePane{
    /**
     * il est conseillé de stocker les touches dans un ArrayList
     */
    private List<Button> clavier;

    /**
     * constructeur du clavier
     * @param touches une chaine de caractères qui contient les lettres à mettre sur les touches
     * @param actionTouches le contrôleur des touches
     * @param tailleLigne nombre de touches par ligne
     */
    public Clavier(String touches, EventHandler<ActionEvent> actionTouches) {
        super();
        clavier = new ArrayList<Button>();
        for (int i = 0; i < touches.length(); i++) {
            Button b = new Button(touches.substring(i, i + 1));
            b.setOnAction(actionTouches);
            b.setMinWidth(50);
            b.setMinHeight(50); 
            b.setMaxWidth(50); 
            b.setMaxHeight(50);
            b.setStyle("-fx-background-radius: 30 20;"); // Ajoutez cette ligne pour arrondir le bouton
            clavier.add(b);
            this.getChildren().add(b);
        }
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(10, 10, 10, 10));
    }

    /**
     * permet de désactiver certaines touches du clavier (et active les autres)
     * @param touchesDesactivees une chaine de caractères contenant la liste des touches désactivées
     */
    public void desactiveTouches(Set<String> touchesDesactivees){
        for (Button b : clavier) {
            if (touchesDesactivees.contains(b.getText())) {
                b.setDisable(true);
            } else {
                b.setDisable(false);
            }
        }
    }
}
