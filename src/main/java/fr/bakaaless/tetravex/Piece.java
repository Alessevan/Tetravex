package fr.bakaaless.tetravex;

// Represent a piece of the board
public class Piece {

    // Value of all piece's sides
    private final int value;
    // Check if the piece is used by the solver or not yet
    private boolean placed;

    public Piece(final int value) {
        this.value = value;
        this.placed = false;
    }

    public int getValue() {
        return this.value;
    }

    public int getLeft() {
        return this.value >> 12;
    }

    public int getTop() {
        return (this.value >> 8) & 0b1111;
    }

    public int getRight() {
        return (this.value >> 4) & 0b1111;
    }

    public int getBottom() {
        return this.value & 0b1111;
    }

    public boolean isPlaced() {
        return this.placed;
    }

    public void setPlaced(final boolean placed) {
        this.placed = placed;
    }
}
