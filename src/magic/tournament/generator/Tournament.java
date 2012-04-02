package magic.tournament.generator;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 4/2/12
 * Time: 11:09 AM
 */
public class Tournament
{
   private int prevRound = 0;
   private int round = 1;
   private int numPlayers;
   private int maxRound;
   private int bestOf;
   private String format;

   public Tournament(int numPlayers, int maxRound, int bestOf, String format) {
      this.numPlayers = numPlayers;
      this.maxRound = maxRound;
      this.bestOf = bestOf;
      this.format = format;
   }

   public int getPrevRound() {
      return prevRound;
   }

   public int getRound() {
      return round;
   }

   public int getNumPlayers() {
      return numPlayers;
   }

   public int getMaxRound() {
      return maxRound;
   }

   public int getBestOf() {
      return bestOf;
   }

   public String getFormat() {
      return format;
   }

   public void nextRound() {
      this.round++;
   }

   public void incPrevRound() {
      this.prevRound++;
   }

   public boolean isNextRound() {
      return round > prevRound;
   }
}
