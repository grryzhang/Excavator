package com.zhongzhou.Excavator.service.impl.migration.MD;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.DAO.postgresql.MD.CorporationDAO;
import com.zhongzhou.Excavator.DAO.postgresql.MD.ItemDAO;
import com.zhongzhou.Excavator.DAO.postgresql.MD.PriceDAO;
import com.zhongzhou.Excavator.model.masterdata.Corporation;
import com.zhongzhou.Excavator.model.masterdata.CorporationSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.ItemSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.Price;
import com.zhongzhou.Excavator.service.migration.MD.CorporationDataService;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.Exception.ServiceRuntimeException;
import com.zhongzhou.common.util.Excel2007Util;

@Service( ServiceNameList.MD_DATA_Corporation_Service )
public class CorporationDataServiceImpl implements CorporationDataService {

	@Resource( name = DAOBeanNameList.postgresql_md_corporation )
	CorporationDAO corporationDAO;
	
	@Resource( name=DAOBeanNameList.postgresql_md_price )
	private PriceDAO mdPriceDAO;
	
	@Resource ( name = DAOBeanNameList.postgresql_md_item )
	ItemDAO itemDAO;
	
	public static final List<String> screenLevelId 
		= new ArrayList<String>(
			Arrays.asList(
				"f8b7d05d-a976-41a5-b2a1-d1f6eda6d3c9",//inital
				"71602a3c-b1e5-4099-93eb-6cc5be6aa28a",//cofirmed
				"df484d05-89cc-428a-a70d-c5240ce1bff8"//recommend
			)
		);
	
	@Override
	public void savPriceInfoFromExcel2007( 
			InputStream excelStream,
			String itemCategoryId,
			String defaultItemName
			) throws ServiceRuntimeException{
		
		XSSFWorkbook excelBook = null;
		
		try {
			excelBook = new XSSFWorkbook( excelStream );
			
			XSSFSheet sheet = excelBook.getSheetAt(0);
			
			if( sheet == null ){
				
				throw new ServiceRuntimeException("Wrong excel file input, no sheet there.");
			}
			
			XSSFRow row = sheet.getRow(0);
			if (row == null) {
				throw new ServiceRuntimeException("Wrong excel file input, no first row there.");
			}else{
				
				String companyName =  Excel2007Util.getCellStringValue( row, 3 ) ;
				
				CorporationSearchParameters searchParameters = new CorporationSearchParameters();
				searchParameters.setNames( new ArrayList<String>( Arrays.asList(companyName) ) );
				List<Corporation> matches = corporationDAO.selectCorporations(searchParameters);
				
				if( matches == null || matches.size() < 1 ){
					searchParameters = new CorporationSearchParameters();
					searchParameters.setEnames( new ArrayList<String>( Arrays.asList(companyName) ) );
					matches = corporationDAO.selectCorporations(searchParameters);
				}
				
				if( matches == null || matches.size() < 1 ){
					throw new ServiceRuntimeException("No match Corporation.");
				}
				
				Corporation supplier = matches.get(0);
				
				List<Price> prices = new ArrayList<Price>();
				for (int r = 1; r < sheet.getPhysicalNumberOfRows(); r++) {
					
					row = sheet.getRow(r);
					if (row == null) {
						continue;
					}else{
						
						
						String itemType  = null;
						String itemPrice = null;
						String itemStandard = null;
						
						for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
							switch ( c ) {
								
								case 0:
									itemType = Excel2007Util.getCellStringValue( row, c );
									break;
								case 1:
									itemStandard = Excel2007Util.getCellStringValue( row, c );
									break;
								case 3:
									itemPrice = Excel2007Util.getCellStringValue( row, c );
									break;
							}
						}
						
						com.zhongzhou.Excavator.model.masterdata.Item item  = null;
						if( itemType!= null && itemType.length() > 2 ){
							
							ItemSearchParameters itemParameters = new ItemSearchParameters();
							itemParameters.setItemType(itemType);
							List<com.zhongzhou.Excavator.model.masterdata.Item> items = itemDAO.selectItems(itemParameters);
							
							if( items!=null && items.size() > 0 ){
								item = items.get(0);
							}
						}else{
							
							if(  itemStandard!= null && itemStandard.length() > 2 ){
								
								com.zhongzhou.Excavator.model.masterdata.Item newItem = new com.zhongzhou.Excavator.model.masterdata.Item();
								
								String newId = UUID.randomUUID().toString();
								
								newItem.setSpecification( itemStandard );
								newItem.setItemCode( defaultItemName + "0000" + r );
								newItem.setItemType( defaultItemName + "0000" + r );
								newItem.setName(defaultItemName);
								newItem.setId(newId);
								
								newId = UUID.randomUUID().toString();
								com.zhongzhou.Excavator.model.masterdata.InterItem2Category ii2c = new com.zhongzhou.Excavator.model.masterdata.InterItem2Category();
								ii2c.setId(newId);
								ii2c.setItemId(newId);
								ii2c.setCategoryId(itemCategoryId);
								
								itemDAO.insertItems( new ArrayList<com.zhongzhou.Excavator.model.masterdata.Item>( Arrays.asList( newItem ) ) );
								itemDAO.insertInterItem2Category( new ArrayList<com.zhongzhou.Excavator.model.masterdata.InterItem2Category>( Arrays.asList( ii2c ) ) );
								
								item = newItem;
							}
							
						}
						
						if( item!= null && itemPrice!=null){
							
							com.zhongzhou.Excavator.model.masterdata.Price price = new com.zhongzhou.Excavator.model.masterdata.Price();
							
							String newPriceId = UUID.randomUUID().toString();
							price.setId( newPriceId );
							price.setCustomerCorpId("4cdd74f3-d583-4370-8143-8e0dc2aa9426");
							price.setOfferCorpId( supplier.getId() );
							price.setPrice( new BigDecimal(itemPrice) );
							price.setItemId( item.getId() );
							
							prices.add( price );
						}
					}
				}
				
				if( prices!=null && prices.size() > 0 ){
					mdPriceDAO.insertPrices( prices );
				}
			}
			
			
			
		}catch( IOException | SQLException e ){
			throw new ServiceRuntimeException( e );
		}finally{
			try{
				excelBook.close();
			}catch(Exception e){
				//TODO
			}
		}
		
		
	}
	
	
	@Override
	public void saveCorporationInfoFromExcel2007( 
			InputStream excelStream,
			String priceOfferCorporationId,
			List<String> customerCorporationIds,
			List<String> dummyItemIdForCategoryMapping,
			String screenLevelId,
			String resource ) throws ServiceRuntimeException{
		
		XSSFWorkbook excelBook = null;
		
		try{
			
			excelBook = new XSSFWorkbook( excelStream );
			
			XSSFSheet sheet = excelBook.getSheetAt(0);
			
			if( sheet == null ){
				
				throw new ServiceRuntimeException("Wrong excel file input, no sheet there.");
			}
			
			List<com.zhongzhou.Excavator.model.masterdata.Corporation> corps = new ArrayList<com.zhongzhou.Excavator.model.masterdata.Corporation>(); 
			
			for (int r = 1; r < sheet.getPhysicalNumberOfRows(); r++) {

				XSSFRow row = sheet.getRow(r);
				if (row == null) {
					continue;
				}else{
					
					com.zhongzhou.Excavator.model.masterdata.Corporation corp = new com.zhongzhou.Excavator.model.masterdata.Corporation();
					
					for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
						switch ( c ) {
							
							case 0:
								corp.setName( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 1:
								corp.seteName( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 2:
								corp.setAddress( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 7:
								corp.setManagementCertifications( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 8:
								corp.setCertifications( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 9:
								{
									String introduction = Excel2007Util.getCellStringValue( row, c );
									if( introduction!= null && introduction.length() > 300){
										introduction = introduction.substring(0, 299);
									}
									corp.setIntroduction( introduction );
									break;	
								}
							case 10:
								corp.setWebsite( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 11:
								corp.setContacts( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 13:
								corp.setPhone( Excel2007Util.getCellStringValue( row, c ) );
								break;
						}
					}
					
					corp.setScreenLevelId(screenLevelId);
					corp.setResource(resource);
					corps.add( corp );
				}
			}
			
			if( corps.size() > 0 ){
				
				for( Corporation corp : corps ){
					
					CorporationSearchParameters searchParameters = new CorporationSearchParameters();
					searchParameters.setNames( new ArrayList<String>( Arrays.asList( corp.getName() ) ) );
					List<Corporation> existed = corporationDAO.selectCorporations(searchParameters);
	
					if( existed ==null || existed.size() < 1 ){
						corporationDAO.insertCorporation( corp );
						
						if( corp.getId() == null ){
							throw new ServiceRuntimeException( 
								"Error in corporation generation, no id returned. Corporation name is: " 
								+ corp.getName() 
								+ ",or e name is:" 
								+ corp.geteName() );
						}
						
						List<Price> prices = new ArrayList<Price>();
						for( String customerCorporationId : customerCorporationIds ){
							
							for( String itemId : dummyItemIdForCategoryMapping ){
								
								Price price = new Price();
								
								String newPriceId = UUID.randomUUID().toString();
								
								price.setId             ( newPriceId );
								price.setCustomerCorpId ( customerCorporationId );
								price.setOfferCorpId    ( priceOfferCorporationId );
								price.setPrice          ( new BigDecimal("-1") );
								price.setItemId         ( itemId );
								
								prices.add( price );
								
								
								price = new Price();
								newPriceId = UUID.randomUUID().toString();
								
								price.setId             ( newPriceId );
								price.setCustomerCorpId ( priceOfferCorporationId );
								price.setOfferCorpId    ( corp.getId() );
								price.setPrice          ( new BigDecimal("-1") );
								price.setItemId         ( itemId );
								
								prices.add( price );
							}
						}
						
						mdPriceDAO.insertPrices(prices);
						
					}else{
						corp.setResource(null);
						corp.setScreenLevelId(null);
						corporationDAO.updateCorporation( corp );
					}
				}
			}
			
		}catch( IOException | SQLException e ){
			throw new ServiceRuntimeException( e );
		}finally{
			try{
				excelBook.close();
			}catch(Exception e){
				//TODO
			}
		}
	}
}
