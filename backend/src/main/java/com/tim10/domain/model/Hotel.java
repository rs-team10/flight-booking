/***********************************************************************
 * Module:  Hotel.java
 * Author:  Filip
 * Purpose: Defines the Class Hotel
 ***********************************************************************/

import java.util.*;

public class Hotel extends Company {
   private java.util.Collection<Room> rooms;
   private java.util.Collection<RoomType> roomTypes;
   private PriceList additionalServicesPriceList;
   private java.util.Collection<QuickRoomReservation> quickRoomReservations;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<Room> getRooms() {
      if (rooms == null)
         rooms = new java.util.HashSet<Room>();
      return rooms;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorRooms() {
      if (rooms == null)
         rooms = new java.util.HashSet<Room>();
      return rooms.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newRooms */
   public void setRooms(java.util.Collection<Room> newRooms) {
      removeAllRooms();
      for (java.util.Iterator iter = newRooms.iterator(); iter.hasNext();)
         addRooms((Room)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newRoom */
   public void addRooms(Room newRoom) {
      if (newRoom == null)
         return;
      if (this.rooms == null)
         this.rooms = new java.util.HashSet<Room>();
      if (!this.rooms.contains(newRoom))
         this.rooms.add(newRoom);
   }
   
   /** @pdGenerated default remove
     * @param oldRoom */
   public void removeRooms(Room oldRoom) {
      if (oldRoom == null)
         return;
      if (this.rooms != null)
         if (this.rooms.contains(oldRoom))
            this.rooms.remove(oldRoom);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllRooms() {
      if (rooms != null)
         rooms.clear();
   }
   /** @pdGenerated default getter */
   public java.util.Collection<RoomType> getRoomTypes() {
      if (roomTypes == null)
         roomTypes = new java.util.HashSet<RoomType>();
      return roomTypes;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorRoomTypes() {
      if (roomTypes == null)
         roomTypes = new java.util.HashSet<RoomType>();
      return roomTypes.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newRoomTypes */
   public void setRoomTypes(java.util.Collection<RoomType> newRoomTypes) {
      removeAllRoomTypes();
      for (java.util.Iterator iter = newRoomTypes.iterator(); iter.hasNext();)
         addRoomTypes((RoomType)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newRoomType */
   public void addRoomTypes(RoomType newRoomType) {
      if (newRoomType == null)
         return;
      if (this.roomTypes == null)
         this.roomTypes = new java.util.HashSet<RoomType>();
      if (!this.roomTypes.contains(newRoomType))
         this.roomTypes.add(newRoomType);
   }
   
   /** @pdGenerated default remove
     * @param oldRoomType */
   public void removeRoomTypes(RoomType oldRoomType) {
      if (oldRoomType == null)
         return;
      if (this.roomTypes != null)
         if (this.roomTypes.contains(oldRoomType))
            this.roomTypes.remove(oldRoomType);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllRoomTypes() {
      if (roomTypes != null)
         roomTypes.clear();
   }
   /** @pdGenerated default getter */
   public java.util.Collection<QuickRoomReservation> getQuickRoomReservations() {
      if (quickRoomReservations == null)
         quickRoomReservations = new java.util.HashSet<QuickRoomReservation>();
      return quickRoomReservations;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorQuickRoomReservations() {
      if (quickRoomReservations == null)
         quickRoomReservations = new java.util.HashSet<QuickRoomReservation>();
      return quickRoomReservations.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newQuickRoomReservations */
   public void setQuickRoomReservations(java.util.Collection<QuickRoomReservation> newQuickRoomReservations) {
      removeAllQuickRoomReservations();
      for (java.util.Iterator iter = newQuickRoomReservations.iterator(); iter.hasNext();)
         addQuickRoomReservations((QuickRoomReservation)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newQuickRoomReservation */
   public void addQuickRoomReservations(QuickRoomReservation newQuickRoomReservation) {
      if (newQuickRoomReservation == null)
         return;
      if (this.quickRoomReservations == null)
         this.quickRoomReservations = new java.util.HashSet<QuickRoomReservation>();
      if (!this.quickRoomReservations.contains(newQuickRoomReservation))
         this.quickRoomReservations.add(newQuickRoomReservation);
   }
   
   /** @pdGenerated default remove
     * @param oldQuickRoomReservation */
   public void removeQuickRoomReservations(QuickRoomReservation oldQuickRoomReservation) {
      if (oldQuickRoomReservation == null)
         return;
      if (this.quickRoomReservations != null)
         if (this.quickRoomReservations.contains(oldQuickRoomReservation))
            this.quickRoomReservations.remove(oldQuickRoomReservation);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllQuickRoomReservations() {
      if (quickRoomReservations != null)
         quickRoomReservations.clear();
   }
   
   public java.util.Collection<HotelAdmin> administrators;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<HotelAdmin> getAdministrators() {
      if (administrators == null)
         administrators = new java.util.HashSet<HotelAdmin>();
      return administrators;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorAdministrators() {
      if (administrators == null)
         administrators = new java.util.HashSet<HotelAdmin>();
      return administrators.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newAdministrators */
   public void setAdministrators(java.util.Collection<HotelAdmin> newAdministrators) {
      removeAllAdministrators();
      for (java.util.Iterator iter = newAdministrators.iterator(); iter.hasNext();)
         addAdministrators((HotelAdmin)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newHotelAdmin */
   public void addAdministrators(HotelAdmin newHotelAdmin) {
      if (newHotelAdmin == null)
         return;
      if (this.administrators == null)
         this.administrators = new java.util.HashSet<HotelAdmin>();
      if (!this.administrators.contains(newHotelAdmin))
      {
         this.administrators.add(newHotelAdmin);
         newHotelAdmin.setCompany(this);      
      }
   }
   
   /** @pdGenerated default remove
     * @param oldHotelAdmin */
   public void removeAdministrators(HotelAdmin oldHotelAdmin) {
      if (oldHotelAdmin == null)
         return;
      if (this.administrators != null)
         if (this.administrators.contains(oldHotelAdmin))
         {
            this.administrators.remove(oldHotelAdmin);
            oldHotelAdmin.setCompany((Hotel)null);
         }
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllAdministrators() {
      if (administrators != null)
      {
         HotelAdmin oldHotelAdmin;
         for (java.util.Iterator iter = getIteratorAdministrators(); iter.hasNext();)
         {
            oldHotelAdmin = (HotelAdmin)iter.next();
            iter.remove();
            oldHotelAdmin.setCompany((Hotel)null);
         }
      }
   }

}