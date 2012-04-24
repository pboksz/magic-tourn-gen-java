package helpers;

/**
 * private object that holds the round, player name, and corresponding opponent name
 */
class PlayerPairing
{
   private String playerName;
   private String opponentName;

   public PlayerPairing(String playerName, String opponentName){
      this.playerName = playerName;
      this.opponentName = opponentName;
   }

   public String getPlayerName(){
      return playerName;
   }

   public String getOpponentName(){
      return opponentName;
   }
}
