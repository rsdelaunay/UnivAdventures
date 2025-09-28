public interface CriterioDeSelecao<T> {
    boolean cumpreOCriterio(T elemento);
}