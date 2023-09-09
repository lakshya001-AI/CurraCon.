package com.curracon.currencyconverterapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.smarteist.autoimageslider.SliderViewAdapter;

class SlideData12 extends SliderViewAdapter<SlideData12.SliderViewHolder> {
    private int images[];
    public SlideData12(int[] images) {
        this.images = images;

    }
    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout,null);
        return new SliderViewHolder(view);
    }
    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
        viewHolder.imageView.setImageResource(images[position]);

    }
    @Override
    public int getCount() {
        return images.length;
    }
    public class SliderViewHolder extends ViewHolder {
        private ImageView imageView;

        public SliderViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.myimage);

        }
    }

}
