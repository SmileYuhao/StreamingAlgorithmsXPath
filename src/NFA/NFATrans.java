package NFA;

public class NFATrans {
    private int stateNumber;
    private String transSymbol;

    /**
     * @param stateNumber Transaction's destination (state's number)
     * @param transSymbol Transfer conditions
     */
    public NFATrans(int stateNumber, String transSymbol) {
        this.stateNumber = stateNumber;
        this.transSymbol = transSymbol;
    }

    public int getStateNumber() {
        return stateNumber;
    }

    public String getTransSymbol() {
        return transSymbol;
    }
}