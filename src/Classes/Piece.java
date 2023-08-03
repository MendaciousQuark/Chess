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
  
}
