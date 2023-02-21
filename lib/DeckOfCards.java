package lib;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class DeckOfCards {
    private List<Card> cards = new ArrayList<>();

    public DeckOfCards() {
        this.cards = new ArrayList<>();
        for (Suits suit: Suits.values()) {
            for (Ranks rank: Ranks.values()) {
                Card c = new Card(suit, rank);
                this.cards.add(c);
            }
        }
    }
    public void shuffle() {
        Collections.shuffle(this.cards); 
    }
    public Card deal() {
        Card c = this.cards.get(0);
        this.cards.remove(0);
        return c;
    }
    public int getDeckSize() {
        return this.cards.size();
    }
}