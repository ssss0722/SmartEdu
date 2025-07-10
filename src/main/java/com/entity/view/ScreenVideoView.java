package com.entity.view;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.entity.CourseVideoEntity;
import com.entity.ScreenVideoEntity;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

@TableName("screen_video")
public class ScreenVideoView extends ScreenVideoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableField("t_name")
    private String tName;

    public ScreenVideoView(){
    }

    public ScreenVideoView(ScreenVideoEntity screenVideoEntity){
        try {
            BeanUtils.copyProperties(this, screenVideoEntity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }
}
