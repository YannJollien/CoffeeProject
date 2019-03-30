package com.example.coffeeproject2;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.coffeeproject2.ui.storage.StorageAddActivity;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

//QR scanner using ZXing
public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    static String data;
    //Parameters for scanner
    private static ZXingScannerView ScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(ScanActivity.this, "Permission is granted!", Toast.LENGTH_LONG).show();
            } else {
                requestPermissions();
            }
        }

    }

    //Getting permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(ScanActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    //Check if SDK is ok, giving permission to use camera or not
    public void onRequestPermissionsResult(int requestCode, String permission[], int grantResult[]) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResult.length > 0) {
                    boolean cameraAccepted = grantResult[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        Toast.makeText(ScanActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ScanActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                displayAlertMessage("You need to allow access for both permissions", new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        ActivityCompat.requestPermissions(ScanActivity.this, new String[]{CAMERA}, REQUEST_CAMERA);
                                    }
                                });
                                return;
                            }

                        }
                    }
                }
                break;

        }
    }

    public void displayAlertMessage(String message, DialogInterface.OnCancelListener listener) {
        new AlertDialog.Builder(ScanActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", (DialogInterface.OnClickListener) listener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    //Putting data from Scanner in add activity
    @Override
    public void handleResult(Result result) {

        data = result.toString();
        String array[] = data.split(",");
        if (array[0].equals("Arabica")) {
            StorageAddActivity.spinner.setSelection(0);
        } else if (array[0].equals("Robusta")) {
            StorageAddActivity.spinner.setSelection(1);
        } else if (array[0].equals("Liberica")) {
            StorageAddActivity.spinner.setSelection(2);
        }
        StorageAddActivity.amountEdit.setText(array[1]);
        StorageAddActivity.dateEdit.setText(array[2]);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        ScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if (ScannerView == null) {
                    ScannerView = new ZXingScannerView(this);
                    setContentView(ScannerView);
                }
                ScannerView.setResultHandler(this);
                ScannerView.startCamera();
            } else {
                requestPermissions();
            }

        }
    }

}
