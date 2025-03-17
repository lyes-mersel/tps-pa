import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T, N;
        int[] tableau, max;

        System.out.println("Créer un tableau de taille (T) avec des nombres entiers aléatoires < (N) :");
        System.out.print("T ---> ");
        T = sc.nextInt();
        System.out.print("N ---> ");
        N = sc.nextInt();

        tableau = creerTableauRand(T, N);
        afficherTableau(tableau);

        System.out.println("Algorithme Naif");
        max = findMaxTab(tableau);
        if (max == null) {
            System.out.println("Le Tableau doit avoir au moins 2 cases !");
        } else {
            System.out.println("Max1 ---> " + max[0]);
            System.out.println("Max2 ---> " + max[1]);
        }

        System.out.println("Algorithme Tournoi");
        max = findMaxTournoi(tableau);
        if (max == null) {
            System.out.println("Le Tableau doit avoir au moins 2 cases !");
        } else {
            System.out.println("Max1 ---> " + max[0]);
            System.out.println("Max2 ---> " + max[1]);
        }

        sc.close();
    }

    /**
     * Retourne un tableau de taille (T) avec des valeurs aléatoires inférieures à (N)
     */
    static public int[] creerTableauRand(int T, int N) {
        Random random = new Random();
        int[] table = new int[T];
        for (int i = 0; i < T; i++) {
            table[i] = random.nextInt(N);
        }
        return table;
    }

    /**
     * Afficher les valeurs d'un tableau
     */
    static public void afficherTableau(int[] tab) {
        System.out.println("Affichage Tableau: ");
        if (tab.length == 0) {
            System.out.println("Le Tableau est Vide !");
        } else {
            for (int i : tab) {
                System.out.print(i + " | ");
            }
        }
        System.out.println();
    }

    static public int[] findMaxTab(int[] tab) {
        if (tab.length < 2) {
            return null;
        }
        int[] max = {tab[0], tab[0]}; //max[0]: 1er max     max[1]: 2eme max
        for (int i = 1; i < tab.length; i++) {
            if (tab[i] > max[0]) {
                max[1] = max[0];
                max[0] = tab[i];
            } else if (tab[i] > max[1]) {
                max[1] = tab[i];
            }
        }
        return max;
    }

    static public int[] findMaxTournoi(int[] tab) {
        int nbLignes = log(tab.length, 2);
        int nbCases, i, j;
        CaseMatrice[][] matrice = new CaseMatrice[nbLignes][];

        if (tab.length < 2) {
            return null;
        } else {
            // Tour 1 + remplir la ligne 0 de la matrice
            nbCases = (tab.length / 2) + (tab.length % 2);
            matrice[0] = new CaseMatrice[nbCases];

            for (j = 0; j < tab.length - 1; j += 2) {
                if (tab[j] < tab[j + 1]) {
                    matrice[0][j / 2] = new CaseMatrice(tab[j + 1], j + 1, j);
                } else {
                    matrice[0][j / 2] = new CaseMatrice(tab[j], j, j + 1);
                }
            }
            if (j < tab.length) { // tab.length impair
                matrice[0][j / 2] = new CaseMatrice(tab[j], j, -1);
            }
        }

        // Tour (2 à nbLignes) + Remplir les lignes matrices (1 à nbLignes-1)
        for (i = 1; i < nbLignes; i++) {
            nbCases = (matrice[i - 1].length / 2) + (matrice[i - 1].length % 2);
            matrice[i] = new CaseMatrice[nbCases];

            for (j = 0; j < matrice[i - 1].length - 1; j += 2) {
                if (matrice[i - 1][j].getVal() < matrice[i - 1][j + 1].getVal()) {
                    matrice[i][j / 2] = new CaseMatrice(matrice[i - 1][j + 1].getVal(), j + 1, j);
                } else {
                    matrice[i][j / 2] = new CaseMatrice(matrice[i - 1][j].getVal(), j, j + 1);
                }
            }
            if (j < matrice[i - 1].length) {// matrice[i-1].length impair
                matrice[i][j / 2] = new CaseMatrice(matrice[i - 1][j].getVal(), j, -1);
            }

        }

        // Parcourir la matrice afin de récupérer le 2eme max
        int[] max = new int[2];
        if (nbLignes == 1) {
            max[0] = matrice[0][0].getVal();
            max[1] = tab[matrice[0][0].getIndPetit()];
            return max;
        } else {
            i = nbLignes - 1;
            CaseMatrice parcourtMax1 = matrice[i][0];
            CaseMatrice parcourtMax2 = matrice[i - 1][parcourtMax1.getIndPetit()];
            i--;

            max[0] = parcourtMax1.getVal();
            max[1] = parcourtMax2.getVal();

            while (i > 0) {
                parcourtMax1 = matrice[i][parcourtMax1.getIndGrand()];
                if (parcourtMax1.getIndPetit() != -1) {
                    parcourtMax2 = matrice[i - 1][parcourtMax1.getIndPetit()];
                    if (parcourtMax2.getVal() > max[1]) {
                        max[1] = parcourtMax2.getVal();
                    }
                }
                i--;
            }
            // i = 0
            parcourtMax1 = matrice[i][parcourtMax1.getIndGrand()];
            if (parcourtMax1.getIndPetit() != -1) {
                if (tab[parcourtMax1.getIndPetit()] > max[1]) {
                    max[1] = tab[parcourtMax1.getIndPetit()];
                }
            }
        }

        return max;
    }

    static public int log(double n, double m) {
        double log = Math.log(n) / Math.log(m);
        int log2 = (int) log;
        if (log2 < log) {
            log2++;
        }
        return log2;
    }
}
