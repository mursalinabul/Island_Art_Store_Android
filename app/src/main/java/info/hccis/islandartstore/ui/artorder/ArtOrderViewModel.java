package info.hccis.islandartstore.ui.artorder;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import info.hccis.islandartstore.entity.ArtOrder;

public class ArtOrderViewModel extends ViewModel {

    private ArrayList<ArtOrder> artOrders = new ArrayList();
    private ArtOrder currentArtOrder = new ArtOrder();

    public ArrayList<ArtOrder> getArtOrders() {
        return artOrders;
    }

    public ArtOrder getCurrentArtOrder(){
        return currentArtOrder;
    }

    public void setArtOrders(List<ArtOrder> artOrders) {
        this.artOrders.clear();
        this.artOrders.addAll(artOrders);
    }

    public void setCurrentArtOrder(ArtOrder artOrder){
        this.currentArtOrder = artOrder;
    }
}
