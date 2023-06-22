public class Square
{

  public int posX;
  public int posY;
  public boolean colour;
  public boolean occupied;
  public Piece piece;

  Square(int posX, int posY, boolean colour, Piece piece)
  {
    this.posX = posX;
    this.posY = posY;
    this.colour = colour;
    this.occupied = true;
    this.piece = piece;
  }

  Square(int posX, int posY, boolean colour)
  {
    this.posX = posX;
    this.posY = posY;
    this.colour = colour;
    this.occupied = false;
    this.piece = null;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    return sameAttributes((Square) obj);
  }

  private boolean sameAttributes(Square other)
  {
    if(this.piece != null && other.piece != null)
    {
      return this.occupied == other.occupied &&
             this.colour == other.colour &&
             this.posY == other.posY &&
             this.posX == other.posX &&
             this.piece.equals(other.piece);
    }
    else
    {
      return this.occupied == other.occupied &&
             this.colour == other.colour &&
             this.posY == other.posY &&
             this.posX == other.posX &&
             this.piece == other.piece;
    }
  }
}
