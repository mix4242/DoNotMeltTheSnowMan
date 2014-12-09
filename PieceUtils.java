public class PieceUtils 
{

  public static Piece charToPiece(char c) 
  {
      switch (c)
      {
          case '^':
              return Piece.EMITTER_NORTH;
          case '>':
              return Piece.EMITTER_EAST;
          case 'v':
              return Piece.EMITTER_SOUTH;
          case '<':
              return Piece.EMITTER_WEST;
          
          case '|':
              return Piece.LASER_VERTICAL;
          case '-':
              return Piece.LASER_HORIZONTAL;
          case '+':
              return Piece.LASER_CROSSED;

          case '/':
              return Piece.MIRROR_SW_NE;
          case '\\':
              return Piece.MIRROR_NW_SE;

          case '#':
              return Piece.WALL;
          case 'o':
              return Piece.TARGET;
          case '@':
              return Piece.SNOWMAN;
          case ' ':
              return Piece.EMPTY;
          default:
              return null;
      }
  }

  public static Piece[][] charsToPieces(char[] description,
                                        int width, int height) 
  {
      String desc = new String(description);
      Piece[][] board = new Piece[width][height];
      for (int i = height-1; i >= 0; i--)
      {
          for (int j = 0; j < width; j++)
          {
              board[j][i] = charToPiece(desc.charAt(j));
          }
          desc = desc.substring(width);
      }
      return board;
  }

  public static boolean isEmitter(Piece p) {
    switch (p) {
      case EMITTER_NORTH:
      case EMITTER_EAST:
      case EMITTER_SOUTH:
      case EMITTER_WEST:
        return true;
    }

    return false;
  }

  public static Coordinate findEmitter(Piece[][] pieces) 
  {
      for (int x = 0; x < pieces.length; x++)
      {
          for (int y = 0; y < pieces[x].length; y++)
          {
              if (isEmitter(pieces[x][y]))
                  return new Coordinate(x, y);
          }
      }
      return null;
  }

  public static Piece hideLaser(Piece p) {
    switch (p) {
      case LASER_VERTICAL:
      case LASER_HORIZONTAL:
      case LASER_CROSSED:
        return Piece.EMPTY;
    }
    return p;
  }

  public static Piece addLaser(Piece p, boolean isHorizontal) 
  {
      if (p == Piece.EMPTY)
          return (isHorizontal) ? Piece.LASER_HORIZONTAL 
                  : Piece.LASER_VERTICAL;
      if ((p == Piece.LASER_HORIZONTAL && !isHorizontal) 
              || (p == Piece.LASER_VERTICAL && isHorizontal))
              return Piece.LASER_CROSSED; 
      return p;
  }

  public static Coordinate move(Piece p, Coordinate c, int xo, int yo) 
  {
      int x, y;
      x = c.getX();
      y = c.getY();
      switch (p) 
      {
          case EMITTER_NORTH:
              return new Coordinate(x , y + 1);
          case EMITTER_EAST:
              return new Coordinate(x + 1, y);
          case EMITTER_SOUTH:
              return new Coordinate(x, y - 1);
          case EMITTER_WEST:
              return new Coordinate(x - 1, y);
          case EMPTY:
          case LASER_HORIZONTAL:
          case LASER_CROSSED:
          case LASER_VERTICAL:
              return new Coordinate(x + xo, y + yo);
          case MIRROR_NW_SE:
              {
                  xo *= -1;
                  yo *= -1;
              }
          case MIRROR_SW_NE:
              return new Coordinate(x + yo, y + xo);
          default:
              return c;
      }
  }


  public static Piece rotate(Piece p) {
    switch (p) {
      case EMITTER_NORTH:
        return Piece.EMITTER_EAST;
      case EMITTER_EAST:
        return Piece.EMITTER_SOUTH;
      case EMITTER_SOUTH:
        return Piece.EMITTER_WEST;
      case EMITTER_WEST:
        return Piece.EMITTER_NORTH;
      case MIRROR_SW_NE:
        return Piece.MIRROR_NW_SE;
      case MIRROR_NW_SE:
        return Piece.MIRROR_SW_NE;
    }
    return p;
  }

}
