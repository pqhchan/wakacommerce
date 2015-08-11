package com.wakacommerce.common.config.service.type;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.wakacommerce.common.WakaEnumType;


public class ModuleConfigurationType implements WakaEnumType, Serializable {

    private static final long serialVersionUID = 1L;

    private static final Map<String, ModuleConfigurationType> TYPES = new LinkedHashMap<String, ModuleConfigurationType>();

    public static final ModuleConfigurationType FULFILLMENT_PRICING = new ModuleConfigurationType("FULFILLMENT_PRICING", "配送费用");
    public static final ModuleConfigurationType TAX_CALCULATION = new ModuleConfigurationType("TAX_CALCULATION", "税费计算");
    public static final ModuleConfigurationType ADDRESS_VERIFICATION = new ModuleConfigurationType("ADDRESS_VERIFICATION", "地址验证");
    public static final ModuleConfigurationType PAYMENT_PROCESSOR = new ModuleConfigurationType("PAYMENT_PROCESSOR", "支付处理器");
    public static final ModuleConfigurationType CDN_PROVIDER = new ModuleConfigurationType("CDN_PROVIDER", "内容分发网络");
    public static final ModuleConfigurationType SITE_MAP = new ModuleConfigurationType("SITE_MAP", "站点地图生成器");

    public static ModuleConfigurationType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;

    public ModuleConfigurationType() {
        //do nothing
    }

    public ModuleConfigurationType(final String type, final String friendlyType) {
        this.friendlyType = friendlyType;
        setType(type);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getFriendlyType() {
        return friendlyType;
    }

    private void setType(final String type) {
        this.type = type;
        if (!TYPES.containsKey(type)) {
            TYPES.put(type, this);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!getClass().isAssignableFrom(obj.getClass()))
            return false;
        ModuleConfigurationType other = (ModuleConfigurationType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

}
