package com.unisinsight.demo.support.question;

import com.unisinsight.demo.repository.PersonRepository;
import com.unisinsight.demo.vos.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

@Component
public class SearchEmptyNoService {
    private final static String COUNT_SQL =
            "select count(phone) from tb_person where phone > ? and phone <= ?";

    private final static String prefix = "Order2020-";

    @Resource
    private PersonRepository repository;

    @Resource
    private JdbcTemplate jdbcTemplate;

    private ExecutorService executorService = Executors.newFixedThreadPool(65);

    private BlockingQueue<Person> blockingQueue = new ArrayBlockingQueue<>(256);

    public void initData(int start, int end){
        Future producer = executorService.submit(()->{
            for (int i=start ;i <= end ;i ++) {
                Person person = new Person();
                person.setId(i);
                person.setName(UUID.randomUUID().toString());
                person.setAddress(UUID.randomUUID().toString());
                person.setAge(UUID.randomUUID().toString());
                person.setPassword(UUID.randomUUID().toString());
                person.setSchool(UUID.randomUUID().toString());
                person.setPhone(formatNo(i));
                try {
                    blockingQueue.put(person);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("producer" + Thread.currentThread().getName() + " finish");

        });

        for (int i = 0 ; i < 64; i ++) {
            executorService.submit(() -> {
                while (true) {
                    if (producer.isDone() && blockingQueue.isEmpty()) {
                        System.out.println(Thread.currentThread().getName() + " finish");
                        break;
                    }
                    Person person = null;
                    try {
                        person = blockingQueue.take();
                        repository.save(person);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private static String formatNo(Integer no){
        String format = "Order2020-00000000";
        String noStr = no.toString();

        return format.substring(0, format.length() - noStr.length()) + noStr;
    }

    public List<String> search(int startIndex, int endIndex, int threadSize){
        final List<String> orders = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);

        for (Query query : slices(startIndex, endIndex, threadSize)) {
            executorService.submit(()->{
                count(query.start, query.end, orders);
                countDownLatch.countDown();
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return orders;
    }

    private List<Query> slices(int startIndex, int endIndex, int threadSize) {
        List<Query> result = new ArrayList<>();
        int step = (endIndex - startIndex) / threadSize;
        int start = startIndex;
        for (int index = 0; index < threadSize ;index ++) {
            Query query = new Query();
            query.start = start;
            if (index == threadSize -1) {
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
        if (countSql(start, end) ==(end - start)) {
            return;
        }

        if (end - start == 1){
            orders.add(prefix + end);
            return;
        }

        int mid = (start + end) / 2;
        count(start, mid, orders);
        count(mid, end, orders);
    }

    private Integer countSql(int start, int end) {
        String[] args = new String[]{prefix + start, prefix + end};
        return jdbcTemplate.queryForObject(COUNT_SQL, args, Integer.class);
    }

    private class Query{
        private int start;
        private int end;
    }


    public static void main(String[] args) {
        System.out.println(formatNo(1));
        System.out.println(formatNo(10));
        System.out.println(formatNo(100));
        System.out.println(formatNo(1000));
        System.out.println(formatNo(10000));
        System.out.println(formatNo(1000000));
        System.out.println(formatNo(10000001));
    }
}