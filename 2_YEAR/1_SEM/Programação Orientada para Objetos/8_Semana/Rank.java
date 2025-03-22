public enum Rank {
    AS(11,true),
    MANILHA(10,true),
    REI(4,true),
    VALETE(3,true),
    DAMA(2,true),
    DEZ(0,false),
    NOVE(0,false),
    OITO(0,false),
    SEIS(0,true),
    CINCO(0,true),
    QUATRO(0,true),
    TRES(0,true),
    DOIS(0,true);

    private final int pontos;
    private final boolean isInBaralho;

    Rank(int pontos,boolean isInBaralho){
        this.pontos = pontos;
        this.isInBaralho = isInBaralho;
    }

    public boolean isInBaralho() {
        return isInBaralho;
    }

    public int getPontos() {
        return pontos;
    }
}
