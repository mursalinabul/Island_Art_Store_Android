package info.hccis.islandartstore.util;

import java.util.ArrayList;

import info.hccis.islandartstore.entity.ArtOrder;
import info.hccis.islandartstore.ui.artorder.ArtOrderViewModel;

public class CisUtility {

    public static void initialize(ArtOrderViewModel artOrderViewModel){

        ArrayList<ArtOrder> theList = artOrderViewModel.getArtOrders();
        if (theList.isEmpty()) {
            ArtOrder artOrder1 = new ArtOrder();
            artOrder1.setNumberOfPaintings(1);
            artOrder1.setDateOfOrder("2023-01-02");
            //artOrder1.setDateOfPerformance("2023-01-02");
            artOrder1.setCustomerName("Joe Smith");
            //artOrder1.setTimeOfPerformance("19:00");
            artOrder1.setCostPerPainting(200);
            artOrder1.getCostOfOrders();
            theList.add(artOrder1);

//            TicketOrder ticketOrder2 = new TicketOrder();
//            ticketOrder2.setNumberOfTickets(10);
//            ticketOrder2.setDateOfOrder("2023-01-27");
//            ticketOrder2.setDateOfPerformance("2023-01-27");
//            ticketOrder2.setCustomerName("Ryan Smith");
//            ticketOrder2.setTimeOfPerformance("20:00");
//            ticketOrder2.setHollpassNumber(13000);
//            ticketOrder2.calculateTicketPrice();
//            theList.add(ticketOrder2);

        }




    }


}

