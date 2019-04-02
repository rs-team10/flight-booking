/***********************************************************************
 * Module:  Seat.java
 * Author:  Filip
 * Purpose: Defines the Class Seat
 ***********************************************************************/

import java.util.*;

public class Seat {
   private Long id;
   private Integer row;
   private Integer column;
   private SegmentClass class;
   
   private Flight flight;
   
   
   /** @pdGenerated default parent getter */
   public Flight getFlight() {
      return flight;
   }
   
   /** @pdGenerated default parent setter
     * @param newFlight */
   public void setFlight(Flight newFlight) {
      if (this.flight == null || !this.flight.equals(newFlight))
      {
         if (this.flight != null)
         {
            Flight oldFlight = this.flight;
            this.flight = null;
            oldFlight.removeSeats(this);
         }
         if (newFlight != null)
         {
            this.flight = newFlight;
            this.flight.addSeats(this);
         }
      }
   }

}