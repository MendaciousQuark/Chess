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
  Board(Square[][] board)
  {
    this.board = board;
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

  private void init()
  {
    boolean colour;
    for(int i = 0; i < 8; i++)
    {
      colour = i % 2 == 0;
      for(int j = 0; j < 8; j++)
      {
        Piece piece = placePiece(i, j);
        if(piece != null)
        {
          if(piece instanceof King)
          {
            ((King) piece).setCanKingSideCastle(true);
            ((King) piece).setCanQueenSideCastle(true);
          }
          this.board[i][j] = new Square(i, j, colour, piece);
        }
        else
        {
          this.board[i][j] = new Square(i, j, colour);
        }
        colour = !colour;
      }
    }
  }

  private Piece placePiece(int i, int j)
  {
    Piece piece = null;
    boolean pieceColour;

    if(i < 2)
    {
      pieceColour = false;
    }
    else if (i > 5)
    {
      pieceColour = true;
    }
    else
    {
      return null;
    }

    if(i == 1 || i==6)
    {
      piece = new Pawn(i, j, pieceColour, 1);
    }
    else
    {
      switch(j % 8)
      {
        case 0, 7 -> piece = new Rook(i, j, pieceColour, 5);
        case 1, 6 -> piece = new Knight(i, j, pieceColour, 3);
        case 2, 5 -> piece = new Bishop(i, j, pieceColour, 3);
        case 3 -> piece = new Queen(i, j, pieceColour, 9);
        case 4 -> piece = new King(i, j, pieceColour, 100);
      }
    }
    return piece;
  }

  public ArrayList<Piece> findPieces(Class <?> pieceClass)
  {
    ArrayList<Piece> piecesFound = new ArrayList<>();

    for(Square [] row: board)
    {
      for(Square square: row)
      {
        Piece currentPiece = square.piece;
        if(pieceClass.isInstance(currentPiece))
        {
          piecesFound.add(currentPiece);
        }
      }
    }

    return piecesFound;
  }

  public boolean isCheckmate(boolean colour)
  {
    //find all possible moves
    findMoves();
    //if colour is true (i.e. White), we need to check if black can still make moves
    ArrayList<Move> movesToCheck = (colour)? blackMoves:whiteMoves;

    //if there are no legal moves return true
    return movesToCheck.size() == 0;
  }

  public boolean isKingInCheck(boolean colour)
  {
    ArrayList<Piece> kings =  findPieces(King.class);
    for(Piece king: kings)
    {
      if(king.colour == colour)
      {
        Square targetSquare = getSquare(king.posI, king.posJ);
        for(Piece piece: targetSquare.attackingPieces)
        {
          if(piece.colour != colour)
          {
            return true;
          }
        }
      }
    }
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

    //for each row
    for(Square [] row: board)
    {
      //for each square
      for(Square square: row)
      {
        //if the square is occupied
        if(square.occupied)
        {
            //find the legal moves for that piece
            square.piece.findMoves(this, turn);
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
    Board tempBoard = this.copy();
    Square startSquare = tempBoard.getSquare(move.getStart()[0], move.getStart()[1]);
    Square endSquare = tempBoard.getSquare(move.getEnd()[0], move.getEnd()[1]);

    Piece movedPiece = startSquare.piece;
    movedPiece.posI = endSquare.posI;
    movedPiece.posJ = endSquare.posJ;

    endSquare.piece = movedPiece;
    endSquare.occupied = true;
    startSquare.piece = null;
    startSquare.occupied = false;

    for(int i = 0; i < 8; i++)
    {
      for(int j = 0; j < 8; j++)
      {
        Piece oldPiece, newPiece = null;
        if(tempBoard.board[i][j].occupied)
        {
          oldPiece = tempBoard.board[i][j].piece;
          newPiece = oldPiece.copy();
        }
        this.board[i][j] = tempBoard.board[i][j];
        this.board[i][j].piece = newPiece;
      }

      Piece targetPiece = endSquare.piece;
      if(targetPiece instanceof Pawn)
      {
        int rowOffset = (targetPiece.colour)? -1:1;
        if(isValidCoordinate(targetPiece.posI + rowOffset, targetPiece.posJ + 1))
        {
          getSquare(targetPiece.posI + rowOffset, targetPiece.posJ + 1).attackingPieces.add(targetPiece);
        }
        if(isValidCoordinate(targetPiece.posI + rowOffset, targetPiece.posJ - 1))
        {
          getSquare(targetPiece.posI + rowOffset, targetPiece.posJ - 1).attackingPieces.add(targetPiece);
        }
      }
      else
      {
        targetPiece.findMoves(this, move.getTurn() + 1);
        for(Move foundMove : targetPiece.moves)
        {
          Square targetSquare = getSquare(foundMove.getEnd()[0], foundMove.getEnd()[1]);
          targetSquare.attackingPieces.add(targetPiece);
        }
      }
      updateAttackedSquares();
    }
  }

  private void updateAttackedSquares()
  {
    for (Square[] row : board)
    {
      for (Square square : row)
      {
        if (!square.attackingPieces.isEmpty())
        {
          Iterator<Piece> iterator = square.attackingPieces.iterator();
          while (iterator.hasNext())
          {
            Piece piece = iterator.next();
            piece.findMoves(this, 0);
            boolean pieceStillAttacking = false;
            for (Move move : piece.moves)
            {
              if (move.getEnd()[0] == square.posI && move.getEnd()[1] == square.posJ)
              {
                pieceStillAttacking = true;
                break;
              }
            }
            if (!pieceStillAttacking) {
              iterator.remove(); // Remove the piece using Iterator.
            }
          }
        }
      }
    }
  }

  public double evaluate()
  {
    return 0.0;
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
  public void setWhiteMoves(ArrayList<Move> whiteMoves)
  {
    this.whiteMoves = whiteMoves;
  }

  public void setBlackMoves(ArrayList<Move> blackMoves)
  {
    this.blackMoves = blackMoves;
  }

  public void setTurn(int turn)
  {
    this.turn = turn;
  }
}
