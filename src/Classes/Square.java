public class Square
{


  public int posI;
  public int posJ;
  public boolean colour;
  public boolean occupied;
  public Piece piece;

  Square(int posI, int posJ, boolean colour, Piece piece)
  {
    this.posI = posI;
    this.posJ = posJ;
    this.colour = colour;
    this.occupied = true;
    this.piece = piece;

  }

  Square(int posI, int posJ, boolean colour)
  {
    this.posI = posI;
    this.posJ = posJ;
    this.colour = colour;
    this.occupied = false;

    this.piece = null;
  }

  public boolean isOccupiedByOpponent(boolean colour)
  {
    return this.occupied && (piece.colour != colour);
  }

  public boolean isAdjacent(int otherI, int otherJ)
  {
    int rowDiff = Math.abs(posI - otherI);
    int colDiff = Math.abs(posJ - otherJ);

    // Check if the squares are adjacent horizontally, vertically, or diagonally
    return (rowDiff <= 1 && colDiff <= 1) && (rowDiff != 0 || colDiff != 0);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Square{\n");
    sb.append("  posX=").append(posI).append(",\n");
    sb.append("  posY=").append(posJ).append(",\n");
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
             this.posJ == other.posJ &&
             this.posI == other.posI &&
             this.piece.equals(other.piece);
    }
    else
    {
      return this.occupied == other.occupied &&
             this.colour == other.colour &&
             this.posJ == other.posJ &&
             this.posI == other.posI &&
             this.piece == other.piece;
    }
  }
}
