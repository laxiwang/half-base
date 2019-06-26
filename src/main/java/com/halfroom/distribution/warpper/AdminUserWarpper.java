package com.halfroom.distribution.warpper;

import com.halfroom.distribution.common.constant.factory.ConstantFactory;
import com.halfroom.distribution.common.warpper.BaseControllerWarpper;

import java.util.List;
import java.util.Map;

/**
 * 用户管理的包装类
 */
public class AdminUserWarpper extends BaseControllerWarpper {

    public AdminUserWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("sexName", ConstantFactory.me().getSexName((Integer) map.get("sex")));
        map.put("roleName", ConstantFactory.me().getRoleName((String) map.get("roleid")));
        map.put("deptName", ConstantFactory.me().getDeptName((Integer) map.get("branchsalerid")));
        map.put("statusName", ConstantFactory.me().getStatusName((Integer) map.get("status")));
    }

}
