package com.halfroom.distribution.controller.branchsaler;

import com.halfroom.distribution.common.annotion.Permission;
import com.halfroom.distribution.common.controller.BaseController;
import com.halfroom.distribution.core.db.Db;
import com.halfroom.distribution.core.shiro.ShiroKit;
import com.halfroom.distribution.dao.BranchsalerAccountChangeDao;
import com.halfroom.distribution.dao.BranchsalerSettlementRecordDao;
import com.halfroom.distribution.persistence.dao.BranchsalerAmountMapper;
import com.halfroom.distribution.persistence.dao.BranchsalerMapper;
import com.halfroom.distribution.persistence.dao.BranchsalerSettlementRecordMapper;
import com.halfroom.distribution.persistence.model.Branchsaler;
import com.halfroom.distribution.persistence.model.BranchsalerAmount;
import com.halfroom.distribution.persistence.model.BranchsalerSettlementRecord;
import com.halfroom.distribution.service.IBranchsalerAccountChangeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * 交易统计控制器
 */
@Controller
@RequestMapping("/branchsalerAccount")
public class BranchsalerAccountController extends BaseController {

    private String prefix = "/system/branchsalerAccount/";

    @Resource
    private IBranchsalerAccountChangeService bacService;
    
    @Resource
    private BranchsalerMapper branchsalerMapper;
    
    @Resource
    private BranchsalerAccountChangeDao branchsalerAccountChangeDao;

    @Resource
    private BranchsalerSettlementRecordMapper branchsalerSettlementRecordMapper;

    @Resource
    private BranchsalerSettlementRecordDao branchsalerSettlementRecordDao;


    /**
     * 跳转到交易统计记录首页
     */
    @RequestMapping("")
    public String index() {
        return prefix + "branchsalerAccount.html";
    }
    /**
     * 查询交易统计记录列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(name="condition",required=false)String condition,
                       @RequestParam(name="accountType",required=false)Integer accountType,
                       @RequestParam(name="paysource",required=false) Integer paysource,
                       @RequestParam(name="payrole",required=false) Integer payrole,
                       @RequestParam(name="beginTime",required=false) String beginTime,
                       @RequestParam(name="endTime",required=false) String endTime
                       ) {
    	return bacService.selectBranchsalerAccountChanges(condition,accountType,paysource,payrole,beginTime,endTime);
    }

    /**
     * 导出交易统计记录列表
     */
    @RequestMapping("/exportList/{condition}/{accountType}/{paysource}/{payrole}/{beginTime}/{endTime}")
    public void exportList(@PathVariable String condition,
                           @PathVariable Integer accountType,
                           @PathVariable Integer paysource,
                           @PathVariable Integer payrole,
                           @PathVariable String beginTime,
                           @PathVariable  String endTime,
                           HttpServletResponse response,ServletOutputStream outputStream
    ) {
         bacService.exportList(response,outputStream,condition,accountType,paysource,payrole,beginTime,endTime);
    }
    /**
     * 省
     */
    @RequestMapping("/province")
    public String province() {
        return prefix + "branchsalerAccount_province.html";
    }

    /**
     * 统计
     * @param condition
     * @param beginTime
     * @param endTime
     * @return
     */
    @RequestMapping("/statistics")
    @ResponseBody
    public Object statistics(@RequestParam(name="condition",required=false)String condition,
                             @RequestParam(name="accountType",required=false)Integer accountType,
                             @RequestParam(name="paysource",required=false) Integer paysource,
                             @RequestParam(name="payrole",required=false) Integer payrole,
                             @RequestParam(name="beginTime",required=false) String beginTime,
                             @RequestParam(name="endTime",required=false) String endTime) {
    	return 	bacService.statistics(condition,accountType,paysource,payrole,beginTime,endTime);
    }
    
    /**
     *分会账户详情页
     */
    @RequestMapping("/amount_info")
    public String amount_info(Model model) {
    	int banchid = ShiroKit.getUser().getBranchsalerId();
    	BranchsalerAmount branchsalerAmount = (BranchsalerAmount) Db.create(BranchsalerAmountMapper.class).selectOneByCon("branchsaler_id", banchid);
    	model.addAttribute("bsa",branchsalerAmount);
    	Map<String, Object> map = new HashMap<>();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return null;
		}
		map.put("branchsaler", branchsaler.getId());
		BigDecimal all = branchsalerAccountChangeDao.selectSumNohasOrder(map).setScale(2, BigDecimal.ROUND_HALF_UP);
		map.put("type", 0);
		BigDecimal in = branchsalerAccountChangeDao.selectSumNohasOrder(map).setScale(2, BigDecimal.ROUND_HALF_UP);
		map.put("type", 1);
		BigDecimal out = branchsalerAccountChangeDao.selectSumNohasOrder(map).setScale(2, BigDecimal.ROUND_HALF_UP);
        map.put("payStatus","0");
		BigDecimal benqi=branchsalerSettlementRecordDao.settlementMoney(map);
        map.put("payStatus","1");
        BigDecimal yijiesuan=branchsalerSettlementRecordDao.settlementMoney(map);
		model.addAttribute("branchsalerAccount_in", in);
		model.addAttribute("branchsalerAccount_out", out);
		model.addAttribute("branchsalerAccount_all", all);
        model.addAttribute("branchsalerAccount_benqi", benqi);
        model.addAttribute("branchsalerAccount_yijiesuan", yijiesuan);
    	return prefix + "branchsalerAmount_info.html";
    }
    
    /**
     *分润结算页面
     */
    @RequestMapping("/settlement_index")
    @Permission
    public String settlement_index() {
    	return prefix + "branchsaler_settlement.html";
    }

    /**
     * 展示分润结算数据
     * @param condition
     * @param beginTime
     * @param endTime
     * @return
     */
    @RequestMapping("/settlement")
    @ResponseBody
    public Object settlement(
    		@RequestParam(required = false) String condition,
    		@RequestParam(required = false) String beginTime,
    		@RequestParam(required = false) String endTime,
            @RequestParam(required = false) String paysources
    		) {
    	return bacService.branchsalerAccountSum(condition, beginTime, endTime,paysources);
    }
    /**
     * 执行结算操作
     * @param beginTime
     * @param endTime
     * @return
     */
    @RequestMapping("/settlement_do")
    @ResponseBody
    public Object settlement_do(
            @RequestParam(required = false) String condition,
            @RequestParam(required = false) String beginTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(required = false) String paysources
    		) {
    	return bacService.settlement_do(condition,beginTime, endTime,paysources);
    }
    
    /**
     *结算记录页面
     */
    @RequestMapping("/settlementRecordIndex")
    @Permission
    public String settlementRecordIndex() {
    	return prefix + "settlement_record.html";
    }
    
    /**
     *结算记录
     */
    @RequestMapping("/settlementRecord")
    @ResponseBody
    public Object settlementRecord(
    		@RequestParam(required = false) String condition,
            @RequestParam(required = false) Integer bsr_status,
            @RequestParam(required = false) Integer pay_status,
    		@RequestParam(required = false) String beginTime,
    		@RequestParam(required = false) String endTime) {
    	return bacService.settlementRecord(condition,bsr_status, pay_status,beginTime, endTime);
    }

    /**
     * 设置发票页面
     * @return
     */
    @RequestMapping("/setInvoiceNumber_index/{id}")
    public String setInvoiceNumber_index(@PathVariable Integer id,Model model) {
        BranchsalerSettlementRecord branchsalerSettlementRecord = new BranchsalerSettlementRecord();
        branchsalerSettlementRecord=branchsalerSettlementRecord.selectById(id);
        model.addAttribute("bsr",branchsalerSettlementRecord);
        return prefix + "setInvoiceNumber_index.html";
    }
    /**
     * 设置发票
     * @return
     */
    @RequestMapping("/updateSettlementRecord")
    @ResponseBody
    public Object updateSettlementRecord(
            @RequestParam Integer id,
            @RequestParam(required = false) String num
           ) {
        BranchsalerSettlementRecord branchsalerSettlementRecord = new BranchsalerSettlementRecord();
        branchsalerSettlementRecord=branchsalerSettlementRecord.selectById(id);
        branchsalerSettlementRecord.setStatus(1);
        branchsalerSettlementRecord.setInvoiceNumber(num);
        branchsalerSettlementRecord.updateById();
        return 1;
    }

    /**
     * 更新打款状态
     * @param id
     * @return
     */
    @RequestMapping("/updateSettlementRecordPayStatus")
    @ResponseBody
    public Object updateSettlementRecordPayStatus(@RequestParam Integer id){
        BranchsalerSettlementRecord branchsalerSettlementRecord = new BranchsalerSettlementRecord();
        branchsalerSettlementRecord=branchsalerSettlementRecord.selectById(id);
        Integer payStatus=0;
        if(branchsalerSettlementRecord.getPayStatus()==0)
            payStatus=1;
        else
            payStatus=0;
        branchsalerSettlementRecord.setPayStatus(payStatus);
        branchsalerSettlementRecord.updateById();
        return 1;
    }



    /**
     * 导出结算记录
     * @param response
     * @param outputStream
     * @param condition
     * @param beginTime
     * @param endTime
     */
    @RequestMapping("/exportSettlementRecord/{condition}/{bsr_status}/{pay_status}/{beginTime}/{endTime}")
    public void exportSettlementRecord(
    		HttpServletResponse response,
    		ServletOutputStream outputStream,
    		@PathVariable String condition,
			@PathVariable Integer bsr_status,
            @PathVariable Integer pay_status,
			@PathVariable String beginTime,
			@PathVariable String endTime) {
    	 bacService.exportsettlementRecord(response,outputStream,condition, bsr_status,pay_status,beginTime, endTime);
    }
    /**
     *分润详情
     */
    @RequestMapping("/branchsalerAccountDetailsIndex")
    public String branchsalerAccountDetailsIndex() {
        return prefix + "branchsalerAccountDetails_index.html";
    }

    @RequestMapping("/branchsalerAccountDetails")
    @ResponseBody
    public Object branchsalerAccountDetails(
            @RequestParam(required = false) String condition,
            @RequestParam(required = false) String beginTime,
            @RequestParam(required = false) String endTime) {
        return bacService.branchsalerAccountDetails(condition,beginTime, endTime);
    }

    @RequestMapping("/exportBranchsalerAccountDetails/{condition}/{beginTime}/{endTime}")
    public void exportBranchsalerAccountDetails(
            HttpServletResponse response,
            ServletOutputStream outputStream,
            @PathVariable String condition,
            @PathVariable String beginTime,
            @PathVariable String endTime) {
        bacService.exportBranchsalerAccountDetails(response,outputStream,condition,beginTime,endTime);
    }
}
