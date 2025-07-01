package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.CourseHomeworkSubmissionEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.ZuoyetijiaoVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.CourseHomeworkSubmissionView;


/**
 * 作业提交
 *
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public interface ZuoyetijiaoService extends IService<CourseHomeworkSubmissionEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ZuoyetijiaoVO> selectListVO(Wrapper<CourseHomeworkSubmissionEntity> wrapper);
   	
   	ZuoyetijiaoVO selectVO(@Param("ew") Wrapper<CourseHomeworkSubmissionEntity> wrapper);
   	
   	List<CourseHomeworkSubmissionView> selectListView(Wrapper<CourseHomeworkSubmissionEntity> wrapper);
   	
   	CourseHomeworkSubmissionView selectView(@Param("ew") Wrapper<CourseHomeworkSubmissionEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<CourseHomeworkSubmissionEntity> wrapper);

   	

}

