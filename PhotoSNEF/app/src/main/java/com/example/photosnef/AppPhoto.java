package com.example.photosnef;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class AppPhoto extends AppCompatActivity implements SurfaceHolder.Callback {

    private Camera camera;
    private SurfaceView surfaceCamera;
    private Boolean isPreview;
    private FileOutputStream stream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_photo);

        // intent
        final Intent intent = getIntent();
        final String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);


        // Nous mettons l'application en plein écran et sans barre de titre
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        isPreview = false;

        // Nous appliquons notre layout
        setContentView(R.layout.activity_app_photo);

        // Nous récupérons notre surface pour le preview
        surfaceCamera = findViewById(R.id.surfaceViewCamera);

        // Méthode d'initialisation de la caméra
        InitializeCamera();

        // Quand nous cliquons sur notre surface
        surfaceCamera.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Nous prenons une photo
                if (camera != null) {
                    SavePicture(message);
                }

            }
        });

    }

    private void SavePicture(String nomPhoto) {
        try {

            String fileName = nomPhoto+".jpg";

            // Metadata pour la photo
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
            //values.put(MediaStore.Images.Media.DESCRIPTION, "Image prise par AppPhoto");
            //values.put(MediaStore.Images.Media.DATE_TAKEN, new Date().getTime());
            //values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 123);
            }
                // Support de stockage
                Uri taken = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        values);

                // Ouverture du flux pour la sauvegarde
                stream = (FileOutputStream) getContentResolver().openOutputStream(
                        taken);


                // Callback pour la prise de photo
                Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {

                    public void onPictureTaken(byte[] data, Camera camera) {
                        if (data != null) {
                            // Enregistrement de votre image
                            try {

                                if (stream != null) {
                                    Toast.makeText(AppPhoto.this, "J'enregistre la photo", Toast.LENGTH_SHORT).show();
                                    stream.write(data);
                                    stream.flush();
                                    stream.close();
                                }
                            } catch (Exception e) {
                                Log.d("App", e.getMessage());
                            }

                            // Nous redémarrons la prévisualisation
                            Intent myIntentBack = new Intent();
                            myIntentBack.putExtra(MainActivity.EXTRA_MESSAGE, "La photo a été sauvegardée");
                            AppPhoto.this.setResult(0, myIntentBack);
                            AppPhoto.this.finish();
                        }
                    }
                };


                camera.takePicture(null, pictureCallback, pictureCallback);


        } catch (Exception e) {
            Log.d("App", e.getMessage());
        }

    }


    public void InitializeCamera() {
        // Nous attachons nos retours du holder à notre activité
        surfaceCamera.getHolder().addCallback(this);
        // Nous spécifions le type du holder en mode SURFACE_TYPE_PUSH_BUFFERS
        surfaceCamera.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Nous prenons le contrôle de la camera
        if (camera == null)
            camera = Camera.open();
            Camera.Parameters params = camera.getParameters();

            // Pour connaître les modes de flash supportés
            List<String> flashs = params.getSupportedFlashModes();


    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Si le mode preview est lancé alors nous le stoppons
        if (isPreview) {
            camera.stopPreview();
        }
        // Nous récupérons les paramètres de la caméra
        Camera.Parameters parameters = camera.getParameters();

        // Nous changeons la taille
        parameters.setPreviewSize(width, height);
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
        parameters.setFlashMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);


        // Nous appliquons nos nouveaux paramètres
        camera.setParameters(parameters);

        try {
            // Nous attachons notre prévisualisation de la caméra au holder de la
            // surface
            camera.setPreviewDisplay(surfaceCamera.getHolder());
        } catch (IOException e) {
        }

        // Nous lançons la preview
        camera.startPreview();

        isPreview = true;
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            camera.stopPreview();
            isPreview = false;
            camera.release();
        }
    }

    // Retour sur l'application

    @Override
    public void onResume() {
        super.onResume();
        camera = Camera.open();
    }

    // Mise en pause de l'application

    @Override
    public void onPause() {
        super.onPause();

        if (camera != null) {
            camera.release();
            camera = null;
        }
    }


}
