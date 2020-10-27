/**
 * @author wjw
 * @date 2020/3/3 18:14
 */
package cn.edu.gdou.wqms.controller;

import cn.edu.gdou.wqms.model.Warning;
import cn.edu.gdou.wqms.service.WarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/warning")
public class WarningController {

    @Autowired
    WarningService service;


    @RequestMapping("/findByUser")
    public Warning findByUser(Integer uid){
        return service.findByUser(uid);
    }


    @RequestMapping("/save")
    public Warning save(Warning warning, Integer uid){
        return service.save(warning, uid);
    }

    @RequestMapping("/turnOff")
    public void turnOff(Integer id){
        service.turnOff(id);
    }
}
