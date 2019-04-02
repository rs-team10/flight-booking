/***********************************************************************
 * Module:  Reservation.java
 * Author:  Filip
 * Purpose: Defines the Class Reservation
 ***********************************************************************/

import java.util.*;

public class Reservation {
   private Long id;
   private Boolean isHost;
   private String invitationCode;
   private RequestStatus status;
   private Integer distance;
   private Double discount;
   private Boolean usedDiscount;
   private Boolean hasPassed;
   
   private FlightReservation flightReservation;
   private RegisteredUser registeredUser;
   private GroupReservation groupReservation;
   private VehicleReservation vehicleReservation;
   private RoomReservation roomReservation;
   
   
   /** @pdGenerated default parent getter */
   public RegisteredUser getRegisteredUser() {
      return registeredUser;
   }
   
   /** @pdGenerated default parent setter
     * @param newRegisteredUser */
   public void setRegisteredUser(RegisteredUser newRegisteredUser) {
      if (this.registeredUser == null || !this.registeredUser.equals(newRegisteredUser))
      {
         if (this.registeredUser != null)
         {
            RegisteredUser oldRegisteredUser = this.registeredUser;
            this.registeredUser = null;
            oldRegisteredUser.removeReservations(this);
         }
         if (newRegisteredUser != null)
         {
            this.registeredUser = newRegisteredUser;
            this.registeredUser.addReservations(this);
         }
      }
   }
   /** @pdGenerated default parent getter */
   public GroupReservation getGroupReservation() {
      return groupReservation;
   }
   
   /** @pdGenerated default parent setter
     * @param newGroupReservation */
   public void setGroupReservation(GroupReservation newGroupReservation) {
      if (this.groupReservation == null || !this.groupReservation.equals(newGroupReservation))
      {
         if (this.groupReservation != null)
         {
            GroupReservation oldGroupReservation = this.groupReservation;
            this.groupReservation = null;
            oldGroupReservation.removeReservations(this);
         }
         if (newGroupReservation != null)
         {
            this.groupReservation = newGroupReservation;
            this.groupReservation.addReservations(this);
         }
      }
   }

}