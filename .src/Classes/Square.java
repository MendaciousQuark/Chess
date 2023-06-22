public class Square
{

  int posX;
  int posY;
  boolean colour;
  boolean occupied;
  Piece piece;

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
  }

}
