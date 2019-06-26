package com.halfroom.distribution.controller.manager;

import com.halfroom.distribution.common.controller.BaseController;
import com.halfroom.distribution.core.util.Convert;
import com.halfroom.distribution.core.util.ExcelUtil;
import com.halfroom.distribution.persistence.vo.ReportVo;
import com.halfroom.distribution.persistence.vo.UserOrderVo;
import com.halfroom.distribution.service.IReportFormService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 报表控制器
 */
@Controller
@RequestMapping("/report")
public class ReportFromController extends BaseController {
    private static String PREFIX = "/system/report";

    @Resource
    private IReportFormService reportFormService;
    /**
     * 跳转到报表一列表页面
     */
    @RequestMapping("/reportUserIndex")
    public String reportUserIndex() {
        return PREFIX + "/reportUser.html";
    }

    @RequestMapping(value = "/reportUserList")
    @ResponseBody
    public Object reportUserList(
            @RequestParam(name="condition",required=false,defaultValue="")String condition,
            @RequestParam(name="beginTime",required=false,defaultValue="")String beginTime,
            @RequestParam(name="endTime",required=false,defaultValue="")String endTime){
        return reportFormService.reportFormUser(condition,beginTime,endTime);
    }
    @RequestMapping(value = "/exportUserList/{beginTime}/{endTime}")
    public void exportUserList(
            @PathVariable  String beginTime,
            @PathVariable String endTime,
            HttpServletResponse response,
            ServletOutputStream outputStream){
        List<ReportVo> reportVoList = new ArrayList<>();
        List<Map<String,Object>> list= reportFormService.reportFormUser(null,beginTime,endTime);
        for (Map<String, Object> map : list) {
            ReportVo vo = (ReportVo) Convert.mapToObject(map,ReportVo.class);
            reportVoList.add(vo);
        }
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        ExcelUtil<ReportVo> myExcel = new ExcelUtil<>();
        String[] headerColumns = {"类型","数值"};
        String[] fieldColumns =  {"name","value"};

        try {
            HSSFSheet creatAuditSheet = myExcel.creatAuditSheet(hssfWorkbook, "用户报表", reportVoList, headerColumns, fieldColumns,true);
            myExcel.generateHeaders(hssfWorkbook, creatAuditSheet, headerColumns);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExcelUtil.doResponse(response, outputStream, "用户报表", hssfWorkbook);
    }




    /**
     * 跳转到订单报表
     */
    @RequestMapping("/reportOrderIndex")
    public String reportOrderIndex() {
        return PREFIX + "/reportOrder.html";
    }

    @RequestMapping(value = "/reportOrderList")
    @ResponseBody
    public Object reportOrderList(
            @RequestParam(name="condition",required=false,defaultValue="")String condition,
            @RequestParam(name="beginTime",required=false,defaultValue="")String beginTime,
            @RequestParam(name="endTime",required=false,defaultValue="")String endTime){
        return reportFormService.reportFormOrder(condition,beginTime,endTime);
    }
    @RequestMapping(value = "/reportOrderList/{beginTime}/{endTime}")
    public void reportOrderList(
            @PathVariable  String beginTime,
            @PathVariable String endTime,
            HttpServletResponse response,
            ServletOutputStream outputStream){
        List<ReportVo> reportVoList = new ArrayList<>();
        List<Map<String,Object>> list= reportFormService.reportFormOrder(null,beginTime,endTime);
        for (Map<String, Object> map : list) {
            ReportVo vo = (ReportVo) Convert.mapToObject(map,ReportVo.class);
            reportVoList.add(vo);
        }
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        ExcelUtil<ReportVo> myExcel = new ExcelUtil<>();
        String[] headerColumns = {"类型","数值"};
        String[] fieldColumns =  {"name","value"};

        try {
            HSSFSheet creatAuditSheet = myExcel.creatAuditSheet(hssfWorkbook, "订单报表", reportVoList, headerColumns, fieldColumns,true);
            myExcel.generateHeaders(hssfWorkbook, creatAuditSheet, headerColumns);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExcelUtil.doResponse(response, outputStream, "订单报表", hssfWorkbook);
    }
}
