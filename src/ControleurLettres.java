import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * Controleur du clavier
 */
public class ControleurLettres implements EventHandler<ActionEvent> {

    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;
    /**
     * vue du jeu
     */
    private Pendu vuePendu;

    /**
     * @param modelePendu modèle du jeu
     * @param vuePendu vue du jeu
     */
    ControleurLettres(MotMystere modelePendu, Pendu vuePendu){
        this.modelePendu = modelePendu;
        this.vuePendu = vuePendu;
    }

    /**
     * Actions à effectuer lors du clic sur une touche du clavier
     * Il faut donc: Essayer la lettre, mettre à jour l'affichage et vérifier si la partie est finie
     * @param actionEvent l'événement
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Button bouton = (Button) actionEvent.getSource();
        char lettre = bouton.getText().charAt(0);
        this.modelePendu.essaiLettre(lettre);
        this.vuePendu.majAffichage();
        if (this.modelePendu.gagne()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bravo !");
            alert.setHeaderText("Vous avez gagné !");
            alert.showAndWait();
            this.vuePendu.modeAccueil();
            
        }
        else if (this.modelePendu.perdu()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Perdu !");
            alert.setHeaderText("Vous avez perdu !");
            alert.showAndWait();
            this.vuePendu.modeAccueil();
            this.vuePendu.lancePartie();
        }
    }
}
