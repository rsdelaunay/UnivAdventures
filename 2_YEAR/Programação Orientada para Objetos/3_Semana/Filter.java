import java.util.ArrayList;
import java.util.List;

public class Filter {

    public static List<String> filterWords(List<String> list, CriterioDeSelecao<String> criterio) {
        ArrayList<String> filtered = new ArrayList<>();
        for (String word : list) {
            if (criterio.cumpreOCriterio(word)) {
                filtered.add(word);
            }
        }
        return filtered;
    }
}
