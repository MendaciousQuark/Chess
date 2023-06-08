import java.util.Arrays;

public class Board
{

  private Square[][] board = new Square[8][8];

  Board()
  {
    init();
  }
  Board(Square[][] board)
  {
    this.board = board;
  }


  @Override
  public String toString()
  {
    return "FEN";
  }

  private  void init()
  {
    //initialise board
  }

  public void Display(Board board){}

  public double evaluate()
  {
    return 0.0;
  }

  public Square[][] getBoard()
  {
    return board;
  }

  public void setBoard(Square[][] board)
  {
    this.board = board;
  }

}
