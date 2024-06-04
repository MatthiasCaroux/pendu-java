import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;

/**
 * Contrôleur à activer lorsque l'on clique sur le bouton engrenage
 */
public class ControleurParametre implements EventHandler<ActionEvent> {
    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;
    /**
     * vue du jeu
     **/
    private Pendu vuePendu;

    /**
     * @param modelePendu modèle du jeu
     * @param p vue du jeu
     */
    public ControleurParametre(MotMystere modelePendu, Pendu vuePendu) {
        this.modelePendu = modelePendu;
        this.vuePendu = vuePendu;
    }

    /**
     * L'action consiste à recommencer une partie. Il faut vérifier qu'il n'y a pas une partie en cours
     * @param actionEvent l'événement action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Alert alert = this.vuePendu.popUpChangementDeCouleur();
        Optional<ButtonType> reponse = alert.showAndWait();
        if (reponse.isPresent() && reponse.get() == ButtonType.YES) { // si la réponse est OK
            ColorPicker colorPicker = (ColorPicker) alert.getDialogPane().getContent();
            this.vuePendu.setCouleur(colorPicker.getValue());
            this.vuePendu.changerCouleur();
        }
    }
}