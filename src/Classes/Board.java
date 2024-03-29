import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Board
{

  private Square[][] board = new Square[8][8];
  private ArrayList<Move> whiteMoves = new ArrayList<>();
  private ArrayList<Move> blackMoves = new ArrayList<>();
  private int turn;

  public  Board()
  {
    init();
  }

  public Board(String fen)
  {
    init();
    setUp(fen);
  }

  @Override
  public String toString()
  {
    StringBuilder fen = new StringBuilder();
    // castle rights as follows white King, white Queen, black King, black Queen
    boolean [] castleRights = {false, false, false, false};
    char [] castleSymbols = {'K', 'Q', 'k', 'q'};

    int emptySquares = 0;
    for(int i = 0; i < 8; i++)
    {
      for(int j = 0; j < 8; j++)
      {
        Square targetSquare = getSquare(i, j);
        if(targetSquare.occupied)
        {
          if(emptySquares > 0)
          {
            fen.append(emptySquares);
            emptySquares = 0;
          }
          fen.append(targetSquare.piece.getName());
          if(targetSquare.piece instanceof King king)
          {
            if (king.colour)
            {
              castleRights[0] = king.hasKingSideCastle();
              castleRights[1] = king.hasQueenSideCastle();
            }
            else
            {
              castleRights[2] = king.hasKingSideCastle();
              castleRights[3] = king.hasQueenSideCastle();
            }
          }
        }
        else
        {
          emptySquares++;
        }
      }
      if(emptySquares > 0)
      {
        fen.append(emptySquares);
        emptySquares = 0;
      }
      fen.append("/");
    }
    // get rid of last '/'
    fen.deleteCharAt(fen.length() - 1);

    fen.append(" ");
    //add castling rights to fen
    boolean atLestOneRight = false;
    for(int i = 0; i < 4; i++)
    {
      fen.append(castleRights[i] ? castleSymbols[i] : "");
      atLestOneRight = atLestOneRight || castleRights[i];
    }
    if(!atLestOneRight)
    {
      fen.append("-");
    }
    return fen.toString();
  }

  //sets up initial board position of any game
  private void init()
  {
    boolean colour;
    //for each row on the board
    for(int i = 0; i < 8; i++)
    {
      //determine the colour of the starting square
      colour = i % 2 == 0;
      //for each column
      for(int j = 0; j < 8; j++)
      {
        //place a piece depending on the location of the board we have reached (can be null)
        Piece piece = placePiece(i, j);
        //if we have place a piece
        if(piece != null)
        {
          //if the placed piece is a King
          if(piece instanceof King king)
          {
            //set the castling rights of that king
            king.setCanKingSideCastle(true);
            king.setCanQueenSideCastle(true);
          }
          //initialise the square we are currently working on with a piece
          this.board[i][j] = new Square(i, j, colour, piece);
        }
        else
        {
          //initialise the square we are currently working on without a piece
          this.board[i][j] = new Square(i, j, colour);
        }
        //alternate the colour of the rest of the squares on the row
        colour = !colour;
      }
    }
  }

  private Piece placePiece(int i, int j)
  {
    Piece piece = null;
    boolean pieceColour;

    //if we are in the top two rows of the Board
    if(i < 2)
    {
      //the piece should be black
      pieceColour = false;
    }
    //if we are in the bottom two rows off the board
    else if (i > 5)
    {
      //the piece should be black
      pieceColour = true;
    }
    //in any other case
    else
    {
      //there shouldn't be a piece in the location
      return null;
    }

    //In the second and penultimate row
    if(i == 1 || i==6)
    {
      //initialise pawns
      piece = new Pawn(i, j, pieceColour, 1);
    }
    //in the first and last row
    else
    {
      //determine which square in the row we are on
      switch(j % 8)
      {
        //initialise the corresponding piece
        case 0, 7 -> piece = new Rook(i, j, pieceColour, 5);
        case 1, 6 -> piece = new Knight(i, j, pieceColour, 3);
        case 2, 5 -> piece = new Bishop(i, j, pieceColour, 3);
        case 3 -> piece = new Queen(i, j, pieceColour, 9);
        case 4 -> piece = new King(i, j, pieceColour, 100);
      }
    }
    //return the piece, so it can be added to the square.
    return piece;
  }

  public ArrayList<Piece> findPieces(Class <?> pieceClass)
  {
    ArrayList<Piece> piecesFound = new ArrayList<>();

    //iterate through the whole board
    for(Square [] row: board)
    {
      for(Square square: row)
      {
        //check the piece of each square
        Piece currentPiece = square.piece;
        //if the piece is of the class we are looking for
        if(pieceClass.isInstance(currentPiece))
        {
          //add the piece to the piece our list
          piecesFound.add(currentPiece);
        }
      }
    }

    return piecesFound;
  }

  public ArrayList<Piece> findAllPieces()
  {
    ArrayList<Piece> allPieces = new ArrayList<>();
    allPieces.addAll(findPieces(King.class));
    allPieces.addAll(findPieces(Queen.class));
    allPieces.addAll(findPieces(Rook.class));
    allPieces.addAll(findPieces(Bishop.class));
    allPieces.addAll(findPieces(Knight.class));
    allPieces.addAll(findPieces(Pawn.class));
    return allPieces;
  }

  public King findKing(boolean colour)
  {
    King king = null;
    //find all King pieces
    ArrayList<Piece> kings = findPieces(King.class);
    for(Piece piece: kings)
    {
      //determine which king is of the right colour
      if(piece.colour == colour)
      {
        //assign that king
        king = (King) piece;
      }
    }
    //return the king
    return king;
  }

  public boolean isCheckOrStalemate(boolean colour)
  {
    //find all possible moves
    findMoves();
    //if colour is true (i.e. White), we need to check if black can still make moves
    ArrayList<Move> movesToCheck = (colour)? whiteMoves:blackMoves;

    //if there are no legal moves return true
    return movesToCheck.size() == 0;
  }

  public boolean isKingInCheck(boolean colour)
  {
    //find the king
    King king = findKing(colour);
    //target the kings square
    Square targetSquare = getSquare(king.posI, king.posJ);

    //check every piece that is attacking that square
    for(Piece piece: targetSquare.attackingPieces)
    {
      //if it is an opposing piece
      if(piece.colour != colour)
      {
        //check
        return true;
      }
    }
    //not check
    return false;
  }

  //mark a king as inCheck
  public void changeCheckStatus(boolean colour, boolean checkStatus)
  {
    //find all kings
    ArrayList<Piece> kings = findPieces(King.class);

    for(Piece piece: kings)
    {
      //select the right king
      if(piece.colour == colour)
      {
        King king = (King) piece;
        //change the check status
        king.setInCheck(checkStatus);

        //check through every piece attacking the kings square
        for(Piece attacker: getSquare(king.posI, king.posJ).attackingPieces)
        {
          //if opposing piece
          if(attacker.colour != king.colour)
          {
            //add the piece to the ones giving check
            king.checkingPieces.add(attacker);
          }
        }
      }
    }
  }

  private void setUp(@NotNull String fen)
  {
    String[] fenParts = fen.split(" ");
    String boardPart = fenParts[0];

    int i = 0, j = 0;
    for (char c : boardPart.toCharArray())
    {
      if (c == '/')
      {
        i++;
        j = 0;
      }
      else if (Character.isDigit(c))
      {
        int emptySquares = Character.getNumericValue(c);
        for (int k = 0; k < emptySquares; k++)
        {
          board[i][j].occupied = false;
          board[i][j].piece = null;
          j++;
        }
      }
      else
      {
        boolean isUpperCase = Character.isUpperCase(c);
        char pieceCode = Character.toLowerCase(c);
        boolean isOccupied = true;
        int value;
        switch(pieceCode)
        {
          case 'k' -> {
            value = 100;
            board[i][j].piece = new King(i, j, isUpperCase, value);
          }
          case 'q' -> {
            value = 9;
            board[i][j].piece = new Queen(i, j, isUpperCase, value);
          }
          case 'b' -> {
            value = 3;
            board[i][j].piece = new Bishop(i, j, isUpperCase, value);
          }
          case 'n' -> {
            value = 3;
            board[i][j].piece = new Knight(i, j, isUpperCase, value);
          }
          case 'r' -> {
            value = 5;
            board[i][j].piece = new Rook(i, j, isUpperCase, value);
          }
          case 'p' -> {
            value = 1;
            board[i][j].piece = new Pawn(i, j, isUpperCase, value);
          }
          default -> isOccupied = false;
        }

        board[i][j].occupied = isOccupied;
        j++;
      }
    }

    if(fenParts.length >= 2)
    {
      String castlePart = fenParts[1];
      if(!Objects.equals(castlePart, "-"))
      {
        for(char castleRight: castlePart.toCharArray())
        {
          King king = (Character.isUpperCase(castleRight))? (King) getSquare(7, 4).piece: (King) getSquare(0, 4).piece;
          if(Character.toLowerCase(castleRight) == 'k')
          {
            king.setCanKingSideCastle(true);
          }
          else
          {
            king.setCanQueenSideCastle(true);
          }
        }
      }
      else
      {
        for(Square [] row: board)
        {
          for(Square square: row)
          {
            if(square.occupied && square.piece instanceof King)
            {
              ((King) square.piece).setCanKingSideCastle(false);
              ((King) square.piece).setCanQueenSideCastle(false);
            }
          }
        }
      }
    }

  }

  public String[] display()
  {
    String[] displayBoard = new String[26];

    StringBuilder sb = new StringBuilder();
    displayBoard[0] = "  __a___b___c___d___e___f___g___h___\n";
    for(int i = 0; i < 8; i++)
    {
      if(i % 2 == 0)
      {
        for(int j = 0; j < 3; j++)
        {
          if(j == 1)
          {
            sb.append(8-i).append(" |").append(middleLine(true, i)).append("|\n");
          }
          else
          {
            sb.append(" ").append(" |").append("****    ****    ****    ****    |\n");
          }
          displayBoard[(((i*3)+j)+1)] = sb.toString();
          sb.delete(0,sb.length());
        }
      }
      else
      {
        for(int j = 0; j < 3; j++)
        {
          if(j == 1)
          {
            sb.append(8-i).append(" |").append(middleLine(false, i)).append("|\n");
          }
          else
          {
            sb.append(" ").append(" |").append("    ****    ****    ****    ****|\n");
          }
          displayBoard[(((i*3)+j)+1)] = sb.toString();
          sb.delete(0,sb.length());
        }
      }
    }
    displayBoard[25] = "  |_a___b___c___d___e___f___g___h__|\n";

    for(String row: displayBoard)
    {
      System.out.print(row);
    }

    return displayBoard;
  }

  private @NotNull
  String middleLine(boolean colour, int row)
  {
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < 8; i++)
    {
      if(this.board[row][i].occupied)
      {
        if(colour)
        {
          sb.append("*").append(board[row][i].piece.getName()).append(board[row][i].piece.getName()).append("*");
        }
        else
        {
          sb.append(" ").append(board[row][i].piece.getName()).append(board[row][i].piece.getName()).append(" ");
        }
      }
      else
      {
        String square = (colour) ? "****":"    ";
        sb.append(square);
      }
      colour = !colour;
    }
    return sb.toString();
  }

  public boolean isValidCoordinate(int row, int col) {
    return row >= 0 && row < 8 && col >= 0 && col < 8;
  }

  public void findMoves()
  {
    //empty the lists containing all the moves of each colour
    blackMoves.clear();
    whiteMoves.clear();

    King blackKing = findKing(false);
    King whiteKing = findKing(true);

    //for each row
    for(Square [] row: board)
    {
      //for each square
      for(Square square: row)
      {
        //if the square is occupied
        if(square.occupied)
        {
          //skip over finding king moves as they require each attacked square to be marked as such
          if(!(square.piece instanceof King))
          {
            //find the legal moves for that piece
            square.piece.findMoves(this, turn);
            //for each found move
            if(square.piece instanceof Pawn)
            {
              addPawnAttacks(square.piece);
            }
            else
            {
              addPieceAttacks(square.piece);
            }

            //depending on the colour of the piece add its moves to the relevant arraylist
            if(square.piece.colour)
            {
              whiteMoves.addAll(square.piece.moves);
            }
            else
            {
              blackMoves.addAll((square.piece.moves));
            }
          }

        }
      }
    }
    //now that all squares that are attacked are marked as attacked, we can find the king moves
    blackKing.findMoves(this, turn);
    blackMoves.addAll(blackKing.moves);
    whiteKing.findMoves(this, turn);
    whiteMoves.addAll(whiteKing.moves);
  }

  public boolean isValidMove(Move move)
  {
    //locate the piece being moved
    Piece targetPiece = getSquare(move.getStart()[0], move.getStart()[1]).piece;

    //find the legal moves for the piece
    targetPiece.findMoves(this, turn);

    //if the provided move is one of the legal moves return true otherwise, false.
    return targetPiece.moves.contains(move);
  }

  public void makeMove(Move move)
  {
    //create a temporary board
    Board tempBoard = this.copy();

    Square startSquare = tempBoard.getSquare(move.getStart()[0], move.getStart()[1]);
    Square endSquare = tempBoard.getSquare(move.getEnd()[0], move.getEnd()[1]);

    //get the moved piece
    Piece movedPiece = startSquare.piece;

    //update the position of the moved piece
    movedPiece.posI = endSquare.posI;
    movedPiece.posJ = endSquare.posJ;

    //update the destination square's data
    endSquare.piece = movedPiece;
    endSquare.occupied = true;
    startSquare.piece = null;
    startSquare.occupied = false;

    //iterate through the temporary board
    for(int i = 0; i < 8; i++)
    {
      for(int j = 0; j < 8; j++)
      {
        Piece oldPiece, newPiece = null;
        //if the square on the tempBoard is occupied
        if(tempBoard.board[i][j].occupied)
        {
          //deep copy the piece
          oldPiece = tempBoard.board[i][j].piece;
          newPiece = oldPiece.copy();
        }
        //point the original boards' square to the square of the copy
        this.board[i][j] = tempBoard.board[i][j];
        //do the same with piece
        this.board[i][j].piece = newPiece;
      }

      Piece targetPiece = endSquare.piece;

      //if the moved piece is a pawn
      if(targetPiece instanceof Pawn)
      {
        //add the diagonally adjacent square as attacked squares
        addPawnAttacks(targetPiece);
      }
      //for every other piece type
      else
      {
        //find the legal moves of the piece
        targetPiece.findMoves(this, move.getTurn() + 1);
        //iterate through the moves
        for(Move foundMove : targetPiece.moves)
        {
          //get the target square of the move
          Square targetSquare = getSquare(foundMove.getEnd()[0], foundMove.getEnd()[1]);
          //if the piece hasn't been added as an attacking piece
          if(!targetSquare.attackingPieces.contains(targetPiece))
          {
            //add it as an attacking piece
            targetSquare.attackingPieces.add(targetPiece);
          }
        }
      }
      //remove any pieces from the attacking pieces arraylist if they aren't attacking it anymore
      updateAttackedSquares();
    }
  }

  private void addPieceAttacks(Piece piece)
  {
    for(Move move : piece.moves)
    {
      //find the destination of the move
      Square targetSquare = getSquare(move.getEnd()[0], move.getEnd()[1]);
      //if the piece hasn't been added as an attacking piece
      if(!targetSquare.attackingPieces.contains(move.getPiece()))
      {
        //add it
        targetSquare.attackingPieces.add(move.getPiece());
      }
    }
  }

  private void addPawnAttacks(Piece targetPiece)
  {
    //determine the direction the pawn is moving in
    int rowOffset = (targetPiece.colour)? -1:1;

    //if right diagonal is a valid coordinate
    if(isValidCoordinate(targetPiece.posI + rowOffset, targetPiece.posJ + 1))
    {
      //add the pawn to that squares attacking pieces
      getSquare(targetPiece.posI + rowOffset, targetPiece.posJ + 1).attackingPieces.add(targetPiece);
    }
    //if left diagonal is a valid coordinate
    if(isValidCoordinate(targetPiece.posI + rowOffset, targetPiece.posJ - 1))
    {
      //add the pawn to that squares attacking pieces
      getSquare(targetPiece.posI + rowOffset, targetPiece.posJ - 1).attackingPieces.add(targetPiece);
    }
  }

  private void updateAttackedSquares()
  {
    //for each square on the boar
    for (Square[] row : board)
    {
      for (Square square : row)
      {
        //if there are attacking pieces
        if (!square.attackingPieces.isEmpty())
        {
          //iterate through each attacking piece
          Iterator<Piece> iterator = square.attackingPieces.iterator();
          while (iterator.hasNext())
          {
            Piece piece = iterator.next();
            //find the moves the piece can make
            piece.findMoves(this, 0);
            boolean pieceStillAttacking = false;
            //for every move
            for (Move move : piece.moves)
            {
              //if the move ends on the current square
              if (move.getEnd()[0] == square.posI && move.getEnd()[1] == square.posJ)
              {
                //there is no need to get rid of this piece
                pieceStillAttacking = true;
                break;
              }
            }
            //if the piece doesn't attack this square anymore
            if (!pieceStillAttacking) {
              //remove it from the attacking list
              iterator.remove(); // Remove the piece using Iterator.
            }
          }
        }
      }
    }
  }

  public double evaluate()
  {
    findMoves();

    double evaluation = 0;
    // Iterate through the entire board
    for (int i = 0; i < 8; i++)
    {
      for (int j = 0; j < 8; j++)
      {
        Square square = getSquare(i, j);
        if (square.occupied)
        {
          Piece piece = square.piece;
          double pieceValue = piece.getValue(this);

          // Add or subtract piece value based on its color
          if (piece.colour)
          {
            evaluation += pieceValue;
          }
          else
          {
            evaluation -= pieceValue;
          }
        }
      }
    }

    // Check for checkmate and check positions
    boolean whiteInCheck = isKingInCheck(true);
    boolean blackInCheck = isKingInCheck(false);

    if(whiteInCheck)
    {
      evaluation -= 50; // White is in checkmate
    }
    if(blackInCheck)
    {
      evaluation += 50; // White is in checkmate
    }
    if (isCheckOrStalemate(true)) {
      evaluation -= 10000.0; // White is in checkmate
    }
    if (isCheckOrStalemate(false)) {
      evaluation += 10000.0; // Black is in checkmate
    }

    return  evaluation*0.1;
  }

  public Square[][] getBoard()
  {
    return board;
  }

  public Square getSquare(int row, int col)
  {
    return board[row][col];
  }

  public boolean squareIsAttacked(boolean colour, int destI, int destJ)
  {
    for(Piece attacker: getSquare(destI, destJ).attackingPieces)
    {
      if(attacker.colour != colour)
      {
        return true;
      }
    }
    return false;
  }

  public void setBoard(String fen)
  {
    setUp(fen);
  }

  public Board copy()
  {
    return new Board(this.toString());
  }

  public ArrayList<Move> getWhiteMoves()
  {
    return whiteMoves;
  }

  public ArrayList<Move> getBlackMoves()
  {
    return blackMoves;
  }

  public int getTurn()
  {
    return turn;
  }

  public void setTurn(int turn)
  {
    this.turn = turn;
  }

}
