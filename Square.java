public class Square
{

  int posX;
  int posY;
  boolean colour;
  boolean occupied;

  Square(int posX, int posY, boolean colour, Piece piece)
  {
    this.posX = posX;
    this.posY = posY;
    this.colour = colour;
    this.occupied = true;
  }

  Square(int posX, int posY, boolean colour)
  {
    this.posX = posX;
    this.posY = posY;
    this.colour = colour;
    this.occupied = false;
  }

}
