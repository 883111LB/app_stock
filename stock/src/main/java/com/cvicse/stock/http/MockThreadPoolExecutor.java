package com.cvicse.stock.http;

import com.mitake.core.network.IRequestCallback;
import com.mitake.core.network.MitakeHttpGet;
import com.mitake.core.network.MitakeHttpParams;
import com.mitake.core.network.MitakeHttpPost;

import java.lang.reflect.Field;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 封装线程池
 * Created by liu_zlu on 2016/12/29 10:45
 */
public class MockThreadPoolExecutor extends ThreadPoolExecutor {
    public MockThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public MockThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public MockThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public MockThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public void execute(Runnable command) {
        super.execute(new MockRunnable(command));
    }

/*    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        MitakeHttpParams params = null;
        if(r instanceof MitakeHttpGet){
            params = ((MitakeHttpGet)r).getMitakeHttpParams();
        } else if(r instanceof MitakeHttpGetA){
            params = ((MitakeHttpGetA)r).getMitakeHttpParams();
        } else if(r instanceof MitakeHttpGetV2){
            params = ((MitakeHttpGetV2)r).getMitakeHttpParams();
        } else if(r instanceof MitakeHttpPost){
            params = ((MitakeHttpPost)r).getMitakeHttpParams();
        } else if(r instanceof MitakeHttpPostA){
            params = ((MitakeHttpPostA)r).getMitakeHttpParams();
        } else if(r instanceof MitakeHttpPostV2){
            params = ((MitakeHttpPostV2)r).getMitakeHttpParams();
        }
        if(params != null){
            if(params.requestCallback instanceof IRequestCallback) {
                try {
                    Field field = params.requestCallback.getClass().getDeclaredField("callback");
                    field.setAccessible(true);
                    field.get(params.requestCallback);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        MitakeHttpParams params = null;
        if(r instanceof MitakeHttpGet){
            params = ((MitakeHttpGet)r).getMitakeHttpParams();
        } else if(r instanceof MitakeHttpGetA){
            params = ((MitakeHttpGetA)r).getMitakeHttpParams();
        } else if(r instanceof MitakeHttpGetV2){
            params = ((MitakeHttpGetV2)r).getMitakeHttpParams();
        } else if(r instanceof MitakeHttpPost){
            params = ((MitakeHttpPost)r).getMitakeHttpParams();
        } else if(r instanceof MitakeHttpPostA){
            params = ((MitakeHttpPostA)r).getMitakeHttpParams();
        } else if(r instanceof MitakeHttpPostV2){
            params = ((MitakeHttpPostV2)r).getMitakeHttpParams();
        }
    }*/
//@Override
//protected void beforeExecute(Thread t, Runnable r) {
//    super.beforeExecute(t, r);
//    MitakeHttpParams params = null;
//    if(r instanceof MitakeHttpGet){
//        params = ((MitakeHttpGet)r).getMitakeHttpParams();
//    } else if(r instanceof MitakeHttpGet){
//        params = ((MitakeHttpGet)r).getMitakeHttpParams();
//    } else if(r instanceof MitakeHttpGet){
//        params = ((MitakeHttpGet)r).getMitakeHttpParams();
//    } else if(r instanceof MitakeHttpPost){
//        params = ((MitakeHttpPost)r).getMitakeHttpParams();
//    } else if(r instanceof MitakeHttpPost){
//        params = ((MitakeHttpPost)r).getMitakeHttpParams();
//    } else if(r instanceof MitakeHttpPost){
//        params = ((MitakeHttpPost)r).getMitakeHttpParams();
//    }
//    if(params != null){
//        if(params.requestCallback instanceof IRequestCallback) {
//            try {
//                Field field = params.requestCallback.getClass().getDeclaredField("callback");
//                field.setAccessible(true);
//                field.get(params.requestCallback);
//            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
//
//    @Override
//    protected void afterExecute(Runnable r, Throwable t) {
//        super.afterExecute(r, t);
//        MitakeHttpParams params = null;
//        if(r instanceof MitakeHttpGet){
//            params = ((MitakeHttpGet)r).getMitakeHttpParams();
//        } else if(r instanceof MitakeHttpGet){
//            params = ((MitakeHttpGet)r).getMitakeHttpParams();
//        } else if(r instanceof MitakeHttpGet){
//            params = ((MitakeHttpGet)r).getMitakeHttpParams();
//        } else if(r instanceof MitakeHttpPost){
//            params = ((MitakeHttpPost)r).getMitakeHttpParams();
//        } else if(r instanceof MitakeHttpPost){
//            params = ((MitakeHttpPost)r).getMitakeHttpParams();
//        } else if(r instanceof MitakeHttpPost){
//            params = ((MitakeHttpPost)r).getMitakeHttpParams();
//        }
//    }
}
