package com.yishanxiu.yishang.model;

/**
 * app 信息
 */
public class ApkInfo extends BaseModel {

	private boolean autoInstall=false;
	private boolean isShowNotification=true;

	private String versionNumber;

	private String versionContent;

	private String versionFileName;

	private String versionFilePath;


	public ApkInfo() {
	}

	public ApkInfo(String name, String versionFilePath) {
		this.versionFileName = name;
		this.versionFilePath = versionFilePath;
	}

	public ApkInfo(boolean autoInstall, boolean isShowNotification, String versionFilePath) {
		this.autoInstall = autoInstall;
		this.isShowNotification = isShowNotification;
		this.versionFilePath = versionFilePath;
	}

	public ApkInfo(boolean autoInstall, boolean isShowNotification, String versionNumber, String versionContent, String versionFileName, String versionFilePath) {
		this.autoInstall = autoInstall;
		this.isShowNotification = isShowNotification;
		this.versionNumber = versionNumber;
		this.versionContent = versionContent;
		this.versionFileName = versionFileName;
		this.versionFilePath = versionFilePath;
	}

	public boolean isAutoInstall() {
		return autoInstall;
	}

	public void setAutoInstall(boolean autoInstall) {
		this.autoInstall = autoInstall;
	}

	public boolean isShowNotification() {
		return isShowNotification;
	}

	public void setShowNotification(boolean showNotification) {
		isShowNotification = showNotification;
	}


	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getVersionContent() {
		return versionContent;
	}

	public void setVersionContent(String versionContent) {
		this.versionContent = versionContent;
	}

	public String getVersionFileName() {
		return versionFileName;
	}

	public void setVersionFileName(String versionFileName) {
		this.versionFileName = versionFileName;
	}

	public String getVersionFilePath() {
		return versionFilePath;
	}

	public void setVersionFilePath(String versionFilePath) {
		this.versionFilePath = versionFilePath;
	}
}