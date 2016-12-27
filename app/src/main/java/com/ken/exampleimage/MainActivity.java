package com.ken.exampleimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "JoinImage";
    private Bitmap mBackImage, mTopImage, mBackground;
    private BitmapDrawable mBitmapDrawable;
    private static String mTempDir;
    private String mSavedImageName = null;
    private FileOutputStream mFileOutputStream = null;
    private Canvas mCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                doUnion2(view);
            }


        });
    }

    private void doUnion(View view) {
        //Create folder in SDCard to store newly generated image
        mTempDir = Environment.getExternalStorageDirectory() + "/";
        Log.d("myLog", "path: " + mTempDir);
        File mTempFile = new File(mTempDir);
        if (!mTempFile.exists()) {
            mTempFile.mkdirs();
        }
        //File name
        mSavedImageName = "Test2.png";
        //Width = 604, Height = 1024 Change as per your requirement
        mBackground = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        //Put back and top images in your res folder
        mBackImage = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mTopImage = BitmapFactory.decodeResource(getResources(), R.drawable.back1);

        mCanvas = new Canvas(mBackground);
        mCanvas.drawBitmap(mBackImage, 0f, 0f, null);
        mCanvas.drawBitmap(mTopImage, 10f, 12f, null);
//        mCanvas.drawBitmap(bmp2, bmp1.getWidth(), 0, null);

        try {
            mBitmapDrawable = new BitmapDrawable(getResources(), mBackground);

            Bitmap mNewSaving = mBitmapDrawable.getBitmap();
            String FtoSave = mTempDir + mSavedImageName;
            File mFile = new File(FtoSave);
            Log.d("myLog", "path2: " + FtoSave);
            mFileOutputStream = new FileOutputStream(mFile);
            mNewSaving.compress(Bitmap.CompressFormat.PNG, 95, mFileOutputStream);
            mFileOutputStream.flush();
            mFileOutputStream.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "FileNotFoundExceptionError " + e.toString());
            Snackbar.make(view, "error", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } catch (IOException e) {
            Log.e(TAG, "IOExceptionError " + e.toString());
            Snackbar.make(view, "Error", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        Log.i(TAG, "Image Created");
        Toast.makeText(getApplicationContext(), "Creado", Toast.LENGTH_SHORT).show();
    }

    public void doUnion2(View view) {

        new AsyncTask<Void, Void, Void>() {

            public Bitmap result;

            @Override
            protected Void doInBackground(Void... voids) {

                mTempDir = Environment.getExternalStorageDirectory() + "/";

                mSavedImageName = "Test5.png";
                Log.d("myLog", "path: " + mTempDir);
                File mTempFile = new File(mTempDir);
                if (!mTempFile.exists()) {
                    mTempFile.mkdirs();
                }

                Bitmap s = BitmapFactory.decodeResource(getResources(), R.drawable.img_1);
                Bitmap c = BitmapFactory.decodeResource(getResources(), R.drawable.img_2);
                Bitmap d = BitmapFactory.decodeResource(getResources(), R.drawable.img_3);


                Bitmap cs = null;

                int width, height = 0;


                width = c.getWidth() + s.getWidth() + d.getWidth();


                if (c.getHeight() > s.getHeight()) {
                    height = c.getHeight();
                } else {
                    height = s.getHeight();
                }
                Log.d("myLog", "widh " + width);
                Log.d("myLog", "heigh " + height);

                cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

                Canvas comboImage = new Canvas(cs);

                comboImage.drawBitmap(c, 0f, 0f, null);
                comboImage.drawBitmap(s, c.getWidth(), 0f, null);
                comboImage.drawBitmap(d, c.getWidth() + s.getWidth(), 0f, null);


                try {
                    mBitmapDrawable = new BitmapDrawable(getResources(), cs);

                    Bitmap mNewSaving = mBitmapDrawable.getBitmap();
                    String FtoSave = mTempDir + mSavedImageName;
                    File mFile = new File(FtoSave);
                    Log.d("myLog", "path2: " + FtoSave);
                    mFileOutputStream = new FileOutputStream(mFile);
                    mNewSaving.compress(Bitmap.CompressFormat.PNG, 100, mFileOutputStream);
                    mFileOutputStream.flush();
                    mFileOutputStream.close();
                } catch (FileNotFoundException e) {
                    Log.e(TAG, "FileNotFoundExceptionError " + e.toString());
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Log.e(TAG, "IOExceptionError " + e.toString());
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Image Created");


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Termino", Toast.LENGTH_SHORT).show();
            }
        }.execute();


    }


    public Bitmap combineImages(Bitmap c, Bitmap s) { // can add a 3rd parameter 'String loc' if you want to save the new image - left some code to do that at the bottom
        Bitmap cs = null;

        int width, height = 0;

        if (c.getWidth() > s.getWidth()) {
            width = c.getWidth() + s.getWidth();
        } else {
            width = s.getWidth() + s.getWidth();
        }

        if (c.getHeight() > s.getHeight()) {
            height = c.getHeight();
        } else {
            height = s.getHeight();
        }

        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(cs);

        comboImage.drawBitmap(c, 0f, 0f, null);
        comboImage.drawBitmap(s, c.getWidth(), 0f, null);

        // this is an extra bit I added, just incase you want to save the new image somewhere and then return the location
    /*String tmpImg = String.valueOf(System.currentTimeMillis()) + ".png";

    OutputStream os = null;
    try {
      os = new FileOutputStream(loc + tmpImg);
      cs.compress(CompressFormat.PNG, 100, os);
    } catch(IOException e) {
      Log.e("combineImages", "problem combining images", e);
    }*/

        return cs;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public interface CombinateImageCallback {

        public void onFinish(Bitmap bitmap);
    }
}
