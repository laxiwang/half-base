package com.halfroom.distribution.controller.branchsaler;

import com.halfroom.distribution.common.annotion.Permission;
import com.halfroom.distribution.common.controller.BaseController;
import com.halfroom.distribution.service.IBranchsalerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 活动控制器
 */
@Controller
@RequestMapping("/branchsalerActivity")
public class BranchsalerActivityController extends BaseController {
    private String prefix = "/system/dept/activity/";

    @Resource
    private IBranchsalerService branchsalerService;

    /**
     * 跳转阳光普照活动界面
     */
    @RequestMapping("/sunshineActivity_index")
    public String sunshineActivity_index() {
        return prefix + "sunshineActivity.html";
    }

    @RequestMapping(value = "/sunshineActivity")
    @ResponseBody
    public Object sunshineActivity(
            @RequestParam(name="timeType",required = false) Integer timeType ,
            @RequestParam(name="level",required = false) Integer level ) {
        if(timeType==null)
            timeType=1;
        if(level==null)
            level=2;
        return branchsalerService.orderActivityList(timeType,level);
    }
    /**
     * 跳转业绩冠军活动界面 / 上马活动
     */
    @RequestMapping("/performanceChampionActivity_index")
    public String performanceChampion_index() {
        return prefix + "performanceChampionActivity.html";
    }

    @RequestMapping(value = "/performanceChampionActivity")
    @ResponseBody
    public Object performanceChampionActivity(
            @RequestParam(name="timeType",required = false) Integer timeType ,
            @RequestParam(name="level",required = false) Integer level ) {
        if(timeType==null)
            timeType=-1;
        if(level==null)
            level=2;
        return branchsalerService.orderActivityList(timeType,level);
    }

}
