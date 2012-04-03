package helpers;

import java.util.ArrayList;
import java.util.SortedMap;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 4/2/12
 * Time: 11:41 AM
 */
public class RoundPairings
{
   Tournament tournament;
   ArrayList<PlayerPairing> queue;

   public RoundPairings(Tournament tournament)
   {
      this.tournament = tournament;
   }

   /**
    * sets each players final ranking when called on
    *
    * @return a list of players with final ranks in place
    */
   public ArrayList<PlayerInfo> showCurrentRankings()
   {
      ArrayList<PlayerInfo> listOfPlayers = PlayerPool.getRankSortedListOfPlayers();
      int rank = 1;
      for (PlayerInfo player : listOfPlayers)
      {
         player.setRank(rank++);
      }
      //TODO for debugging only
//      printRankings()
      return listOfPlayers;
   }

   /**
    * method used to print to console for debugging each round
    */
   private void printRankings()
   {
      ArrayList<PlayerInfo> listOfPlayers = PlayerPool.getRankSortedListOfPlayers();
      for (PlayerInfo player : listOfPlayers)
      {
         System.out.println(player.getRank() + ") " + player.getName());
         System.out.println("\tRound Wins: " + player.getRoundWins());
         System.out.println("\tRound Byes: " + player.getRoundByes());
         System.out.println("\tRound Losses: " + player.getRoundLosses());
         System.out.println("\tIndividual Wins: " + player.getIndividualWins());
         System.out.println("\tIndividual Losses: " + player.getIndividualLosses());

         System.out.println("\tOpponents Each Round:");
         SortedMap<Integer, String> opponentsList = player.getRoundPairings();
         int size = opponentsList.size();
         for (int i = 1; i <= size; i++)
         {
            System.out.println("\t\t" + i + ") " + opponentsList.get(i));
         }
      }
      System.out.println("------------------------------------------------");
   }

   /**
    * method the recursively tries to set the pairings
    * these actions are stored in a queue and activate when you get back here
    */
   public void setRoundPairings()
   {
      //list of sorted players
      ArrayList<PlayerInfo> sorted;
      //create a queue object
      queue = new ArrayList<PlayerPairing>();
      //if first round get other sorting
      if (tournament.getRound() == 1)
      {
         sorted = PlayerPool.getSeedSortedListOfPlayers();
      }
      else
      {
         sorted = PlayerPool.getRankSortedListOfPlayers();
      }
      //try doing all the pairings
      trySettingRoundPairings(sorted);
      //after all that activate the pairings in the final queue
      for(PlayerPairing pair : queue){
         PlayerPool.setRoundPairing(pair.getRound(), pair.getPlayerName(), pair.getOpponentName());
      }
   }

   /**
    * takes a sorted list tries to pair all the players together
    *
    * @param sorted takes a list of PlayerInfo objects sorted by rank (sometimes in reverse order)
    */
   private void trySettingRoundPairings(ArrayList<PlayerInfo> sorted)
   {
      int firstIndex = 1;
      boolean isFirstPlayer = true;
      //try and pair off all the other people
      tryPairingAllPlayers(sorted, isFirstPlayer, firstIndex);
   }

   /**
    * tries to pair each player and if it ends up failing will redo this whole process and will try pairing the top player with second in line, and so forth
    *
    * @param sorted        takes a list of PlayerInfo objects sorted by rank
    * @param isFirstPlayer a boolean that is true says to start trying to pair the first player with the player at index 'firstIndex'
    * @param firstIndex    the index of the player to start trying to pair with
    */
   private void tryPairingAllPlayers(ArrayList<PlayerInfo> sorted, boolean isFirstPlayer, int firstIndex)
   {
      //try setting the bye first to last player without a bye
      if (sorted.size() % 2 == 1)
      {
         int lastIndex = sorted.size() - 1;
         trySettingLowestBye(sorted, lastIndex);
      }
      //has this successfully paired the first player?
      boolean successFullyPairedAll = true;
      while ((sorted.size() != 0) && successFullyPairedAll)
      {
         int pairIndex = 1;
         //if this is the first player being sorted for the first time may change the index of the player to look at first
         if (isFirstPlayer)
         {
            pairIndex = firstIndex;
            isFirstPlayer = false;
         }
         //pair first player starting from opponent at pairIndex
         successFullyPairedAll = tryPairingNextPlayer(sorted, pairIndex);
      }
      //this would mean that the player cannot be paired with anyone in the set
      if (!successFullyPairedAll)
      {
         queue.clear();
         isFirstPlayer = true;
         sorted = PlayerPool.getRankSortedListOfPlayers();
         tryPairingAllPlayers(sorted, isFirstPlayer, ++firstIndex);
      }
   }

   /**
    * will set the bye to the lowest player available to get a bye
    *
    * @param sorted list of players sorted by rank
    * @param last   index of the last player
    */
   private void trySettingLowestBye(ArrayList<PlayerInfo> sorted, int last)
   {
      boolean byeIsNotSet = true;
      if (PlayerPool.getNumPlayers() == 3)
      {
         byeIsNotSet = handle3PlayerByes(sorted, last);
      }
      //else this normally gives the Bye to the last possible player
      if (byeIsNotSet)
      {
         PlayerInfo player = sorted.get(last);
         if (player.canUseBye())
         {
            queue.add(new PlayerPairing(tournament.getRound(), player.getName(), player.getOpponent()));
            sorted.remove(last);
         }
         else
         {
            trySettingLowestBye(sorted, --last);
         }
      }
   }

   /**
    * This is a method to deal with 3 player specific problems that stem from dropping a player before Round 2
    *
    * @param sorted a sorted list of players
    * @param last   the last index of the player list
    * @return a boolean as to whether the bye was set or not
    */
   private boolean handle3PlayerByes(ArrayList<PlayerInfo> sorted, int last)
   {
      boolean byeIsNotSet = true;
      //if this is second to last round pairing, there can be an issue with the last player not being able to
      //have a bye, as that player needs to play one of the other two players
      if (tournament.getRound() == (tournament.getMaxRound() - 1))
      {
         //if the last player has 3 possible opponents (that is one of the other two has to play him), set second to last as bye
         if (sorted.get(last).getPossibleOpponents().size() == 3)
         {
            PlayerInfo player = sorted.get(last - 1);
            queue.add(new PlayerPairing(tournament.getRound(), player.getName(), "Bye"));
            sorted.remove(last - 1);
            byeIsNotSet = false;
         }
      }
      //if this is last round pairing, there has a potential for a player to only be able to have a bye
      if (tournament.getRound() == tournament.getMaxRound())
      {
         for (int i = 0; i < sorted.size(); i++)
         {
            PlayerInfo player = sorted.get(i);
            if (player.canOnlyGetBye())
            {
               queue.add(new PlayerPairing(tournament.getRound(), player.getName(), "Bye"));
               sorted.remove(i);
               byeIsNotSet = false;
               break;
            }
         }
      }
      return byeIsNotSet;
   }

   /**
    * this is a recursive method that pairs a player with one of the players left in the list
    *
    * @param sorted    a list of players by current rank available to be paired with
    * @param pairIndex the index of the player to try and pair first
    * @return a boolean on whether this pairing was successful of not
    */
   private boolean tryPairingNextPlayer(ArrayList<PlayerInfo> sorted, int pairIndex)
   {
      if (pairIndex < sorted.size())
      {
         //get the first player to look at
         PlayerInfo player = sorted.get(0);
         //if there are potential players to match
         PlayerInfo opponent = sorted.get(pairIndex);
         //if player has not previously played this opponent
         if (player.canPlayThisPlayer(opponent.getName()))
         {
            //add this pair to the queue, as later this may get scrapped
            queue.add(new PlayerPairing(tournament.getRound(), player.getName(), player.getOpponent()));
            //remove the player and opponent paired from sorted and reverts back to trySettingRoundPairs() with two players removed
            sorted.remove(pairIndex);
            sorted.remove(0);
            return true;
         }
         //else test the next player in sorted and see if thats a better match
         else
         {
            return tryPairingNextPlayer(sorted, ++pairIndex);
         }
      }
      else
      {
         return false;
      }
   }
}
