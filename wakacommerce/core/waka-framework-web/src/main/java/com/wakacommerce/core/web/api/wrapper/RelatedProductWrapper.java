
package com.wakacommerce.core.web.api.wrapper;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wakacommerce.core.catalog.domain.RelatedProduct;

/**
 * This is a JAXB wrapper for RelatedProducts
 *
 * User: Kelly Tisdell
 * Date: 4/10/12
 */
@XmlRootElement(name = "relatedProduct")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class RelatedProductWrapper extends BaseWrapper implements APIWrapper<RelatedProduct> {

    @XmlElement
    protected Long id;
    
    @XmlElement
    protected BigDecimal sequence;
    
    @XmlElement
    protected String promotionalMessage;

    @XmlElement
    protected ProductWrapper product;
    
    @Override
    public void wrapDetails(RelatedProduct model, HttpServletRequest request) {
        this.id = model.getId();
        this.sequence = model.getSequence();
        this.promotionalMessage = model.getPromotionMessage();
        product = (ProductWrapper) context.getBean(ProductWrapper.class.getName());
        product.wrapSummary(model.getRelatedProduct(), request);
    }

    @Override
    public void wrapSummary(RelatedProduct model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    
    /**
     * @return the sequence
     */
    public BigDecimal getSequence() {
        return sequence;
    }

    
    /**
     * @param sequence the sequence to set
     */
    public void setSequence(BigDecimal sequence) {
        this.sequence = sequence;
    }

    
    /**
     * @return the promotionalMessage
     */
    public String getPromotionalMessage() {
        return promotionalMessage;
    }

    
    /**
     * @param promotionalMessage the promotionalMessage to set
     */
    public void setPromotionalMessage(String promotionalMessage) {
        this.promotionalMessage = promotionalMessage;
    }

    
    /**
     * @return the product
     */
    public ProductWrapper getProduct() {
        return product;
    }

    
    /**
     * @param product the product to set
     */
    public void setProduct(ProductWrapper product) {
        this.product = product;
    }
}
