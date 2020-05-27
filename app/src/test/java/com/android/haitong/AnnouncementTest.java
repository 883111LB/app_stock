package com.android.haitong;

import com.cvicse.stock.BuildConfig;
import com.mitake.core.Announcement;
import com.mitake.core.request.AnnouncementRequest;
import com.mitake.core.response.AnnouncementResponse;
import com.mitake.core.response.IResponseCallback;
import com.mitake.core.response.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
/**
 * Created by liu_zlu on 2016/12/22 09:52
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class AnnouncementTest {
    AnnouncementResponse announcementResponse;
    String string;
    @Test
    public void addition_isCorrect() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        AnnouncementRequest announcementRequest = new AnnouncementRequest();
        announcementRequest.send( new IResponseCallback() {
            @Override
            public void callback(Response response) {
                announcementResponse = (AnnouncementResponse) response;
                Announcement info = announcementResponse.info;
                latch.countDown();
                assertNull(info);
                if (info==null){

                    return;
                }
            }

            @Override
            public void exception(int i, String s) {
                string = s;
                latch.countDown();
            }
        });
        latch.await();
        assertNotNull(announcementResponse);
    }
}
