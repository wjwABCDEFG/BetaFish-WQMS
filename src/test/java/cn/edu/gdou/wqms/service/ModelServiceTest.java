package cn.edu.gdou.wqms.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModelServiceTest {
    @Autowired
    private ModelService modelService;

    @Test
    public void trainModel() {
        String indicator = "DO";
        String method = "SVM";
        int uid = 1;
        modelService.trainModel(indicator, method, uid);
    }
}