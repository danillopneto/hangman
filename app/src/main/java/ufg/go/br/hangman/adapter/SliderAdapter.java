package ufg.go.br.hangman.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ufg.go.br.hangman.R;

/**
 * Created by claud on 01/05/2018.
 */

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public int[] slide_images = {
        R.mipmap.normal,
        R.mipmap.dead
    };

    @Override
    public int getCount() {
        return getSlideHeadings().length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout)object;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(getSlideHeadings()[position]);
        slideDescription.setText(getSlideDescs()[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }

    private Context getContext(){
        return this.context;
    }

    private String[] getSlideDescs(){
        return new String[]{
                getContext().getResources().getString(R.string.ProofYouAreNotNoob),
                ""
        };
    }

    private String[] getSlideHeadings(){
        return new String[]{
                getContext().getString(R.string.challenge),
                getContext().getResources().getString(R.string.forcaComVoce)
        };
    }
}
