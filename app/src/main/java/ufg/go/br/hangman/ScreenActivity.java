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
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScreenActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mLayout;
    private TextView[] mDots;
    private SliderAdapter sliderAdapter;
    private Button mNextButton;
    private Button mPreviousButton;
    private int mCurrentPage;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        // Armazenando dados inciais da configuração

        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed  = preferences.edit();

        if(preferences.getString("switchvibrate", "")==""){
            ed.putBoolean("switchvibrate", false);
        }

        if(preferences.getString("switchsound", "")==""){
            ed.putBoolean("switchsound", false);
        }

        if(preferences.getString("switchreveal", "")==""){
            ed.putBoolean("switchreveal", false);
        }


        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mLayout = (LinearLayout) findViewById(R.id.mLayout);

        mNextButton = (Button) findViewById(R.id.nextButton);
        mPreviousButton = (Button) findViewById(R.id.previousButton);

        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);

        addPaginacao(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        mNextButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(mCurrentPage == 1){
                    Intent myIntent = new Intent(ScreenActivity.this, MainActivity.class);
                    ScreenActivity.this.startActivity(myIntent);
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
                mNextButton.setText("Próximo");

                mPreviousButton.setEnabled(false);
                mPreviousButton.setVisibility(View.INVISIBLE);
                mPreviousButton.setText("");
            } else{
                mNextButton.setText("Pular");

                mPreviousButton.setEnabled(true);
                mPreviousButton.setVisibility(View.VISIBLE);
                mPreviousButton.setText("Voltar");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
