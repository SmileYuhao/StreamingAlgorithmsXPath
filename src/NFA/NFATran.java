package NFA;

public class NFATran {
    private int nextStateNumber;
    private String transSymbol;

    /**
     * @param nextStateNumber Transaction's destination (state's number)
     * @param transSymbol     Transfer conditions
     */
    public NFATran(int nextStateNumber, String transSymbol) {
        this.nextStateNumber = nextStateNumber;
        this.transSymbol = transSymbol;
    }

    public int getNextStateNumber() {
        return nextStateNumber;
    }

    public String getTransSymbol() {
        return transSymbol;
    }
}