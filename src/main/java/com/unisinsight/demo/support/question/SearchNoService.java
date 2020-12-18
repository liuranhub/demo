package com.unisinsight.demo.support.question;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchNoService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private ExecutorService executorService = Executors.newFixedThreadPool(64);

    private final static String COUNT_SQL =
            "select count(phone) from tb_person where phone > ? and phone <= ?";
    private final static String prefix = "Order2020-";

    public List<String> search(int startIndex, int endIndex, int threadSize){
        final List<String> result = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);

        for (Query query : slices(startIndex, endIndex, threadSize)) {
            executorService.submit(()->{
                count(query.start, query.end, result);
                countDownLatch.countDown();
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    private List<Query> slices(int startIndex, int endIndex, int size) {
        List<Query> result = new ArrayList<>();
        int step = (endIndex - startIndex) / size;
        int start = startIndex;
        for (int index = 0; index < size ;index ++) {
            Query query = new Query();
            query.start = start;
            if (index == size -1) {
                query.end = endIndex;
            } else {
                query.end = query.start + step;
            }
            start = start + step;
            result.add(query);
        }
        return result;
    }

    private void count(int start, int end, List<String> orders) {
        if (countSql(start, end) >=(end - start)) {
            return;
        }

        if (end - start == 1){
            orders.add(formatNo(end));
            return;
        }

        int mid = (start + end) / 2;

        CountDownLatch countDownLatch = new CountDownLatch(2);
        executorService.submit(()->{
            count(start, mid, orders);
            countDownLatch.countDown();
        });

        executorService.submit(()->{
            count(mid, end, orders);
            countDownLatch.countDown();
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private Integer countSql(int start, int end) {
        String[] args = new String[]{formatNo(start), formatNo(end)};
        return jdbcTemplate.queryForObject(COUNT_SQL, args, Integer.class);
    }

    private class Query{
        private int start;
        private int end;
    }

    private static String formatNo(Integer no){
        String format = prefix + "00000000";
        String noStr = no.toString();

        return format.substring(0, format.length() - noStr.length()) + noStr;
    }
}
