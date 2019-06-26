package com.halfroom.distribution.dao;

import com.halfroom.distribution.persistence.model.IntegraLog;

public interface IntegraLogDao {
	 int insertSelective(IntegraLog record);
}
