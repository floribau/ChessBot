package Util.Exception;

import Game.Move;

public class IllegalMoveException extends Exception{
  public IllegalMoveException(Move m){
    super("IllegalMoveException: " + m.toString());
  }
}
