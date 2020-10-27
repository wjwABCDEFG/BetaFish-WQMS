/**
 * @author wjw
 * @date 2020/3/3 18:15
 */
package cn.edu.gdou.wqms.repository;

import cn.edu.gdou.wqms.model.Warning;
import cn.edu.gdou.wqms.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface WarningRepository extends Repository<Warning, Integer> {
    public Warning findByUser(User user);
    public Warning save(Warning warning);
    @Query("UPDATE Warning SET PH = 999, DO = 999, NH3N = 999, waterTemperature = 999 WHERE ID = ?1")
    public void turnOff(Integer id);
}
