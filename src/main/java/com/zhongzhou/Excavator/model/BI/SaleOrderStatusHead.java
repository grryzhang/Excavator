package com.zhongzhou.Excavator.model.BI;

import java.util.List;

public class SaleOrderStatusHead {
	
	public String customerOrderId;
	
	public String orderTime;
	
	public String invoiceCode;
	
	public String customerName;
	
	public String customerBuyer;
	
	public String tradeTerm;
	
	public String businessUser;
	
	public String deliveryTime;
	
	/* production */
	public String productionEndTime;
	
	/* QC */
	public String QCResult;
	
	public String QCConfirm;
	
	/* shipment */
	public String invoiceNumber;
	
	public String shipmentBookingTime;
	
	public String shipmentTime;
	
	public String portArrivalTime;
	
	public String customerInvArrivalTime;
	
	public String containerNumber;
	
	public String loadingPort;
	
	public String unloadingPort;
	
	public String containerSize;
	
	public String shipmentType;
	
	/* finance */
	public String invoicedTime;
	
	public String paymentTerm;
	
	public List<SaleOrderStatus> items;
}
