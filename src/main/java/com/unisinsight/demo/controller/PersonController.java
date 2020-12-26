package com.unisinsight.demo.controller;

import com.unisinsight.demo.service.PersonService;
import com.unisinsight.demo.service.RandomUpdateDataService;
import com.unisinsight.demo.service.SequenceInitDataService;
import com.unisinsight.demo.support.sort.BubbleSort;
import com.unisinsight.demo.support.sort.QuickSort;
import com.unisinsight.demo.support.sort.Sort;
import com.unisinsight.demo.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("person")
public class PersonController {

    @Resource
    private SequenceInitDataService<Person> seqPersonInitDataService;

    @Resource
    private RandomUpdateDataService<Person> randomUpdateDataService;

//    @Resource
    private PersonService personService;

    //        FileToJson.TreeNode root = FileToJson.readFile("/Users/liuran/Desktop/test.json");
//        String json = JSON.toJSONString(root);

    private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);

    @GetMapping("{id}")
    public Person get(@PathVariable Integer id){
       return personService.get(id);
    }

    @PostMapping("seq/init/{start}/{end}")
    public void initData(@PathVariable Integer start, @PathVariable Integer end){
        seqPersonInitDataService.execute(start, end);
    }

    @PutMapping("random/init/{start}/{end}")
    public void updateData(@PathVariable Integer start, @PathVariable Integer end){
        randomUpdateDataService.execute(start, end);
    }

    @GetMapping("/invoke/sort/{type}/{size}")
    public void test(@PathVariable String type, @PathVariable Integer size){
        long time = sortFactory(type).sortTime(randomList(size));
        LOG.info("sort time :{}", time);
    }

    private List<Integer> randomList(int size){
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < size ;i ++){
            result.add(new Random().nextInt());
        }
        return result;
    }

    private Sort sortFactory(String type){
        if ("bubble".equals(type)){
            return new BubbleSort();
        }

        return new QuickSort();
    }

    @GetMapping(value = "sleep")
    public void sleep(){
        try {
            Thread.sleep(60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
