package info.hccis.islandartstore.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import info.hccis.islandartstore.R;
import info.hccis.islandartstore.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Switch switch_button = binding.switchLoadCustomersFromRoom;

        //******************************************************************************************
        // Using the default shared preferences.  Using the application context - may want to access the
        // shared prefs from other activities.
        //******************************************************************************************

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        boolean loadFromRoom = sharedPref.getBoolean(getString(R.string.preference_load_from_room), true);
        switch_button.setChecked(loadFromRoom);

        //*********************************************************************************
        // Set a checked change listener for switch button
        //*********************************************************************************

        switch_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // If the switch button is on
                    // Show the switch button checked status as toast message
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Will load from local database if no network", Toast.LENGTH_LONG).show();

                } else {
                    // If the switch button is off
                    // Show the switch button checked status as toast message
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Will NOT load from local database if no network", Toast.LENGTH_LONG).show();
                }
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(getString(R.string.preference_load_from_room), isChecked);
                editor.commit();

            }
        });





        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}