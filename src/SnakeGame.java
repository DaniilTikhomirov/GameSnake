import com.javarush.engine.cell.*;

public class SnakeGame extends Game{
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private static final int GOAL = 28;
    private int score;
    private int turnDelay;
    private Snake snake;
    private Apple apple;
    private boolean isGameStopped;

    @Override
    public void initialize(){
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    @Override
    public void onTurn(int fps){
        snake.move(apple);
        if (!apple.isAlive){
            createNewApple();
            score += 5;
            turnDelay -= 10;
            setScore(score);
            setTurnTimer(turnDelay);

        }
        if (!snake.isAlive){
            gameOver();
        }
        if(snake.getLength() > GOAL){
            win();
        }
        drawScene();

    }

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.SPACE && isGameStopped){
            createGame();
        }
        setDirectionForKeyPress(key);

    }

    private void createGame(){
        turnDelay = 300;
        isGameStopped = false;
        score = 0;
        setScore(score);
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        snake.setDirection(Direction.LEFT);
        createNewApple();

        drawScene();
        setTurnTimer(turnDelay);
    }

    private void setDirectionForKeyPress(Key key){
        if (key == Key.LEFT && snake.getDirection() != Direction.RIGHT){
            snake.setDirection(Direction.LEFT);
        }else if (key == Key.RIGHT && snake.getDirection() != Direction.LEFT){
            snake.setDirection(Direction.RIGHT);
        }else if (key == Key.UP && snake.getDirection() != Direction.DOWN){
            snake.setDirection(Direction.UP);
        }else if (key == Key.DOWN && snake.getDirection() != Direction.UP){
            snake.setDirection(Direction.DOWN);
        }
    }

    private void drawScene(){
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                setCellValueEx(x, y, Color.LIGHTGREEN, "");
            }

        }
        snake.draw(this);
        apple.draw(this);
    }

    private void createNewApple(){
        Apple newApple;
        do {
            int x = getRandomNumber(WIDTH);
            int y = getRandomNumber(HEIGHT);
            newApple = new Apple(x, y);
        } while (snake.checkCollision(newApple));
        this.apple = newApple;
    }

    private void gameOver(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "GAME OVER", Color.BLACK, 50);
    }

    private void win(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "YOU WIN", Color.BLACK, 50);
    }
}
