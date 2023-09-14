package com.girlsskinsminecraft.boyskinsminecraft.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.girlsskinsminecraft.boyskinsminecraft.R;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class Helpers {
    public static final String TAG = "AAAA_SKINS";

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static File createFileInGallery(Context context, String str) {
        return new File(String.format("%s/%s.png", getAppFolder(context).getAbsolutePath(), str));
    }

    public static File createFileInApplication(String str) {
        return new File(String.format("%s/%s.png", getPEFolder().getAbsolutePath(), str));
    }

    public static void saveImageToGallery(Context context, File cacheFile, String fileName) {
        Bitmap bitmap = BitmapFactory.decodeFile(cacheFile.getAbsolutePath());

        OutputStream fos;
        Uri imageUri;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName + ".png");
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
            values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

            imageUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            try {
                fos = context.getContentResolver().openOutputStream(imageUri);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void copyFile(File file, File file2) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read > 0) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileOutputStream.close();
                    fileInputStream.close();
                    return;
                }
            }
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static void goToStore(final Context context) {
        if (context == null) {
            return;
        }
        final ReviewManager create = ReviewManagerFactory.create(context);
        create.requestReviewFlow().addOnCompleteListener(new OnCompleteListener<ReviewInfo>() { 
            @Override 
            public void onComplete(Task<ReviewInfo> task) {
                if (task.isSuccessful()) {
                    Task<Void> flow = create.launchReviewFlow((Activity) context, task.getResult()).addOnFailureListener(new OnFailureListener() { 
                        @Override 
                        public void onFailure(Exception exc) {
                            Toast.makeText(context, "Rating Failed", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Void>() { 
                        @Override 
                        public void onComplete(Task<Void> task2) {
                            Toast.makeText(context, "Review Completed, Thank You!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() { 
            @Override 
            public void onFailure(Exception exc) {
                String packageName = context.getPackageName();
                try {
                    Context context2 = context;
                    context2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packageName)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } catch (ActivityNotFoundException unused) {
                    Context context3 = context;
                    context3.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                }
            }
        });
    }

    public static void goToDevPage(Context context) {
        if (context == null) {
            return;
        }
        try {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://developer?id=Moliya+Centre")));
        } catch (ActivityNotFoundException unused) {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/developer?id=Moliya+Centre")));
        }
    }

    public static void shareApp(Context context) {
        if (context == null) {
            return;
        }
        String packageName = context.getPackageName();
        String string = context.getString(R.string.app_name);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", String.format("Check out \"%s\"\r\nhttps://play.google.com/store/apps/details?id=%s", string, packageName));
        intent.setType("text/plain");
        context.startActivity(intent);
    }

    private static File getAppFolder(Context context) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), context.getString(R.string.app_name));
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private static File getPEFolder() {
        File file = new File(Environment.getExternalStorageDirectory(), "games/com.mojang/minecraftpe");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
