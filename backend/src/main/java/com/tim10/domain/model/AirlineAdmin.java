/***********************************************************************
 * Module:  AirlineAdmin.java
 * Author:  Filip
 * Purpose: Defines the Class AirlineAdmin
 ***********************************************************************/

import java.util.*;

public class AirlineAdmin extends User {
   private Boolean hasCustomPassword;
   
   private Airline company;
   
   
   /** @pdGenerated default parent getter */
   public Airline getCompany() {
      return company;
   }
   
   /** @pdGenerated default parent setter
     * @param newAirline */
   public void setCompany(Airline newAirline) {
      if (this.company == null || !this.company.equals(newAirline))
      {
         if (this.company != null)
         {
            Airline oldAirline = this.company;
            this.company = null;
            oldAirline.removeAdministrators(this);
         }
         if (newAirline != null)
         {
            this.company = newAirline;
            this.company.addAdministrators(this);
         }
      }
   }

}