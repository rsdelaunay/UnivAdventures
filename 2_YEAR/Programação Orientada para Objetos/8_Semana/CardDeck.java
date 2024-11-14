import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.Comparator;

public class CardDeck implements Iterable<Card> {

    private final List<Card> cards; //lista cartas baralho ou na mão do jogador

    public CardDeck() {
        this.cards = new ArrayList<>(); //criar um molho vazio
    }

    public int getTotalCards() {
        return cards.size(); //saber o total dos molhos
    }

    public void addCard(Card card) {
        if (card != null) {
            cards.add(card); //adicionar uma carta
        }
    }

    public boolean removeCard(Card card) {
        return cards.remove(card); //remover uma carta
    }

    public static CardDeck fullDeck(Predicate<Card> filter) { //criar novo molho com filtro
        CardDeck deck = new CardDeck();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                Card card = Card.getCard(suit, rank);
                if (filter == null || filter.test(card)) {
                    deck.addCard(card);
                }
            }
        }
        return deck;
    }

    public void shuffle() { //baralhar
        Collections.shuffle(cards);
    }

    public void sort(Comparator<Card> comparator) { //comparar
        cards.sort(comparator);
    }

    @Override
    public Iterator<Card> iterator() {
        return cards.iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Cartas no molho:\n");
        for (Card card : cards) {
            sb.append(card).append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {

        CardDeck espadas = fullDeck(card -> card.getRank().equals(Rank.CINCO));

        for(Card c : espadas)
            System.out.println(c);

       // cards.sort(CardSortingStrategy.BY_SUIT_AND_THEN_RANK);

    }
}
