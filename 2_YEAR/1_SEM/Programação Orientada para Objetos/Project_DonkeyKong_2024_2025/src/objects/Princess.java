package objects;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Princess extends Personagem implements Interactable {


    public Princess(Point2D position) {
        super("Princess", position, 1, 0, 0, 1, true, false);
    }

    @Override
    public void interactsWithHero() {}

    @Override
    public void interaction() {
        ImageGUI.getInstance().showMessage("WIN!!!!!!!","Congratulations!");
        int totalSeconds = GameEngine.getInstance().getLastTickProcessed()/2;
        Score finalScore = new Score(ImageGUI.getInstance().askUser("Insert name: "), new Time(totalSeconds));
        Scoreboard.getInstance().addScore(finalScore);
        Scoreboard.getInstance().updateScores();
        ImageGUI.getInstance().showMessage("High Scores",Scoreboard.getInstance().top10());
        System.exit(0);
    }

}
