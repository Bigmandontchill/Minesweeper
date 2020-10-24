import java.util.ArrayList;
import java.util.List;

public class BombSquare extends GameSquare {
    private GameBoard board;// Object reference to the GameBoard this square is part of.
    private boolean hasBomb;// return true if square is a bomb
    private boolean reveal = false;//make sure user can't change the ImageIcon of the square
    private boolean show = false;// return true if square is clicked
    private boolean flag = false;// monitor whether user can remove a flag from the square  .
    public static final int MINE_PROBABILITY = 10;

    public BombSquare(int x, int y, GameBoard board) {
        super(x, y, "images/blank.png");
        this.board = board;
        this.hasBomb = ( (int)(Math.random() * MINE_PROBABILITY))==1;
    }

    /**
     * when a square is clicked with the left mouse button,display an bomb or squares.
     */
    @Override
    public void leftClicked() {
        if (hasBomb) {
            setImage("images/bomb.png");
        }
        else {
            Fill(this);
        }
        reveal = true;
    }

    /**
     * When a square is clicked with the right mouse button, display an image of a flag if the
     * square has not been revealed. A second click should remove a flag from a square.
     */
    @Override
    public void rightClicked() {
        if (!flag && !reveal) {
            setImage("images/flag.png");
            flag = true;
        } else if (!reveal) {
            setImage("images/blank.png");
            flag = false;
        }
    }

    /**
     *return true if square is a bomb
     */
    public boolean isHasBomb() {
        return hasBomb;
    }

    /**
     * Display square according to the count
     * @param count number on the square
     */
    private void display(int count,BombSquare square) {
        String Count = String.valueOf(count);
        Count = "images/" + Count + ".png";
        square.setImage(Count);
        square.show = true;
    }

    /**
     * return number of surrounding bombs
     */
    public int GetBombs() {
        int count = 0;
        int i = getXLocation();
        int j = getYLocation();
        for (int a = i - 1; a <= i + 1; a++) {
            for (int b = j - 1; b <= j + 1; b++) {
                if (board.getSquareAt(a, b) == null || board.getSquareAt(a, b) == this) {
                    System.out.println("Ignore");
                } else {
                    BombSquare square = (BombSquare) board.getSquareAt(a, b);
                    if (square.isHasBomb()) count++;
                }
            }
        }
        return count;
    }

    /**
     * when a square is clicked that has zero bombs in its surrounding squares,
     * it not only displays that square, but also any adjacent squares
     * Note that if one of those squares also has zero
     * surrounding bombs, it should display all of its adjacent squares.
     */
    private void Fill(BombSquare square) {
        if (square == null||square.isHasBomb()||square.show) return;
        else if (square.GetBombs() > 0) {
           display(square.GetBombs(), square);
        }
        else {
            display(square.GetBombs(), square);
            Fill((BombSquare) board.getSquareAt(square.getXLocation() + 1, square.getYLocation()));
            Fill((BombSquare) board.getSquareAt(square.getXLocation() - 1, square.getYLocation()));
            Fill((BombSquare) board.getSquareAt(square.getXLocation(), square.getYLocation() - 1));
            Fill((BombSquare) board.getSquareAt(square.getXLocation(), square.getYLocation() + 1));
        }
    }


}


