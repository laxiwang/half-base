package com.halfroom.distribution.controller.echarts;

import com.halfroom.distribution.common.controller.BaseController;
import com.halfroom.distribution.dao.GeneralUserDao;
import com.halfroom.distribution.service.IEchartsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 图形控制器
 */
@Controller
@RequestMapping("/echarts")
public class EchartsController  extends BaseController {

    @Resource
    private IEchartsService iEchartsService;
    @Resource
    private GeneralUserDao userDao;

    private static String PREFIX = "/system/echarts";

    /**
     * 新增用户统计图--天
     * @return
     */
    @RequestMapping("/userNewsEchartsDay")
    public String userNewsEcharts() {
        return PREFIX + "/usernewsechartsday.html";
    }

    @RequestMapping("/userNewsEchartsDay_do")
    @ResponseBody
    public Object userNewsEchartsDay_do(
            @RequestParam(name = "beginTime", required = false, defaultValue = "") String beginTime,
            @RequestParam(name = "endTime", required = false, defaultValue = "") String endTime
    ) {
        return  iEchartsService.userNewsEchartsDay(beginTime,endTime);
    }
    /**
     * 新增用户统计图--天
     * @return
     */
    @RequestMapping("/userMap")
    public String userMap() {
        return PREFIX + "/usermap.html";
    }


    @RequestMapping("/userMap_do")
    @ResponseBody
    public Object userMap_do() {
        List list =  new ArrayList();
        list.addAll(userDao.selectProvince());
        list.addAll(userDao.selectCity());
        return list;
    }
}
