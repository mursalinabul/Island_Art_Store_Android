package info.hccis.islandartstore.ui.artorders;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import info.hccis.islandartstore.MainActivity;
import info.hccis.islandartstore.R;
import info.hccis.islandartstore.adapter.CustomAdapterArtOrder;
import info.hccis.islandartstore.databinding.FragmentArtOrdersBinding;
import info.hccis.islandartstore.entity.ArtOrder;
import info.hccis.islandartstore.ui.artorder.ArtOrderViewModel;
import info.hccis.islandartstore.util.NotificationApplication;
import info.hccis.islandartstore.util.NotificationUtil;

public class ArtOrdersFragment extends Fragment  implements CustomAdapterArtOrder.ArtChosenListener{
    private static Context context;
    private List<ArtOrder> artOrderArrayList;
    private ArtOrderViewModel artOrderViewModel;

    private FragmentArtOrdersBinding binding;

    private static RecyclerView recyclerView;

    /**
     * This method will tell the recyclerview that the data has changed.  This will trigger the
     * data to be reloaed on the ui.
     * @param message
     * @since 20230209
     * @author BJM
     */
    public static void notifyDataChanged(String message) {
        Log.d("bjm", "Data changed:  " + message);
        //Send a notification that the data has changed.
        try {
            recyclerView.getAdapter().notifyDataSetChanged();
        } catch (Exception e) {
            Log.d("bjm api", "Exception when trying to notify that the data set as changed");
        }
    }

    /**
     * Provide notification tha the data has changed.  This method will notify the adapter that the
     * rows have changed so it will know to refresh.  It will also send a notification to the user which
     * will allow them to go directly back to the list from another activity of the app.
     *
     * @param message          Message to display
     * @param activity         - originating activity
     * @param destinationClass - class associated with the intent associated with the notification.
     */
    public static void notifyDataChanged(String message, Activity activity, Class destinationClass) {
        Log.d("bjm", "Data changed:  " + message);
        try {
            notifyDataChanged(message);
            NotificationApplication.setContext(context);
            NotificationUtil.sendNotification("ArtOrder Data Update", message, activity, MainActivity.class);
        } catch (Exception e) {
            Log.d("bjm notification", "Exception occured when notifying. " + e.getMessage());
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentArtOrdersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ArtOrdersFragment artOrdersFragment = ArtOrdersFragment.this;

        //************************************************************************************
        // Set the view model.  This can be used to share data between fragments.
        // Corresponding to the add fragment, the view model is accessed to obtain a reference
        // to the list of ticket order bo objects.
        //************************************************************************************

        artOrderViewModel = new ViewModelProvider(getActivity()).get(ArtOrderViewModel.class);

        //************************************************************************************
        //NO LONGER USED BUT LEFT AS EXAMPLE
        //Bundle is accessed to get the ticket order which is passed from the add order fragment.
        //************************************************************************************
        //        Bundle bundle = getArguments(); //Note not doing anything...here for example
        //        TicketOrder ticketOrder = (TicketOrder) bundle.getSerializable(AddOrderFragment.KEY);
        //        Log.d("ViewOrdersFragment BJM", "Ticket passed in:  " + ticketOrder.toString());

        //************************************************************************************
        //Build the output to be displayed in the textview
        // NOTE:  This output string is no longer displayed since we added the RecyclerView
        //************************************************************************************

//        String output = "";
//        double total = 0;
//        for (TicketOrder order : ticketOrderViewModel.getTicketOrders()) {
//            output += order.toString() + System.lineSeparator();
//            total += order.calculateTicketPrice();
//        }
//        output += System.lineSeparator() + "Total: $" + total;

        //************************************************************************************
        // Set the context to be used when sending notifications
        //************************************************************************************

        context = getView().getContext();

        //************************************************************************************
        // Setup the recycler view for displaying the items in the ticket order list.
        //************************************************************************************

        recyclerView = binding.recyclerView;
        artOrderArrayList = artOrderViewModel.getArtOrders();
        setAdapter();

        //************************************************************************************
        // Button sends the user back to the add fragment
        //************************************************************************************

        binding.buttonAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Clear the current ticket order
                artOrderViewModel.setCurrentArtOrder(new ArtOrder());
                NavHostFragment.findNavController(ArtOrdersFragment.this)
                        .navigate(R.id.nav_art_order);

                Bundle result = new Bundle();
                result.putString("bundleKey", String.valueOf(artOrderArrayList.size()));
                getParentFragmentManager().setFragmentResult("qrCoding", result);
            }
        });


    }


    /**
     * Set the adapter for the recyclerview
     *
     * @author BJM
     * @since 20220129
     */
    private void setAdapter() {
        CustomAdapterArtOrder adapter = new CustomAdapterArtOrder(artOrderArrayList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onArtOrderClick(ArtOrder artOrder) {
        Log.d("bjtest","CLICKED" + artOrder.toString());
        artOrderViewModel.setCurrentArtOrder(artOrder);
        NavHostFragment.findNavController(ArtOrdersFragment.this)
                .navigate(R.id.nav_art_order);
    }


}