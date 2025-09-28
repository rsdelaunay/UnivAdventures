import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public enum CardSortingStrategy implements Comparator<Card> {

    BY_SUIT {
        public int compare(Card a, Card b) {
            return a.getSuit().ordinal() - b.getSuit().ordinal();
        }
    },
    BY_RANK {
        public int compare(Card a, Card b) {
            return a.getRank().ordinal() - b.getRank().ordinal();
        }
    },
    BY_SUIT_AND_THEN_RANK {
        public int compare(Card a, Card b) {
            if(a.getSuit().ordinal() == b.getSuit().ordinal())
                return a.getRank().ordinal() - b.getRank().ordinal();
            return a.getSuit().ordinal() - b.getSuit().ordinal();
        }
    };

    public static void main(String[] args) {

        List<Card> cards = new ArrayList<>();
        Card c1 = Card.getCard(Suit.ESPADAS,Rank.MANILHA);
        Card c2 = Card.getCard(Suit.OUROS,Rank.AS);


        cards.add(c1);
        cards.add(c2);

        cards.sort(CardSortingStrategy.BY_SUIT_AND_THEN_RANK);

        for(Card c : cards){
            System.out.println(c);
        }
    }
}
