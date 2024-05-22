import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Executable {
    public static void main(String[] args) {
        MotMystere mot = new  MotMystere("bonjour", 1, 8);
        System.out.println("Bienvenue dans le jeu du pendu");
        while (!mot.gagne()) {
            System.out.println("Entrez un caractère : ");
            System.out.println("Mot à trouver : " + mot.getMotCrypte());
            Scanner sc = new Scanner(System.in);
            char c = sc.next().charAt(0);
            System.out.println("Vous avez entré : " + c);
            mot.essaiLettre(c);
            System.out.println();
        }
        System.out.println("Bravo, vous avez gagné !");
    }
}