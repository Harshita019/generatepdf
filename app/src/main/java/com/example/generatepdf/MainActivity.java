package com.example.generatepdf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btn;
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button);
        name = findViewById(R.id.etname);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        createPDF();
    }

    private void createPDF() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PdfDocument pdfDocument = new PdfDocument();
                Paint mypaint = new Paint();

                String strname = name.getText().toString();

                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(210,297,1).create();
                PdfDocument.Page mypage = pdfDocument.startPage(pageInfo);

                Canvas canvas = mypage.getCanvas();
                canvas.drawText("Alankit Leads", 40,50, mypaint);

                mypaint.setTextAlign(Paint.Align.LEFT);
                mypaint.setTextSize(14);
                mypaint.setColor(Color.rgb(122,119,119));
                canvas.drawText("User's Data",10,70, mypaint);

                mypaint.setTextSize(12);
                mypaint.setColor(Color.BLACK);
                canvas.drawText("Name", 10, 100, mypaint);

                mypaint.setTextSize(12);
                mypaint.setColor(Color.BLACK);
                canvas.drawText(strname, 60, 100, mypaint);

                pdfDocument.finishPage(mypage);

                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "First.pdf");

                try {
                    pdfDocument.writeTo(new FileOutputStream(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pdfDocument.close();
            }
        });
    }
}