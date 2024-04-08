package com.example.barcodescanner;
import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.Surface;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    private TextView resultTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        resultTextView = findViewById(R.id.result_text);

        Button scanButton = findViewById(R.id.scan_button);
        scanButton.setOnClickListener(v -> {
            // Initialize the IntentIntegrator to start the QR scan
            IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
            integrator.setCaptureActivity(CustomScanner.class);
            integrator.setPrompt("Scan a barcode or QRcode");
            integrator.setBeepEnabled(true);
            integrator.setOrientationLocked(true);
            integrator.initiateScan();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                // If the result is null, set the text to "Cancelled"
                resultTextView.setText("Cancelled");
            } else {
                // If the result is not null, set the text to the scanned result
                resultTextView.setText("Scanned: " + result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}