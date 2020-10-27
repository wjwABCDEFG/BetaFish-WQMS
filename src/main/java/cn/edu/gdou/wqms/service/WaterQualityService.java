package cn.edu.gdou.wqms.service;

import cn.edu.gdou.wqms.repository.WaterQualityRepository;
import cn.edu.gdou.wqms.model.WaterQuality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

@Service
public class WaterQualityService {
    @Autowired
    private WaterQualityRepository waterQualityRepository;

    public List<WaterQuality> findAllWaterQualities() {
        List<WaterQuality> res = waterQualityRepository.findAllByOrderByDate();
        return res;
    }

    public List<WaterQuality> findQueriedWaterQualities(String startDateStr, String endDateStr, Integer station) {
        DateFormat df = DateFormat.getDateInstance();
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = df.parse(startDateStr);
            endDate = df.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(station == -1) {
            return waterQualityRepository.findByDateBetweenOrderByDate(startDate, endDate);
        }
        return waterQualityRepository.findByDateBetweenAndStationOrderByDate(startDate, endDate, station);
    }

    public Boolean updateWaterQuality(WaterQuality waterQuality) {
        try {
            waterQualityRepository.save(waterQuality);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean deleteWaterQuality(Integer id) {
        try {
            waterQualityRepository.deleteById(id);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean addWaterQuality(WaterQuality waterQuality) {
        try {
            waterQualityRepository.save(waterQuality);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Integer> getAllStations() {
        return waterQualityRepository.findAllStations();
    }

    public Map<String, Object> getDataForPlot(int station, int period, String indicator) {
        Pageable pageable = PageRequest.of(0, period * 7);
        List<WaterQuality> total = waterQualityRepository.findByStationOrderByDateDesc(station, pageable);
        List<Float> specWaterQualities = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        for(WaterQuality waterQuality : total) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(waterQuality.getDate());
            String dateStr = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1 + "-" + calendar.get(Calendar.DAY_OF_MONTH));
            dates.add(dateStr);
            if(indicator.equalsIgnoreCase("ph")) {
                specWaterQualities.add(waterQuality.getPH());
            } else if(indicator.equalsIgnoreCase("do")) {
                specWaterQualities.add(waterQuality.getDO());
            } else if(indicator.equalsIgnoreCase("nh3n")){
                specWaterQualities.add(waterQuality.getNH3N());
            }else {
                specWaterQualities.add(waterQuality.getWaterTemperature());
            }
        }
        Collections.reverse(specWaterQualities);
        Collections.reverse(dates);
        Map<String, Object> res = new HashMap<>();
        res.put("waterquality", specWaterQualities);
        res.put("dates", dates);
        return res;
    }

    public Map<String, Object> getMeanDataForPredictionPlot(String indicator) {
        return null;
    }

    public List<WaterQuality> getRecentWaterQualities(int num) {
        Pageable pageable = PageRequest.of(0, num);
        List<WaterQuality> recent = waterQualityRepository.findAllByOrderByDateDesc(pageable);
        return recent;
    }

    public Map<String, Object> getDateForPrediction(String indicator) {
        Map<String, Object> res = new HashMap<>();
        Pageable pageable = PageRequest.of(0, 5);
        List<String> dateStrs = waterQualityRepository.findLastDates(pageable);
        List<Float> forPlot = new ArrayList<>();
        List<Map<String, Float>> forPrediction = new ArrayList<>();
        for(String dateStr : dateStrs) {
            List<WaterQuality> waterQualitiesByDate = getWaterQaulitiesBySpecificDate(dateStr);
//            Float average = calAverage(indicator, waterQualitiesByDate);
            Map<String, Float> allMethodsAvg = calEachAverage(waterQualitiesByDate);
            //画图5个点，预测只需要最大的3个点
            //forPlot:五个日期的指定方法对应的值
            forPlot.add(allMethodsAvg.get(indicator.toUpperCase()));
            //forPrediction:三个日期的所有方法对应的值（注意现在日期是反的）
            if (forPrediction.size() < 3){
                forPrediction.add(allMethodsAvg);
            }
        }

        Collections.reverse(dateStrs);
        Collections.reverse(forPlot);
        Collections.reverse(forPrediction);
        res.put("dates", dateStrs);
        res.put("forPlot", forPlot);
        res.put("forPrediction", forPrediction);
        return res;
    }

    private float calAverage(String indicator, List<WaterQuality> waterQualities) {
        float sum = 0;
        for (WaterQuality waterQuality : waterQualities) {
            if(indicator.equalsIgnoreCase("PH")) {
                sum = sum + waterQuality.getPH();
            } else if(indicator.equalsIgnoreCase("DO")) {
                sum = sum + waterQuality.getDO();
            } else if(indicator.equalsIgnoreCase("NH3N")){
                sum = sum + waterQuality.getNH3N();
            } else {
                sum = sum + waterQuality.getWaterTemperature();
            }
        }
        return sum / waterQualities.size();
    }

    private Map<String, Float> calEachAverage(List<WaterQuality> waterQualities) {
        Map<String, Float> map = new HashMap<>();
        float sumPH = 0;
        float sumDO = 0;
        float sumNH3N = 0;
        float sumWaterTemperature = 0;
        for (WaterQuality waterQuality : waterQualities) {
            sumPH += waterQuality.getPH();
            sumDO += waterQuality.getDO();
            sumNH3N += waterQuality.getNH3N();
            sumWaterTemperature += waterQuality.getWaterTemperature();
        }
        float avgPH = new BigDecimal((sumPH / waterQualities.size()))
                .setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        float avgDO = new BigDecimal((sumDO / waterQualities.size()))
                .setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        float avgNH3N = new BigDecimal((sumNH3N / waterQualities.size()))
                .setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        float avgWaterTemperature = new BigDecimal((sumWaterTemperature / waterQualities.size()))
                .setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        map.put("PH", avgPH);
        map.put("DO", avgDO);
        map.put("NH3N", avgNH3N);
        map.put("WATERTEMPERATURE", avgWaterTemperature);
        return map;
    }

    private List<WaterQuality> getWaterQaulitiesBySpecificDate(String dateStr) {
        String startDateStr = dateStr + " 00:00:00";
        String endDateStr = dateStr + " 23:59:59";
        DateFormat dateFormat = DateFormat.getDateInstance();
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = dateFormat.parse(startDateStr);
            endDate = dateFormat.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<WaterQuality> res = waterQualityRepository.findBySpecificDate(startDate, endDate);
        return res;
    }

}
