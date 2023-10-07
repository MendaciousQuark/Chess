import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Bot  extends Player
{

  public Bot(boolean castle, boolean colour)
  {
    super(castle, colour);
  }

  private Move generateMove(Board board)
  {
    ArrayList<Future<Pair<Move, Double>>> futures = new ArrayList<>();
    int numberOfThreads = 8;
    ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
    ArrayList<Piece> allPieces = board.findAllPieces();

    // Make a defensive copy of the allPieces list
    ArrayList<Piece> allPiecesCopy = new ArrayList<>(allPieces);

    for(ArrayList<Piece> assignedPieces: splitArrayList(allPiecesCopy, numberOfThreads))
    {
      futures.add(executorService.submit(new ParallelMoveProcessor(board, assignedPieces, this.colour)));
    }

    double bestEval = (this.colour)? Double.NEGATIVE_INFINITY:Double.POSITIVE_INFINITY;
    Move bestMove = null;
    for(Future<Pair<Move, Double>> future: futures)
    {
      try {
        Pair<Move, Double> moveEvalPair = future.get();
        // Process the result here based on your requirements
        if(colour)
        {
          if(moveEvalPair.getSecond() >= bestEval)
          {
            if(moveEvalPair.getFirst() != null)
            {
              bestMove = moveEvalPair.getFirst();
            }
          }
        }
        else
        {
          if(moveEvalPair.getSecond() <= bestEval)
          {
            if(moveEvalPair.getFirst() != null)
            {
              bestMove = moveEvalPair.getFirst();
            }
          }
        }
      } catch (InterruptedException | ExecutionException e) {
        System.err.println(e.getMessage());
      }

    }

    executorService.shutdown();
    return bestMove;
  }

  private List<ArrayList<Piece>> splitArrayList(ArrayList<Piece> inputList, int numberOfParts)
  {
    int totalSize = inputList.size();
    int partSize = (totalSize + numberOfParts - 1) / numberOfParts; // Calculate the size of each part, rounding up

    List<ArrayList<Piece>> parts = new ArrayList<>(numberOfParts);

    for (int i = 0; i < totalSize; i += partSize)
    {
      int toIndex = Math.min(i + partSize, totalSize);
      ArrayList<Piece> part = new ArrayList<>();
      for(int j = i; j < toIndex; j++)
      {
        part.add(inputList.get(j));
      }
      parts.add(part);
    }

    return parts;
  }

  protected Piece promotePawn(Pawn pawn)
  {
    return null;
  }

  @Override
  protected Move getMove(Board board)
  {
    return generateMove(board);
  }
}
