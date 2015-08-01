package com.wakacommerce.cms.file.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.openadmin.audit.AdminAuditableListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(value = { AdminAuditableListener.class })
@Table(name = "BLC_IMG_STATIC_ASSET")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blCMSElements")
public class ImageStaticAssetImpl extends StaticAssetImpl implements ImageStaticAsset {

	private static final long serialVersionUID = 1L;

	@Column(name ="WIDTH")
    @AdminPresentation(friendlyName = "宽度",
            order = Presentation.FieldOrder.LAST + 1000,
            tab = Presentation.Tab.Name.File_Details, tabOrder = Presentation.Tab.Order.File_Details,
            readOnly = true)
    protected Integer width;

    @Column(name ="HEIGHT")
    @AdminPresentation(friendlyName = "高度",
            order = Presentation.FieldOrder.LAST + 2000,
            tab = Presentation.Tab.Name.File_Details, tabOrder = Presentation.Tab.Order.File_Details,
            readOnly = true)
    protected Integer height;

    @Override
    public Integer getWidth() {
        return width;
    }

    @Override
    public void setWidth(Integer width) {
        this.width = width;
    }

    @Override
    public Integer getHeight() {
        return height;
    }

    @Override
    public void setHeight(Integer height) {
        this.height = height;
    }

}
