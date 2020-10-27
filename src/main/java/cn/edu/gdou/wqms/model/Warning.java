/**
 * @author wjw
 * @date 2020/3/3 18:07
 */
package cn.edu.gdou.wqms.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "warning")
public class Warning implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Float PH;
    private Float DO;
    private Float NH3N;
    private Float waterTemperature;



    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getPH() {
        return PH;
    }

    public void setPH(Float PH) {
        this.PH = PH;
    }

    public Float getDO() {
        return DO;
    }

    public void setDO(Float DO) {
        this.DO = DO;
    }

    public Float getNH3N() {
        return NH3N;
    }

    public void setNH3N(Float NH3N) {
        this.NH3N = NH3N;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Float getWaterTemperature() {
        return waterTemperature;
    }

    public void setWaterTemperature(Float waterTemperature) {
        this.waterTemperature = waterTemperature;
    }

    @Override
    public String toString() {
        return "Warning{" +
                "id=" + id +
                ", PH=" + PH +
                ", DO=" + DO +
                ", NH3N=" + NH3N +
                ", waterTemperature=" + waterTemperature +
                ", user=" + user +
                '}';
    }
}
