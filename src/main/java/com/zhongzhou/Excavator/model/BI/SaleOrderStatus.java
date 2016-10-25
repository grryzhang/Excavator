package com.zhongzhou.Excavator.model.BI;

import org.mongodb.morphia.annotations.Entity;

@Entity( value="BIReports" )
public class SaleOrderStatus {

	private String customerOrderId;
	
	private String orderTime;
	
	private String invoiceCode;
	
	private String customerName;
	
	private String customerBuyer;
	
	private String customerItemId;
	
	private String itemName;
	
	private String itemStandard;
	
	private String itemUnit;
	
	private String orderQuantity;
	
	private String tradeTerm;
	
	private String itemPrice;
	
	private String totalPrice;
	
	private String businessUser;
	
	private String deliveryTime;
	
	/* production */
	private String productionEndTime;
	
	/* QC */
	private String QCResult;
	
	private String QCConfirm;
	
	/* shipment */
	private String invoiceNumber;
	
	private String shipmentBookingTime;
	
	//ETD
	private String shipmentTime;
	
	//ETA
	private String portArrivalTime;
	
	private String customerInvArrivalTime;
	
	private String containerNumber;
	
	private String loadingPort;
	
	private String unloadingPort;
	
	private String containerSize;
	
	private String shipmentType;
	
	/* finance */
	private String invoicedTime;
	
	private String paymentTerm;

	public String getCustomerOrderId() {
		return customerOrderId;
	}

	public void setCustomerOrderId(String customerOrderId) {
		this.customerOrderId = customerOrderId;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerBuyer() {
		return customerBuyer;
	}

	public void setCustomerBuyer(String customerBuyer) {
		this.customerBuyer = customerBuyer;
	}

	public String getCustomerItemId() {
		return customerItemId;
	}

	public void setCustomerItemId(String customerItemId) {
		this.customerItemId = customerItemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemStandard() {
		return itemStandard;
	}

	public void setItemStandard(String itemStandard) {
		this.itemStandard = itemStandard;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public String getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(String orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public String getTradeTerm() {
		return tradeTerm;
	}

	public void setTradeTerm(String tradeTerm) {
		this.tradeTerm = tradeTerm;
	}

	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getBusinessUser() {
		return businessUser;
	}

	public void setBusinessUser(String businessUser) {
		this.businessUser = businessUser;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getProductionEndTime() {
		return productionEndTime;
	}

	public void setProductionEndTime(String productionEndTime) {
		this.productionEndTime = productionEndTime;
	}

	public String getQCResult() {
		return QCResult;
	}

	public void setQCResult(String qCResult) {
		QCResult = qCResult;
	}

	public String getQCConfirm() {
		return QCConfirm;
	}

	public void setQCConfirm(String qCConfirm) {
		QCConfirm = qCConfirm;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getShipmentBookingTime() {
		return shipmentBookingTime;
	}

	public void setShipmentBookingTime(String shipmentBookingTime) {
		this.shipmentBookingTime = shipmentBookingTime;
	}

	public String getShipmentTime() {
		return shipmentTime;
	}

	public void setShipmentTime(String shipmentTime) {
		this.shipmentTime = shipmentTime;
	}

	public String getPortArrivalTime() {
		return portArrivalTime;
	}

	public void setPortArrivalTime(String portArrivalTime) {
		this.portArrivalTime = portArrivalTime;
	}

	public String getCustomerInvArrivalTime() {
		return customerInvArrivalTime;
	}

	public void setCustomerInvArrivalTime(String customerInvArrivalTime) {
		this.customerInvArrivalTime = customerInvArrivalTime;
	}

	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	public String getLoadingPort() {
		return loadingPort;
	}

	public void setLoadingPort(String loadingPort) {
		this.loadingPort = loadingPort;
	}

	public String getUnloadingPort() {
		return unloadingPort;
	}

	public void setUnloadingPort(String unloadingPort) {
		this.unloadingPort = unloadingPort;
	}

	public String getContainerSize() {
		return containerSize;
	}

	public void setContainerSize(String containerSize) {
		this.containerSize = containerSize;
	}

	public String getShipmentType() {
		return shipmentType;
	}

	public void setShipmentType(String shipmentType) {
		this.shipmentType = shipmentType;
	}

	public String getInvoicedTime() {
		return invoicedTime;
	}

	public void setInvoicedTime(String invoicedTime) {
		this.invoicedTime = invoicedTime;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}
	
	
}
