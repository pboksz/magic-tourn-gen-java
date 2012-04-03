package helpers;

/**
 * private object that holds the round, player name, and corresponding opponent name
 */
class PlayerPairing
{
   private int round;
   private String playerName;
   private String opponentName;

   public PlayerPairing(int round, String playerName, String opponentName){
      this.round = round;
      this.playerName = playerName;
      this.opponentName = opponentName;
   }

   public int getRound(){
      return round;
   }

   public String getPlayerName(){
      return playerName;
   }

   public String getOpponentName(){
      return opponentName;
   }
}
