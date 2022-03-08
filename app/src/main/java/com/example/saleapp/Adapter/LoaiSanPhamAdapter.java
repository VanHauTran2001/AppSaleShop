package com.example.saleapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.saleapp.Model.LoaiSP;
import com.example.saleapp.R;

import java.util.List;

public class LoaiSanPhamAdapter extends BaseAdapter {
    Context context;
    List<LoaiSP> loaiSPList;

    public LoaiSanPhamAdapter(Context context, List<LoaiSP> loaiSPList) {
        this.context = context;
        this.loaiSPList = loaiSPList;
    }

    @Override
    public int getCount() {
        return loaiSPList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        TextView txtTensp;
        ImageView imgHinhanh;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_sanpham,null);
            viewHolder.txtTensp = view.findViewById(R.id.tensp);
            viewHolder.imgHinhanh = view.findViewById(R.id.item_image);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.txtTensp.setText(loaiSPList.get(i).getTensanpham());
        Glide.with(context).load(loaiSPList.get(i).getHinhanh()).into(viewHolder.imgHinhanh);
        return view;
    }
}
