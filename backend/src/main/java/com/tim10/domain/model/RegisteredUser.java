/***********************************************************************
 * Module:  RegisteredUser.java
 * Author:  Filip
 * Purpose: Defines the Class RegisteredUser
 ***********************************************************************/

import java.util.*;

public class RegisteredUser extends User {
   private String phone;
   private String address;
   private Integer bonusPoints;
   private String avatar;
   private String verificationCode;
   private Boolean isConfirmed;
   
   private java.util.Collection<Reservation> reservations;
   private java.util.Collection<Friendship> friendships;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<Reservation> getReservations() {
      if (reservations == null)
         reservations = new java.util.HashSet<Reservation>();
      return reservations;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorReservations() {
      if (reservations == null)
         reservations = new java.util.HashSet<Reservation>();
      return reservations.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newReservations */
   public void setReservations(java.util.Collection<Reservation> newReservations) {
      removeAllReservations();
      for (java.util.Iterator iter = newReservations.iterator(); iter.hasNext();)
         addReservations((Reservation)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newReservation */
   public void addReservations(Reservation newReservation) {
      if (newReservation == null)
         return;
      if (this.reservations == null)
         this.reservations = new java.util.HashSet<Reservation>();
      if (!this.reservations.contains(newReservation))
      {
         this.reservations.add(newReservation);
         newReservation.setRegisteredUser(this);      
      }
   }
   
   /** @pdGenerated default remove
     * @param oldReservation */
   public void removeReservations(Reservation oldReservation) {
      if (oldReservation == null)
         return;
      if (this.reservations != null)
         if (this.reservations.contains(oldReservation))
         {
            this.reservations.remove(oldReservation);
            oldReservation.setRegisteredUser((RegisteredUser)null);
         }
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllReservations() {
      if (reservations != null)
      {
         Reservation oldReservation;
         for (java.util.Iterator iter = getIteratorReservations(); iter.hasNext();)
         {
            oldReservation = (Reservation)iter.next();
            iter.remove();
            oldReservation.setRegisteredUser((RegisteredUser)null);
         }
      }
   }
   /** @pdGenerated default getter */
   public java.util.Collection<Friendship> getFriendships() {
      if (friendships == null)
         friendships = new java.util.HashSet<Friendship>();
      return friendships;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorFriendships() {
      if (friendships == null)
         friendships = new java.util.HashSet<Friendship>();
      return friendships.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newFriendships */
   public void setFriendships(java.util.Collection<Friendship> newFriendships) {
      removeAllFriendships();
      for (java.util.Iterator iter = newFriendships.iterator(); iter.hasNext();)
         addFriendships((Friendship)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newFriendship */
   public void addFriendships(Friendship newFriendship) {
      if (newFriendship == null)
         return;
      if (this.friendships == null)
         this.friendships = new java.util.HashSet<Friendship>();
      if (!this.friendships.contains(newFriendship))
      {
         this.friendships.add(newFriendship);
         newFriendship.setSender(this);      
      }
   }
   
   /** @pdGenerated default remove
     * @param oldFriendship */
   public void removeFriendships(Friendship oldFriendship) {
      if (oldFriendship == null)
         return;
      if (this.friendships != null)
         if (this.friendships.contains(oldFriendship))
         {
            this.friendships.remove(oldFriendship);
            oldFriendship.setSender((RegisteredUser)null);
         }
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllFriendships() {
      if (friendships != null)
      {
         Friendship oldFriendship;
         for (java.util.Iterator iter = getIteratorFriendships(); iter.hasNext();)
         {
            oldFriendship = (Friendship)iter.next();
            iter.remove();
            oldFriendship.setSender((RegisteredUser)null);
         }
      }
   }

}