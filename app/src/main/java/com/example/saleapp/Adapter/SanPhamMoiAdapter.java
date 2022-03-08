package com.example.saleapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.saleapp.Model.SanPhamMoi;
import com.example.saleapp.R;
import com.example.saleapp.databinding.ItemSanphammoiBinding;

import java.text.DecimalFormat;
import java.util.List;

public class SanPhamMoiAdapter extends RecyclerView.Adapter<SanPhamMoiAdapter.ViewHolder> {
    ItemSanphammoiBinding binding;
    Context context;
    List<SanPhamMoi> sanPhamMoiList;

    public SanPhamMoiAdapter(Context context, List<SanPhamMoi> sanPhamMoiList) {
        this.context = context;
        this.sanPhamMoiList = sanPhamMoiList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_sanphammoi,parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(SanPhamMoiAdapter.ViewHolder holder, int position) {
        SanPhamMoi sanPhamMoi = sanPhamMoiList.get(position);
        binding.txtTenSPmoi.setText(sanPhamMoi.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        binding.txtGiaSPmoi.setText(decimalFormat.format(Double.parseDouble(sanPhamMoi.getGia())) + " đ     ");
        Glide.with(context).load(sanPhamMoi.getHinhanh()).into(binding.imgSPmoi);
    }


    @Override
    public int getItemCount() {
        return sanPhamMoiList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder( View itemView) {
            super(itemView);
        }
    }
}
