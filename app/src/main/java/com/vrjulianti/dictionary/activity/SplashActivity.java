package com.vrjulianti.dictionary.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.database.SQLException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vrjulianti.dictionary.Class.DictPreference;
import com.vrjulianti.dictionary.Class.Dictionary;
import com.vrjulianti.dictionary.Database.DictBuilder;
import com.vrjulianti.dictionary.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity
{
    @BindView(R.id.loading)
    ProgressBar load_proses;

    @BindView(R.id.load_text)
    TextView text;

    @BindView(R.id.splash_image)
    ImageView splashImg;

    private final static String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

        new LoadData().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadData extends AsyncTask<Void, Integer, Void>
    {
        DictBuilder dictBuilder;
        DictPreference dictPreference;
        double progress;
        double max_progress = 1000;

        @Override
        protected Void doInBackground(Void... voids)
        {
            Boolean first_run = dictPreference.getFirstRun();

            if(first_run)
            {
                //Ambil data dari raw text
                ArrayList<Dictionary> KamusIndo = startLoad(R.raw.indonesia_english);
                ArrayList<Dictionary> KamusEng = startLoad(R.raw.english_indonesia);

                publishProgress((int) progress);

                try
                {
                    dictBuilder.open();
                }

                catch (SQLException e)
                {
                    e.printStackTrace();
                }

                Double ProgressMaxInsert = 100.0;
                Double progressDiff = (ProgressMaxInsert - progress) / (KamusIndo.size() + KamusEng.size());

                dictBuilder.InsertTransaction(KamusIndo, true);
                progress += progressDiff;
                publishProgress((int) progress);

                dictBuilder.InsertTransaction(KamusEng, false);
                progress += progressDiff;
                publishProgress((int) progress);

                Log.d(TAG, "number of data received "+ KamusIndo.size() + KamusEng.size());

                dictBuilder.close();
                dictPreference.setFirstRun(false);
                publishProgress((int) max_progress);
            }
            else
            {
                text.setText("Welcome to Flash Dictionary");
                try
                {
                    synchronized (this)
                    {
                        this.wait(1000);
                        publishProgress(50);

                        this.wait(300);
                        publishProgress((int) max_progress);
                    }
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute()
        {
            dictBuilder = new DictBuilder(SplashActivity.this);
            dictPreference = new DictPreference(SplashActivity.this);
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);

            finish();
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            load_proses.setProgress(values[0]);
        }

        @Override
        protected void onCancelled(Void aVoid)
        {
            super.onCancelled(aVoid);
        }

        @Override
        protected void onCancelled()
        {
            super.onCancelled();
        }
    }

    public ArrayList<Dictionary> startLoad(int data)
    {
        ArrayList<Dictionary> kamusList = new ArrayList<>();
        BufferedReader reader;

        try
        {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(data);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            String line = null;

            do
            {
                line  = reader.readLine();
                String[] splitstr = line.split("\t");
                Dictionary dictionary = new Dictionary(splitstr[0], splitstr[1]);
                kamusList.add(dictionary);
            }
            while(line != null);


        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return kamusList;
    }
}

