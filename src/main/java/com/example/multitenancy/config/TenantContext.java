package com.example.multitenancy.config;

import com.example.multitenancy.dto.TenantInfo;

public class TenantContext {
    private static final ThreadLocal<TenantInfo> CONTEXT = new ThreadLocal<>();

    public static void setTenantInfo(TenantInfo tenantInfo) {
        CONTEXT.set(tenantInfo);
    }

    public static TenantInfo getTenantInfo() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
