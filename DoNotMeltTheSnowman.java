public class DoNotMeltTheSnowman {

  public static void main(String[] args) 
  {
      int levelNo = Integer.parseInt(args[0]);
      Level level = Levels.getLevels()[levelNo];
      Board board = new Board(PieceUtils.charsToPieces(level
                  .getCharArray(), level.getWidth(), level.getHeight()));
      Result result;
      do
      {
          result = board.fireLaser();
          board.renderBoard();
          if (result == Result.HIT_TARGET)
          {
              System.out.println("Well done you win");
              break;
          }
          if (result == Result.MELT_SNOWMAN)
          {
              System.out.println("You melted the snowman");
              break;
          }
          System.out.println("Ener row then column to rotate: ");
          int row = IOUtil.readInt();
          int column = IOUtil.readInt();
          board.rotatePiece(new Coordinate(column, row));
          board.clearLasers();
      } while (result == Result.MISS);

  }

}
