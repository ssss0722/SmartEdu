package com.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.service.TeacherService;
import com.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.annotation.IgnoreAuth;

import com.entity.CourseVideoEntity;
import com.entity.view.CourseVideoView;

import com.service.CourseVideoService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.service.StoreupService;
import com.entity.StoreUpEntity;

/**
 * 教学视频
 * 后端接口
 * @author 
 * @email 
 * @date 2024-03-05 11:41:23
 */
@RestController
@RequestMapping("/courseVideo")
public class CourseVideoController {
    @Autowired
    private CourseVideoService courseVideoService;

    @Autowired
    private StoreupService storeupService;

    @Autowired
    private TeacherService teacherService;
    



    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, CourseVideoEntity courseVideo,
                  String token,String tableName){
        Long id= JwtUtils.getUserIdFromToken(token);
		if(tableName.equals("user_teacher")) {
			courseVideo.settUsername(teacherService.selectById(id).getT_username());
		}
        EntityWrapper<CourseVideoEntity> ew = new EntityWrapper<CourseVideoEntity>();

		PageUtils page = courseVideoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, courseVideo), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, CourseVideoEntity courseVideo){
        EntityWrapper<CourseVideoEntity> ew = new EntityWrapper<CourseVideoEntity>();
		PageUtils page = courseVideoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, courseVideo), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list(CourseVideoEntity courseVideo){
       	EntityWrapper<CourseVideoEntity> ew = new EntityWrapper<CourseVideoEntity>();
      	ew.allEq(MPUtil.allEQMapPre( courseVideo, "cv"));
        return R.ok().put("data", courseVideoService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(CourseVideoEntity courseVideo){
        EntityWrapper<CourseVideoEntity> ew = new EntityWrapper<CourseVideoEntity>();
 		ew.allEq(MPUtil.allEQMapPre( courseVideo, "cv"));
		CourseVideoView courseVideoView =  courseVideoService.selectView(ew);
		return R.ok("查询教学视频成功").put("data", courseVideoView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        CourseVideoEntity courseVideo = courseVideoService.selectById(id);
		courseVideo.setClicknum(courseVideo.getClicknum()+1);
		courseVideo.setClicktime(new Date());
		courseVideoService.updateById(courseVideo);
        courseVideo = courseVideoService.selectView(new EntityWrapper<CourseVideoEntity>().eq("id", id));
        return R.ok().put("data", courseVideo);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        CourseVideoEntity courseVideo = courseVideoService.selectById(id);
		courseVideo.setClicknum(courseVideo.getClicknum()+1);
		courseVideo.setClicktime(new Date());
		courseVideoService.updateById(courseVideo);
        courseVideo = courseVideoService.selectView(new EntityWrapper<CourseVideoEntity>().eq("id", id));
        return R.ok().put("data", courseVideo);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/add")
    public R save(@RequestBody CourseVideoEntity courseVideo,String token){
        Long id=JwtUtils.getUserIdFromToken(token);
        String role=JwtUtils.getRoleFromToken(token);
        if(role.equals("teacher"))
        {
            courseVideo.settUsername(teacherService.selectById(id).getT_username());
            courseVideo.settName(teacherService.selectById(id).getT_name());
        }
        courseVideo.setPublishedAt(new Date());
        courseVideoService.insert(courseVideo);
        return R.ok("添加成功");
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody CourseVideoEntity courseVideo){
        courseVideoService.updateById(courseVideo);//全部更新
        return R.ok("更新成功");
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        courseVideoService.deleteBatchIds(Arrays.asList(ids));
        return R.ok("删除成功");
    }
    
	
	/**
     * 前端智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params, CourseVideoEntity jiaoxueshipin, HttpServletRequest request, String pre){
        EntityWrapper<CourseVideoEntity> ew = new EntityWrapper<CourseVideoEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = entry.getKey();
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		params.put("sort", "clicknum");
        params.put("order", "desc");
		PageUtils page = courseVideoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jiaoxueshipin), params), params));
        return R.ok().put("data", page);
    }


    /**
     * 协同算法（按收藏推荐）
     */
    @RequestMapping("/autoSort2")
    public R autoSort2(@RequestParam Map<String, Object> params, CourseVideoEntity jiaoxueshipin, HttpServletRequest request){
        String userId = request.getSession().getAttribute("userId").toString();
        String inteltypeColumn = "kechengleibie";
        List<StoreUpEntity> storeups = storeupService.selectList(new EntityWrapper<StoreUpEntity>().eq("type", 1).eq("userid", userId).eq("tablename", "jiaoxueshipin").orderBy("addtime", false));
        List<String> inteltypes = new ArrayList<String>();
        Integer limit = params.get("limit")==null?10:Integer.parseInt(params.get("limit").toString());
        List<CourseVideoEntity> jiaoxueshipinList = new ArrayList<CourseVideoEntity>();
        //去重
        if(storeups!=null && storeups.size()>0) {
            for(StoreUpEntity s : storeups) {
                jiaoxueshipinList.addAll(courseVideoService.selectList(new EntityWrapper<CourseVideoEntity>().eq(inteltypeColumn, s.getInteltype())));
            }
        }
        EntityWrapper<CourseVideoEntity> ew = new EntityWrapper<CourseVideoEntity>();
        params.put("sort", "id");
        params.put("order", "desc");
        PageUtils page = courseVideoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jiaoxueshipin), params), params));
        List<CourseVideoEntity> pageList = (List<CourseVideoEntity>)page.getList();
        if(jiaoxueshipinList.size()<limit) {
            int toAddNum = (limit-jiaoxueshipinList.size())<=pageList.size()?(limit-jiaoxueshipinList.size()):pageList.size();
            for(CourseVideoEntity o1 : pageList) {
                boolean addFlag = true;
                for(CourseVideoEntity o2 : jiaoxueshipinList) {
                    if(o1.getId().intValue()==o2.getId().intValue()) {
                        addFlag = false;
                        break;
                    }
                }
                if(addFlag) {
                    jiaoxueshipinList.add(o1);
                    if(--toAddNum==0) break;
                }
            }
        } else if(jiaoxueshipinList.size()>limit) {
            jiaoxueshipinList = jiaoxueshipinList.subList(0, limit);
        }
        page.setList(jiaoxueshipinList);
        return R.ok().put("data", page);
    }








}
