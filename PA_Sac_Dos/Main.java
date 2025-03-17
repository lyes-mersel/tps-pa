import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // contrainte : poids max
        final int poidsMax = 8;

        // liste des objets : [poids, valeur]
        int[][] objets = {{3, 2}, {1, 2}, {3, 4}, {4, 5}, {2, 3}};
        int[][] test = {{2, 3}, {3, 4}, {4, 5}, {5, 6}};

        // remplir la matrice de la résolution du problème
        int[][] matrice = remplirMatrice(test, poidsMax);

        // List : index des elements de la solution optimale
        List<Integer> idxSolOpt = indexSolutionOptimale(test, matrice, poidsMax);

        // affichage
        afficherMatrice(matrice);
        afficherSolutionOptimale(test, idxSolOpt);
    }

    public static void afficherMatrice(int[][] mat) {
        System.out.println("Affichage Matrice: ");
        if (mat.length == 0) {
            System.out.println("Le Tableau est Vide !");
        } else {
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat[i].length; j++) {
                    System.out.print(mat[i][j] + " | ");
                }
                System.out.println();
            }
        }
        System.out.println();
    }

    public static void afficherSolutionOptimale(int[][] objets, List<Integer> idxSol) {
        System.out.println("Affichage Solution Optimale: ");
        for (int i = 0; i < idxSol.size(); i++) {
            int idx = idxSol.get(i);
            System.out.println((i + 1) + " : poids --> " + objets[idx - 1][0] + ", valeurs --> " + objets[idx - 1][1]);
        }
    }

    public static int[][] remplirMatrice(int[][] objets, int pmax) {
        int[][] matrice = new int[objets.length + 1][pmax + 1];

        // initialiser les cases de la matrice à 0
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                matrice[i][j] = 0;
            }
        }

        // remplissage des cases
        for (int i = 1; i < matrice.length; i++) {
            for (int j = 1; j < matrice[i].length; j++) {
                if (j < objets[i - 1][0]) {
                    matrice[i][j] = matrice[i - 1][j];
                } else {
                    int temp = objets[i - 1][1] + matrice[i - 1][j - objets[i - 1][0]];
                    matrice[i][j] = Math.max(temp, matrice[i - 1][j]);
                }
            }
        }

        return matrice;
    }

    /**
     * Retourne les index des elements de la solution optimale sous forme d'une liste
     */
    public static List<Integer> indexSolutionOptimale(int[][] objets, int[][] matrice, int pmax) {
        List<Integer> solution = new ArrayList<>();

        int k = objets.length;
        int p = pmax;

        while (k > 0) {
            if (matrice[k][p] != matrice[k - 1][p]) {
                solution.add(k);
                p -= objets[k - 1][0];
            }
            k--;
        }

        return solution;
    }
}