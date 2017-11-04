package com.cst.whut.se.MainFragment;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.cst.whut.se.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 12421 on 2017/11/1.
 */

public class TestFragment extends Fragment {
    public LocationClient locationClient;
    private TextView position;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        locationClient = new LocationClient(getActivity().getApplicationContext());
        locationClient.registerLocationListener(new MyLocationListener());
        position = (TextView)view.findViewById(R.id.myposition);
        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);;
            ActivityCompat.requestPermissions(getActivity(),permissions,1);
        }else{
            requestLocation();
        }
        return view;
    }
    private void requestLocation(){
        initLocation();
        locationClient.start();
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        locationClient.setLocOption(option);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    for(int result:grantResults){
                        if(result!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(getActivity(),"需要同意所有权限才能使用",Toast.LENGTH_SHORT).show();
                            try {
                                finalize();
                                return;
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(getActivity(),"发生未知错误",Toast.LENGTH_SHORT).show();
                    try {
                        finalize();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuilder currentLocation = new StringBuilder();
            currentLocation.append("维度：").append(bdLocation.getLatitude())
                    .append("\n");
            currentLocation.append("经度：").append(bdLocation.getLongitude())
                    .append("\n");
            currentLocation.append("定位方式：");
            if(bdLocation.getLocType() == BDLocation.TypeGpsLocation){
                currentLocation.append("GPS");
            }else if(bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                currentLocation.append("网络");
            }
            position.setText(currentLocation.toString());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationClient.stop();
    }
}
