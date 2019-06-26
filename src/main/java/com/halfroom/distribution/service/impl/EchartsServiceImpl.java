package com.halfroom.distribution.service.impl;

import com.halfroom.distribution.core.shiro.ShiroKit;
import com.halfroom.distribution.core.util.DateTimeUtil;
import com.halfroom.distribution.dao.GeneralUserDao;
import com.halfroom.distribution.dao.UserOrderDao;
import com.halfroom.distribution.persistence.dao.BranchsalerMapper;
import com.halfroom.distribution.persistence.model.Branchsaler;
import com.halfroom.distribution.service.IEchartsService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class EchartsServiceImpl implements IEchartsService {
    @Resource
    private BranchsalerMapper branchsalerMapper;
    @Resource
    private GeneralUserDao generalUserDao;
    @Resource
    private UserOrderDao userOrderDao;
    @Override
    public Map<String, Object> userNewsEchartsDay(String beginTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int banchid = ShiroKit.getUser().getBranchsalerId();
        Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
        if (branchsaler == null) {
            return null;
        }

        Map<String, Object> resMap = new HashMap<>();

        Map<String, Object> params = new HashMap<>();
        if (branchsaler != null && branchsaler.getLevel() != 0) {
            params.put("branchsaler", branchsaler.getId());
        }
        if(StringUtils.isBlank(beginTime) || StringUtils.isBlank(endTime)) {
            beginTime= DateTimeUtil.getMinMonthDate();
            endTime=sdf.format(DateTimeUtil.getEndTimeOfDay(new Date()));
        }




            Date dStart = null;
            Date dEnd = null;
            try {
                dStart = sdf.parse(beginTime);
                dEnd = sdf.parse(endTime);
            } catch (
                    ParseException e) {
                e.printStackTrace();
            }
            List<Date> dateList = DateTimeUtil.findDates(dStart, dEnd);
            String xData[]=new String[dateList.size()];
            String sData1[]=new String[dateList.size()];
            String sData2[]=new String[dateList.size()];
            String sData3[]=new String[dateList.size()];
            for (int i=0;i< dateList.size();i++ ) {
                String stime=sdf.format(dateList.get(i));
                String etime=sdf.format(dateList.get(i))+" 23:59:59";
                params.put("beginTime",stime);
                params.put("endTime",etime);
                xData[i]=stime;
                String zhuce =generalUserDao.countGeneralUser(params).toString();
                sData1[i]= zhuce;
                params.put("flag",false);
                String dingdan=userOrderDao.selectCountUser(params).size()+"";
                sData2[i]=dingdan;
                if(Integer.parseInt(zhuce)==0){
                    sData3[i]=0+"";
                }else{
                    sData3[i]= new BigDecimal((double)(Integer.parseInt(dingdan) / (double)Integer.parseInt(zhuce)) * 100).setScale(2, BigDecimal.ROUND_HALF_UP)+"";
                }

            }
            resMap.put("xData",xData);
            resMap.put("sData1",sData1);
            resMap.put("sData2",sData2);
            resMap.put("sData3",sData3);
        return resMap;
    }
}
