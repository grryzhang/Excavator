package com.zhongzhou.Excavator.service.impl.migration.mailMonitor;

public class MailDataParserAdaptorConfig {

	private String mailNameRegular;
	
	private String attachmentNameRegular;
	
	private String adaptorServiceName;

	public String getMailNameRegular() {
		return mailNameRegular;
	}

	public void setMailNameRegular(String mailNameRegular) {
		this.mailNameRegular = mailNameRegular;
	}

	public String getAttachmentNameRegular() {
		return attachmentNameRegular;
	}

	public void setAttachmentNameRegular(String attachmentNameRegular) {
		this.attachmentNameRegular = attachmentNameRegular;
	}

	public String getAdaptorServiceName() {
		return adaptorServiceName;
	}

	public void setAdaptorServiceName(String adaptorServiceName) {
		this.adaptorServiceName = adaptorServiceName;
	}
}
