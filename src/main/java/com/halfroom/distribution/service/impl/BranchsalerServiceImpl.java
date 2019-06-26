package com.halfroom.distribution.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.halfroom.distribution.core.shiro.ShiroKit;
import com.halfroom.distribution.core.util.Convert;
import com.halfroom.distribution.core.util.DateTimeUtil;
import com.halfroom.distribution.core.util.ExcelUtil;
import com.halfroom.distribution.dao.BranchsalerAccountChangeDao;
import com.halfroom.distribution.dao.BranchsalerDao;
import com.halfroom.distribution.dao.GeneralUserDao;
import com.halfroom.distribution.dao.UserOrderDao;
import com.halfroom.distribution.persistence.dao.BranchsalerMapper;
import com.halfroom.distribution.persistence.model.Branchsaler;
import com.halfroom.distribution.persistence.vo.*;
import com.halfroom.distribution.service.IBranchsalerService;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.ibatis.reflection.wrapper.BaseWrapper;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class BranchsalerServiceImpl implements IBranchsalerService {

    @Resource
    private BranchsalerMapper branchsalerMapper;
    @Resource
    private BranchsalerDao deptDao;
    @Resource
    private GeneralUserDao generalUserDao;
    @Resource
    private UserOrderDao userOrderDao;
    @Resource
    private BranchsalerAccountChangeDao branchsalerAccountChangeDao;

    @Override
    public void deleteDept(Integer deptId) {
        Branchsaler branchsaler = branchsalerMapper.selectById(deptId);
        Wrapper<Branchsaler> wrapper = new EntityWrapper<>();
        wrapper = wrapper.like("pids", "%[" + branchsaler.getId() + "]%");
        List<Branchsaler> subDepts = branchsalerMapper.selectList(wrapper);
        for (Branchsaler temp : subDepts) {
            temp.setStatus(1);
            temp.updateById();
        }
        branchsaler.setStatus(1);
        branchsaler.updateById();
    }

    @Override
    public List<Map<String, Object>> agentOrderlist(String beginTime, String endTime, String condition) {
        int banchid = ShiroKit.getUser().getBranchsalerId();
        Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
        Map<String, Object> map = new HashMap<>();
        if (branchsaler == null) {
            return null;
        }
        map.put("branchsaler", branchsaler.getId());
        if (StringUtils.isNotBlank(condition) && !"null".equals(condition)) {
            map.put("condition", condition);
        }
        List<Map<String, Object>> list = this.deptDao.agentlist(map);
        if (StringUtils.isNotBlank(beginTime) && !"null".equals(beginTime)) {
            map.put("beginTime",beginTime);
        }
        if (StringUtils.isNotBlank(endTime) && !"null".equals(endTime)) {
            map.put("endTime",endTime+" 23:59:59");
        }
        if (list.size() > 0) {
            if (list.get(0).get("levelName").equals("省级分会") || list.get(0).get("levelName").equals("市级分会")) {
                list.get(0).put("pid", 0);
            }
        }
        // 查询订单
        for (Map<String, Object> resMap : list) {
            map.put("flag", false);
            map.put("branchsaler", resMap.get("id"));
            //h5 自然流量
            map.put("paysource",0);
            map.put("payrole",2);
            int h51 = userOrderDao.selectCount(map);

            // 推广
            map.put("paysource",null);
            map.put("payrole",0);
            int tuiguang = userOrderDao.selectCount(map);

            // 销售
            map.put("paysource",null);
            map.put("payrole",1);
            int xiaoshou = userOrderDao.selectCount(map);
            //知识送礼
            map.put("paysource",null);
            map.put("payrole",3);
            int give = userOrderDao.selectCount(map);

            //ios支付  --以下-自然流量
            map.put("payrole",2);
            map.put("paysource",1);
            int ios = userOrderDao.selectCount(map);
            //android支付
            map.put("paysource",2);
            int android = userOrderDao.selectCount(map);
            //实体卡支付
            map.put("paysource",3);
            int card = userOrderDao.selectCount(map);
            resMap.put("h51", h51);
            resMap.put("tuiguang", tuiguang);
            resMap.put("xiaoshou", xiaoshou);
            resMap.put("ios", ios);
            resMap.put("android", android);
            resMap.put("card", card);
            resMap.put("give", give);
            resMap.put("sum", h51+tuiguang+xiaoshou+ios+android+card+give);
        }

        if (list.size() > 0) {
            if (list.get(0).get("levelName").equals("省级分会") || list.get(0).get("levelName").equals("市级分会")) {
                list.get(0).put("pid", 0);
            }
            if (list.get(0).get("fullname").equals(branchsaler.getFullname())) {
                Map<String, Object> map2 = new HashMap<>();
                if(branchsaler.getId()!=1){
                    map.put("branchsaler", branchsaler.getId());
                    map.put("flag", true);
                }else{
                    map.put("branchsaler", null);
                    map.put("flag", null);
                }
                //h5 自然流量
                map.put("paysource",0);
                map.put("payrole",2);
                int h51 = userOrderDao.selectCount(map);

                // 推广
                map.put("paysource",null);
                map.put("payrole",0);
                int tuiguang = userOrderDao.selectCount(map);

                // 销售
                map.put("paysource",null);
                map.put("payrole",1);
                int xiaoshou = userOrderDao.selectCount(map);
                //知识送礼
                map.put("paysource",null);
                map.put("payrole",3);
                int give = userOrderDao.selectCount(map);

                //ios支付  --以下-自然流量
                map.put("payrole",2);
                map.put("paysource",1);
                int ios = userOrderDao.selectCount(map);
                //android支付
                map.put("paysource",2);
                int android = userOrderDao.selectCount(map);
                //实体卡支付
                map.put("paysource",3);
                int card = userOrderDao.selectCount(map);
                map2.put("h51", h51);
                map2.put("tuiguang", tuiguang);
                map2.put("xiaoshou", xiaoshou);
                map2.put("ios", ios);
                map2.put("android", android);
                map2.put("card", card);
                map2.put("give", give);
                map2.put("sum", h51+tuiguang+xiaoshou+ios+android+card+give);
                map2.put("id", 0);
                map2.put("simplename", branchsaler.getSimplename() + "-总计");
                map2.put("fullname", branchsaler.getFullname());
                map2.put("levelName", list.get(0).get("levelName"));
                map2.put("pid", -1);
                map2.put("pids", -1);
                list.add(0, map2);
            }
        }
        return list;
    }

    @Override
    public void agentOrderlistExecl(HttpServletResponse response, ServletOutputStream outputStream, String beginTime, String endTime, String condition) {
        List<Map<String, Object>> list = agentOrderlist(beginTime, endTime, condition);

        List<AgentOrderVo> agentOrderVos = new ArrayList<>();
        for (Map<String, Object> map2 : list) {
            AgentOrderVo agentOrderVo = (AgentOrderVo) Convert.mapToObject(map2, AgentOrderVo.class);
            agentOrderVos.add(agentOrderVo);
        }
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        ExcelUtil<AgentOrderVo> myExcel = new ExcelUtil<>();
        String[] headerColumns = {"分会简称", "分会全称", "分会级别", "销售总计",
                "推广大使", "销售大使", "知识送礼", "公众号", "ios"
                , "android", "实体卡"};
        String[] fieldColumns = {"simplename", "fullname", "levelName", "sum",
                "tuiguang", "xiaoshou", "give", "h51","ios",
                "android", "card"};
        try {
            HSSFSheet creatAuditSheet = myExcel.creatAuditSheet(hssfWorkbook, "分会订单统计", agentOrderVos, headerColumns, fieldColumns, false);
            myExcel.generateHeaders(hssfWorkbook, creatAuditSheet, headerColumns);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExcelUtil.doResponse(response, outputStream, "分会订单统计", hssfWorkbook);
    }

    @Override
    public void agentChangelistExecl(HttpServletResponse response, ServletOutputStream outputStream, String beginTime, String endTime, String condition) {
        List<Map<String, Object>> list = agentAccountChangelist(beginTime, endTime, condition);

        List<AgentChangeVo> agentChangeVos = new ArrayList<>();
        for (Map<String, Object> map2 : list) {
            AgentChangeVo agentChangeVo = (AgentChangeVo) Convert.mapToObject(map2, AgentChangeVo.class);
            agentChangeVos.add(agentChangeVo);
        }
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        ExcelUtil<AgentChangeVo> myExcel = new ExcelUtil<>();
        String[] headerColumns = {"分会简称", "分会全称", "分会级别", "分润总计",
                 "推广大使", "销售大使", "知识送礼", "公众号","ios"
                , "android"};
        String[] fieldColumns = {"simplename", "fullname", "levelName", "sum",
                "h51", "tuiguang", "xiaoshou", "give", "ios",
                "android"};
        try {
            HSSFSheet creatAuditSheet = myExcel.creatAuditSheet(hssfWorkbook, "分会分润统计", agentChangeVos, headerColumns, fieldColumns, false);
            myExcel.generateHeaders(hssfWorkbook, creatAuditSheet, headerColumns);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExcelUtil.doResponse(response, outputStream, "分会分润统计", hssfWorkbook);
    }

    @Override
    public List<Map<String, Object>> agentAccountChangelist(String beginTime, String endTime, String condition) {
        int banchid = ShiroKit.getUser().getBranchsalerId();
        Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
        Map<String, Object> map = new HashMap<>();
        if (branchsaler == null) {
            return null;
        }
        map.put("branchsaler", branchsaler.getId());
        if (StringUtils.isNotBlank(condition) && !"null".equals(condition)) {
            map.put("condition", condition);
        }
        List<Map<String, Object>> list = this.deptDao.agentlist(map);
        if (StringUtils.isNotBlank(beginTime) && !"null".equals(beginTime)) {
            map.put("beginTime", beginTime);
        }
        if (StringUtils.isNotBlank(endTime) && !"null".equals(endTime)) {
            map.put("endTime", endTime+" 23:59:59");
        }
        if (list.size() > 0) {
            if (list.get(0).get("levelName").equals("省级分会") || list.get(0).get("levelName").equals("市级分会")) {
                list.get(0).put("pid", 0);
            }
        }
        // 查询分润记录
        for (Map<String, Object> resMap : list) {
            map.put("branchsaler", resMap.get("id"));
            //map.put("type", 0);

            // 推广
            map.put("paysource", null);
            map.put("payrole", 0);
            BigDecimal tuiguang = branchsalerAccountChangeDao.selectSum(map).setScale(2, BigDecimal.ROUND_HALF_UP);

            //  销售
            map.put("paysource", null);
            map.put("payrole", 1);
            BigDecimal xiaoshou = branchsalerAccountChangeDao.selectSum(map).setScale(2, BigDecimal.ROUND_HALF_UP);
            //知识送礼
            map.put("payrole", 3);
            BigDecimal give = branchsalerAccountChangeDao.selectSum(map).setScale(2, BigDecimal.ROUND_HALF_UP);

            // h5 自然流量
            map.put("paysource", 0);
            map.put("payrole", 2);
            BigDecimal h51 = branchsalerAccountChangeDao.selectSum(map).setScale(2, BigDecimal.ROUND_HALF_UP);

            // ios支付
            map.put("paysource", 1);
            BigDecimal ios = branchsalerAccountChangeDao.selectSum(map).setScale(2, BigDecimal.ROUND_HALF_UP);
            // android支付
            map.put("paysource", 2);
            BigDecimal android = branchsalerAccountChangeDao.selectSum(map).setScale(2, BigDecimal.ROUND_HALF_UP);

            resMap.put("h51", h51);
            resMap.put("tuiguang", tuiguang);
            resMap.put("xiaoshou", xiaoshou);
            resMap.put("give", give);
            resMap.put("ios", ios);
            resMap.put("android", android);
            resMap.put("sum", new BigDecimal(h51.doubleValue() + tuiguang.doubleValue() + xiaoshou.doubleValue() + give.doubleValue() + ios.doubleValue() + android.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> agentUserNum(String beginTime,String endTime,String condition) {
        int banchid = ShiroKit.getUser().getBranchsalerId();
        Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
        Map<String, Object> map = new HashMap<>();
        if (branchsaler == null) {
            return null;
        }
        if (StringUtils.isNotBlank(beginTime) && !"null".equals(beginTime)) {
            map.put("beginTime", beginTime);
        }
        if (StringUtils.isNotBlank(endTime) && !"null".equals(endTime)) {
            map.put("endTime", endTime +" 23:59:59");
        }
        if (StringUtils.isNotBlank(condition) && !"null".equals(condition)) {
            map.put("condition", condition);
        }
        map.put("branchsaler", branchsaler.getId());
        List<Map<String, Object>> list = this.deptDao.agentlist(map);
        if (list.size() > 0) {
            if (list.get(0).get("levelName").equals("省级分会") || list.get(0).get("levelName").equals("市级分会")) {
                list.get(0).put("pid", 0);
            }
        }
        long start = System.currentTimeMillis();
        for (Map<String, Object> resMap : list) {
            long dan = System.currentTimeMillis();
            map.put("branchsaler", resMap.get("id"));
            map.put("flag", false);
            int count = generalUserDao.countGeneralUser(map);
            int paid = generalUserDao.paidGeneralUser(map);
            int cardPay = generalUserDao.cardGeneralUser(map);
            int sup = generalUserDao.supGeneralUser(map);
            int suped = generalUserDao.supedGeneralUser(map);
            int giftGiving = generalUserDao.giftGiving(map);
            int exchange = generalUserDao.exchange(map);
            int cardGiving = generalUserDao.cardGiving(map);
            int fullmembers = paid + giftGiving + exchange + cardGiving + cardPay;
            resMap.put("count", count);
            resMap.put("paid", paid);
            resMap.put("cardPay", cardPay);
            resMap.put("sup", sup);
            resMap.put("suped", suped);
            resMap.put("giftGiving", giftGiving);
            resMap.put("exchange", exchange);
            resMap.put("cardGiving", cardGiving);
            resMap.put("fullmembers", fullmembers);
        }

        if (list.size() > 0) {
            if (list.get(0).get("levelName").equals("省级分会") || list.get(0).get("levelName").equals("市级分会")) {
                list.get(0).put("pid", 0);
            }
            if (list.get(0).get("fullname").equals(branchsaler.getFullname())) {
                Map<String, Object> map2 = new HashMap<>();
                if(branchsaler.getId()!=1){
                    map.put("branchsaler", branchsaler.getId());
                    map.put("flag", true);
                }else{
                    map.put("branchsaler", null);
                    map.put("flag", null);
                }
                int count = generalUserDao.countGeneralUser(map);
                int paid = generalUserDao.paidGeneralUser(map);
                int cardPay = generalUserDao.cardGeneralUser(map);
                int sup = generalUserDao.supGeneralUser(map);
                int suped = generalUserDao.supedGeneralUser(map);
                int giftGiving = generalUserDao.giftGiving(map);
                int exchange = generalUserDao.exchange(map);
                int cardGiving = generalUserDao.cardGiving(map);
                int fullmembers = paid + giftGiving + exchange + cardGiving + cardPay;
                map2.put("count", count);
                map2.put("paid", paid);
                map2.put("cardPay", cardPay);
                map2.put("sup", sup);
                map2.put("suped", suped);
                map2.put("giftGiving", giftGiving);
                map2.put("exchange", exchange);
                map2.put("cardGiving", cardGiving);
                map2.put("fullmembers", fullmembers);
                map2.put("id", 0);
                map2.put("simplename", branchsaler.getSimplename() + "-总计");
                map2.put("fullname", branchsaler.getFullname());
                map2.put("levelName", list.get(0).get("levelName"));
                map2.put("pid", -1);
                map2.put("pids", -1);
                list.add(0, map2);
            }
        }
        return list;
    }

    @Override
    public void exportAgentUserNum(HttpServletResponse response, ServletOutputStream outputStream,String beginTime, String endTime, String condition) {
        List<Map<String, Object>> list = agentUserNum(beginTime,endTime,condition);

        List<AgentUserVo> agentUserVos = new ArrayList<>();
        for (Map<String, Object> map2 : list) {
            AgentUserVo agentUserVo = (AgentUserVo) Convert.mapToObject(map2, AgentUserVo.class);
            agentUserVos.add(agentUserVo);
        }
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        ExcelUtil<AgentUserVo> myExcel = new ExcelUtil<>();
        String[] headerColumns = {"分会简称", "分会全称", "分会级别", "注册用户",
                "正式用户", "体验中用户", "体验过期用户", "赠卡用户","付费用户",
                "实体卡支付用户","积分兑换用户","知识送礼用户"};
        String[] fieldColumns = {"simplename", "fullname", "levelName", "count",
                "fullmembers", "sup", "suped", "cardGiving", "paid",
                "cardPay","exchange","giftGiving"};
        try {
            HSSFSheet creatAuditSheet = myExcel.creatAuditSheet(hssfWorkbook, "分会用户统计", agentUserVos, headerColumns, fieldColumns, true);
            myExcel.generateHeaders(hssfWorkbook, creatAuditSheet, headerColumns);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExcelUtil.doResponse(response, outputStream, "分会用户统计", hssfWorkbook);

    }

    @Override
    public boolean branchsalerOnTeam(Integer branchsaler1, Integer branchsaler2) {
        Branchsaler b1 = branchsalerMapper.selectById(branchsaler1);
        Branchsaler b2 = branchsalerMapper.selectById(branchsaler2);
        if (b1 != null && b2 != null) {
            String[] pids1 = b1.getPids().split(",");
            String[] pids2 = b2.getPids().split(",");
            //总部的卡可以任意划拨
            if (b1.getLevel() == 0)
                return true;
            //可以划给总会
            if (b2.getLevel() == 0)
                return true;
            //如果划拨给省需要判断 b1 的省级 是否是b2
            if (b2.getLevel() == 1&&b1.getPids().contains("[" + b2.getId() + "]"))
                return true;

            //如果划拨给市级或者市级一下 需要判断两个分会的省级是否相同
            if (b2.getLevel()>=2&&pids1[2].equals(pids2[2]))
                return true;
        }
        return false;
    }

    @Override
    public boolean branchsalerDirectlyUnder(Integer branchsaler1, Integer branchsaler2) {
        Branchsaler b1 = branchsalerMapper.selectById(branchsaler1);
        Branchsaler b2 = branchsalerMapper.selectById(branchsaler2);
        if (b1 != null && b2 != null) {
            String[] pids1 = b1.getPids().split(",");
            String[] pids2 = b2.getPids().split(",");
            if (b1.getLevel() == 0) {
                return true;
            }
            if (b2.getPids().contains("[" + b1.getId() + "]") && pids2[pids2.length - 1].equals("[" + b1.getId() + "]")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Map<String, Object>> agentBranchSalerNumList(String beginTime, String endTime, String condition) {
        int banchid = ShiroKit.getUser().getBranchsalerId();
        Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
        Map<String, Object> map = new HashMap<>();
        if (branchsaler == null) {
            return null;
        }
        map.put("branchsaler", branchsaler.getId());
        if (StringUtils.isNotBlank(condition) && !"null".equals(condition)) {
            map.put("condition", condition);
        }
        if (StringUtils.isNotBlank(beginTime) && !"null".equals(beginTime)) {
            map.put("beginTime",beginTime);
        }
        if (StringUtils.isNotBlank(endTime) && !"null".equals(endTime)) {
            map.put("endTime", endTime+" 23:59:59");
        }
        List<Map<String, Object>> list = this.deptDao.agentlist(map);
        if (list.size() > 0) {
            if (list.get(0).get("levelName").equals("省级分会") || list.get(0).get("levelName").equals("市级分会")) {
                list.get(0).put("pid", 0);
            }
        }
        for (Map<String, Object> resMap : list) {
            Map<String, Object> map2 = new HashMap<>();
            map.put("branchsaler", resMap.get("id"));
            map.put("level", 1);
            Integer sheng=deptDao.AgentBranchSalerNum(map);

            map.put("level", 2);
            Integer shi=deptDao.AgentBranchSalerNum(map);

            map.put("level", 3);
            Integer qu=deptDao.AgentBranchSalerNum(map);

            map.put("level", 4);
            Integer xq=deptDao.AgentBranchSalerNum(map);
            resMap.put("sheng",sheng);
            resMap.put("shi",shi);
            resMap.put("qu",qu);
            resMap.put("xq",xq);
            resMap.put("sum",sheng+shi+qu+xq);
        }
        return list;
    }

    @Override
    public void exportAgentBranchSalerNumList(HttpServletResponse response, ServletOutputStream outputStream, String beginTime, String endTime, String condition) {
        List<Map<String, Object>> list = agentBranchSalerNumList(beginTime,endTime,condition);

        List<AgentBranchSalerNumVo> agentBranchSalerNumVos = new ArrayList<>();
        for (Map<String, Object> map2 : list) {
            AgentBranchSalerNumVo agentBranchSalerNumVo = (AgentBranchSalerNumVo) Convert.mapToObject(map2, AgentBranchSalerNumVo.class);
            agentBranchSalerNumVos.add(agentBranchSalerNumVo);
        }
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        ExcelUtil<AgentBranchSalerNumVo> myExcel = new ExcelUtil<>();
        String[] headerColumns = {"分会简称", "分会全称", "分会级别", "总计",
                "省", "市", "区县", "小渠道"};
        String[] fieldColumns = {"simplename", "fullname", "levelName", "sum",
                "sheng", "shi", "qu", "xq"};
        try {
            HSSFSheet creatAuditSheet = myExcel.creatAuditSheet(hssfWorkbook, "分会数统计", agentBranchSalerNumVos, headerColumns, fieldColumns, true);
            myExcel.generateHeaders(hssfWorkbook, creatAuditSheet, headerColumns);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExcelUtil.doResponse(response, outputStream, "分会数统计", hssfWorkbook);
    }

    @Override
    public List<Map<String, Object>> orderActivityList(Integer timeType, Integer level) {
        int banchid = ShiroKit.getUser().getBranchsalerId();
        Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
        Map<String, Object> map = new HashMap<>();
        if (branchsaler == null) {
            return null;
        }
        Integer limit=10;
        if(level==1)
            limit=5;
        if(level==2)
            limit=10;
        if (timeType!=null && timeType==1){
            map.put("beginTime","2019-3-28");
            map.put("endTime", "2019-4-17 23:59:59");
        } else if(timeType!=null && timeType==2){
            map.put("beginTime","2019-4-17");
            map.put("endTime", "2019-5-6 23:59:59");
        }
        else if(timeType!=null && timeType==3){
            map.put("beginTime","2019-5-7");
            map.put("endTime", "2019-5-26 23:59:59");
        }
        else if(timeType!=null && timeType==-1){
            map.put("beginTime","2019-3-28");
            map.put("endTime", "2019-5-26 23:59:59");
        }


        List<Map<String,Object>> list=deptDao.activityBranchSalerList(null,level);

        for(int i=0;i<list.size();i++){
            if((Integer) list.get(i).get("id")==1){
                list.remove(i);
                continue;
            }
            map.put("branchsaler", list.get(i).get("id"));
            map.put("flag", true);
            list.get(i).put("num",userOrderDao.selectCount(map));
        }
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Integer num1 = Integer.valueOf(o1.get("num").toString());
                Integer num2 = Integer.valueOf(o2.get("num").toString());
                return num2.compareTo(num1);
            }
        });

        List<Map<String,Object>> resList=new ArrayList<>();
        for(int i=0;i<limit;i++){
            if(i==0){
                list.get(i).put("order",(i+1));
                list.get(i).put("resNum","--");
                resList.add(list.get(i));
                continue;
            }
            list.get(i).put("order",(i+1));
            list.get(i).put("resNum",((Integer)list.get(i-1).get("num")-(Integer)list.get(i).get("num")));
            resList.add(list.get(i));
        }

        return resList;
    }
}
