package com.halfroom.distribution.controller.manager;

import com.halfroom.distribution.common.annotion.Permission;
import com.halfroom.distribution.common.annotion.log.BussinessLog;
import com.halfroom.distribution.common.constant.Const;
import com.halfroom.distribution.common.constant.Dict;
import com.halfroom.distribution.common.constant.cache.Cache;
import com.halfroom.distribution.common.constant.factory.ConstantFactory;
import com.halfroom.distribution.common.constant.state.ManagerStatus;
import com.halfroom.distribution.common.constant.tips.AbstractTip;
import com.halfroom.distribution.common.controller.BaseController;
import com.halfroom.distribution.common.exception.BizExceptionEnum;
import com.halfroom.distribution.common.exception.BussinessException;
import com.halfroom.distribution.core.cache.CacheKit;
import com.halfroom.distribution.core.shiro.ShiroDbRealm;
import com.halfroom.distribution.persistence.dao.AdminUserMapper;
import com.halfroom.distribution.persistence.dao.BranchsalerMapper;
import com.halfroom.distribution.persistence.model.AdminUser;
import com.halfroom.distribution.persistence.model.Branchsaler;
import com.halfroom.distribution.config.properties.DisProperties;
import com.halfroom.distribution.core.db.Db;
import com.halfroom.distribution.core.log.LogObjectHolder;
import com.halfroom.distribution.core.shiro.ShiroKit;
import com.halfroom.distribution.core.shiro.ShiroUser;
import com.halfroom.distribution.core.util.ToolUtil;
import com.halfroom.distribution.dao.UserMgrDao;
import com.halfroom.distribution.factory.AdminUserFactory;
import com.halfroom.distribution.transfer.UserDto;
import com.halfroom.distribution.warpper.AdminUserWarpper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.naming.NoPermissionException;
import javax.validation.Valid;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 系统管理员控制器
 */
@Controller
@RequestMapping("/mgr")
public class AdminUserMgrController extends BaseController {
   
    private static String PREFIX = "/system/user/";

    @Resource
    private DisProperties disProperties;

    @Resource
    private UserMgrDao managerDao;

    @Resource
    private AdminUserMapper adminUserMapper;
    
    @Resource
    private BranchsalerMapper branchsalerMapper;

    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "user.html";
    }

    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping("/user_add")
    public String addView() {
        return PREFIX + "user_add.html";
    }

    /**
     * 跳转到角色分配页面
     */
    @Permission
    @RequestMapping("/role_assign/{userId}")
    public String roleAssign(@PathVariable Integer userId, Model model) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        AdminUser adminUser = (AdminUser) Db.create(AdminUserMapper.class).selectOneByCon("id", userId);
        model.addAttribute("userId", userId);
        model.addAttribute("userAccount", adminUser.getAccount());
        return PREFIX + "user_roleassign.html";
    }

    /**
     * 跳转到编辑管理员页面
     */
    @Permission
    @RequestMapping("/user_edit/{userId}")
    public String userEdit(@PathVariable Integer userId, Model model) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        AdminUser adminUser = adminUserMapper.selectById(userId);
        model.addAttribute(adminUser);
        model.addAttribute("roleName", ConstantFactory.me().getRoleName(adminUser.getRoleid()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(adminUser.getBranchsalerid()));
        LogObjectHolder.me().set(adminUser);
        return PREFIX + "user_edit.html";
    }

    /**
     * 跳转到查看用户详情页面
     */
    @RequestMapping("/user_info")
    public String userInfo(Model model) {
        Integer userId = ShiroKit.getUser().getId();
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        AdminUser adminUser = adminUserMapper.selectById(userId);
        model.addAttribute(adminUser);
        model.addAttribute("roleName", ConstantFactory.me().getRoleName(adminUser.getRoleid()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(adminUser.getBranchsalerid()));
        LogObjectHolder.me().set(adminUser);
        return PREFIX + "user_view.html";
    }

    /**
     * 跳转到修改密码界面
     */
    @RequestMapping("/user_chpwd")
    public String chPwd() {
        return PREFIX + "user_chpwd.html";
    }

    /**
     * 修改当前用户的密码
     */
    @RequestMapping("/changePwd")
    @ResponseBody
    public Object changePwd(@RequestParam String oldPwd, @RequestParam String newPwd, @RequestParam String rePwd) {
        if (!newPwd.equals(rePwd)) {
            throw new BussinessException(BizExceptionEnum.TWO_PWD_NOT_MATCH);
        }
        newPwd=newPwd.trim();
        Integer userId = ShiroKit.getUser().getId();
        AdminUser adminUser = adminUserMapper.selectById(userId);
        String oldMd5 = ShiroKit.md5(oldPwd, adminUser.getSalt());
        if (adminUser.getPassword().equals(oldMd5)) {
            String newMd5 = ShiroKit.md5(newPwd, adminUser.getSalt());
            adminUser.setPassword(newMd5);
            adminUser.updateById();
            return SUCCESS_TIP;
        } else {
            throw new BussinessException(BizExceptionEnum.OLD_PWD_NOT_RIGHT);
        }
    }

    /**
     * 查询管理员列表
     */
    @RequestMapping("/list")
    @Permission
    @ResponseBody
    public Object list(@RequestParam(required = false) String condition, @RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) Integer deptid) {
        String superAccount="";
//        String account= ShiroKit.getUser().getAccount();
//        if(当前用户是超级管理员){//用于实现当前管理员只能查询自己的下级，目前只能由超级管理员查询管理员列表，通过角色过滤。
//            superAccount=account;
//        }
        List<Map<String, Object>> users = managerDao.selectUsers(condition, beginTime, endTime, deptid,superAccount);
        return new AdminUserWarpper(users).warp();
    }

    /**
     * 添加管理员
     */
    @RequestMapping("/add")
    @BussinessLog(value = "添加管理员", key = "account", dict = Dict.USER_DICT)
    @ResponseBody
    public AbstractTip add(@Valid UserDto user, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        user.setAccount(user.getAccount().trim());
        user.setPassword(user.getPassword().trim());
        // 判断账号是否重复
        AdminUser theUser = managerDao.getByAccount(user.getAccount());
        if (theUser != null) {
            throw new BussinessException(BizExceptionEnum.USER_ALREADY_REG);
        }
        String account= ShiroKit.getUser().getAccount();
        AdminUser currentUser= managerDao.getByAccount(account);
        Integer level= Integer.parseInt(currentUser.getLevel())+1;
//        Map<String, Object> map= sysDicService.selectListByCodeNo("quanxianid",level.toString());
        // 完善账号信息
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5(user.getPassword(), user.getSalt()));
        user.setStatus(ManagerStatus.OK.getCode());
        user.setCreatetime(new Date());
        user.setSuperaccount(account);
        user.setFullindex(currentUser.getFullindex()+"."+user.getAccount());
        user.setLevel(level.toString());
//        user.setRoleid(map.get("dicValue").toString());
        
        //添加权限
        //分会等级
        Branchsaler branchsaler= branchsalerMapper.selectById(user.getBranchsalerid());
        int b_level=branchsaler.getLevel();
        switch (b_level) {
		case 1:
			//省级
			user.setRoleid(12+"");
			if(branchsaler.getSimplename().contains("北京")||branchsaler.getSimplename().contains("天津")||branchsaler.getSimplename().contains("上海")||branchsaler.getSimplename().contains("重庆")){
				user.setRoleid(16+"");
			}
			break;
		case 2:
			//市级
			user.setRoleid(13+"");
			break;
		case 3:
			//区县级
			user.setRoleid(14+"");
			break;
		case 4:
			//小渠道
			user.setRoleid(15+"");
			break;
		default:
			break;
		}
        
        adminUserMapper.insert(AdminUserFactory.createUser(user));

        return SUCCESS_TIP;
    }

    /**
     * 修改管理员
     *
     * @throws NoPermissionException
     */
    @RequestMapping("/edit")
    @BussinessLog(value = "修改管理员", key = "account", dict = Dict.USER_DICT)
    @ResponseBody
    public AbstractTip edit(@Valid UserDto user, BindingResult result) throws NoPermissionException {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        if (ShiroKit.hasRole(Const.ADMIN_NAME)) {
        	AdminUser findUser1=adminUserMapper.selectById(user.getId());
        		//账号修改时
	        	if(!findUser1.getAccount().equals(user.getAccount())){
	        		AdminUser selUser1=new AdminUser();
	        		selUser1.setAccount(user.getAccount());
	        		AdminUser findUser2 = adminUserMapper.selectOne(selUser1);
	        		if(findUser2!=null){
	        			throw new BussinessException(BizExceptionEnum.USER_ALREADY_REG);
	        		}
	        	}
            adminUserMapper.updateById(AdminUserFactory.createUser(user));
            return SUCCESS_TIP;
        } else {
            ShiroUser shiroUser = ShiroKit.getUser();
            if (shiroUser.getId().equals(user.getId())) {
                adminUserMapper.updateById(AdminUserFactory.createUser(user));
                return SUCCESS_TIP;
            } else {
                throw new BussinessException(BizExceptionEnum.NO_PERMITION);
            }
        }
    }

    /**
     * 删除管理员（逻辑删除）
     */
    @RequestMapping("/delete")
    @BussinessLog(value = "删除管理员", key = "userId", dict = Dict.USER_DICT)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public AbstractTip delete(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能删除超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new BussinessException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }
        managerDao.setStatus(userId, ManagerStatus.DELETED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 查看管理员详情
     */
    @RequestMapping("/view/{userId}")
    @ResponseBody
    public AdminUser view(@PathVariable Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        return adminUserMapper.selectById(userId);
    }

    /**
     * 重置管理员的密码
     */
    @RequestMapping("/reset")
    @BussinessLog(value = "重置管理员密码", key = "userId", dict = Dict.USER_DICT)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public AbstractTip reset(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        AdminUser user = this.adminUserMapper.selectById(userId);
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5(Const.DEFAULT_PWD, user.getSalt()));
        this.adminUserMapper.updateById(user);
        return SUCCESS_TIP;
    }

    /**
     * 冻结用户
     */
    @RequestMapping("/freeze")
    @BussinessLog(value = "冻结用户", key = "userId", dict = Dict.USER_DICT)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public AbstractTip freeze(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能冻结超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new BussinessException(BizExceptionEnum.CANT_FREEZE_ADMIN);
        }
        this.managerDao.setStatus(userId, ManagerStatus.FREEZED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 解除冻结用户
     */
    @RequestMapping("/unfreeze")
    @BussinessLog(value = "解除冻结用户", key = "userId", dict = Dict.USER_DICT)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public AbstractTip unfreeze(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        this.managerDao.setStatus(userId, ManagerStatus.OK.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 分配角色
     */
    @RequestMapping("/setRole")
    @BussinessLog(value = "分配角色", key = "userId,roleIds", dict = Dict.USER_DICT)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public AbstractTip setRole(@RequestParam("userId") Integer userId, @RequestParam("roleIds") String roleIds) {
        if (ToolUtil.isOneEmpty(userId, roleIds)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能修改超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new BussinessException(BizExceptionEnum.CANT_CHANGE_ADMIN);
        }
        managerDao.setRoles(userId, roleIds);
        //删除缓存
        CacheKit.removeAll(Cache.CONSTANT);


        return SUCCESS_TIP;
    }


    /**
     * 上传图片(上传到项目的webapp/static/img)
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile picture) {
        String pictureName = UUID.randomUUID().toString() + ".jpg";
        try {
            String fileSavePath = disProperties.getFileUploadPath();
            picture.transferTo(new File(fileSavePath + pictureName));
        } catch (Exception e) {
            throw new BussinessException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return pictureName;
    }
}
