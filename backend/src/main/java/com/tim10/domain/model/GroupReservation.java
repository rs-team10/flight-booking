/***********************************************************************
 * Module:  GroupReservation.java
 * Author:  Filip
 * Purpose: Defines the Class GroupReservation
 ***********************************************************************/

import java.util.*;

public class GroupReservation {
   private Long id;
   private LocalDateTime creationDateTime;
   
   private java.util.Collection<Reservation> reservations;
   
   
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
         newReservation.setGroupReservation(this);      
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
            oldReservation.setGroupReservation((GroupReservation)null);
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
            oldReservation.setGroupReservation((GroupReservation)null);
         }
      }
   }

}