package com.tzubeli.contactme.services;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Base64OutputStream;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tzubeli.contactme.beans.Profile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class QRCodeSvc {
    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    public final static int WIDTH = 400;

    private static QRCodeSvc mInstance;

    public static QRCodeSvc getInstance(){
        if(mInstance == null){
            mInstance = new QRCodeSvc();
        }
        return mInstance;
    }

    public void readProfile(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        System.out.println(result.getContents());
    }

    public String profileToString(Profile profile) {
        return objectToString(profile);
    }

    public Profile stringToProfile(String encodedObject) {
        return (Profile)stringToObject(encodedObject);
    }

    public Bitmap profileToImage(Profile profile) {
        return stringToImage(objectToString(profile));
    }

    private static String objectToString(Serializable object) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            new ObjectOutputStream(out).writeObject(object);
            byte[] data = out.toByteArray();
            out.close();

            out = new ByteArrayOutputStream();
            Base64OutputStream b64 = new Base64OutputStream(out, Base64.DEFAULT);
            b64.write(data);
            b64.close();
            out.close();

            return new String(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Object stringToObject(String encodedObject) {
        try {
            return new ObjectInputStream(new Base64InputStream(
                    new ByteArrayInputStream(encodedObject.getBytes()), Base64.DEFAULT)).readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Bitmap stringToImage(String encodedObject) {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(encodedObject,
                    BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);
        } catch (IllegalArgumentException| WriterException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, WIDTH, 0, 0, w, h);
        return bitmap;
    }
}
