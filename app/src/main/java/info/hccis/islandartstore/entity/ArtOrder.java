package info.hccis.islandartstore.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Class brought from the Performance Hall web application.  It also has some additional methods
 * related to Ticket Orders.  It's attributes correspond to the attributes which are found in
 * the database.
 *
 * @author bjmaclean
 * @since 20220202
 * modified by Mursalin
 */
@Entity(tableName = "artorder")
public class ArtOrder implements Serializable {

    //private static final long serialVersionUID = 1L;
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private String dateOfOrder;

    private String customerName;

    private String paintingName;

    private String addressCustomer;

    private int numberOfPaintings;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation

    private double costPerPainting;


    private double costOfOrders;

    @Ignore
    private String customerInitials;

    public double getCostOfOrders() {
        costOfOrders = numberOfPaintings * costPerPainting;
        return costOfOrders;
    }

    public void setCostOfOrders(double costOfOrders) {
        this.costOfOrders = costOfOrders;
    }


    public ArtOrder() {
    }

    @Ignore
    public ArtOrder(Integer id) {
        this.id = id;
    }

    @Ignore
    public ArtOrder(Integer id, String dateOfOrder, String customerName, String paintingName, String addressCustomer, int numberOfPaintings, double costPerPainting) {
        this.id = id;
        this.dateOfOrder = dateOfOrder;
        this.customerName = customerName;
        this.paintingName = paintingName;
        this.addressCustomer = addressCustomer;
        this.numberOfPaintings = numberOfPaintings;
        this.costPerPainting = costPerPainting;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(String dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerInitials() {
        int space = getCustomerName().indexOf(" ");
        try {
            customerInitials = customerName.substring(0, 1).toUpperCase() +
                    customerName.substring((space + 1), (space + 2)).toUpperCase();
        } catch (Exception e){
            customerInitials = "AA";
        }

        return customerInitials;
    }

    public String getPaintingName() {
        return paintingName;
    }

    public void setPaintingName(String paintingName) {
        this.paintingName = paintingName;
    }

    public String getAddressCustomer() {
        return addressCustomer;
    }

    public void setAddressCustomer(String addressCustomer) {
        this.addressCustomer = addressCustomer;
    }

    public int getNumberOfPaintings() {
        return numberOfPaintings;
    }

    public void setNumberOfPaintings(int numberOfPaintings) {
        this.numberOfPaintings = numberOfPaintings;
    }

    public double getCostPerPainting() {
        return costPerPainting;
    }

    public void setCostPerPainting(double costPerPainting) {
        this.costPerPainting = costPerPainting;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArtOrder)) {
            return false;
        }
        ArtOrder other = (ArtOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

   // @Override
//    public String toString() {
//        return "info.hccis.islandArtStore.jpa.entity.ArtOrder[ id=" + id + " ]";
//    }


    @Override
    public String toString() {
        return "Painting Order#"+id + System.lineSeparator()
                + "Name: "+customerName+System.lineSeparator()
                + "Painting Name: "+paintingName+System.lineSeparator()
                //+ "Performance Date " +dateOfPerformance+System.lineSeparator()
                //+ "Performance Time: "+timeOfPerformance+System.lineSeparator()
                + "Date of Order" +dateOfOrder+System.lineSeparator();
               // + "HollPass Number=" + hollpassNumber + System.lineSeparator()
                //+ "Number of Tickets=" + numberOfTickets + System.lineSeparator()
                //+ "Order cost: $" + costOfTickets + System.lineSeparator();
    }
}
