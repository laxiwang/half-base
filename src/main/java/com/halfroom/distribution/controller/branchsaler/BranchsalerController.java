package com.halfroom.distribution.controller.branchsaler;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.halfroom.distribution.common.annotion.Permission;
import com.halfroom.distribution.common.annotion.log.BussinessLog;
import com.halfroom.distribution.common.constant.Dict;
import com.halfroom.distribution.common.constant.factory.ConstantFactory;
import com.halfroom.distribution.common.controller.BaseController;
import com.halfroom.distribution.common.exception.BizExceptionEnum;
import com.halfroom.distribution.common.exception.BussinessException;
import com.halfroom.distribution.common.node.ZTreeNode;
import com.halfroom.distribution.dao.BranchMappingDao;
import com.halfroom.distribution.persistence.dao.ProvinceCityBranchMappingMapper;
import com.halfroom.distribution.persistence.dao.BranchsalerAmountMapper;
import com.halfroom.distribution.persistence.dao.BranchsalerMapper;
import com.halfroom.distribution.persistence.model.ProvinceCityBranchMapping;
import com.halfroom.distribution.persistence.model.Branchsaler;
import com.halfroom.distribution.persistence.model.BranchsalerAmount;
import com.halfroom.distribution.core.log.LogObjectHolder;
import com.halfroom.distribution.core.shiro.ShiroKit;
import com.halfroom.distribution.core.util.ToolUtil;
import com.halfroom.distribution.dao.BranchsalerDao;
import com.halfroom.distribution.service.IBranchsalerService;
import com.halfroom.distribution.warpper.AgentBranchsalerWarpper;
import com.halfroom.distribution.warpper.BranchsalerWarpper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.*;

/**
 * 部门控制器
 */
@Controller
@RequestMapping("/dept")
public class BranchsalerController extends BaseController {

    private String prefix = "/system/dept/";
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Resource
    private BranchsalerDao deptDao;
    @Resource
    private BranchsalerMapper branchsalerMapper;
    @Resource
    private IBranchsalerService deptService;
    @Resource
    private BranchsalerAmountMapper branchsalerAmountMapper;
    @Resource
    private BranchMappingDao branchMappingDao;
    @Resource
    private ProvinceCityBranchMappingMapper branchMappingMapper;
    /**
     * 跳转到部门管理首页
     */
    @RequestMapping("")
    public String index() {
        return prefix + "dept.html";
    }

    /**
     * 跳转到添加部门
     */
    @RequestMapping("/dept_add")
    public String deptAdd() {
        return prefix + "dept_add.html";
    }

    /**
     * 跳转到修改部门
     */
    @Permission
    @RequestMapping("/dept_update/{deptId}")
    public String deptUpdate(@PathVariable Integer deptId, Model model) {
        Branchsaler branchsaler = branchsalerMapper.selectById(deptId);
        model.addAttribute(branchsaler);
        model.addAttribute("pName", ConstantFactory.me().getDeptName(branchsaler.getPid()));
        LogObjectHolder.me().set(branchsaler);
        return prefix + "dept_edit.html";
    }

    /**
     * 获取部门的tree列表
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public List<ZTreeNode> tree() {
        List<ZTreeNode> tree = this.deptDao.tree();
      //  tree.add(ZTreeNode.createParent());
        return tree;
    }
    
    /**
     * 获取部门的tree列表
     */
    @RequestMapping(value = "/treeByBranchsaler")
    @ResponseBody
    public List<ZTreeNode> treeByBranchsaler() {
    	int banchid = ShiroKit.getUser().getBranchsalerId();
        List<ZTreeNode> tree = this.deptDao.treeByBranchsaler(banchid);
      //  tree.add(ZTreeNode.createParent());
        return tree;
    }
    /**
     * 获取部门的tree列表
     */
    @RequestMapping(value = "/treeByBranchsalerNoSelf")
    @ResponseBody
    public List<ZTreeNode> treeByBranchsalerNoSelf() {
    	int banchid = ShiroKit.getUser().getBranchsalerId();
        List<ZTreeNode> tree =new ArrayList<>();
        if(banchid==1||banchid==75)
            tree=this.deptDao.treeByBranchsaler(banchid);
        else
            tree=this.deptDao.treeByBranchsalerNoSelf(banchid);

        return tree;
    }
    /**
     * 新增部门
     */
    @BussinessLog(value = "添加部门", key = "simplename", dict = Dict.DEPT_DICT)
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(Branchsaler branchsaler) {
        if (ToolUtil.isOneEmpty(branchsaler, branchsaler.getSimplename())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        if((branchsaler.getLevel()==3)&&(branchsaler.getLevel()<branchsalerMapper.selectById(branchsaler.getPid()).getLevel())){
        	return -1;
        }
        if((branchsaler.getLevel()!=3)&&(branchsaler.getLevel()<=branchsalerMapper.selectById(branchsaler.getPid()).getLevel())){
        	return -1;
        } 
        //完善pids,根据pid拿到pid的pids
        deptSetPids(branchsaler);
        branchsalerMapper.insert(branchsaler);
        branchsalerAmountMapper.insert(new BranchsalerAmount(branchsaler.getId(), branchsaler.getFullname(), new BigDecimal("0.0"),  new BigDecimal("0.0"),  new BigDecimal("0.0")));
        return 1;
    }

    /**
     * 获取所有部门列表
     */
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.deptDao.list(condition);
        return super.warpObject(new BranchsalerWarpper(list));
    }

    /**
     * 部门详情
     */
    @RequestMapping(value = "/detail/{deptId}")
    @Permission
    @ResponseBody
    public Object detail(@PathVariable("deptId") Integer deptId) {
        return branchsalerMapper.selectById(deptId);
    }

    /**
     * 修改部门
     */
    @BussinessLog(value = "修改部门", key = "simplename", dict = Dict.DEPT_DICT)
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(Branchsaler branchsaler) {
        if (ToolUtil.isEmpty(branchsaler) || branchsaler.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        if((branchsaler.getLevel()==3)&&(branchsaler.getLevel()<branchsalerMapper.selectById(branchsaler.getPid()).getLevel())){
        	return -1;
        }
        if((branchsaler.getLevel()!=3)&&(branchsaler.getLevel()<=branchsalerMapper.selectById(branchsaler.getPid()).getLevel())){
        	return -1;
        } 
        deptSetPids(branchsaler);
        branchsalerMapper.updateById(branchsaler);
        //更新下级部门的pids
        if(branchsaler.getLevel()!=0){
            deptUpdatePids(branchsaler);
        }
        return SUCCESS_TIP;
    }
    private StringBuilder pids(Branchsaler branchsaler,StringBuilder sb){
        //查找上级
        Branchsaler  b= branchsalerMapper.selectById(branchsaler.getPid());
        if(b!=null){
            sb.insert(0,"["+b.getId()+"],");
            if(b.getPid()==0){
                sb.insert(0,"["+0+"],");
                return sb;
            }
            pids(b,sb);
        }
        return sb;
    }
    private  void deptUpdatePids(Branchsaler branchsaler){
        String parentId="["+branchsaler.getId()+"]";
        //查找下级
        Wrapper<Branchsaler> wrapper = new EntityWrapper<>();
        wrapper.like("pids",parentId);
        List<Branchsaler>   list= branchsalerMapper.selectList(wrapper);
        for (Branchsaler b: list) {
            //递归拼接当前分会的pids
            String allPids=pids(b,new StringBuilder()).toString();
            if(!b.getPids().equals(allPids.toString())){
                log.info("aaa {}","更新"+b.getFullname()+"的pids,原pids"+b.getPids()+",,现pids"+allPids.toString());
                b.setPids(allPids.toString());
                branchsalerMapper.updateById(b);
            }
        }
    }

    /**
     * 删除部门
     */
    @BussinessLog(value = "删除部门", key = "deptId", dict = Dict.DELETE_DICT)
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Integer deptId) {

        //缓存被删除的部门名称
        LogObjectHolder.me().set(ConstantFactory.me().getDeptName(deptId));

        deptService.deleteDept(deptId);

        return SUCCESS_TIP;
    }

    private void deptSetPids(Branchsaler branchsaler) {
        if (ToolUtil.isEmpty(branchsaler.getPid()) || "0".equals(branchsaler.getPid())) {
            branchsaler.setPid(0);
            branchsaler.setPids("[0],");
        } else {
            int pid = branchsaler.getPid();
            Branchsaler temp = branchsalerMapper.selectById(pid);
            String pids = temp.getPids();
            branchsaler.setPid(pid);
            branchsaler.setPids(pids + "[" + pid + "],");
        }
    }
    
    

    /**
     *
     * 省市映射分会页面
     * @return
     */
    @RequestMapping("/mapping")
    public String mapping() {
        return prefix + "mapping.html";
    }

    @RequestMapping("/mappingList")
    @ResponseBody
    public Object mappingList(
            @RequestParam(name="condition",required=false,defaultValue="")String condition) {
        Map<String,Object> params = new HashMap<>();
        params.put("condition",condition);
        return branchMappingDao.list(params);
    }
    /**
     *
     * 省市映射分会添加页面
     * @return
     */
    @RequestMapping("/mapping_add_index")
    public String mapping_add() {
        return prefix + "mapping_add.html";
    }

    @RequestMapping("/mapping_add")
    @ResponseBody
    public Object mapping_add(
            @RequestParam(name="pid",required=false)Integer pid,
            @RequestParam(name="province",required=false)String province,
            @RequestParam(name="city",required=false)String city) {
        ProvinceCityBranchMapping branchMapping = new ProvinceCityBranchMapping();
        branchMapping.setBranchSaleId(pid);
        branchMapping.setProvince(province);
        branchMapping.setCity(city);
        branchMappingMapper.insert(branchMapping);
        return 1;
    }
    /**
     *
     * 省市映射分会编辑页面
     * @return
     */
    @RequestMapping("/mapping_update_index/{id}")
    public String mapping_update_index(@PathVariable Integer id,Model model)
    {
        ProvinceCityBranchMapping provinceCityBranchMapping= branchMappingMapper.selectById(id);
        model.addAttribute("mapping",provinceCityBranchMapping);
        model.addAttribute("bname",branchsalerMapper.selectById(provinceCityBranchMapping.getBranchSaleId()).getSimplename());
        return prefix + "mapping_edit.html";
    }
    @RequestMapping("/mapping_update")
    @ResponseBody
    public Object mapping_update(
            ProvinceCityBranchMapping provinceCityBranchMapping,
            @RequestParam(name="pid",required=false)Integer pid){
        provinceCityBranchMapping.setBranchSaleId(pid);
        branchMappingMapper.updateById(provinceCityBranchMapping);
        return  1;
    }

    @RequestMapping("/mapping_delete")
    @ResponseBody
    public Object mapping_delete(
            @RequestParam(name="id",required=false)Integer id){
        branchMappingMapper.deleteById(id);
        return  1;
    }

}
