package info.hccis.islandartstore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import info.hccis.islandartstore.api.ApiWatcher;
import info.hccis.islandartstore.broadcast.receiver.AirplanceModeChangeReceiver;
import info.hccis.islandartstore.broadcast.receiver.ArtOrderBroadcastReceiver;
import info.hccis.islandartstore.databinding.ActivityMainBinding;
import info.hccis.islandartstore.entity.ArtOrderContent;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private static ApiWatcher apiWatcher;

    CarouselView carouselView;

    int[] sampleImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_carousel);
//
//        carouselView = (CarouselView) findViewById(R.id.carouselView);
//        carouselView.setPageCount(sampleImages.length);
//
//        carouselView.setImageListener(imageListener);

        //*******************************************************************************************
        // Register receivers.
        //*******************************************************************************************
        MainActivity.this.registerReceiver(new AirplanceModeChangeReceiver(), new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED));
        MainActivity.this.registerReceiver(new ArtOrderBroadcastReceiver(), new IntentFilter("info.hccis.phall.order"));

        apiWatcher = new ApiWatcher();
        apiWatcher.setActivity(this);
        apiWatcher.start();  //Start the background thread


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Creating carousel

//        class SampleCarouselViewActivity extends AppCompatActivity {




//            @Override
//            protected void onCreate(Bundle savedInstanceState) {
                //super.onCreate(savedInstanceState);
                //super.onCreate(savedInstanceState);


//            }



//        }


        //***********************************************************************************
        //Setup the myAppDatabase
        //Once this is set in the TicketOrderContent class it can be used throughout the app
        //***********************************************************************************
        ArtOrderContent.setMyAppDatabase(getApplicationContext());


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        //**************************************************************************
        // Review the top level destinations
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //**************************************************************************
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_art_orders, R.id.nav_social, R.id.nav_service, R.id.nav_qrcode)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.d("MainActivity BJM", "Option selected Settings");
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.nav_settings);
            //**************************************************************************************
            //todo 20230210 Review that the navController as setup above will send the user to a
            //non top level destination.  Note that this syntax does not work to send the user to
            //a top level destination and causes issues if used for top level / non top level navigation.
            //***************************************************************************************
            return true;
        }
//        } else if (id == R.id.action_about) {
//            Log.d("MainActivity BJM", "Option selected About");
//            Intent intent = new Intent(this, AboutActivity.class);
//            startActivity(intent);
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}