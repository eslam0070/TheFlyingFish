package com.crazyfish;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Objects;

public class GameOverActivity extends AppCompatActivity {

    private InterstitialAd interstitialAd;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        AdView mAdView = findViewById(R.id.adView);
        AdView mAdView2 = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest
                .Builder()
                .build();
        mAdView.loadAd(adRequest);
        mAdView2.loadAd(adRequest);
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.full_screen));
        AdRequest adRequest1 = new AdRequest.Builder()
                .build();
        interstitialAd.loadAd(adRequest1);
        interstitialAd.setAdListener(new AdListener()
        {
            public void onAdLoaded()
            {
                showInterstitial();
            }
        });
        String sscore = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("Score")).toString();
        TextView dscore = findViewById(R.id.displayScore);
        Button startGameAgain = findViewById(R.id.play_again_btn);
        startGameAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent g = new Intent(GameOverActivity.this,MainActivity.class);
                startActivity(g);
                finish();
            }
        });
        dscore.setText("score = " + sscore);
    }

    private void showInterstitial()
    {
        if (interstitialAd.isLoaded())
        {
            interstitialAd.show();
        }
    }
}
