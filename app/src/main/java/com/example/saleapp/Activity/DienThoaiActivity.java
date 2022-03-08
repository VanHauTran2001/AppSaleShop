package com.example.saleapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.saleapp.Adapter.DienThoaiAdapter;
import com.example.saleapp.Adapter.SanPhamMoiAdapter;
import com.example.saleapp.Model.SanPhamMoi;
import com.example.saleapp.R;
import com.example.saleapp.Retrofit.APIService;
import com.example.saleapp.Retrofit.DataService;
import com.example.saleapp.Retrofit.RetrofitClient;
import com.example.saleapp.databinding.ActivityDienThoaiBinding;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DienThoaiActivity extends AppCompatActivity {
    ActivityDienThoaiBinding binding;
    DienThoaiAdapter dienThoaiAdapter;
    ArrayList<SanPhamMoi> sanPhamMoiArrayList;
    APIService apiService;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 1;
    int loai;
    Handler handler = new Handler();
    boolean isLoading = false;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_dien_thoai);
        apiService = RetrofitClient.getInstance(DataService.BASE_URL).create(APIService.class);
        ActionBar();
        getData(page);
        addEventLoading();
    }

    private void addEventLoading() {
        binding.recylerDienThoai.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLoading == false){
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition()==sanPhamMoiArrayList.size()-1){
                        isLoading=true;
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                //add null
                sanPhamMoiArrayList.add(null);
                dienThoaiAdapter.notifyItemInserted(sanPhamMoiArrayList.size()-1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //remover null
                sanPhamMoiArrayList.remove(sanPhamMoiArrayList.size()-1);
                dienThoaiAdapter.notifyItemRemoved(sanPhamMoiArrayList.size());
                page = page+1;
                getData(page);
                dienThoaiAdapter.notifyDataSetChanged();
                isLoading = true;
            }
        },2000);
    }

    private void getData(int page) {
        loai = getIntent().getIntExtra("loai",1);
        compositeDisposable.add(apiService.getSanPhamDT(page,loai)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    sanPhamMoiModel -> {
                                        if (dienThoaiAdapter==null){
                                            sanPhamMoiArrayList = (ArrayList<SanPhamMoi>) sanPhamMoiModel.getResult();
                                            dienThoaiAdapter = new DienThoaiAdapter(this,sanPhamMoiArrayList);
                                            linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
                                            binding.recylerDienThoai.setLayoutManager(linearLayoutManager);
                                            binding.recylerDienThoai.setHasFixedSize(true);
                                            binding.recylerDienThoai.setAdapter(dienThoaiAdapter);
                                        }else {
                                            int vitri = sanPhamMoiArrayList.size()-1;
                                            int soluongAdd = sanPhamMoiModel.getResult().size();
                                            for (int i=0;i<soluongAdd;i++){
                                                sanPhamMoiArrayList.add(sanPhamMoiModel.getResult().get(i));
                                            }
                                            dienThoaiAdapter.notifyItemRangeInserted(vitri,soluongAdd);
                                        }

                                    }
                            ));
    }

    private void ActionBar() {
        setSupportActionBar(binding.toolBarDienThoai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolBarDienThoai.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}