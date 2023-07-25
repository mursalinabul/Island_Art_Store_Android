package info.hccis.islandartstore.ui.artorder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import info.hccis.islandartstore.R;
import info.hccis.islandartstore.databinding.FragmentArtOrderBinding;
import info.hccis.islandartstore.entity.ArtOrder;
import info.hccis.islandartstore.entity.ArtOrderRepository;
import info.hccis.islandartstore.ui.service.QrCodeFragment;
import info.hccis.islandartstore.util.ContentProviderUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtOrderFragment extends Fragment {

    private FragmentArtOrderBinding binding;

    public static final String KEY = "info.hccis.islandArtStore.ORDER";
    private ArtOrder artOrder;
    private ArtOrderViewModel artOrderViewModel;

    private String dateOfOrder = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentArtOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Log.d("AddOrderFragment BJM", "onViewCreated triggered");
        binding.circularProgressAddOrder.hide();

        artOrderViewModel = new ViewModelProvider(getActivity()).get(ArtOrderViewModel.class);
        artOrder = artOrderViewModel.getCurrentArtOrder();
        binding.editTextId.setText(""+artOrder.getId());
        binding.editTextCustomerName.setText(artOrder.getCustomerName());
        binding.editTextPaintingName.setText(artOrder.getPaintingName());
        binding.editTextCustomerAddress.setText(artOrder.getAddressCustomer());
        binding.editTextCostOfPainting.setText("" + artOrder.getCostPerPainting());
        binding.editTextNumberOfPaintings.setText("" + artOrder.getNumberOfPaintings());
        //binding.editTextDateOfOrder.setText(artOrder.getDateOfOrder());

        //******************************************************************************
        //calculate the long value of the order date.
        //******************************************************************************

        Date dateOfOrderDate = null;
        try {
            //https://www.javatpoint.com/java-string-to-date
            dateOfOrderDate = new SimpleDateFormat("yyyy-MM-dd").parse(artOrder.getDateOfOrder());
        } catch (Exception e) {
            Log.e("bjtest", "Could not parse the performance date");
            dateOfOrderDate = new Date();
            //Toast.makeText(getContext(), "There was an issue parsing the order date", Toast.LENGTH_SHORT).show();
        }
        binding.calendarViewDateOfOrder.setDate(dateOfOrderDate.getTime());

        binding.calendarViewDateOfOrder.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                dateOfOrder = year + "-" + (month+1) + "-" + dayOfMonth;
                Log.d("BJM date change?","Date of performance: "+dateOfOrder);
            }
        });


        //************************************************************************************
        // Add a listener on the button which submits the add art order.
        //************************************************************************************

        binding.buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show the progress indicator
                binding.circularProgressAddOrder.show();

                Log.d("AddOrderFragment BJM", "Calculate was clicked");

                try {
                    //************************************************************************************
                    // Call the calculate method
                    //************************************************************************************

                    try {
                        calculate();
                    } catch (Exception e) {
                        //************************************************************************************
                        // If there was an exception thrown in the calculate method, just put a message
                        // in the Logcat and leave the user on the add fragment.
                        //************************************************************************************
                        //Toast.makeText(getContext(), "" + R.string.messageMaxTickets + TicketOrder.MAX_TICKETS, Toast.LENGTH_SHORT).show();
                        Log.d("AddOrderFragment BJM", "Error calculating: " + e.getMessage());
                        throw e;
                    }

                    //************************************************************************************
                    // I have made th e TicketOrderBO implement Serializable.  This will allow us to
                    // serialize the object and then it can be passed in the bundle.  Note that the
                    // calculate method also sets the cost (Note that this is not important to the
                    // functionality of the app.
                    //************************************************************************************

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KEY, artOrder);

                    //************************************************************************************
                    // See the view model research component.  The ViewModel is associated with the Activity
                    // and the add and view fragments share the same activity.  Am using the view model
                    // to hold the list of TicketOrderBO objects.  This will allow both fragments to
                    // access the list.
                    //******************************************************************************

                    //******************************************************************************
                    // Handle the new ticket order
                    //******************************************************************************

                    //******************************************************************************
                    //The art order repository provides the Retrofit object which allows access to
                    //the api.
                    //******************************************************************************
                    ArtOrderRepository artOrderRepository = ArtOrderRepository.getInstance();

                    artOrderRepository.getArtOrderService().createArtOrder(artOrder).enqueue(new Callback<ArtOrder>() {
                        @Override
                        public void onResponse(Call<ArtOrder> call, Response<ArtOrder> r) {
                            //*************************************************
                            //Check the response. Give an appropriate message.
                            //*************************************************
                            if (r.code() == HttpURLConnection.HTTP_OK) {
                                Toast.makeText(getContext(), R.string.messageArtOrderCreated, Toast.LENGTH_LONG).show();

                                //******************************************************************************
                                //Create the event without using Calendar intent
                                //******************************************************************************

                                //todo Error when using the createEvent rather than intent
                                long eventID = ContentProviderUtil.createEvent(getActivity(), artOrder.toString());
                                Toast.makeText(getActivity(), "Calendar Event Created (" + eventID + ")", Toast.LENGTH_LONG);
                                Log.d("BJM Calendar", "Calendar Event Created (" + eventID + ")");

                                if(eventID < 0){
                                    Snackbar snackbar = Snackbar
                                            .make(root, "Could not create calendar event.  Permission enabled? (see Settings app)", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }

                                //Using an intent
                                //ContentProviderUtil.createCalendarEventIntent(getActivity(), ticketOrder);


                                //******************************************************************************
                                // Send a broadcast.
                                //******************************************************************************

                                //Send a broadcast.
                                LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(view.getContext());
                                Intent intent = new Intent();
                                intent.setAction("info.hccis.islandartstore.order");
                                lbm.sendBroadcast(intent);
                                getContext().sendBroadcast(intent);
                                Log.d("BJM Broadcast","Sent a broadcast");






                                //************************************************************************************
                                // Navigate to the view orders fragment.  Note that the navigation is defined in
                                // the nav_graph.xml file.
                                //************************************************************************************

                                NavHostFragment.findNavController(ArtOrderFragment.this)
                                        .navigate(R.id.nav_art_orders);


                            } else {
                                binding.circularProgressAddOrder.hide();
                                Toast.makeText(getContext(), R.string.messageArtOrderCreationError, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ArtOrder> call, Throwable t) {
                            Toast.makeText(getContext(), R.string.messageArtOrderCreationError + "(" + t.getMessage() + ")", Toast.LENGTH_SHORT).show();
                        }
                    });


                    artOrderViewModel.setCurrentArtOrder(artOrder);
                    Log.d("BJM", "added art order:" + artOrder.toString());
                    //clear(); //clear the form
                    binding.circularProgressAddOrder.hide();
                } catch (Exception e) {
                    //Toast.makeText(getContext(), "" + R.string.messageError, Toast.LENGTH_SHORT).show();
                    Log.d("Error", "Unexpected error occured:" + e.getMessage());
                    binding.circularProgressAddOrder.hide();
                }
            }
        });
        return root;

    }

    /**
     * A method which will clear the entries on the ui
     * @since 20230209
     * @author BJM
     * modified by Mursalin
     */
    public void clear() {
        binding.editTextId.setText("0");
        binding.editTextCustomerName.setText("");
        binding.editTextPaintingName.setText("");
        binding.editTextCustomerAddress.setText("");
        binding.editTextNumberOfPaintings.setText("");
        binding.editTextCostOfPainting.setText("");
    }

    /**
     * Process the the ticket cost based on the controls on the view.
     *
     * @throws Exception Throw exception if number of tickets entered caused an issue.
     * @author CIS2250
     * @since 20220118
     */
    public void calculate() throws Exception {

        Log.d("BJM-MainActivity", "Painting cost entered =" + binding.editTextCostOfPainting.getText().toString());
        Log.d("BJM-MainActivity", "Number of paintings = " + binding.editTextNumberOfPaintings.getText().toString());
        Log.d("BJM-MainActivity", "Calculate button was clicked.");

        //data to QR Code

        //String pCost =binding.editTextCostOfPainting.getText().toString();


//        getParentFragmentManager().setFragmentResultListener("qrCoding", this, new FragmentResultListener() {
//            @Override
//            public void onFragmentResult(@NonNull String qrCoding, @NonNull Bundle bundle) {
//                // We use a String here, but any type that can be put in a Bundle is supported
//                String result = bundle.getString("bundleKey");
//                result = binding.editTextCustomerName.getText().toString();
//            }
//        });



        double costOfPainting = 0;
        try {
            costOfPainting = Double.parseDouble(binding.editTextCostOfPainting.getText().toString());
        } catch (Exception e) {
            costOfPainting = 0;
        }
        //boolean validHollPassNumber;
        int numberOfPaintings;
        try {
            numberOfPaintings = Integer.parseInt(binding.editTextNumberOfPaintings.getText().toString());
        } catch (Exception e) {
            numberOfPaintings = 0;
        }

        if (dateOfOrder.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateOfOrder = sdf.format(new Date(binding.calendarViewDateOfOrder.getDate()));
            Log.d("Calendar date", dateOfOrder);

        }

        artOrder = new ArtOrder();
        artOrder.getCostOfOrders();

        if (binding.editTextId == null || binding.editTextId.getText() == null
                || binding.editTextId.getText().toString().isEmpty()
                || binding.editTextId.getText().toString().equals("null")) {
            artOrder.setId(0);
        } else {
            String temp = binding.editTextId.getText().toString();
            artOrder.setId(Integer.parseInt(temp));
        }
        artOrder.setCustomerName(binding.editTextCustomerName.getText().toString());
        artOrder.setAddressCustomer(binding.editTextCustomerAddress.getText().toString());
        artOrder.setPaintingName(binding.editTextPaintingName.getText().toString());
        artOrder.setCostPerPainting(costOfPainting);
        artOrder.setNumberOfPaintings(numberOfPaintings);
        artOrder.setDateOfOrder(dateOfOrder);

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}