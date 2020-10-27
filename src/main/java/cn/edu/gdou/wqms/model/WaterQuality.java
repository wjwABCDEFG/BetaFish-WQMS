package cn.edu.gdou.wqms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "waterquality")
public class WaterQuality implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Float PH;
    private Float DO;
    private Float NH3N;
    private Float waterTemperature;
    private Date date;
    private Integer station;

    @Override
    public String toString() {
        return "WaterQuality{" +
                "id=" + id +
                ", PH=" + PH +
                ", DO=" + DO +
                ", NH3N=" + NH3N +
                ", WaterTemperature=" + waterTemperature +
                ", date=" + date +
                ", station=" + station +
                '}';
    }

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

    public Float getWaterTemperature() {
        return waterTemperature;
    }

    public void setWaterTemperature(Float waterTemperature) {
        this.waterTemperature = waterTemperature;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getStation() {
        return station;
    }

    public void setStation(Integer station) {
        this.station = station;
    }
}
