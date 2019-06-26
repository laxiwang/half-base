package com.halfroom.distribution.controller.branchsaler;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.halfroom.distribution.common.controller.BaseController;
import com.halfroom.distribution.service.IBranchsalerService;
import com.halfroom.distribution.warpper.AgentBranchsalerWarpper;

/**
 * 下级渠道分会控制器
 */
@Controller
@RequestMapping("/agent/branchsaler")
public class AgentBranchsalerController extends BaseController {
    @Resource
    private IBranchsalerService deptService;

    private String prefix = "/system/dept/agent";

    /**
     * 跳转 --订单统计
     */
    @RequestMapping("agent_order_index")
    public String agent_order_index() {
        return prefix + "/agent_order.html";
    }

    /**
     * 订单统计
     */
    @RequestMapping(value = "/agent_order")
    @ResponseBody
    public Object agent_order(
            @RequestParam(name = "condition", required = false, defaultValue = "") String condition,
            @RequestParam(name = "beginTime", required = false, defaultValue = "") String beginTime,
            @RequestParam(name = "endTime", required = false, defaultValue = "") String endTime) {
        return super.warpObject(new AgentBranchsalerWarpper(deptService.agentOrderlist(beginTime, endTime, condition)));
    }

    /**
     * 导出订单统计
     */
    @RequestMapping(value = "/agent_orderExecl/{beginTime}/{endTime}/{condition}")
    public void agent_orderExecl(
            @PathVariable String condition,
            @PathVariable String beginTime,
            @PathVariable String endTime,
            HttpServletResponse response,
            ServletOutputStream outputStream) {
        deptService.agentOrderlistExecl(response, outputStream, beginTime, endTime, condition);
    }

    /**
     * 跳转 --分润统计
     */
    @RequestMapping("agent_accountChange_index")
    public String agent_accountChange_index() {
        return prefix + "/agent_accountchange.html";
    }

    /**
     * 分润记录统计
     */
    @RequestMapping(value = "/agent_accountChange")
    @ResponseBody
    public Object agent_accountChange(
            @RequestParam(name = "condition", required = false, defaultValue = "") String condition,
            @RequestParam(name = "beginTime", required = false, defaultValue = "") String beginTime,
            @RequestParam(name = "endTime", required = false, defaultValue = "") String endTime) {
        return super.warpObject(new AgentBranchsalerWarpper(deptService.agentAccountChangelist(beginTime, endTime, condition)));
    }
    /**
     * 导出分润记录统计
     */
    @RequestMapping(value = "/agent_changeExecl/{beginTime}/{endTime}/{condition}")
    public void agent_changeExecl(
            @PathVariable String condition,
            @PathVariable String beginTime,
            @PathVariable String endTime,
            HttpServletResponse response,
            ServletOutputStream outputStream) {
        deptService.agentChangelistExecl(response, outputStream, beginTime, endTime, condition);
    }

    /**
     * 跳转 --用户数统计
     */
    @RequestMapping("agent_usersum_index")
    public String agent_usersum_index() {
        return prefix + "/agent_usersum.html";
    }

    /**
     * 用户数统计
     */
    @RequestMapping(value = "/agent_userSum")
    @ResponseBody
    public Object agent_userSum(@RequestParam(name = "condition", required = false, defaultValue = "") String condition,
                                @RequestParam(name = "beginTime", required = false, defaultValue = "") String beginTime,
                                @RequestParam(name = "endTime", required = false, defaultValue = "") String endTime) {
        return super.warpObject(new AgentBranchsalerWarpper(deptService.agentUserNum(beginTime,endTime,condition)));
    }

    @RequestMapping(value = "/export_agent_userSum/{beginTime}/{endTime}/{condition}")
    public void export_agent_userSum(
            HttpServletResponse response, ServletOutputStream outputStream,
            @PathVariable String beginTime,
            @PathVariable String endTime,
            @PathVariable String condition) {
         deptService.exportAgentUserNum(response,outputStream,beginTime,endTime,condition);
    }

    /**
     * 跳转 --分会数统计
     */
    @RequestMapping("agent_branchSaler_num_index")
    public String agent_branchSaler_num_index() {
        return prefix + "/agent_branchsaler_num.html";
    }

    /**
     * 分会数统计
     */
    @RequestMapping(value = "/agent_branchSaler_num")
    @ResponseBody
    public Object agent_branchSaler_num(@RequestParam(name = "condition", required = false, defaultValue = "") String condition,
                                @RequestParam(name = "beginTime", required = false, defaultValue = "") String beginTime,
                                @RequestParam(name = "endTime", required = false, defaultValue = "") String endTime) {
        return super.warpObject(new AgentBranchsalerWarpper(deptService.agentBranchSalerNumList(beginTime,endTime,condition)));
    }

    @RequestMapping(value = "/export_agent_branchSaler_num/{beginTime}/{endTime}/{condition}")
    public void export_agent_branchSaler_num(
            HttpServletResponse response, ServletOutputStream outputStream,
            @PathVariable String beginTime,
            @PathVariable String endTime,
            @PathVariable String condition) {
        deptService.exportAgentBranchSalerNumList(response,outputStream,beginTime,endTime,condition);
    }

}
