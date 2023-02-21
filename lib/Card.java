package lib;

public class Card {
    private Suits suit;
    private Ranks rank;

    public Card(Suits suit, Ranks rank) {
        this.suit = suit;
        this.rank = rank;
    }
    public Suits getSuit() {
        return this.suit;
    }
    public Ranks getRank() {
        return this.rank;
    }
}