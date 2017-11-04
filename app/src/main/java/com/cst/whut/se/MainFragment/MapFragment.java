package com.cst.whut.se.MainFragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cst.whut.se.R;

/**
 * Created by 12421 on 2017/10/29.
 */

public class MapFragment extends Fragment {
    private WebView webview;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        webview = (WebView)view.findViewById(R.id.map);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webview.clearCache(true);
        webview.clearFormData();
    }
}
