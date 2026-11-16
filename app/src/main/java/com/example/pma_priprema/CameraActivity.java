
package com.example.pma_priprema;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.net.Uri;
import android.provider.MediaStore;

import com.google.common.util.concurrent.ListenableFuture;

public class CameraActivity extends AppCompatActivity {

    private PreviewView previewView;
    private ImageCapture imageCapture;

    private ActivityResultLauncher<String> requestPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        pokreniKameru();
                    } else {
                        Toast.makeText(this,
                                "Dozvola za kameru nije odobrena",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
        );

        previewView = findViewById(R.id.previewView);
        Button buttonSlikaj = findViewById(R.id.buttonSlikaj);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            pokreniKameru();

        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }

        buttonSlikaj.setOnClickListener(v -> slikaj());
    }

    private void pokreniKameru() {

        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider =
                        cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();

                preview.setSurfaceProvider(
                        previewView.getSurfaceProvider()
                );

                imageCapture = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .build();

                CameraSelector cameraSelector =
                        CameraSelector.DEFAULT_BACK_CAMERA;

                cameraProvider.unbindAll();

                cameraProvider.bindToLifecycle(
                        this,
                        cameraSelector,
                        preview,
                        imageCapture
                );

            } catch (Exception e) {
                Toast.makeText(this,
                        "Greška pri pokretanju kamere",
                        Toast.LENGTH_SHORT).show();
            }

        }, ContextCompat.getMainExecutor(this));
    }

    private void slikaj() {

        if (imageCapture == null) {
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(
                MediaStore.MediaColumns.DISPLAY_NAME,
                "Fotografija_" + System.currentTimeMillis()
        );
        contentValues.put(
                MediaStore.MediaColumns.MIME_TYPE,
                "image/jpeg"
        );

        ImageCapture.OutputFileOptions outputOptions =
                new ImageCapture.OutputFileOptions.Builder(
                        getContentResolver(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                ).build();

        imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {

                    @Override
                    public void onImageSaved(
                            @NonNull ImageCapture.OutputFileResults outputFileResults) {

                        Uri imageUri = outputFileResults.getSavedUri();

                        if (imageUri != null) {
                            getIntent().putExtra("imageUri", imageUri.toString());
                            setResult(RESULT_OK, getIntent());
                            finish();
                        }
                    }

                    @Override
                    public void onError(
                            @NonNull ImageCaptureException exception) {

                        Toast.makeText(CameraActivity.this,
                                "Greška pri spremanju fotografije",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
