package com.zhongzhou.Excavator.DAO.postgresql.MD;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.Annotation.DataSource;
import com.zhongzhou.Excavator.model.masterdata.Inquiry;
import com.zhongzhou.Excavator.model.masterdata.InquiryItem;
import com.zhongzhou.Excavator.model.masterdata.InquirySearchParameters;
import com.zhongzhou.Excavator.model.masterdata.QuoteItem;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@DataSource( dataSource=DataSourceList.MASTER_POSTGRESQL )  
@Service ( DAOBeanNameList.postgresql_md_inquiry )
public interface InquiryDAO {
	
	public void insertInquiries    ( List<Inquiry> inquiries ) throws SQLException;
	
	public void insertInquiryItems ( List<InquiryItem> inquiryItems ) throws SQLException;
	
	public void insertQuoteItems   ( List<QuoteItem> quoteItems ) throws SQLException;
	
	public void updateInquiries    ( List<Inquiry> inquiries ) throws SQLException;
	
	public void updateInquiryItems ( List<InquiryItem> inquiryItems ) throws SQLException;
	
	public void updateQuoteItems   ( List<QuoteItem> quoteItems ) throws SQLException;
	
	public List<Inquiry> selectInquiries( InquirySearchParameters searchParameters ) throws SQLException;
}
