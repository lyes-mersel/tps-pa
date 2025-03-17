public class CaseMatrice {
    private int val;
    private int indGrand;
    private int indPetit;

    public CaseMatrice() {
    }

    public CaseMatrice(int val, int indGrand, int indPetit) {
        this.val = val;
        this.indGrand = indGrand;
        this.indPetit = indPetit;
    }

    public int getVal() {
        return val;
    }

    public int getIndGrand() {
        return indGrand;
    }

    public int getIndPetit() {
        return indPetit;
    }
}
