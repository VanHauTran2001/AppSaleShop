package com.example.saleapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.saleapp.Model.Pager;
import com.example.saleapp.R;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private List<Pager> pagerList;

    public ViewPagerAdapter(List<Pager> pagerList) {
        this.pagerList = pagerList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_pager,container,false);
        ImageView imgPager = view.findViewById(R.id.imgPager);
        Pager pager = pagerList.get(position);
        Glide.with(container.getContext()).load(pager.getResourcePager()).into(imgPager);
        //add view
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if (pagerList != null){
            return pagerList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view,Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position,Object object) {
        container.removeView((View) object);
    }
}
