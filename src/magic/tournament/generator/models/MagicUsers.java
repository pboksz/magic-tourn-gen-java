package magic.tournament.generator.models;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * {NAME}
 * Author: Phillip Boksz
 * Date: 5/22/12
 * Time: 3:02 PM
 */
@Entity
public class MagicUsers {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String name;
   private String email;
   private String hashedPassword;

   @OneToMany
   private ArrayList<Tournament> tournaments;

   @OneToMany
   private ArrayList<PlayerInfo> playerInfos;

   public Long getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public String getEmail() {
      return email;
   }

   public ArrayList<Tournament> getTournaments() {
      return tournaments;
   }

   public ArrayList<PlayerInfo> getPlayerInfos() {
      return playerInfos;
   }
}
