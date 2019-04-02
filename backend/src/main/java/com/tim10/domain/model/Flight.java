/***********************************************************************
 * Module:  Flight.java
 * Author:  Filip
 * Purpose: Defines the Class Flight
 ***********************************************************************/

import java.util.*;

public class Flight {
   private Long id;
   private String flightNumber;
   private BigDecimal ticketPrice;
   private LocalDateTime date;
   private Integer duration;
   private Integer distance;
   private Integer transitCount;
   private Double averageFeedback;
   
   private Destination destination;
   private Destination departure;
   private java.util.Collection<Destination> transitDestinations;
   private java.util.Collection<Seat> seats;
   private Airline airline;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<Destination> getTransitDestinations() {
      if (transitDestinations == null)
         transitDestinations = new java.util.HashSet<Destination>();
      return transitDestinations;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorTransitDestinations() {
      if (transitDestinations == null)
         transitDestinations = new java.util.HashSet<Destination>();
      return transitDestinations.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newTransitDestinations */
   public void setTransitDestinations(java.util.Collection<Destination> newTransitDestinations) {
      removeAllTransitDestinations();
      for (java.util.Iterator iter = newTransitDestinations.iterator(); iter.hasNext();)
         addTransitDestinations((Destination)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newDestination */
   public void addTransitDestinations(Destination newDestination) {
      if (newDestination == null)
         return;
      if (this.transitDestinations == null)
         this.transitDestinations = new java.util.HashSet<Destination>();
      if (!this.transitDestinations.contains(newDestination))
         this.transitDestinations.add(newDestination);
   }
   
   /** @pdGenerated default remove
     * @param oldDestination */
   public void removeTransitDestinations(Destination oldDestination) {
      if (oldDestination == null)
         return;
      if (this.transitDestinations != null)
         if (this.transitDestinations.contains(oldDestination))
            this.transitDestinations.remove(oldDestination);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllTransitDestinations() {
      if (transitDestinations != null)
         transitDestinations.clear();
   }
   /** @pdGenerated default getter */
   public java.util.Collection<Seat> getSeats() {
      if (seats == null)
         seats = new java.util.HashSet<Seat>();
      return seats;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorSeats() {
      if (seats == null)
         seats = new java.util.HashSet<Seat>();
      return seats.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newSeats */
   public void setSeats(java.util.Collection<Seat> newSeats) {
      removeAllSeats();
      for (java.util.Iterator iter = newSeats.iterator(); iter.hasNext();)
         addSeats((Seat)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newSeat */
   public void addSeats(Seat newSeat) {
      if (newSeat == null)
         return;
      if (this.seats == null)
         this.seats = new java.util.HashSet<Seat>();
      if (!this.seats.contains(newSeat))
      {
         this.seats.add(newSeat);
         newSeat.setFlight(this);      
      }
   }
   
   /** @pdGenerated default remove
     * @param oldSeat */
   public void removeSeats(Seat oldSeat) {
      if (oldSeat == null)
         return;
      if (this.seats != null)
         if (this.seats.contains(oldSeat))
         {
            this.seats.remove(oldSeat);
            oldSeat.setFlight((Flight)null);
         }
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllSeats() {
      if (seats != null)
      {
         Seat oldSeat;
         for (java.util.Iterator iter = getIteratorSeats(); iter.hasNext();)
         {
            oldSeat = (Seat)iter.next();
            iter.remove();
            oldSeat.setFlight((Flight)null);
         }
      }
   }
   /** @pdGenerated default parent getter */
   public Airline getAirline() {
      return airline;
   }
   
   /** @pdGenerated default parent setter
     * @param newAirline */
   public void setAirline(Airline newAirline) {
      if (this.airline == null || !this.airline.equals(newAirline))
      {
         if (this.airline != null)
         {
            Airline oldAirline = this.airline;
            this.airline = null;
            oldAirline.removeFlights(this);
         }
         if (newAirline != null)
         {
            this.airline = newAirline;
            this.airline.addFlights(this);
         }
      }
   }

}