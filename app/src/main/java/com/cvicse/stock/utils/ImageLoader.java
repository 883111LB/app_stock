package com.cvicse.stock.utils;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.cvicse.stock.R;
import com.cvicse.stock.http.ResponseCallback;
import com.mitake.core.request.FinInfoImageRequest;
import com.mitake.core.response.FinInfoImageResponse;
import com.mitake.core.response.Response;
import com.squareup.picasso.Picasso;

import java.io.File;

import static com.mitake.core.network.Network.context;

/**
 * Created by ruan_ytai on 17-1-10.
 */

public class ImageLoader {

    private static final int ID_KEY = 2;
    static Handler mHandler = new Handler(Looper.getMainLooper());
    public static void loadId(final ImageView imageView,final String id){
        File file = FileUtils.getFile(id);//第一次进来 file为null
        if(file != null){
            loadURL(imageView,file);
            return;
        }
        imageView.setTag(id);
        FinInfoImageRequest finInfoImageRequest = new FinInfoImageRequest();
        finInfoImageRequest.send(id, new ResponseCallback(finInfoImageRequest) {
            @Override
            public void onBack(Response response) {
                FinInfoImageResponse finInfoImageResponse = (FinInfoImageResponse) response;
                byte[] imageData = finInfoImageResponse.imageData;
                final File file = FileUtils.byteToFile(imageData,id);
                String imageId = (String) imageView.getTag();
                if(id.equals(imageId)){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            loadURL(imageView,file);
                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }


    public static void loadURL(final ImageView imageView,final File url){
        Picasso.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .noFade()
                /*.fit()*/
                .into(imageView);
    }
}
