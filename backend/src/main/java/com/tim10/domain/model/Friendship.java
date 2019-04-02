/***********************************************************************
 * Module:  Friendship.java
 * Author:  Filip
 * Purpose: Defines the Class Friendship
 ***********************************************************************/

import java.util.*;

public class Friendship {
   private Long id;
   private RequestStatus status;
   
   private RegisteredUser sender;
   private RegisteredUser receiver;
   
   
   /** @pdGenerated default parent getter */
   public RegisteredUser getSender() {
      return sender;
   }
   
   /** @pdGenerated default parent setter
     * @param newRegisteredUser */
   public void setSender(RegisteredUser newRegisteredUser) {
      if (this.sender == null || !this.sender.equals(newRegisteredUser))
      {
         if (this.sender != null)
         {
            RegisteredUser oldRegisteredUser = this.sender;
            this.sender = null;
            oldRegisteredUser.removeFriendships(this);
         }
         if (newRegisteredUser != null)
         {
            this.sender = newRegisteredUser;
            this.sender.addFriendships(this);
         }
      }
   }

}