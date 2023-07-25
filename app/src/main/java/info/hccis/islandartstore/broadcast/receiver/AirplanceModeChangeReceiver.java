package info.hccis.islandartstore.broadcast.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

public class AirplanceModeChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BJM Broadcast Receiver","airplane mode change broadcast received.");

        //Can we detect if currently in airplane mode??

        Log.d("BJM checking airplane mode","-->"+Settings.System.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0));



    }
}
