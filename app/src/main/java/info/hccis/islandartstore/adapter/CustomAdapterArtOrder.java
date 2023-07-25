package info.hccis.islandartstore.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import info.hccis.islandartstore.entity.ArtOrder;
import info.hccis.islandartstore.R;


public class CustomAdapterArtOrder extends RecyclerView.Adapter<CustomAdapterArtOrder.ArtOrderViewHolder> {

    private List<ArtOrder> artOrderArrayList;
    private ArtChosenListener artOrderListener;

    public CustomAdapterArtOrder(List<ArtOrder> artOrderBOArrayList, ArtChosenListener listener) {
        this.artOrderArrayList = artOrderBOArrayList;
        this.artOrderListener = listener;
    }

    @NonNull
    @Override
    public ArtOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_order, parent, false);
        return new ArtOrderViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull ArtOrderViewHolder holder, int position) {

        String customerInitials = "" + artOrderArrayList.get(position).getCustomerInitials();
        String numberOfPaintings = "" + artOrderArrayList.get(position).getNumberOfPaintings();
        String dateOfOrder = "" + artOrderArrayList.get(position).getDateOfOrder();
        String paintingName = "" + artOrderArrayList.get(position).getPaintingName();
        String customerAddress = "" + artOrderArrayList.get(position).getAddressCustomer();
        String costPerPainting = "" + artOrderArrayList.get(position).getCostPerPainting();
        String cost = "" + artOrderArrayList.get(position).getCostOfOrders();
        holder.textViewNameIcon.setText(customerInitials);
        holder.textViewNumberOfPaintings.setText(numberOfPaintings);
        holder.textViewDateOfOrder.setText(dateOfOrder);
        holder.textViewPaintingName.setText(paintingName);
        holder.textViewCustomerAddress.setText(customerAddress);
        holder.textViewCostPerPainting.setText(costPerPainting);
        holder.textViewCost.setText(cost);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("bjtest", "row was clicked");
                artOrderListener.onArtOrderClick(artOrderArrayList.get(holder.getAdapterPosition()));
            }
        });
    }


    @Override
    public int getItemCount() {
        return artOrderArrayList.size();
    }






    public class ArtOrderViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewNameIcon;
        private TextView textViewNumberOfPaintings;

        private TextView textViewDateOfOrder;

        private TextView textViewPaintingName;

        private TextView textViewCustomerAddress;
        private TextView textViewCostPerPainting;
        private TextView textViewCost;

        public ArtOrderViewHolder(final View view) {
            super(view);
            textViewNameIcon = view.findViewById(R.id.textViewNameIcon);
            textViewNumberOfPaintings = view.findViewById(R.id.textViewNumberOfPaintingsEntered);
            textViewDateOfOrder = view.findViewById(R.id.textViewDateOfOrderEntered);
            textViewPaintingName = view.findViewById(R.id.textViewPaintingNameEntered);
            textViewCustomerAddress = view.findViewById(R.id.textViewCustomerAddressEntered);
            textViewCostPerPainting = view.findViewById(R.id.textViewCostPerPaintingEntered);
            textViewCost = view.findViewById(R.id.textViewCostOfOrdersEntered);
        }
    }

    /**
     * https://community.codenewbie.org/theplebdev/adding-onclicklistener-to-recyclerview-in-android-1bdl
     * This interface will be implemented by the Fragment class to allow navigation to the detail
     * fragment when the row is clicked.
     *
     * @author bjmaclean
     * @since 20220628
     */
    public interface ArtChosenListener {
        void onArtOrderClick(ArtOrder artOrder);
    }
}
