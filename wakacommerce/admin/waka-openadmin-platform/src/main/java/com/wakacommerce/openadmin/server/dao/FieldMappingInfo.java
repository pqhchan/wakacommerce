package com.wakacommerce.openadmin.server.dao;

import java.lang.reflect.Type;

public class FieldMappingInfo {

    protected String name; 					// 字段名称
    protected Type genericType; 				// 字段的java类型
    protected String manyToManyMappedBy; 		// 多对多映射时关系维护者的映射字段
    protected String manyToManyTargetEntity; 	// 多对多映射时关系维护者的类名-Class.getName()
    protected String oneToManyMappedBy; 		// 一对多映射时关系维护者的映射字段
    protected String oneToManyTargetEntity;	// 一对多映射时关系维护者的类名-Class.getName()
    protected String mapKey;					// Map集合映射时作为Map的Key的字段名

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getGenericType() {
        return genericType;
    }

    public void setGenericType(Type genericType) {
        this.genericType = genericType;
    }

    public String getManyToManyMappedBy() {
        return manyToManyMappedBy;
    }

    public void setManyToManyMappedBy(String manyToManyMappedBy) {
        this.manyToManyMappedBy = manyToManyMappedBy;
    }

    public String getManyToManyTargetEntity() {
        return manyToManyTargetEntity;
    }

    public void setManyToManyTargetEntity(String manyToManyTargetEntity) {
        this.manyToManyTargetEntity = manyToManyTargetEntity;
    }

    public String getOneToManyMappedBy() {
        return oneToManyMappedBy;
    }

    public void setOneToManyMappedBy(String oneToManyMappedBy) {
        this.oneToManyMappedBy = oneToManyMappedBy;
    }

    public String getOneToManyTargetEntity() {
        return oneToManyTargetEntity;
    }

    public void setOneToManyTargetEntity(String oneToManyTargetEntity) {
        this.oneToManyTargetEntity = oneToManyTargetEntity;
    }

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }

}
