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

  public boolean isOccupiedByOpponent(boolean colour)
  {
    return this.occupied && this.colour;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Square{\n");
    sb.append("  posX=").append(posX).append(",\n");
    sb.append("  posY=").append(posY).append(",\n");
    sb.append("  colour=").append(colour ? "White" : "Black").append(",\n");
    sb.append("  occupied=").append(occupied).append(",\n");
    sb.append("  piece=").append(piece).append("\n");
    sb.append("}");
    return sb.toString();
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
