import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class GameModeTimed extends GameModeClassic {
    private int turnTimeLimit; // The time limit for each turn
    private int currentTime;
    private Timer timer;
    private JLabel timerLabel;
    int tTurn = 0;

    public GameModeTimed(JFrame gameWindow, JLabel timerLabel, int turnTimeLimit) {
        super(gameWindow);
        this.timerLabel = timerLabel;
        this.turnTimeLimit = turnTimeLimit; // Set custom time limit
        this.currentTime = turnTimeLimit;
        this.timer = new Timer(true);
    }

    @Override
    public void handleSquareClick(int x, int y) {
        super.handleSquareClick(x, y);
        if (selectedX == -1 && selectedY == -1 && tTurn < turn) {
            resetTimer();
            tTurn++;
        }
    }

    @Override
    public void checkForSpecialConditions() {
        super.checkForSpecialConditions();
        resetTimer();
    }

    public void startTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    currentTime--;
                    updateTimerLabel();
                    if (currentTime <= 0) {
                        timer.cancel();
                        displayEndGameScreen(whiteTurn ? "Black" : "White");
                    }
                });
            }
        }, 1000, 1000);
    }

    private void resetTimer() {
        currentTime = turnTimeLimit; // Use the custom time limit
        updateTimerLabel();
    }

    private void updateTimerLabel() {
        timerLabel.setText((whiteTurn ? "White's" : "Black's") + " Turn: " + currentTime + "s remaining");
    }

    @Override
    public void displayEndGameScreen(String winner) {
        timer.cancel();
        super.displayEndGameScreen(winner);
    }
}
