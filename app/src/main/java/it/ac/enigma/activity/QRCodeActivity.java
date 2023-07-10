package it.ac.enigma.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Size;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;


import com.android.volley.VolleyError;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.ExecutionException;

import it.ac.enigma.R;
import it.ac.enigma.adapter.SquadreAdapter;
import it.ac.enigma.model.ProvaDto;
import it.ac.enigma.qrcode.QRCodeFoundListener;
import it.ac.enigma.qrcode.QRCodeImageAnalyzer;
import it.ac.enigma.rest.ResponseQRCodeStep;
import it.ac.enigma.rest.RestManager;
import it.ac.enigma.utility.SharedPreferencesUtils;
import it.ac.enigma.utility.Utility;

public class QRCodeActivity extends AppCompatActivity {
    Context _context;
    RestManager restManager;

    private PreviewView previewView;
    private Spinner squadraSpinner;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    private static final int PERMISSION_REQUEST_CAMERA = 0;

    Boolean isRequestOn;


    ProvaDto _squadra;
    private TextView esitoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        restManager = new RestManager(this);
        _squadra = null;
        _context = this;
        isRequestOn = false;

        previewView = findViewById(R.id.activity_main_previewView);
        squadraSpinner = findViewById(R.id.squadra_spinner);

        aggiungiSquadreSpinner();

        esitoTextView = findViewById(R.id.qrcode_esito_text_view);


        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        requestCamera();

    }

    private void aggiungiSquadreSpinner() {

        List<ProvaDto> squadraDtoList = SharedPreferencesUtils.getSquadre(_context);
        squadraDtoList.add(0, new ProvaDto(0L, "SEL", "-- seleziona una squadra"));
        squadraSpinner.setAdapter(new SquadreAdapter(_context, squadraDtoList));

        squadraSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                _squadra = (ProvaDto) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void requestCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(QRCodeActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startCamera() {
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindCameraPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(this, "Error starting camera " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindCameraPreview(@NonNull ProcessCameraProvider cameraProvider) {
        previewView.setPreferredImplementationMode(PreviewView.ImplementationMode.SURFACE_VIEW);

        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(previewView.createSurfaceProvider());

        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new QRCodeImageAnalyzer(new QRCodeFoundListener() {
            @Override
            public void onQRCodeFound(String _qrCode) {

                if(!isRequestOn && _qrCode!= null && _qrCode.trim().length()!=0 &&_squadra!=null && !_squadra.getId().equals(0L)){
                    isRequestOn = true;
                    restManager.makeQrCodeStepRequest(_qrCode,_squadra.getId(), new RestManager.QRCodeStepCallback() {
                        @Override
                        public void onSuccess(ResponseQRCodeStep responseQRCodeStep) {

                            if(responseQRCodeStep==null){
                                esitoTextView.setText("OK! Step completato, CACCIA COMPLETATA!");

                            } else {
                                esitoTextView.setText("OK! Step completato, consegnare il prossimo step: " + responseQRCodeStep.getDescrizione());

                            }

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    isRequestOn = true;
                                }
                            }, 6000);
                        }
                        @Override
                        public void onError(VolleyError error) {
                            if (error.networkResponse != null && error.networkResponse.statusCode == 404) {
                                // Handle 404 error
                                // You can show an appropriate error message or perform any required actions
                                Utility.showToast(_context, "Il qrCode non corrisponde alla squadra!");
                            } else if (error.networkResponse != null && error.networkResponse.statusCode == 400){
                                Utility.showToast(_context, "Step errato! Verificare");
                            }

                            isRequestOn = false;
                        }
                    });

                } else if(isRequestOn) {

                } else{
                    Utility.showToast(_context, getString(R.string.scegli_squadra));
                }
            }

            @Override
            public void qrCodeNotFound() {
            }
        }));

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, imageAnalysis, preview);

    }

}
