/**
 * @author wjw
 * @date 2020/3/6 17:28
 */
package cn.edu.gdou.wqms.service;

import cn.edu.gdou.wqms.model.User;
import cn.edu.gdou.wqms.utils.MailUtils;
import org.junit.Test;

public class WarningServiceTest {

    @Test
    public void warnTest(){
        User user = new User();
        user.setEmail("2276230432@qq.com");
        String target = "PH";
        Float value = 20.5f;
        String content = "温馨提醒，根据水质管理系统的监测，" + target + "值为" + value + "，已经超过预警值,请做好应对措施。";
        MailUtils.sendMail(user.getEmail(), content, "水质数据预警提醒");
    }
}
