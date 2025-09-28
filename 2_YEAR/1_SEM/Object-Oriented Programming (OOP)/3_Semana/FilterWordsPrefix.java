public class FilterWordsPrefix implements CriterioDeSelecao<String>{

    private String prefix;

    public FilterWordsPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public boolean cumpreOCriterio(String elemento) {
        return elemento.startsWith(prefix);
    }
}
