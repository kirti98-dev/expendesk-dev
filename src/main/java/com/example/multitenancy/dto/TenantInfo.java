package com.example.multitenancy.dto;

public class TenantInfo {
	private String clientId;
	private String dbUrl;
	private String schema;
	private String userId;
	private String password;

	public TenantInfo(String clientId, String dbUrl, String schema, String userId, String password) {
		this.clientId = clientId;
		this.dbUrl = dbUrl;
		this.schema = schema;
		this.userId = userId;
		this.password = password;
	}

	public String getClientId() {
		return clientId;
	}

	public String getSchema() {
		return schema;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

}
