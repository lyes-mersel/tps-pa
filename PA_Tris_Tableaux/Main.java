import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T, N;
        int[] tableau, max;

        int[] tableauTest8 = {57, 19, 33, 12, 7, 89, 56, 24};
        int[] tableauTest10 = {45, 28, 12, 77, 34, 99, 3, 67, 18, 56};
        int[] tableauTest100 = {1000, 564, 4789, 232, 1, 98, 4738, 2000, 782, 431, 764, 12, 0, 987, 2900, 365, 256, 34, 89, 111, 229, 4857, 749, 3920, 3821, 1034, 1548, 38, 59, 77, 2450, 876, 321, 1234, 56, 199, 4005, 3003, 4321, 219, 8765, 3223, 754, 3012, 88, 1011, 765, 1098, 422, 2331, 890, 672, 5432, 2384, 992, 402, 23, 74, 9382, 1567, 364, 5073, 919, 3789, 214, 5678, 100, 234, 671, 1289, 2645, 899, 1723, 354, 101, 9098, 524, 478, 13, 2350, 556, 762, 91, 257, 508, 3981, 1879, 7889, 3456, 2850, 6134, 4012, 5567, 9823, 382, 7632, 2921, 4871, 2938, 3030, 469, 1547, 1425, 5513, 8736, 292, 1090, 674, 2200, 659, 3173, 2851, 988, 2589, 4297, 832, 2173, 6091, 2764, 689, 4212, 3409, 516, 887, 1287, 6520, 3023, 2221};

        System.out.println("Creer un tableau de taille (T) avec des nombres entiers aléatoires < (N) :");
        System.out.print("T ---> ");
        T = sc.nextInt();
        System.out.print("N ---> ");
        N = sc.nextInt();

        tableau = creerTableauRand(T, N);
        afficherTableau(tableau);

        // triInsertion(tableau);
        // triFusion(tableau, 0, tableau.length - 1);
        // triRapide(tableau, 0, tableau.length - 1);
        triTas(tableau);

        afficherTableau(tableau);
        sc.close();
    }

    /**
     * Retourne un tableau de taille (T) avec des valeurs aléatoires inférieures à (N)
     */
    public static int[] creerTableauRand(int T, int N) {
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
    public static void afficherTableau(int[] tab) {
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

    /**
     * Afficher un sous-tableau
     * inf: indice début
     * sup: indice sup
     */
    public static void afficherSousTableau(int[] tab, int inf, int sup) {
        if (inf > sup) {
            System.out.println("Le Tableau est Vide !");
        } else {
            for (int i = inf; i <= sup; i++) {
                System.out.print(tab[i] + " | ");
            }
        }
        System.out.println();
    }

    public static void triInsertion(int[] tab) {
        int i, j, temp;
        for (i = 1; i < tab.length; i++) {
            j = i;
            temp = tab[i];
            while (j > 0 && tab[j - 1] > temp) {
                tab[j] = tab[j - 1];
                j--;
            }
            tab[j] = temp;
        }
    }

    public static void triFusion(int[] tab, int inf, int sup) {
        if (inf < sup) {
            int mil = (inf + sup) / 2;
            triFusion(tab, inf, mil);
            triFusion(tab, mil + 1, sup);
            fusionner(tab, inf, mil, sup);
        }
    }

    public static void fusionner(int[] tab, int inf, int mil, int sup) {
        int[] tabLeft = copierSousTab(tab, inf, mil);
        int[] tabRight = copierSousTab(tab, mil + 1, sup);
        int i = 0, j = 0;

        for (int k = inf; k <= sup; k++) {
            if (i == tabLeft.length) {
                tab[k] = tabRight[j];
                j++;
            } else if (j == tabRight.length) {
                tab[k] = tabLeft[i];
                i++;
            } else if (tabLeft[i] < tabRight[j]) {
                tab[k] = tabLeft[i];
                i++;
            } else { // tabLeft[i] >= tabRight[j]
                tab[k] = tabRight[j];
                j++;
            }
        }
    }

    /**
     * Copier les valeurs d'un sous-tableau vers un nouveau tableau
     */
    public static int[] copierSousTab(int[] tab, int inf, int sup) {
        int lenght = sup - inf + 1;
        int[] newTab = new int[lenght];
        System.arraycopy(tab, inf, newTab, 0, lenght);
        return newTab;
    }

    public static void triRapide(int[] tab, int inf, int sup) {
        if (inf < sup) {
            int position = partition(tab, inf, sup);
            triRapide(tab, inf, position - 1);
            triRapide(tab, position + 1, sup);
        }
    }


    public static int partition(int[] tab, int inf, int sup) {
        int pivot = tab[inf]; // Choisir le premier élément comme pivot
        int i = inf + 1;
        int j = sup;

        while (i <= j) {
            // Trouver le premier élément à gauche du pivot qui est plus grand que le pivot
            while (i <= j && tab[i] <= pivot) {
                i++;
            }
            // Trouver le premier élément à droite du pivot qui est plus petit que le pivot
            while (i <= j && tab[j] > pivot) {
                j--;
            }
            // Échanger tab[i] et tab[j]
            if (i < j) {
                permuter(tab, i, j);
            }
        }

        // Placer le pivot à sa position supale
        permuter(tab, inf, j);

        return j;  // Retourner l'indice du pivot
    }

    public static int partition2(int[] tab, int inf, int sup) {
        int pivot = tab[inf];  // Choose the first element as the pivot
        int i = inf + 1;
        int j = sup;

        while (i <= j) {
            // Find the first element greater than the pivot from the left
            if (tab[i] <= pivot) {
                i++;
            }
            // Find the first element smaller than the pivot from the right
            else if (tab[j] > pivot) {
                j--;
            }
            // Swap elements at indices i and j
            else {
                permuter(tab, i, j);
                i++;
                j--;
            }
        }

        // Place the pivot in the correct position by swapping it with tab[j]
        permuter(tab, inf, j);

        return j;  // Return the index of the pivot
    }

    public static void triTas(int[] tab) {
        for (int i = tab.length / 2 - 1; i >= 0; i--) {
            tas(tab, tab.length, i);
        }
        for (int i = tab.length - 1; i >= 0; i--) {
            permuter(tab, 0, i);
            tas(tab, i, 0);
        }
    }

    public static void tas(int[] tab, int taille, int idx) {
        int maxIdx = idx;
        int g = 2 * idx + 1;
        int d = 2 * idx + 2;

        if (g < taille && tab[g] > tab[maxIdx]) {
            maxIdx = g;
        }
        if (d < taille && tab[d] > tab[maxIdx]) {
            maxIdx = d;
        }
        if (maxIdx != idx) {
            permuter(tab, maxIdx, idx);
            tas(tab, taille, maxIdx);
        }

    }

    /**
     * permuter 2 elements du tableau dont les indices sont i & j
     */
    public static void permuter(int[] tab, int i, int j) {
        int temp = tab[i];
        tab[i] = tab[j];
        tab[j] = temp;
    }
}
