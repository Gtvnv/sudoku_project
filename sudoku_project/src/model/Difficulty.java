package model;

public enum Difficulty {

	EASY(40), // 40 números preenchidos
    MEDIUM(30),// 30 números preenchidos
    HARD(20); // 20 números preenchidos

    private final int filled;

    Difficulty(int filled) {
        this.filled = filled;
    }

    public int getPreenchidos() {
        return filled;
   }
	
}
