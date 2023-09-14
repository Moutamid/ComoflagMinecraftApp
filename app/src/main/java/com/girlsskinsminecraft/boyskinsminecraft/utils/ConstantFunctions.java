package com.girlsskinsminecraft.boyskinsminecraft.utils;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.girlsskinsminecraft.boyskinsminecraft.R;
import com.girlsskinsminecraft.boyskinsminecraft.myapplication.Myapplication;


public class ConstantFunctions {


    public static final String LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArBueEiTWfZk7O+BE104DZiZh6Dj8tIMJdZter0tO2KH0MUzn1UB14nrg6bb5IGup29NEvG6UI0ZelHgxClkYvLxzgXjagFgFKN46U6kijHxsccY9evn06NWNyJbPFoAkTQyVIPk44SBufx7g4H5f0azOtY28DL3fg5nvLDJ7yPAejGSvdZXuiVGopwS1A05QrjrgA6ol1YOUzxu22Vanb4ncIDTdF35MA2arbVf74fYFcqkJgWcfWkENRDxoj8IPo1tzWH2rO1/vaILmosJKd1SOrMhrmmyLita0AzJXH/d59Gwu3ed53Ct/Qcq9bDIX3TOALPdQ+NaRkapT2w0twQIDAQAB";
    public static final  String VIP_MONTH = "remove_ads";
    public static RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);

    public static void loadImage(String str, ImageView imageView) {
        Glide.with(Myapplication.getContext()).load(str).thumbnail(Glide.with(Myapplication.getContext()).load(Integer.valueOf((int) R.drawable.default_skin))).apply((BaseRequestOptions<?>) requestOptions).into(imageView);
    }

    public static void loadImageWithoutThumbnail(String str, ImageView imageView) {
        Glide.with(Myapplication.getContext()).load(str).apply((BaseRequestOptions<?>) requestOptions).into(imageView);
    }
}
