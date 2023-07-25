package info.hccis.islandartstore.broadcast.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ArtOrderBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "BJM BroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        StringBuilder sb = new StringBuilder();
        sb.append("Action: " + intent.getAction() + "\n");
        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
        String log = sb.toString();
        Log.d("BJM Broadcast Received", "ticket order broadcast was received.");

//        ActivityNameBinding binding =
//                ActivityNameBinding.inflate(layoutInflater);
//        val view = binding.root;
//        setContentView(view);
//
//        Snackbar.make(view, log, Snackbar.LENGTH_LONG).show();
    }
}
