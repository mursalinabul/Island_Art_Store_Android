package info.hccis.islandartstore.api;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import info.hccis.islandartstore.MainActivity;
import info.hccis.islandartstore.entity.ArtOrder;
import info.hccis.islandartstore.entity.ArtOrderContent;
import info.hccis.islandartstore.entity.ArtOrderRepository;
import info.hccis.islandartstore.ui.artorder.ArtOrderViewModel;
import info.hccis.islandartstore.ui.artorders.ArtOrdersFragment;
import info.hccis.islandartstore.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ApiWatcher class will be used as a background thread which will monitor the api. It will notify
 * the ui activity if the number of rows changes.
 *
 * @author BJM modified by Mariana Alkabalan 20210402 remodified by BJM 20220202
 * @since 20210329
 */

public class ApiWatcher extends Thread {

    //public static final String API_BASE_URL = "https://bjmac2.hccis.info/api/TicketOrderService/";
    public static final String API_BASE_URL = "http://10.0.2.2:8082/api/ArtOrderService/";

    private int lengthLastCall = -1;  //Number of rows returned

    //The activity is passed in to allow the runOnUIThread to be used.
    private FragmentActivity activity = null;

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        super.run();
        try {
            do {

                //************************************************************************
                // A lot of this code is duplicated from the TicketOrderContent class.  It will
                // access the api and if if notes that the number of orders has changed, will
                // notify the view order fragment that the data is changed.
                //************************************************************************

                Log.d("BJM api", "running");

//                //Use Retrofit to connect to the service
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl(ApiWatcher.API_BASE_URL)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//
//                JsonTicketOrderApi jsonTicketOrderApi = retrofit.create(JsonTicketOrderApi.class);

                //Create a list of ticket orders.
                Call<List<ArtOrder>> call = ArtOrderRepository.getInstance().getArtOrderService().getArtOrders();

                ArtOrderViewModel artOrderViewModel = new ViewModelProvider(activity).get(ArtOrderViewModel.class);

                call.enqueue(new Callback<List<ArtOrder>>() {

                    @Override
                    public void onResponse(Call<List<ArtOrder>> call, Response<List<ArtOrder>> response) {

                        //See if we can get the view model.  This contains the list of orders
                        //which is used to populate the recycler view on the list fragment.
                        Log.d("BJM api", "found art order view model. size=" + artOrderViewModel.getArtOrders().size());


                        if (!response.isSuccessful()) {
                            Log.d("BJM api", "BJM not successful response from rest for art orders Code=" + response.code());

                            //******************************************************************************************
                            // Using the default shared preferences.  Using the application context - may want to access the
                            // shared prefs from other activities.
                            //******************************************************************************************

                            loadFromRoomIfPreferred(artOrderViewModel);
                            lengthLastCall = -1; //Indicate couldn't load from api will trigger reload next time

                        } else {
                            //Take the list and pass to constructor of ArrayList to convert it.
                            ArrayList<ArtOrder> artOrdersTemp = new ArrayList(response.body()); //note gson will be used implicitly
                            int lengthThisCall = artOrdersTemp.size();

                            Log.d("BJM api", "back from api, size=" + lengthThisCall);


                            if (lengthLastCall == -1) {
                                //first time - don't notify
                                lengthLastCall = lengthThisCall;
                                //******************************************************************
                                //Note here.  The recycler view is using the ArrayList from this
                                //ticketOrderViewModel class.  We will take the ticket orders obtained
                                //from the api and add refresh the list .  Will then notify the
                                //recyclerview that things have changed.
                                //******************************************************************
                                artOrderViewModel.setArtOrders(artOrdersTemp); //Will addAll
                                Log.d("BJM api ", "First load of ticket orders from the api");

                                //**********************************************************************
                                // This method will allow a call to the runOnUiThread which will be allowed
                                // to interact with the ui components of the app.
                                //**********************************************************************
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("BJM api", "trying to notify adapter that things have changed");
                                        ArtOrdersFragment.notifyDataChanged("Found more rows");
//                                        Log.d("bjm api2", "trying to notify adapter that things have changed");
//                                        Log.d("bjm api3", "Also using method to send a notification to the user");
//                                        TicketOrdersFragment.notifyDataChanged("Update - the data has changed", activity, MainActivity.class);

                                    }
                                });

                                //******************************************************************
                                //Save latest orders in the database.
                                //******************************************************************
                                ArtOrderContent.reloadArtOrdersInRoom(artOrdersTemp);




                            } else if (lengthThisCall != lengthLastCall) {
                                //******************************************************************
                                //data has changed
                                //******************************************************************
                                Log.d("BJM api", "Data has changed");
                                lengthLastCall = lengthThisCall;
                                artOrderViewModel.getArtOrders().clear();
                                artOrderViewModel.getArtOrders().addAll(artOrdersTemp);

                                //**********************************************************************
                                // This method will allow a call to the runOnUiThread which will be allowed
                                // to interact with the ui components of the app.
                                //**********************************************************************
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("bjm api", "trying to notify adapter that things have changed");
                                        Log.d("bjm api", "Also using method to send a notification to the user");
                                        ArtOrdersFragment.notifyDataChanged("Update - the data has changed", activity, MainActivity.class);
                                    }
                                });

                                //******************************************************************
                                //Save latest orders in the database.
                                //******************************************************************
                                ArtOrderContent.reloadArtOrdersInRoom(artOrdersTemp);

                            } else {
                                //*******************************************************************
                                // Same number of rows so don't bother updating the list.
                                //*******************************************************************
                                Log.d("bjm api", "Data has not changed");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ArtOrder>> call, Throwable t) {

                        //**********************************************************************************
                        // If the api call failed, give a notification to the user.
                        //**********************************************************************************
                        Log.d("bjm api", "api call failed");
                        Log.d("bjm api", t.getMessage());

                        //******************************************************************************************
                        // Using the default shared preferences.  Using the application context - may want to access the
                        // shared prefs from other activities.
                        //******************************************************************************************

                        lengthLastCall = -1; //Indicate couldn't load from api will trigger reload next time
                        loadFromRoomIfPreferred(artOrderViewModel);

                    }
                });

                //***********************************************************************************
                // Sleep so not checking all the time
                //***********************************************************************************
                final int SLEEP_TIME = 10000;
                Log.d("BJM Sleep", "Sleeping for " + (SLEEP_TIME / 1000) + " seconds");
                Thread.sleep(SLEEP_TIME); //Check api every 10 seconds

            } while (true);
        } catch (InterruptedException e) {
            Log.d("BJM api", "Thread interrupted.  Stopping in the thread.");
        }
    }

    /**
     * Check the shared preferences and load from the db if the setting is set to do such a thing.
     * @param artOrderViewModel
     * @since 20220211
     * @author BJM
     */
    public void loadFromRoomIfPreferred(ArtOrderViewModel artOrderViewModel) {
        Log.d("BJM room","Check to see if should load from room");
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        boolean loadFromRoom = sharedPref.getBoolean(activity.getString(R.string.preference_load_from_room), true);
        Log.d("BJM room","Load from Room="+loadFromRoom);
        if (loadFromRoom) {
            List<ArtOrder> testList = ArtOrderContent.getArtOrdersFromRoom();
            Log.d("BJM room","Obtained art orders from the db: "+testList.size());
            artOrderViewModel.setArtOrders(testList); //Will add all ticket orders

            //**********************************************************************
            // This method will allow a call to the runOnUiThread which will be allowed
            // to interact with the ui components of the app.
            //**********************************************************************
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("bjm api", "trying to notify adapter that things have changed");
                    ArtOrdersFragment.notifyDataChanged("Found more rows");
                }

            });

        }
    }


}

