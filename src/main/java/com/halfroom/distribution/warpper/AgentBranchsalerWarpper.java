package com.halfroom.distribution.warpper;

import com.halfroom.distribution.common.constant.factory.ConstantFactory;
import com.halfroom.distribution.common.warpper.BaseControllerWarpper;
import com.halfroom.distribution.core.util.ToolUtil;

import java.util.Arrays;
import java.util.Map;


public class AgentBranchsalerWarpper extends BaseControllerWarpper {

    public AgentBranchsalerWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {

        Integer pid = (Integer) map.get("pid");

        if (ToolUtil.isEmpty(pid) || pid.equals(0)) {
            map.put("pName", "--");
        } else {
            map.put("pName", ConstantFactory.me().getDeptName(pid));
        }
    }

}
