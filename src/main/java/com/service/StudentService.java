package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.StudentEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.XueshengVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.StudentView;


/**
 * 学生
 *
 * @author 
 * @email 
 * @date 2024-03-05 11:41:23
 */
public interface StudentService extends IService<StudentEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<XueshengVO> selectListVO(Wrapper<StudentEntity> wrapper);
   	
   	XueshengVO selectVO(@Param("ew") Wrapper<StudentEntity> wrapper);
   	
   	List<StudentView> selectListView(Wrapper<StudentEntity> wrapper);
   	
   	StudentView selectView(@Param("ew") Wrapper<StudentEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<StudentEntity> wrapper);

   	

}

