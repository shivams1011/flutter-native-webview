package com.ashank.nativeflutterwebview;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

/**
 * Created by ashankbharati on 01,October,2020
 */
public class FlutterWebViewInterface extends Activity {
    Context context;
    private static final int REQUEST = 112;

    public FlutterWebViewInterface(Context c) {
        context = c;
    }
    //this method is called by flutter web before initialisation
    @JavascriptInterface
    public String getString() {
        return "Hello from Native"; 
    }


    @JavascriptInterface
    public boolean askContactPermission(){
        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {android.Manifest.permission.READ_CONTACTS};
            if (!hasPermissions(context, PERMISSIONS)) {
                ActivityCompat.requestPermissions((Activity) context, PERMISSIONS, REQUEST );
                return false;
            } else {
//                readContacts();
                return true;
            }
        } else {
//            readContacts();
            return true;
        }
    }

    @JavascriptInterface
    public void loadContacts(){

    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    readContacts();
                } else {
                    Toast.makeText(context, "The app was not allowed to read your contact", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
