package com.entity.view;

import com.entity.ExamRecordEntity;
import com.entity.HomeworkRecordEntity;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

public class HomeworkRecordView extends HomeworkRecordEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public HomeworkRecordView(){
    }

    public HomeworkRecordView(HomeworkRecordEntity homeworkRecordEntity){
        try {
            BeanUtils.copyProperties(this, homeworkRecordEntity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private BigDecimal accuracy;

    private Integer anum;

    private Integer bnum;

    private Integer cnum;

    private Integer dnum;

    public BigDecimal getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(BigDecimal accuracy) {
        this.accuracy = accuracy;
    }

    public Integer getAnum() {
        return anum;
    }

    public void setAnum(Integer anum) {
        this.anum = anum;
    }

    public Integer getBnum() {
        return bnum;
    }

    public void setBnum(Integer bnum) {
        this.bnum = bnum;
    }

    public Integer getCnum() {
        return cnum;
    }

    public void setCnum(Integer cnum) {
        this.cnum = cnum;
    }

    public Integer getDnum() {
        return dnum;
    }

    public void setDnum(Integer dnum) {
        this.dnum = dnum;
    }
}
