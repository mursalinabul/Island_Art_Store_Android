package info.hccis.islandartstore.ui.service;

import static android.content.Context.BIND_AUTO_CREATE;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import info.hccis.islandartstore.databinding.FragmentServiceBinding;
import info.hccis.islandartstore.service.ArtOrderService;

public class ServiceFragment extends Fragment {

    private FragmentServiceBinding binding;
    private ArtOrderService.DownloadBinder downloadBinder;

    private ServiceConnection connection=new ServiceConnection() {
        //override the serviceConnection method
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder=(ArtOrderService.DownloadBinder)service;
            //get method from the service method
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentServiceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        TextView tv_view=binding.tvView;
        Button btn_startButton = binding.btnStartButton;
        btn_startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ArtOrderService.class);
                tv_view.setText("Start the Service");
                getActivity().startService(intent);

            }
        });


        Button btn_StopButton = binding.btnStopButton;
        btn_StopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ArtOrderService.class);
                tv_view.setText("Stop the Service");
                getActivity().stopService(intent);
            }
        });

        Button btn_BindButton = binding.btnBindButton;
        btn_BindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bindIntent = new Intent(getActivity(), ArtOrderService.class);
                getActivity().bindService(bindIntent, connection, BIND_AUTO_CREATE);
                tv_view.setText("Bind the Service");
            }
        });

        Button btn_UnBindButton = binding.btnUnBlindButton;
        btn_UnBindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().unbindService(connection);
                tv_view.setText("UnBind the Service");
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






