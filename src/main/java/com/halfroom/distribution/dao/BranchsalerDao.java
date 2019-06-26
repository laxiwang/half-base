package com.halfroom.distribution.dao;

import com.halfroom.distribution.common.node.ZTreeNode;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 */
public interface BranchsalerDao {

    /**
     * 获取ztree的节点列表
     *
     */
    List<ZTreeNode> tree();
    
    List<ZTreeNode> treeByBranchsaler(Integer branchsalerid);
    
    List<ZTreeNode> treeByBranchsalerNoSelf(Integer branchsalerid);

    List<Map<String, Object>> list(@Param("condition") String condition);

    List<Map<String, Object>>  activityBranchSalerList(@Param("condition") String condition,@Param("level") Integer level);

    List<Map<String, Object>> agentlist(Map<String, Object> map);

    Integer AgentBranchSalerNum(Map<String, Object> map);

}
