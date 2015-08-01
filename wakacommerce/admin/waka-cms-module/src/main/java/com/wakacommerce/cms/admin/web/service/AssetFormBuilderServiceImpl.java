package com.wakacommerce.cms.admin.web.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wakacommerce.cms.file.service.StaticAssetService;
import com.wakacommerce.cms.file.service.operation.StaticMapNamedOperationComponent;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.openadmin.web.form.component.ListGrid;
import com.wakacommerce.openadmin.web.form.component.ListGridRecord;
import com.wakacommerce.openadmin.web.form.component.MediaField;
import com.wakacommerce.openadmin.web.form.entity.Field;
import com.wakacommerce.openadmin.web.service.FormBuilderService;

@Service("blAssetFormBuilderService")
public class AssetFormBuilderServiceImpl implements AssetFormBuilderService {
    
    @Resource(name = "blFormBuilderService")
    protected FormBuilderService formBuilderService;
    
    @Resource(name = "blStaticAssetService")
    protected StaticAssetService staticAssetService;
    
    @Resource(name = "blStaticMapNamedOperationComponent")
    protected StaticMapNamedOperationComponent operationMap;
 
    @Override
    public void addImageThumbnailField(ListGrid listGrid, String urlField) {
        listGrid.getHeaderFields().add(new Field()
            .withName("thumbnail")
            .withFriendlyName("缩略图")
            .withFieldType(SupportedFieldType.STRING.toString())
            .withOrder(Integer.MIN_VALUE)
            .withColumnWidth("50px")
            .withFilterSortDisabled(true));
        
        for (ListGridRecord record : listGrid.getRecords()) {
            // 获取资源对应url地址
            String imageUrl = record.getField(urlField).getValue();
            
            // 如果需要的话加入url前缀
            String staticAssetUrlPrefix = staticAssetService.getStaticAssetUrlPrefix();
            if (staticAssetUrlPrefix != null && !staticAssetUrlPrefix.startsWith("/")) {
                staticAssetUrlPrefix = "/" + staticAssetUrlPrefix;
            }
            if (staticAssetUrlPrefix == null) {
                staticAssetUrlPrefix = "";
            } else {
                imageUrl = staticAssetUrlPrefix + imageUrl;
            }
            
            MediaField mf = (MediaField) new MediaField()
                .withName("thumbnail")
                .withFriendlyName("缩略图")
                .withFieldType(SupportedFieldType.IMAGE.toString())
                .withOrder(Integer.MIN_VALUE)
                .withValue(imageUrl);
            
            // 添加隐藏字段存储相关信息，以便后面获取大图
            record.getHiddenFields().add(new Field()
                .withName("cmsUrlPrefix")
                .withValue(staticAssetUrlPrefix));
            
            record.getHiddenFields().add(new Field()
                .withName("thumbnailKey")
                .withValue("?smallAdminThumbnail"));
            
            record.getHiddenFields().add(new Field()
                .withName("servletContext")
                .withValue(WakaRequestContext.getWakaRequestContext().getRequest().getContextPath()));
            
            // 设置高度
            mf.setHeight(operationMap.getNamedOperations().get("smallAdminThumbnail").get("resize-height-amount"));
            record.getFields().add(mf);
            
            // 清空缓存，确保新添加的字段正确显示
            record.clearFieldMap();
        }
    }
}
