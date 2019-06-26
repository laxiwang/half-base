package com.halfroom.distribution.controller.manager;

import com.google.code.kaptcha.Constants;
import com.halfroom.distribution.common.controller.BaseController;
import com.halfroom.distribution.common.exception.InvalidKaptchaException;
import com.halfroom.distribution.common.node.MenuNode;
import com.halfroom.distribution.dao.UserMgrDao;
import com.halfroom.distribution.persistence.dao.AdminUserMapper;
import com.halfroom.distribution.persistence.model.AdminUser;
import com.halfroom.distribution.core.log.LogManager;
import com.halfroom.distribution.core.log.factory.LogTaskFactory;
import com.halfroom.distribution.core.shiro.ShiroKit;
import com.halfroom.distribution.core.shiro.ShiroUser;
import com.halfroom.distribution.core.util.ToolUtil;
import com.halfroom.distribution.dao.MenuDao;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.halfroom.distribution.core.shiro.factory.IShiro;

import java.util.List;

import static com.halfroom.distribution.core.support.HttpKit.getIp;


@Controller
public class LoginController extends BaseController {

    @Autowired
    MenuDao menuDao;

    @Autowired
    AdminUserMapper userMapper;
    @Autowired
    IShiro shiro;
    @Autowired
    UserMgrDao userMgrDao;

    /**
     * 跳转到主页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        //获取菜单列表
        List<Integer> roleList = ShiroKit.getUser().getRoleList();
        if (roleList == null || roleList.size() == 0) {
            ShiroKit.getSubject().logout();
            model.addAttribute("tips", "该用户没有角色，无法登陆");
            return "/login.html";
        }
        List<MenuNode> menus = menuDao.getMenusByRoleIds(roleList);
        List<MenuNode> titles = MenuNode.buildTitle(menus);
        model.addAttribute("titles", titles);

        //获取用户头像
        Integer id = ShiroKit.getUser().getId();
        AdminUser user = userMapper.selectById(id);
        String avatar = user.getAvatar();
        model.addAttribute("avatar", avatar);

        return "/index.html";
    }

    /**
     * 跳转到登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
            return REDIRECT + "/";
        } else {
            return "/login.html";
        }
    }

    /**
     * 点击登录执行的动作
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginVali() {

        String username = super.getPara("username").trim();
        String password = super.getPara("password").trim();

        //验证验证码是否正确
        if (ToolUtil.getKaptchaOnOff()) {
            String kaptcha = super.getPara("kaptcha").trim();
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equals(code)) {
                throw new InvalidKaptchaException();
            }
        }

        Subject currentUser = ShiroKit.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());


        currentUser.login(token);
        token.setRememberMe(true);


        ShiroUser shiroUser = ShiroKit.getUser();
        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getAccount());

        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), getIp()));

        ShiroKit.getSession().setAttribute("sessionFlag", true);

        return REDIRECT + "/";
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut() {
        LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), getIp()));
        ShiroKit.getSubject().logout();
        return REDIRECT + "/login";
    }


    /**
     * 登录代理账号
     *
     * @param account
     * @return
     */
    @RequestMapping(value = "/runAs", method = RequestMethod.POST)
    @ResponseBody
    public Object runAs(String account) {
        Subject subject = SecurityUtils.getSubject();
        AdminUser adminUser = shiro.admin_user(account);
        if (adminUser != null && !subject.isRunAs()) {
            ShiroUser shiroUser = shiro.shiroUser(adminUser);
            shiroUser.setAgen(true);
            subject.runAs(new SimplePrincipalCollection(shiroUser, subject.getPrincipals().getRealmNames().iterator().next()));
            super.getSession().setAttribute("shiroUser", shiroUser);
            super.getSession().setAttribute("username", shiroUser.getAccount());
            return 1;
        }
        return 0;
    }

    /**
     * 退出代理
     *
     * @return
     */
    @RequestMapping("runRelease")
    public String runRelease() {
        Subject subject = SecurityUtils.getSubject();
        AdminUser adminUser = shiro.admin_user("admin");
        if (adminUser != null && subject.isRunAs()) {
            ShiroUser shiroUser = shiro.shiroUser(adminUser);
            shiroUser.setAgen(false);
            subject.releaseRunAs();
            super.getSession().setAttribute("shiroUser", shiroUser);
            super.getSession().setAttribute("username", shiroUser.getAccount());
        }
        return REDIRECT + "/";
    }
}
