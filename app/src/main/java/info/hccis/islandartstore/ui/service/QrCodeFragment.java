package info.hccis.islandartstore.ui.service;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;


import com.google.zxing.WriterException;




import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import info.hccis.islandartstore.databinding.FragmentQrcodeBinding;



public class QrCodeFragment extends Fragment {



    private FragmentQrcodeBinding binding;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentQrcodeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ImageView qrcode=binding.qrcode;
        Button bttnGenerate = binding.bttnGenerate;

        getParentFragmentManager().setFragmentResultListener("qrCoding", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String qrCoding, @NonNull Bundle bundle){
                // We use a String here, but any type that can be put in a Bundle is supported
                String result=bundle.getString("bundleKey");
//            }
//        });


        bttnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String toQrCode = result;



                int width = 300;
                int height = 450;

                // generating dimension from width and height.
                int dimen = Math.min(width, height);
                dimen = dimen * 3 / 4;

                    QRGEncoder qrgEncoder = new QRGEncoder(toQrCode, null,QRGContents.Type.TEXT, dimen);

                try {

                    //initalize bitmap

                  Bitmap bitmap = qrgEncoder.encodeAsBitmap();

                    //set bitmap on imageview

                    qrcode.setImageBitmap(bitmap);



                } catch (WriterException e) {
                    Log.e("MUR", e.toString());
                }


            }
        });
            }
        });
        return root;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("qrCoding", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String qrCoding, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                String result = bundle.getString("bundleKey");
                //result = String.valueOf(artOrderArrayList.size());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    }







