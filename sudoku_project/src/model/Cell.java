package model;

public class Cell { 
    private int value;
    private boolean isFixed;

    public Cell(int value, boolean isFixed) {
        this.value = value;
        this.isFixed = isFixed;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        // Allows setting the value only if the cell is not fixed
        if (!this.isFixed) {
            this.value = value;
        }
        // Optional: Throw an exception or log if attempting to change a fixed cell
    }

    public boolean isFixed() {
        return isFixed;
    }
    
    public void setFixed(boolean fixed) {
        this.isFixed = fixed;
    }
}
