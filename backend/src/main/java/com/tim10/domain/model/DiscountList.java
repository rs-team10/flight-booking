/***********************************************************************
 * Module:  DiscountList.java
 * Author:  Filip
 * Purpose: Defines the Class DiscountList
 ***********************************************************************/

import java.util.*;

public class DiscountList {
   private Long id;
   
   private java.util.Collection<DiscountListItem> discountListItem;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<DiscountListItem> getDiscountListItem() {
      if (discountListItem == null)
         discountListItem = new java.util.HashSet<DiscountListItem>();
      return discountListItem;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorDiscountListItem() {
      if (discountListItem == null)
         discountListItem = new java.util.HashSet<DiscountListItem>();
      return discountListItem.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newDiscountListItem */
   public void setDiscountListItem(java.util.Collection<DiscountListItem> newDiscountListItem) {
      removeAllDiscountListItem();
      for (java.util.Iterator iter = newDiscountListItem.iterator(); iter.hasNext();)
         addDiscountListItem((DiscountListItem)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newDiscountListItem */
   public void addDiscountListItem(DiscountListItem newDiscountListItem) {
      if (newDiscountListItem == null)
         return;
      if (this.discountListItem == null)
         this.discountListItem = new java.util.HashSet<DiscountListItem>();
      if (!this.discountListItem.contains(newDiscountListItem))
         this.discountListItem.add(newDiscountListItem);
   }
   
   /** @pdGenerated default remove
     * @param oldDiscountListItem */
   public void removeDiscountListItem(DiscountListItem oldDiscountListItem) {
      if (oldDiscountListItem == null)
         return;
      if (this.discountListItem != null)
         if (this.discountListItem.contains(oldDiscountListItem))
            this.discountListItem.remove(oldDiscountListItem);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllDiscountListItem() {
      if (discountListItem != null)
         discountListItem.clear();
   }

}