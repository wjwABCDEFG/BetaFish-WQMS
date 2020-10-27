/**
 * @author wjw
 * @date 2020/3/3 18:28
 */
package cn.edu.gdou.wqms.service;

import cn.edu.gdou.wqms.model.Warning;
import cn.edu.gdou.wqms.repository.UserRepository;
import cn.edu.gdou.wqms.repository.WarningRepository;
import cn.edu.gdou.wqms.utils.MailUtils;
import cn.edu.gdou.wqms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarningService {

    @Autowired
    WarningRepository repository;
    @Autowired
    UserRepository userRepository;

    public Warning findByUser(Integer uid){
        return repository.findByUser(userRepository.findUserById(uid));
    }

    public Warning save(Warning warning, Integer uid){
        Warning w = repository.findByUser(userRepository.findUserById(uid));
        if (w == null){
            //添加
            warning.setUser(userRepository.findUserById(uid));
            return repository.save(warning);
        }else {
            //更新
            return update(uid, warning.getPH(), warning.getDO(), warning.getNH3N(), warning.getWaterTemperature());
        }
    }

    public Warning update(Integer userid, Float PH, Float DO, Float NH3N,  Float waterTemperature){
        Warning warning = repository.findByUser(userRepository.findUserById(userid));
        if (PH != null){
            warning.setPH(PH);
        }
        if (DO != null){
            warning.setDO(DO);
        }
        if (NH3N != null){
            warning.setNH3N(NH3N);
        }
        if (waterTemperature != null){
            warning.setWaterTemperature(waterTemperature);
        }
        return repository.save(warning);
    }

    public void turnOff(Integer id){
        repository.turnOff(id);
    }

    public void warn(User user, String target, Float value){
        String content = "温馨提醒，根据水质管理系统的监测，" + target + "值为" + value + "，已经超过预警值,请做好应对措施。";
        MailUtils.sendMail(user.getEmail(), content, "水质数据预警提醒");
    }
}