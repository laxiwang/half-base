package com.halfroom.distribution.service.impl;

import com.halfroom.distribution.core.shiro.ShiroKit;
import com.halfroom.distribution.core.util.DateTimeUtil;
import com.halfroom.distribution.dao.GeneralUserDao;
import com.halfroom.distribution.dao.ReportFormDao;
import com.halfroom.distribution.persistence.dao.BranchsalerMapper;
import com.halfroom.distribution.persistence.model.Branchsaler;
import com.halfroom.distribution.service.IReportFormService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ReportFormServiceImpl implements IReportFormService {
    @Resource
    private BranchsalerMapper branchsalerMapper;
    @Resource
    private ReportFormDao reportFormDao;
    @Resource
    private GeneralUserDao userDao;
    @Override
    public List<Map<String, Object>> reportFormUser(String condition, String beginTime, String endTime) {
        List<Map<String,Object>> resList = new ArrayList<>();
        Map<String,Object>  params = new HashMap<>();
        int banchid = ShiroKit.getUser().getBranchsalerId();
        Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
        if (branchsaler == null) {
            return null;
        }
        if (branchsaler != null && branchsaler.getLevel() != 0) {
            params.put("branchsaler", branchsaler.getId());
        }
        Date beginTimeDate=DateTimeUtil.parseDate(DateTimeUtil.getMinMonthDate(),DateTimeUtil.DEFAULT_FORMAT_DATE);
        String endTimeDate=DateTimeUtil.getMaxMonthDate();
        endTimeDate=endTimeDate.substring(0,endTimeDate.indexOf(" "))+" 23:59:59";


        if((StringUtils.isBlank(beginTime)||"null".equals(beginTime))||(StringUtils.isBlank(endTime)||"null".equals(endTime))){
            params.put("beginTime",beginTimeDate);
            params.put("endTime",endTimeDate);
        } else{
            params.put("beginTime",beginTime);
            params.put("endTime",endTime+" 23:59:59");
        }

       //注册用户数
        int  registerUserNum =reportFormDao.selectRegisterUserNum(params);
        Map<String,Object> resMap1 = new HashMap<>();
        resMap1.put("name","注册用户数");
        resMap1.put("value",registerUserNum+"");
        resList.add(resMap1);


        //活跃用户数
        int ActiveNum=reportFormDao.selectActiveNum(params);

      /*  Map<String,Object> resMap6 = new HashMap<>();
        resMap6.put("name","活跃用户数");
        resMap6.put("value",ActiveNum+"");
        resList.add(resMap6);*/

        //活跃注册用户数
        int ActiveNum2=reportFormDao.selectActiveNum2(params);
        //体验  --flag
        int  supGeneralUser=reportFormDao.supGeneralUser(params);

        Map<String,Object> resMap62 = new HashMap<>();
        resMap62.put("name","体验用户数");
        resMap62.put("value",supGeneralUser+"");
        resList.add(resMap62);

        //付费用户数 包含实体卡
        params.put("isCard",true);
        params.put("isRepeat",false);
        int payUserNumIsCard=reportFormDao.selectPayUserNumOrderTime(params).size();
        Map<String,Object> resMap2 = new HashMap<>();
        resMap2.put("name","付费用户数-订单时间");
        resMap2.put("value",payUserNumIsCard+"");
        resList.add(resMap2);

        int payUserNumUserTime=reportFormDao.selectPayUserNumUserTime(params).size();
        Map<String,Object> resMap21 = new HashMap<>();
        resMap21.put("name","付费用户数-注册时间");
        resMap21.put("value",payUserNumUserTime+"");
        resList.add(resMap21);

        Map<String,Object> resMap22 = new HashMap<>();
        resMap22.put("name","付费用户转化率-注册时间");
        if(registerUserNum==0||payUserNumUserTime==0){
            resMap22.put("value","0%");
        }else{
            resMap22.put("value",new BigDecimal(((double)payUserNumUserTime / (double)ActiveNum2) * 100).setScale(2, BigDecimal.ROUND_HALF_UP)   +"%");
        }

        resList.add(resMap22);


        //付费复购数 用户数 包含实体卡
        params.put("isRepeat",true);
        int payUserNumIsCardIsRepeat=reportFormDao.selectPayUserNumOrderTime(params).size();

        Map<String,Object> resMap41 = new HashMap<>();
        resMap41.put("name","付费用户复购数");
        resMap41.put("value",payUserNumIsCardIsRepeat+"");
        resList.add(resMap41);

        Map<String,Object> resMap4 = new HashMap<>();
        resMap4.put("name","付费用户复购率");
        if(payUserNumIsCard==0||payUserNumIsCardIsRepeat==0){
            resMap4.put("value","0%");
        }else{
            resMap4.put("value",new BigDecimal(((double)payUserNumIsCardIsRepeat / (double)payUserNumIsCard) * 100).setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
        }

        resList.add(resMap4);


        //线上付费用户数
        params.put("isCard",false);
        params.put("isRepeat",false);
        int payUserNum=reportFormDao.selectPayUserNumOrderTime(params).size();
        Map<String,Object> resMap3 = new HashMap<>();
        resMap3.put("name","线上付费用户数");
        resMap3.put("value",payUserNum+"");
        resList.add(resMap3);



        //线上付费用户数   复购数
        params.put("isCard",false);
        params.put("isRepeat",true);
        int payUserNumIsRepeat=reportFormDao.selectPayUserNumOrderTime(params).size();

        Map<String,Object> resMap51 = new HashMap<>();
        resMap51.put("name","线上付费用户复购数");
        resMap51.put("value",payUserNumIsRepeat+"");
        resList.add(resMap51);

        Map<String,Object> resMap5 = new HashMap<>();
        resMap5.put("name","线上复购率");
        if(payUserNum==0||payUserNumIsRepeat==0){
            resMap5.put("value","0%");
        }else{
            resMap5.put("value",new BigDecimal(((double)payUserNumIsRepeat / (double)payUserNum)*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"%");
        }

        resList.add(resMap5);
        return resList;
    }

    @Override
    public List<Map<String, Object>> reportFormOrder(String condition, String beginTime, String endTime) {
        List<Map<String,Object>> resList = new ArrayList<>();
        Map<String,Object>  params = new HashMap<>();
        int banchid = ShiroKit.getUser().getBranchsalerId();
        Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
        if (branchsaler == null) {
            return null;
        }
        if (branchsaler != null && branchsaler.getLevel() != 0) {
            params.put("branchsaler", branchsaler.getId());
        }
        Date beginTimeDate=DateTimeUtil.parseDate(DateTimeUtil.getMinMonthDate(),DateTimeUtil.DEFAULT_FORMAT_DATE);
        String endTimeDate=DateTimeUtil.getMaxMonthDate();
        endTimeDate=endTimeDate.substring(0,endTimeDate.indexOf(" "))+" 23:59:59";

        params.put("beginTime",beginTime);
        params.put("endTime",endTime);

        if((StringUtils.isBlank(beginTime)||"null".equals(beginTime))||(StringUtils.isBlank(endTime)||"null".equals(endTime))){
            params.put("beginTime",beginTimeDate);
            params.put("endTime",endTimeDate);
        }
        params.put("isCard",null);
        params.put("isRepeat",null);
        //ios 订单数
        params.put("paysource",1);
        int ios =reportFormDao.selectOrderNum(params);
        Map<String,Object> resMap7 = new HashMap<>();
        resMap7.put("name","ios订单");
        resMap7.put("value",ios+"");
        resList.add(resMap7);
        //andriod 订单数
        params.put("paysource",2);
        int andriod =reportFormDao.selectOrderNum(params);
        Map<String,Object> resMap8 = new HashMap<>();
        resMap8.put("name","andriod订单");
        resMap8.put("value",andriod+"");
        resList.add(resMap8);
        //h5 公众号订单数
        params.put("paysource",0);
        int h5 =reportFormDao.selectOrderNum(params);
        Map<String,Object> resMap9 = new HashMap<>();
        resMap9.put("name","公众号订单");
        resMap9.put("value",h5+"");
        resList.add(resMap9);
        //课程包数 不包含实体卡
        params.put("paysource",null);
        params.put("isCard",false);
        int classNum  =reportFormDao.selectOrderNum(params);
        Map<String,Object> resMap10 = new HashMap<>();
        resMap10.put("name","线上订单数量");
        resMap10.put("value",classNum+"");
        resList.add(resMap10);
        // 198 实体卡订单数
        params.put("paysource",3);
        params.put("isCard",null);
        params.put("fee",198);
        int card198  =reportFormDao.selectOrderNum(params);
        Map<String,Object> resMap11 = new HashMap<>();
        resMap11.put("name","198实体卡");
        resMap11.put("value",card198+"");
        resList.add(resMap11);
        // 298 实体卡订单数
        params.put("fee",298);
        int card298  =reportFormDao.selectOrderNum(params);
        Map<String,Object> resMap12 = new HashMap<>();
        resMap12.put("name","298实体卡");
        resMap12.put("value",card298+"");
        resList.add(resMap12);

        Map<String,Object> resMap13 = new HashMap<>();
        resMap13.put("name","总订单数");
        resMap13.put("value",(card298+card198+classNum)+"");
        resList.add(resMap13);
        return resList;
    }
}
