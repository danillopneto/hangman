package ufg.go.br.hangman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import ufg.go.br.hangman.adapter.SliderAdapter;

public class WelcomeScreenActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mLayout;
    private TextView[] mDots;
    private SliderAdapter sliderAdapter;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences preferences = getApplicationContext().getSharedPreferences("welcome", android.content.Context.MODE_PRIVATE);

        boolean screen = preferences.getBoolean("screen", false);
        if(screen==false) {

            setContentView(R.layout.activity_screen);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("screen", true);
            editor.commit();


        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mLayout = (LinearLayout) findViewById(R.id.mLayout);

        mNextButton =  findViewById(R.id.nextButton);
        mPreviousButton = findViewById(R.id.previousButton);

        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);

        addPaginacao(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        mNextButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(mCurrentPage == 1){
                    Intent myIntent = new Intent(WelcomeScreenActivity.this, MainActivity.class);
                    WelcomeScreenActivity.this.startActivity(myIntent);
                } else{
                    mSlideViewPager.setCurrentItem(mCurrentPage + 1);
                }
            }
        });

        mPreviousButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });

        }
        else{
            Intent myIntent = new Intent(WelcomeScreenActivity.this, MainActivity.class);
            WelcomeScreenActivity.this.startActivity(myIntent);
        }
    }

    public void addPaginacao(int position){
        mDots = new TextView[2];
        mLayout.removeAllViews();

        for(int i = 0; i < mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.white));

            mLayout.addView(mDots[i]);
        }

        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addPaginacao(position);

            mCurrentPage = position;
            if(position == 0){

                mPreviousButton.setEnabled(false);
                mPreviousButton.setVisibility(View.INVISIBLE);
            } else{

                mPreviousButton.setEnabled(true);
                mPreviousButton.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
