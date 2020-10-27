package cn.edu.gdou.wqms.controller;

import cn.edu.gdou.wqms.model.Model;
import cn.edu.gdou.wqms.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/model")
public class ModelController {
    @Autowired
    private ModelService modelService;

    @GetMapping("/training")
    public Map<String, Object> trainModel(@RequestParam(value = "indicator") String indicator,
          @RequestParam(value = "method") String method, @RequestParam(value = "uid") Integer uid) {
        indicator = indicator.toUpperCase();
        method = method.toUpperCase();
        return modelService.trainModel(indicator, method, uid);
    }

    @GetMapping("/available")
    public List<Model> getAvailableModel(@RequestParam(value = "indicator") String indicator,
                                            @RequestParam(value = "method") String method) {
        if(method.equalsIgnoreCase("all")) {
            return modelService.getAvailableModel(indicator);
        }
        return modelService.getAvailableModel(indicator, method);
    }

    @GetMapping("/list")
    public List<String> getAllModelsByTarget(@RequestParam(value = "indicator") String indicator) {
        return modelService.getAllModelsByTarget(indicator);
    }

    @GetMapping("/prediction")
    public Map<String, Object> predictNextDay(@RequestParam(value = "id") Integer id,
                    @RequestParam(value = "indicator") String indicator,
                    @RequestParam(value = "uid") Integer uid) {
        return modelService.predictNextDay(id, indicator, uid);
    }

    @PostMapping("/delete/{id}")
    public Map<String, Object> deleteModel(@PathVariable int id) {
        Map<String, Object> resp = new HashMap<>();
        if(modelService.deleteModel(id)) {
            resp.put("status", "success");
        } else {
            resp.put("status", "failure");
        }
        return resp;
    }
}
