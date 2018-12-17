package cn.stylefeng.guns.huobi.thread;


import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsyncTask {
    private final static Logger logger = LoggerFactory.getLogger(AsyncTask.class);
    public String test1(){
        try {
            //10万条数据
            List<String> list = new ArrayList<>();
            List<String> list2 = new ArrayList<>();

            for(int i = 1; i <= 100000;i++){
                list.add("test:"+i);
            }

            //每条线程处理的数据尺寸
            int size = 250;
            int count = list.size()/size;
            if(count*size != list.size()){
                count ++;
            }
            int countNum = 0;
            final CountDownLatch countDownLatch =  new CountDownLatch(count);
            ExecutorService executorService = Executors.newFixedThreadPool(8);
            ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
            while (countNum < list.size()){
                countNum += size;
                ConCallable callable = new ConCallable();
                //截取list的数据，分给不同线程处理
                callable.setList(ImmutableList.copyOf(list.subList(countNum - size,countNum < list.size() ? countNum : list.size())));
                ListenableFuture listenableFuture = listeningExecutorService.submit(callable);
                Futures.addCallback(listenableFuture, new FutureCallback<List<String>>() {
                    @Override
                    public void onSuccess(List<String> list1) {
                        countDownLatch.countDown();
                        list2.addAll(list1);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        countDownLatch.countDown();
                        logger.info("处理出错：",throwable);

                    }
                });
            }
            countDownLatch.await(30, TimeUnit.MINUTES);
            logger.info("符合条件的返回数据个数为："+list2.size());
            logger.info("回调函数："+list2.toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "正在处理......";

    }

    public static void main(String[] args) {
        new AsyncTask().test1();
    }
}
