import java.util.ArrayList;

public abstract class Piece
{

  protected int posX, posY;
  protected boolean colour;
  protected int value;
  protected boolean chained;
  protected ArrayList<Move> moves = new ArrayList<>();

  Piece(int posX, int posY, boolean colour, int value)
  {
    this.posX = posX;
    this.posY = posY;
    this.colour = colour;
    this.value = value;
  }

  protected String getName()
  {
    return "Piece";
  }

  protected abstract void findMoves(Board board, int turn, boolean check);

  @Override
  public boolean equals(Object obj)
  {
    if(this == obj)
    {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    return sameAttributes((Piece) obj);
  }

  private boolean sameAttributes(Piece other)
  {
    return this.colour == other.colour &&
           this.value == other.value;
  }

  protected int[] setStart(int i, int j) {
    // Create a new integer array representing the start position
    int[] start = new int[2];
    // Set the row and column values for the start position
    start[0] = i;
    start[1] = j;
    // Return the start position array
    return start;
  }

  protected int[] setEnd(int i, int j) {
    // Create a new integer array representing the end position
    int[] end = new int[2];
    // Set the row and column values for the end position
    end[0] = i;
    end[1] = j;
    // Return the end position array
    return end;
  }
  
}
