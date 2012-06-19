package magic.tournament.generator.helpers;

import java.util.Comparator;

/**
 * a comparator to sort by seed
 */
public class SeedComparator implements Comparator
{
   public int compare(Object o1, Object o2)
   {
      PlayerInfo info1 = (PlayerInfo) o1;
      PlayerInfo info2 = (PlayerInfo) o2;
      return (info1.getSeed() - info2.getSeed());
   }
}
