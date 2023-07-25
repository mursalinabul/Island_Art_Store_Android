package info.hccis.islandartstore.ui.social;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.google.android.material.snackbar.Snackbar;

import info.hccis.islandartstore.databinding.FragmentSocialBinding;

public class SocialFragment extends Fragment {

    private FragmentSocialBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        SlideshowViewModel slideshowViewModel =
//                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSocialBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        getParentFragmentManager().setFragmentResultListener("qrCoding", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String qrCoding, @NonNull Bundle bundle){
                // We use a String here, but any type that can be put in a Bundle is supported
                String result=bundle.getString("bundleKey");

        EditText shareMessage = binding.txtShare;
        Button btnShare = binding.bttnShare;
        Button btnTweet = binding.btnTweet;
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = shareMessage.getText().toString();
                //new TweetTask().execute(message);
                
                //https://www.digitalocean.com/community/tutorials/android-snackbar-example-tutorial
                Snackbar snackbar = Snackbar
                        .make(root, "Tweet sdk not implemented", Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        });


        btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // string variable for text
                //String message = shareMessage.getText().toString();
                String message = "Total order no: " + result;
                // the intent "ACTION_SEND" will be used to send the information from one activity to another
                Intent intent = new Intent(Intent.ACTION_SEND);
                // set the type of input you will be sending to the application
                intent.setType("text/plain");

                //startActivity(intent);
                intent.putExtra(Intent.EXTRA_TEXT, message);
//                This creates the dialogue menu pop up of the apps that the message can be shared using
                startActivity(intent.createChooser(intent, "Share"));


            }
        });
            }
        });

        return root;
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getParentFragmentManager().setFragmentResultListener("qrCoding", this, new FragmentResultListener() {
//            @Override
//            public void onFragmentResult(@NonNull String qrCoding, @NonNull Bundle bundle) {
//                // We use a String here, but any type that can be put in a Bundle is supported
//                String result = bundle.getString("bundleKey");
//                //result = String.valueOf(artOrderArrayList.size());
//            }
//        });
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}