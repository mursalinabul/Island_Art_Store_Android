package info.hccis.islandartstore.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ArtOrderService extends Service {
    private static final String TAG = "MyService";
    private boolean isRunning = true;
    private String message;
    private Thread thread;
    private DownloadBinder mBinder = new DownloadBinder();

    //get download binder
    public class DownloadBinder extends Binder {
        public void startDownload() {
            Log.d(TAG, "startDownload executed");
        }

        public int getProgress() {
            Log.d(TAG, "getProgress executed");
            return 0;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind:");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate:");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand:");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        Log.d(TAG, "onDestroy:");
        stopForeground(true);
        super.onDestroy();
    }
}
