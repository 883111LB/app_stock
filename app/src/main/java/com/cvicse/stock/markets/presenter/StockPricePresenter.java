package com.cvicse.stock.markets.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.data.MorePriceBo;
import com.cvicse.stock.markets.presenter.contract.StockPriceContract;
import com.cvicse.stock.util.FormatUtils;
import com.mitake.core.QuoteItem;
import com.mitake.core.request.MorePriceRequest;
import com.mitake.core.response.MorePriceResponse;
import com.mitake.core.response.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by liu_zlu on 2017/3/7 16:49
 */
public class StockPricePresenter extends BasePresenter implements StockPriceContract.Presenter {

    private StockPriceContract.View view;
    private QuoteItem quoteItem;
    private ArrayList<MorePriceBo> morePriceBos = new ArrayList<>();
    private int priceSort = 0; //0 默认 1 升序 2 降序
    private int volumeSort = 0;//0 默认 1 升序 2 降序
    @Override
    public void setView(StockPriceContract.View view) {
        this.view = view;
    }

    /**
     * 初始化stockId
     *
     * @param quoteItem
     */
    @Override
    public void init(QuoteItem quoteItem) {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        this.quoteItem = quoteItem;
    }

    /**
     * 查询分价信息
     */
    @Override
    public void queryMorePrice() {
        MorePriceRequest morePriceRequest = new MorePriceRequest();
        morePriceRequest.send(quoteItem.id, quoteItem.subtype, new ResponseCallback(morePriceRequest) {
            @Override
            public void onBack(Response response) {
                MorePriceResponse morePriceResponse = (MorePriceResponse) response;
                String[][] strs = morePriceResponse.strs;
                morePriceBos.clear();
                MorePriceBo morePriceBo = null ;
                float max = 0;
                if(strs != null){
                    for(String[] str:strs){
                        morePriceBo = new MorePriceBo();
                        morePriceBo.price = str[0];
                        morePriceBo.realprice = FormatUtils.isStandard(morePriceBo.price) ? Float.parseFloat(morePriceBo.price) : 0.0f;  // new
                        morePriceBo.volume = str[1];
                        morePriceBo.realVolume = FormatUtils.isStandard(morePriceBo.volume) ? Float.parseFloat(morePriceBo.volume) : 0.0f;  // new;
                        max = max > morePriceBo.realVolume ? max:morePriceBo.realVolume;
                        morePriceBo.volume = FormatUtils.format(morePriceBo.volume);  // new
                        morePriceBos.add(morePriceBo);
                    }

                    for(MorePriceBo morePriceBo1:morePriceBos){
                        morePriceBo1.prencet = (int) (morePriceBo1.realVolume*100/max);
                    }
                }
                view.onMorePriceSuccess(morePriceBos);
            }

            @Override
            public void onError(int i, String s) {
                view.onMorePriceFail();
            }
        });
    }

    /**
     * 价格排序
     */
    @Override
    public void sortPrice() {
        volumeSort = 0;
        if(priceSort == 0 || priceSort == 2){
            priceSort = 1;
            Collections.sort(morePriceBos, new Comparator<MorePriceBo>() {
                @Override
                public int compare(MorePriceBo lhs, MorePriceBo rhs) {
                    if(lhs.realprice == rhs.realprice) return 0;
                    return lhs.realprice > rhs.realprice ? 1:-1;
                }
            });
        } else {
            priceSort = 2;
            Collections.sort(morePriceBos, new Comparator<MorePriceBo>() {
                @Override
                public int compare(MorePriceBo lhs, MorePriceBo rhs) {
                    if(lhs.realprice == rhs.realprice) return 0;
                    return lhs.realprice > rhs.realprice ? -1:1;
                }
            });
        }

        view.onMorePriceSuccess(morePriceBos);
    }

    /**
     * 成交量排序
     */
    @Override
    public void sortVolume() {
        priceSort = 0;
        if(volumeSort == 0 || volumeSort == 2){
            volumeSort = 1;
        } else {
            volumeSort = 2;
        }
        Collections.sort(morePriceBos, new Comparator<MorePriceBo>() {
            @Override
            public int compare(MorePriceBo lhs, MorePriceBo rhs) {
                if(lhs.realVolume == rhs.realVolume) return 0;
                return volumeSort == 1 ? (lhs.realVolume < rhs.realVolume ? -1:1):(lhs.realVolume > rhs.realVolume?-1:1);
            }
        });
        view.onMorePriceSuccess(morePriceBos);
    }
}
