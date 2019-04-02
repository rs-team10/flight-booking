/***********************************************************************
 * Module:  Room.java
 * Author:  Filip
 * Purpose: Defines the Class Room
 ***********************************************************************/

import java.util.*;

public class Room {
   private Long id;
   private Integer floor;
   private Integer squareFootage;
   private Boolean hasBalcony;
   
   private RoomType roomType;
   private java.util.Collection<RoomReservation> roomReservations;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<RoomReservation> getRoomReservations() {
      if (roomReservations == null)
         roomReservations = new java.util.HashSet<RoomReservation>();
      return roomReservations;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorRoomReservations() {
      if (roomReservations == null)
         roomReservations = new java.util.HashSet<RoomReservation>();
      return roomReservations.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newRoomReservations */
   public void setRoomReservations(java.util.Collection<RoomReservation> newRoomReservations) {
      removeAllRoomReservations();
      for (java.util.Iterator iter = newRoomReservations.iterator(); iter.hasNext();)
         addRoomReservations((RoomReservation)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newRoomReservation */
   public void addRoomReservations(RoomReservation newRoomReservation) {
      if (newRoomReservation == null)
         return;
      if (this.roomReservations == null)
         this.roomReservations = new java.util.HashSet<RoomReservation>();
      if (!this.roomReservations.contains(newRoomReservation))
      {
         this.roomReservations.add(newRoomReservation);
         newRoomReservation.setRoom(this);      
      }
   }
   
   /** @pdGenerated default remove
     * @param oldRoomReservation */
   public void removeRoomReservations(RoomReservation oldRoomReservation) {
      if (oldRoomReservation == null)
         return;
      if (this.roomReservations != null)
         if (this.roomReservations.contains(oldRoomReservation))
         {
            this.roomReservations.remove(oldRoomReservation);
            oldRoomReservation.setRoom((Room)null);
         }
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllRoomReservations() {
      if (roomReservations != null)
      {
         RoomReservation oldRoomReservation;
         for (java.util.Iterator iter = getIteratorRoomReservations(); iter.hasNext();)
         {
            oldRoomReservation = (RoomReservation)iter.next();
            iter.remove();
            oldRoomReservation.setRoom((Room)null);
         }
      }
   }

}