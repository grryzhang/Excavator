package com.zhongzhou.Excavator.DAO.MSSQL.FUTONG;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.Annotation.DataSource;
import com.zhongzhou.Excavator.model.masterdata.Inquiry;
import com.zhongzhou.Excavator.model.masterdata.InquirySearchParameters;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@DataSource( dataSource=DataSourceList.FUTONG_MSSQL )  
@Service( DAOBeanNameList.MSSQL_FUTONG_inquiry )
public interface InquiryDAO {

	public List<Inquiry> selectActiveInquiryFromFUTONG( InquirySearchParameters searchParameters ) throws SQLException;
	
	public Integer countActiveInquiryAtQuoteLevelFromFUTONG() throws SQLException;
}
