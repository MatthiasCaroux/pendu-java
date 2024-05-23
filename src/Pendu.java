import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData ;

import java.util.List; 
import java.util.Arrays;
import java.io.File;
import java.util.ArrayList;


/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {
    /**
     * modèle du jeu
     **/
    private MotMystere modelePendu;
    /**
     * Liste qui contient les images du jeu
     */
    private ArrayList<Image> lesImages;
    /**
     * Liste qui contient les noms des niveaux
     */    
    public List<String> niveaux;

    // les différents contrôles qui seront mis à jour ou consultés pour l'affichage
    /**
     * le dessin du pendu
     */
    private ImageView dessin;
    /**
     * le mot à trouver avec les lettres déjà trouvé
     */
    private Text motCrypte;
    /**
     * la barre de progression qui indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier;
    /**
     * le text qui indique le niveau de difficulté
     */
    private Text leNiveau;
    /**
     * le chronomètre qui sera géré par une clasee à implémenter
     */
    private Chronometre chrono;
    /**
     * le panel Central qui pourra être modifié selon le mode (accueil ou jeu)
     */
    private BorderPane panelCentral;
    /**
     * le bouton Paramètre / Engrenage
     */
    private Button boutonParametres;
    /**
     * le bouton Accueil / Maison
     */    
    private Button boutonMaison;
    /**
     * le bouton Info / Point d'interrogation renversé
     */
    private Button boutonInfo;
    /**
     * le bouton qui permet de (lancer ou relancer une partie
     */ 
    private Button bJouer;

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono ...)
     */
    @Override
    public void init() {
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");
        
        this.panelCentral = new BorderPane();

        int numDifficulté = this.modelePendu.getNiveau();
        String nomDiff = this.modelePendu.intToString(numDifficulté);
        this.leNiveau = new Text("Niveau " + nomDiff);
        this.pg = new ProgressBar(0);
        this.dessin = new ImageView(this.lesImages.get(0));
        this.clavier = new Clavier("AZERTYUIOPQSDFGHJKLMWXCVBN-", new ControleurLettres(this.modelePendu, this));
        this.motCrypte = new Text(this.modelePendu.getMotCrypte());
        // A terminer d'implementer
    }

    /**
     * @return  le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene(){
        BorderPane fenetre = new BorderPane();
        fenetre.setTop(this.titre());
        fenetre.setCenter(this.panelCentral);
        return new Scene(fenetre, 800, 1000);
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private Pane titre(){
        BorderPane res = new BorderPane();
        Label titre = new Label("Jeu du pendu"); 
        titre.setFont(new Font("Arial", 30));
        titre.setStyle("-fx-font-weight: bold;");
        res.setLeft(titre);
        HBox hb = new HBox();
        boutonMaison = new Button();
        boutonMaison.setOnAction(new ControleurBoutonHome(this.modelePendu, this));
        boutonParametres = new Button();
        boutonInfo = new Button();
        boutonInfo.setOnAction(new ControleurInfos(this));
        ImageView maison = new ImageView(new Image(new File("./img/home.png").toURI().toString()));
        maison.setFitHeight(30);
        maison.setFitWidth(30);
        ImageView parametres = new ImageView(new Image(new File("./img/parametres.png").toURI().toString()));
        parametres.setFitHeight(30);
        parametres.setFitWidth(30);
        ImageView info = new ImageView(new Image(new File("./img/info.png").toURI().toString()));
        info.setFitHeight(30);
        info.setFitWidth(30);
        boutonMaison.setGraphic(maison);
        boutonParametres.setGraphic(parametres);
        boutonInfo.setGraphic(info);
        hb.getChildren().addAll(boutonMaison, boutonParametres, boutonInfo);
        hb.setSpacing(5);
        res.setRight(hb);
        res.setPadding(new Insets(10, 10, 10, 10));
        res.setStyle("-fx-background-color: #E6E6FA;");
        return res;
    }

    /**
     * @return le panel du chronomètre
     */
    private TitledPane leChrono(){
        Label l = new Label("0s");
        TitledPane res = new TitledPane("Chronomètre", l);
        return res;
    }

    /**
     * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
     *         de progression et le clavier
     */
    private Pane fenetreJeu(){
        BorderPane borderPane = new BorderPane();


        VBox droite = new VBox();
        droite.setSpacing(30);
        droite.setPadding(new Insets(20, 20, 20, 20));//top, right, bottom, left
        droite.setAlignment(Pos.TOP_CENTER);
        Button nvMot = new Button("Nouveau mot");
        droite.getChildren().addAll(this.leNiveau, this.leChrono(), nvMot);
        borderPane.setRight(droite);

        VBox centre = new VBox();
        this.motCrypte = new Text(this.modelePendu.getMotCrypte());
        int numDifficulté = this.modelePendu.getNiveau();
        String nomDiff = this.modelePendu.intToString(numDifficulté);

        this.leNiveau = new Text("Niveau " + nomDiff);
        this.motCrypte.setStyle("-fx-font-size: 30;");
        this.leNiveau.setStyle("-fx-font-size: 20;");
        centre.getChildren().addAll(this.motCrypte, this.dessin, this.pg, this.clavier);
        centre.setAlignment(Pos.TOP_CENTER);
        centre.setSpacing(10);
        centre.setPadding(new Insets(10, 10, 10, 10));
        borderPane.setCenter(centre);
        return borderPane;
    }

    /**
     * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de jeu
     */
    private Pane fenetreAccueil(){
        VBox vb = new VBox();
        bJouer = new Button("Lancer une partie");
        bJouer.setOnAction(new ControleurLancerPartie(this.modelePendu, this));
        VBox radiosButton = new VBox();
        TitledPane tp = new TitledPane("Niveau de difficulté", radiosButton);
        tp.setCollapsible(false);
        ToggleGroup tg = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Facile");
        rb1.setOnAction(new ControleurNiveau(this.modelePendu));
        RadioButton rb2 = new RadioButton("Medium");
        rb2.setOnAction(new ControleurNiveau(this.modelePendu));
        RadioButton rb3 = new RadioButton("Difficile");
        rb3.setOnAction(new ControleurNiveau(this.modelePendu));
        RadioButton rb4 = new RadioButton("Expert");
        rb4.setOnAction(new ControleurNiveau(this.modelePendu));
        tg.getToggles().addAll(rb1, rb2, rb3, rb4);
        rb1.setSelected(true);
        radiosButton.getChildren().addAll(rb1, rb2, rb3, rb4);
        radiosButton.setSpacing(5);
        radiosButton.setPadding(new Insets(10, 10, 10, 10));
        vb.getChildren().addAll(bJouer, tp);
        vb.setSpacing(10);
        vb.setPadding(new Insets(20, 10, 10, 10));//top, right, bottom, left
        return vb;
    }

    /**
     * charge les images à afficher en fonction des erreurs
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImages(String repertoire){
        for (int i=0; i<this.modelePendu.getNbErreursMax()+1; i++){
            File file = new File(repertoire+"/pendu"+i+".png");
            System.out.println(file.toURI().toString());
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }

    public void modeAccueil(){
        this.panelCentral.setCenter(this.fenetreAccueil());
        boutonMaison.setDisable(true);
        boutonParametres.setDisable(false);
    }
    
    public void modeJeu(){
        this.panelCentral.setCenter(this.fenetreJeu());
        boutonMaison.setDisable(false);
        boutonParametres.setDisable(true);
    }
    
    public void modeParametres(){
        // A implémenter
    }

    /** lance une partie */
    public void lancePartie(){
        // A implementer
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(){
        this.motCrypte.setText(this.modelePendu.getMotCrypte());
        this.clavier.desactiveTouches(this.modelePendu.getLettresEssayees());
        this.dessin.setImage(this.lesImages.get(this.modelePendu.getNbErreursMax()-this.modelePendu.getNbErreursRestants()));
        this.pg.setProgress((double)this.modelePendu.getNbErreursMax()/this.modelePendu.getNbErreursRestants());
    }

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono(){
        // A implémenter
        return null; // A enlever
    }

    public Alert popUpPartieEnCours(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La partie est en cours!\n Etes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }
        
    public Alert popUpReglesDuJeu(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Règles du jeu");
        alert.setHeaderText("Le jeu du pendu");
        alert.setContentText("Le jeu du pendu consiste à trouver un mot en un nombre limité de tentatives.\n"
                + "Le mot à trouver est caché par des étoiles. Vous devez proposer des lettres pour le découvrir.\n"
                + "Si vous proposez une lettre qui n'est pas dans le mot, vous perdez une tentative.\n"
                + "Si vous proposez une lettre qui est dans le mot, elle est affichée à sa place.\n"
                + "Si vous proposez une lettre déjà proposée, vous ne perdez pas de tentative.\n"
                + "Si vous proposez un mot, vous ne perdez pas de tentative.\n"
                + "Si vous trouvez le mot, vous gagnez la partie.\n"
                + "Si vous n'avez plus de tentatives, vous perdez la partie.\n"
                + "                                                          \n"
                + "Bonne chance!");
        alert.getDialogPane().setMinHeight(javafx.scene.layout.Region.USE_PREF_SIZE);
        return alert;
    }
    
    public Alert popUpMessageGagne(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION);        
        return alert;
    }
    
    public Alert popUpMessagePerdu(){
        // A implementer    
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        return alert;
    }

    /**
     * créer le graphe de scène et lance le jeu
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        stage.setScene(this.laScene());
        this.modeAccueil();
        stage.show();
    }

    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
