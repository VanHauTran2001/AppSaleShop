package com.example.saleapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.saleapp.Activity.ChiTietSanPhamActivity;
import com.example.saleapp.Interface.ItemClickListener;
import com.example.saleapp.Model.SanPhamMoi;
import com.example.saleapp.R;
import com.example.saleapp.databinding.ItemDienthoaiBinding;

import java.text.DecimalFormat;
import java.util.List;

public class DienThoaiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    List<SanPhamMoi> sanPhamMoiList;
    ItemDienthoaiBinding binding;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LODING = 1;


    public DienThoaiAdapter(Context context, List<SanPhamMoi> sanPhamMoiList) {
        this.context = context;
        this.sanPhamMoiList = sanPhamMoiList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DATA){
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_dienthoai,parent,false);
            return new ViewHolder(binding.getRoot());
        }else {
            View view  = LayoutInflater.from(context).inflate(R.layout.item_loading,parent,false);
            return new loadingViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){
            SanPhamMoi arraySP = sanPhamMoiList.get(position);
            binding.txtDienThoai.setText(arraySP.getTensp());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            binding.txtGiaDT.setText(decimalFormat.format(Double.parseDouble(arraySP.getGia())) + " đ     ");
            Glide.with(context).load(arraySP.getHinhanh()).into(binding.imgDienThoai);
            binding.txtMoTaDT.setText(arraySP.getMota());
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean islongClick) {
                    if (!islongClick){
                        //click
                        Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }else {
            loadingViewHolder loadingViewHolder = (DienThoaiAdapter.loadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return sanPhamMoiList.get(position)==null ? VIEW_TYPE_LODING : VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return sanPhamMoiList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ItemClickListener itemClickListener;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
                itemClickListener.onClick(view,getAdapterPosition(),false);
        }
    }

    public class loadingViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        public loadingViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
