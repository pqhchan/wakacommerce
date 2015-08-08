package com.wakacommerce.core.catalog.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.common.util.DimensionUnitOfMeasureType;
import com.wakacommerce.common.vendor.service.type.ContainerShapeType;
import com.wakacommerce.common.vendor.service.type.ContainerSizeType;

@Embeddable
public class Dimension implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "WIDTH")
    @AdminPresentation(
    	friendlyName = "Dimension_width", 
    	order = 2000,
        tab = ProductImpl.Presentation.Tab.Name.Shipping, tabOrder = ProductImpl.Presentation.Tab.Order.Shipping,
        group = ProductImpl.Presentation.Group.Name.Shipping, groupOrder = ProductImpl.Presentation.Group.Order.Shipping)
    protected BigDecimal width;

    @Column(name = "HEIGHT")
    @AdminPresentation(friendlyName = "Dimension_height", order = 3000,
        tab = ProductImpl.Presentation.Tab.Name.Shipping, tabOrder = ProductImpl.Presentation.Tab.Order.Shipping,
        group = ProductImpl.Presentation.Group.Name.Shipping, groupOrder = ProductImpl.Presentation.Group.Order.Shipping)
    protected BigDecimal height;

    @Column(name = "DEPTH")
    @AdminPresentation(friendlyName = "Dimension_depth", order = 4000,
        tab = ProductImpl.Presentation.Tab.Name.Shipping, tabOrder = ProductImpl.Presentation.Tab.Order.Shipping,
        group = ProductImpl.Presentation.Group.Name.Shipping, groupOrder = ProductImpl.Presentation.Group.Order.Shipping)
    protected BigDecimal depth;

    @Column(name = "GIRTH")
    @AdminPresentation(friendlyName = "Dimension_girth", order = 5000,
        tab = ProductImpl.Presentation.Tab.Name.Shipping, tabOrder = ProductImpl.Presentation.Tab.Order.Shipping,
        group = ProductImpl.Presentation.Group.Name.Shipping, groupOrder = ProductImpl.Presentation.Group.Order.Shipping)
    protected BigDecimal girth;

    @Column(name = "CONTAINER_SIZE")
    @AdminPresentation(friendlyName = "Dimension_containerSize", order = 6000,
        tab = ProductImpl.Presentation.Tab.Name.Shipping, tabOrder = ProductImpl.Presentation.Tab.Order.Shipping,
        group = ProductImpl.Presentation.Group.Name.Shipping, groupOrder = ProductImpl.Presentation.Group.Order.Shipping,
        fieldType = SupportedFieldType.WAKA_ENUMERATION, 
        wakaEnumeration = "com.wakacommerce.common.vendor.service.type.ContainerSizeType")
    protected String size;

    @Column(name = "CONTAINER_SHAPE")
    @AdminPresentation(
    	friendlyName = "Dimension_containerShape",
    	order = 7000,
        tab = ProductImpl.Presentation.Tab.Name.Shipping, 
        tabOrder = ProductImpl.Presentation.Tab.Order.Shipping,
        group = ProductImpl.Presentation.Group.Name.Shipping, 
        groupOrder = ProductImpl.Presentation.Group.Order.Shipping,
        fieldType = SupportedFieldType.WAKA_ENUMERATION,
        wakaEnumeration = "com.wakacommerce.common.vendor.service.type.ContainerShapeType")
    protected String container;

    @Column(name = "DIMENSION_UNIT_OF_MEASURE")
    @AdminPresentation(
    	friendlyName = "Dimension_dimensionUnitOfMeasure", 
    	order = 8000,
        tab = ProductImpl.Presentation.Tab.Name.Shipping, 
        tabOrder = ProductImpl.Presentation.Tab.Order.Shipping,
        group = ProductImpl.Presentation.Group.Name.Shipping, 
        groupOrder = ProductImpl.Presentation.Group.Order.Shipping,
        fieldType = SupportedFieldType.WAKA_ENUMERATION, 
        wakaEnumeration = "com.wakacommerce.common.util.DimensionUnitOfMeasureType")
    protected String dimensionUnitOfMeasure;

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(final BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(final BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getDepth() {
        return depth;
    }

    public void setDepth(final BigDecimal depth) {
        this.depth = depth;
    }

    public String getDimensionString() {
        return height + "Hx" + width + "Wx" + depth + "D\"";
    }

    public BigDecimal getGirth() {
        return girth;
    }

    public void setGirth(final BigDecimal girth) {
        this.girth = girth;
    }

    public ContainerSizeType getSize() {
        return ContainerSizeType.getInstance(size);
    }

    public void setSize(final ContainerSizeType size) {
        if (size != null) {
            this.size = size.getType();
        }
    }

    public ContainerShapeType getContainer() {
        return ContainerShapeType.getInstance(container);
    }

    public void setContainer(final ContainerShapeType container) {
        if (container != null) {
            this.container = container.getType();
        }
    }

    public DimensionUnitOfMeasureType getDimensionUnitOfMeasure() {
        return DimensionUnitOfMeasureType.getInstance(dimensionUnitOfMeasure);
    }

    public void setDimensionUnitOfMeasure(final DimensionUnitOfMeasureType dimensionUnitOfMeasure) {
        if (dimensionUnitOfMeasure != null) {
            this.dimensionUnitOfMeasure = dimensionUnitOfMeasure.getType();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;

        Dimension dimension = (Dimension) o;

        if (container != null ? !container.equals(dimension.container) : dimension.container != null) return false;
        if (depth != null ? !depth.equals(dimension.depth) : dimension.depth != null) return false;
        if (dimensionUnitOfMeasure != null ? !dimensionUnitOfMeasure.equals(dimension.dimensionUnitOfMeasure) :
                dimension.dimensionUnitOfMeasure != null)
            return false;
        if (girth != null ? !girth.equals(dimension.girth) : dimension.girth != null) return false;
        if (height != null ? !height.equals(dimension.height) : dimension.height != null) return false;
        if (size != null ? !size.equals(dimension.size) : dimension.size != null) return false;
        if (width != null ? !width.equals(dimension.width) : dimension.width != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = width != null ? width.hashCode() : 0;
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (depth != null ? depth.hashCode() : 0);
        result = 31 * result + (girth != null ? girth.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (container != null ? container.hashCode() : 0);
        result = 31 * result + (dimensionUnitOfMeasure != null ? dimensionUnitOfMeasure.hashCode() : 0);
        return result;
    }
}
