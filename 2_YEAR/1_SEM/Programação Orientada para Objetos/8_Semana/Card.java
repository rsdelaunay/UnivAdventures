public final class Card {

    private static final Card[] cards; //guardar as cartas
    private final Suit suit;
    private final Rank rank;

    static { //matriz cards com cartas possíveis
        cards = new Card[Suit.values().length * Rank.values().length]; //para criar matriz cards com todas as cartas possíveis
        int index = 0;
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards[index++] = new Card(suit, rank);
            }
        }
    }

    // Construtor privado para impedir criação fora da classe
    private Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    // Metodo de fábrica para obter uma carta específica (padrão Flyweight)
    public static Card getCard(Suit suit, Rank rank) {
        // Calcula o índice com base no naipe e valor
        int index = suit.ordinal() * Rank.values().length + rank.ordinal();
        return cards[index];
    }

    // Métodos de acesso para obter o naipe e valor da carta
    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    // Metodo toString para exibir a carta
    @Override
    public String toString() {
        return rank + " de " + suit;
    }
}

