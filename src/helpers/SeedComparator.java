package helpers;

import java.util.Comparator;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 4/3/12
 * Time: 11:16 AM
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
