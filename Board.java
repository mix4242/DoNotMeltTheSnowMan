public class Board {

  private Piece[][] board;
  private Coordinate emitter;

  public Board(Piece[][] board) {
    this.board    = board;
    this.emitter  = PieceUtils.findEmitter(board);
  }

  public boolean laserEnds(Coordinate c) 
  {
      if (!isInsideBoard(c))
          return true;
      Piece piece = board[c.getX()][c.getY()];
      switch (piece)
      {
          case EMPTY:
          case MIRROR_NW_SE:
          case MIRROR_SW_NE:
          case LASER_HORIZONTAL:
          case LASER_VERTICAL:
          case LASER_CROSSED:
              return false;
      }
      return true;
  }

  private boolean isInsideBoard(Coordinate c)
  {
      int x, y;
      x = c.getX();
      y = c.getY();
      if (x < 0 || y < 0 || x >= board.length || y >= board[0].length)
          return false;
      return true;
  }


  public Result calculateResult(Coordinate c) 
  {
      if (isInsideBoard(c))
      {
          Piece piece = board[c.getX()][c.getY()];
          if (piece == Piece.TARGET)
              return Result.HIT_TARGET;
          else if (piece == Piece.SNOWMAN)
              return Result.MELT_SNOWMAN;
      }
      return Result.MISS;
  }

  private Piece getPiece(Coordinate c)
  {
      assert isInsideBoard(c);
      return board[c.getX()][c.getY()];
  }

  public Result fireLaser() 
  {
      Coordinate current = PieceUtils.findEmitter(board);
      Coordinate next;
      int vx = 0, vy = 0;
      do
      {
          board[current.getX()][current.getY()] 
              = PieceUtils.addLaser(getPiece(current), vx != 0);
          next = PieceUtils.move(getPiece(current), current, vx, vy);
          vx = next.getX() - current.getX();
          vy = next.getY() - current.getY();
          current = next;
      } while (!laserEnds(current));
      return calculateResult(current);
  }

  public void rotatePiece(Coordinate c) {
    assert c.getX() >= 0 && c.getX() < board.length
        && c.getY() >= 0 && c.getY() < board[0].length;

    board[c.getX()][c.getY()]
      = PieceUtils.rotate(board[c.getX()][c.getY()]);
  }

  public void clearLasers() {
    for (int i = 0; i < board.length ; i++) {
      for (int j = 0; j < board[i].length ; j++) {
        board[i][j] = PieceUtils.hideLaser(board[i][j]);
      }
    }
  }


  private static final char ESC = 27;

  public void renderBoard() {

    System.out.print(ESC + "[30;47m  ");
    for (int i = 0 ; i < board.length ; i++) {
      System.out.print(i);
    }
    System.out.println(" ");

    System.out.print(" ┏");
    for (int i = 0 ; i < board.length ; i++) {
      System.out.print("━");
    }
    System.out.println("┓");

    for (int j = board[0].length - 1 ; j >= 0 ; j--) {
      System.out.print(ESC + "[30m" + j +"┃");
      for (int i = 0 ; i < board.length ; i++ ) {
        System.out.print(renderPiece(board[i][j]));
      }
      System.out.println(ESC + "[30m┃");
    }
    System.out.print(ESC + "[30m ┗");
    for (int i = 0 ; i < board.length ; i++) {
      System.out.print("━");
    }
    System.out.println("┛");

  }

  private static String renderPiece(Piece p) {
    switch (p) {
      case EMITTER_NORTH:
        return ESC + "[32m↑";
      case EMITTER_EAST:
        return ESC + "[32m→";
      case EMITTER_SOUTH:
        return ESC + "[32m↓";
      case EMITTER_WEST:
        return ESC + "[32m←";

      case LASER_VERTICAL:
        return ESC + "[31m│";
      case LASER_HORIZONTAL:
        return ESC + "[31m─";
      case LASER_CROSSED:
        return ESC + "[31m┼";

      case MIRROR_SW_NE:
        return ESC + "[34m╱";
      case MIRROR_NW_SE:
        return ESC + "[34m╲";

      case WALL:
        return ESC + "[36m█";

      case TARGET:
        return ESC + "[35m☼";

      case EMPTY:
        return " ";

      case SNOWMAN:
        return ESC + "[30m☃";
    }
    return "!";
  }

}
