package com.zhongzhou.Excavator.model;

import java.io.Serializable;
import java.sql.Timestamp;

public interface BasicStoreDocument{

	public String getDocSource();

	public void setDocSource(String docSource);

	public String getDocSourceId();

	public void setDocSourceId(String docSourceId);

	public String getDocCreateUser();

	public void setDocCreateUser(String docCreateUser);

	public String getDocCreateUserId();

	public void setDocCreateUserId(String docCreateUserId);

	public Timestamp getDocCreateTime();

	public void setDocCreateTime(Timestamp docCreateTime);

	public void setDocLastUpdateTime(Timestamp docLastUpdateTime);

	public String getDocUpdateUser();

	public void setDocUpdateUser(String docUpdateUser) ;

	public String getDocUpdateUserId();

	public void setDocUpdateUserId(String docUpdateUserId);

	public Timestamp getDocLastUpdateTime();
}
