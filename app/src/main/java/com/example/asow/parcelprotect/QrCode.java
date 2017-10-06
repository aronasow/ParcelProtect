package com.example.asow.parcelprotect;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class QrCode extends AppCompatActivity {
    SurfaceView cameraPreview;
    TextView txtResult;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionId = 1001;
    final int RequestSmsPermissionId = 1002;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionId: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode);

        cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
        txtResult = (TextView) findViewById(R.id.txtResult);

        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(640, 480).build();

        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            //send SMS Request Permission
            ActivityCompat.requestPermissions(QrCode.this, (new  String[]{Manifest.permission.SEND_SMS}), RequestSmsPermissionId);
        }
        //ADD EVENT
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //RequestPermission
                    ActivityCompat.requestPermissions(QrCode.this, (new String[]{android.Manifest.permission.CAMERA}), RequestCameraPermissionId);
                    return;
                }

                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder,int i,int i1,int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();

            }

        });

        //BarcodeDetector
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if(qrcodes.size() != 0 ){
                    txtResult.post(new Runnable() {
                        @Override
                        public void run() {
                            //Toast lecture qr-code
                            Toast toastQR = Toast.makeText(getApplicationContext(), "Lecture en cours", Toast.LENGTH_SHORT);
                            toastQR.show();
                            System.out.println(qrcodes.valueAt(0));

                            //create vibrator
                            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);
                            txtResult.setText(qrcodes.valueAt(0).displayValue);

                            //Send email
                            Toast toastMail = Toast.makeText(getApplicationContext(),"Sending email", Toast.LENGTH_LONG);
                            toastMail.show();
                            try {
                                wait(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            sendEmail();
                            //sendMessage("0753846820");

                            //lancer intent
                            //Uri uri = Uri.parse("http://www.google.com/#q="+qrcodes.valueAt(0).displayValue);
                            Uri uri = Uri.parse("http://www."+qrcodes.valueAt(0).displayValue+".com");

                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            //startActivity(intent);

                        }
                    });
                }

            }
        });
    }

    //Gestion des mails
    private void sendEmail() {
        //setting content for email
        String email =  "amadylome93@live.fr";
        String subject = "Désactivation sécurité du harnais numéro :";
        String message = "Bonjour,\nVotre colis a été ouvert par QR-code\n\nCordialement\n#TEAMHARNAIS";

        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send the mail
        sm.execute();
    }
    public void sendMessage(String numero) {
        String message = "Bonjour,\n\nVotre colis à été ouvert par QR-code\n\nCordialement\n#TEAMHARNAIS";

        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(numero,null,message,null,null);
    }
}