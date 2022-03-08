package com.example.saleapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.saleapp.Adapter.LoaiSanPhamAdapter;
import com.example.saleapp.Adapter.SanPhamMoiAdapter;
import com.example.saleapp.Model.LoaiSP;
import com.example.saleapp.Model.Pager;
import com.example.saleapp.Model.SanPhamMoi;
import com.example.saleapp.Model.SanPhamMoiModel;
import com.example.saleapp.R;
import com.example.saleapp.Retrofit.APIService;
import com.example.saleapp.Retrofit.DataService;
import com.example.saleapp.Retrofit.RetrofitClient;
import com.example.saleapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import com.example.saleapp.Adapter.ViewPagerAdapter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    int current;
    Runnable runnable;
    LoaiSanPhamAdapter loaiSanPhamAdapter;
    ArrayList<LoaiSP> loaiSPArrayList;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    APIService apiService;
    ArrayList<SanPhamMoi> sanPhamMoiArayList;
    SanPhamMoiAdapter sanPhamMoiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        apiService = RetrofitClient.getInstance(DataService.BASE_URL).create(APIService.class);
        apiService = DataService.getAPIService();
        ActionBar();
        KhoiTao();
        if (isConnectedInternet(this)){
            ActionViewFlipper();
            getLoaiSP();
            getSPMoi();
            getEventClick();
        }else {
            Toast.makeText(this, "No internet", Toast.LENGTH_LONG).show();
        }

    }

    private void getEventClick() {
        binding.listViewMHChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent trangChu = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(trangChu);
                        break;
                    case 1:
                        Intent dienthoai = new Intent(getApplicationContext(),DienThoaiActivity.class);
                        dienthoai.putExtra("loai",1);
                        startActivity(dienthoai);
                        break;
                    case 2:
                        Intent laptop = new Intent(getApplicationContext(),DienThoaiActivity.class);
                        laptop.putExtra("loai",2);
                        startActivity(laptop);
                        break;
                }
            }
        });
    }

    private void getSPMoi() {
        compositeDisposable.add(apiService.getSPMoi()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    sanPhamMoiModel -> {
                                        if (sanPhamMoiModel.isSuccess()){
                                            sanPhamMoiArayList = (ArrayList<SanPhamMoi>) sanPhamMoiModel.getResult();
                                            sanPhamMoiAdapter = new SanPhamMoiAdapter(this,sanPhamMoiArayList);
                                            binding.recylerView.setLayoutManager(new GridLayoutManager(this,2));
                                            binding.recylerView.setHasFixedSize(true);
                                            binding.recylerView.setAdapter(sanPhamMoiAdapter);
                                        }
                                    }
                            ));
    }

    private void KhoiTao() {
        loaiSPArrayList = new ArrayList<>();
        sanPhamMoiArayList = new ArrayList<>();
    }

    private void getLoaiSP() {
        compositeDisposable.add(apiService.getLoaiSP()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                               loaiSpModel -> {
                                   if (loaiSpModel.isSuccess()){
                                       loaiSPArrayList = (ArrayList<LoaiSP>) loaiSpModel.getResult();
                                       loaiSanPhamAdapter = new LoaiSanPhamAdapter(this,loaiSPArrayList);
                                       binding.listViewMHChinh.setAdapter(loaiSanPhamAdapter);
                                   }
                               }
                            ));
    }

    private void ActionViewFlipper() {
        ArrayList<Pager> pagerArrayList = new ArrayList<>();
        pagerArrayList.add(new Pager("https://inanaz.com.vn/wp-content/uploads/2020/02/mau-banner-quang-cao-dep-1.jpg"));
        pagerArrayList.add(new Pager("https://intphcm.com/data/upload/banner-quang-cao.jpg"));
        pagerArrayList.add(new Pager("https://inanaz.com.vn/wp-content/uploads/2020/02/mau-banner-quang-cao-dep-15.jpg"));
        pagerArrayList.add(new Pager("https://inanaz.com.vn/wp-content/uploads/2020/02/mau-banner-quang-cao-3.jpg"));
        pagerArrayList.add(new Pager("https://inanaz.com.vn/wp-content/uploads/2020/02/mau-banner-quang-cao-dep-3.jpg"));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(pagerArrayList);
        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.cirCleindicator.setViewPager(binding.viewPager);
        Handler handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                current =binding.viewPager.getCurrentItem();
                current++;
                if (current>=binding.viewPager.getAdapter().getCount()){
                    current =0;
                }
                binding.viewPager.setCurrentItem(current,true);
                handler.postDelayed(runnable,5000);
            }
        };
        handler.postDelayed(runnable,5000);
    }

    private void ActionBar() {
        setSupportActionBar(binding.toolBarMHChinh);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolBarMHChinh.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        binding.toolBarMHChinh.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.dramerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    //Kiem tra ket noi internet
    private boolean isConnectedInternet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo networkMobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkWifi!=null && networkWifi.isConnected() || networkMobile!=null && networkMobile.isConnected()){
            return true;
        }else {
            return false;
        }

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}