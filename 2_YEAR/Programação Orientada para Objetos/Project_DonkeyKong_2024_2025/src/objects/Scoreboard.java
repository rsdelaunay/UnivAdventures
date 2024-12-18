package objects;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Scoreboard {

    private static Scoreboard INSTANCE;
    private List<Score> allTimeScores = new ArrayList<>();

    public static Scoreboard getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Scoreboard();
        return INSTANCE;
    }

    public List<Score> getScores() {
        return allTimeScores;
    }

    public void add(Score score){
        allTimeScores.add(score);
    }

    public void addScore(Score score) {

        File file = new File("Scores.txt");
        try {
            FileWriter fileWriter = new FileWriter(file,true);
            fileWriter.write(score.getNome() + " - " + score.getTime()+"\n");
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateScores(){

        File scores = new File("Scores.txt");
        try {
            Scanner sc = new Scanner(scores);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] tokens = line.split("-");
                String nome = tokens[0].trim();
                String timeString = tokens[1].trim();

                Time time = new Time(timeString);

                add(new Score(nome,time));
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar resultados " + e.getMessage());
        }

    }

    public String top10(){
        int count = 10;
        String highScores = "";
        allTimeScores.sort(Comparator.comparingInt(s -> s.getTime().getTotalSeconds()));
        for (Score score : allTimeScores){
            if(count > 0){
                highScores += score.toString() + "\n";
                count--;
            }else break;

        }
        return highScores;
    }


}
