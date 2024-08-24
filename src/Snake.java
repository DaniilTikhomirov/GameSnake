import com.javarush.engine.cell.*;
import com.javarush.engine.cell.Color;

import java.util.ArrayList;
import java.util.List;

public class Snake extends GameObject {
    private List<GameObject> snakeParts;
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    public Snake(int x, int y) {
        super(x, y);
        snakeParts = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            snakeParts.add(new GameObject(x + i, y));
        }
    }

    public void draw(Game game){
        for (int i = 0; i < snakeParts.size(); i++) {
            GameObject snakePart = snakeParts.get(i);
            if (i == 0){
                game.setCellValueEx(snakePart.x, snakePart.y, Color.NONE, HEAD_SIGN, isAlive? Color.DARKGREEN:Color.RED, 75);
            }else{
                game.setCellValueEx(snakePart.x, snakePart.y, Color.NONE, BODY_SIGN, isAlive? Color.DARKGREEN:Color.RED, 75);
            }

        }

    }


    public void removeTail(){
        snakeParts.remove(snakeParts.size() - 1);
    }

    public GameObject createNewHead(){
        GameObject snakeHead = snakeParts.get(0);
        return new GameObject(snakeHead.x + getValueDirectionX(), snakeHead.y + getValueDirectionY());

    }

    private int getValueDirectionX(){
        if(direction == Direction.LEFT){
            return -1;
        }else if(direction == Direction.RIGHT){
            return 1;
        }else{
            return 0;
        }
    }

    private int getValueDirectionY(){
        if(direction == Direction.UP){
            return -1;
        }else if(direction == Direction.DOWN){
            return 1;
        }else{
            return 0;
        }
    }

    public void move(Apple apple) {
        GameObject newHead = createNewHead();
        if (checkCollision(newHead) || newHead.x >= SnakeGame.WIDTH || newHead.x < 0 || newHead.y >= SnakeGame.HEIGHT || newHead.y < 0) {
            isAlive = false;
            return;
        }
        snakeParts.add(0, newHead);
        if (apple.isAlive && apple.x == newHead.x && apple.y == newHead.y) {
            apple.isAlive = false;
        }else {
            removeTail();
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        GameObject pos1 = snakeParts.get(0);
        GameObject pos2 = snakeParts.get(1);
        if((direction == Direction.LEFT || direction == Direction.RIGHT) && pos1.y == pos2.y){
            return;
        }
        if ((direction == Direction.UP || direction == Direction.DOWN) && pos1.x == pos2.x){
            return;
        }

        if (direction == Direction.LEFT && this.direction == Direction.RIGHT) {
            return;
        }else if (direction == Direction.RIGHT && this.direction == Direction.LEFT){
            return;
        }else if (direction == Direction.UP && this.direction == Direction.DOWN){
            return;
        }else if (direction == Direction.DOWN && this.direction == Direction.UP){
            return;
        }
        this.direction = direction;
    }

    public boolean checkCollision(GameObject gameObject) {
        for (int i = 1; i < snakeParts.size(); i++) {
            GameObject snakePart = snakeParts.get(i);
            if(snakePart.x == gameObject.x && snakePart.y == gameObject.y){
                return true;
            }
        }
        return false;
    }

    public int getLength() {
        return snakeParts.size();
    }


    
}
