
public class WordsLessThanThree implements CriterioDeSelecao<String> {

    private int tamanhoMaximo;

    public WordsLessThanThree(int tamanhoMaximo) {
        this.tamanhoMaximo = tamanhoMaximo;
    }

    @Override
    public boolean cumpreOCriterio(String elemento) {
        return elemento.length() < tamanhoMaximo;
    }
}
