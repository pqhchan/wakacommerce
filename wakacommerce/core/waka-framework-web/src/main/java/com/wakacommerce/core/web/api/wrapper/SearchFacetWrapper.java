
package com.wakacommerce.core.web.api.wrapper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wakacommerce.core.search.domain.SearchFacetDTO;
import com.wakacommerce.core.search.domain.SearchFacetResultDTO;

/**
 *
 * @ hui
 */
@XmlRootElement(name = "searchFacet")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class SearchFacetWrapper extends BaseWrapper implements APIWrapper<SearchFacetDTO> {

    /*
     * Name of the field. (e.g. you might have a field name 
     * like "price" or "description")
     */
    @XmlElement
    protected String fieldName;

    /*
     * Indicates if this facet is active in the current search.
     */
    @XmlElement
    protected Boolean active = Boolean.FALSE;

    /*
     * List of values that are appropriate for this facet.
     */
    @XmlElement
    protected List<SearchFacetValueWrapper> values;

    @Override
    public void wrapDetails(SearchFacetDTO model, HttpServletRequest request) {
        this.fieldName = model.getFacet().getField().getAbbreviation();
        this.active = model.isActive();

        if (model.getFacetValues() != null) {
            this.values = new ArrayList<SearchFacetValueWrapper>();
            for (SearchFacetResultDTO result : model.getFacetValues()) {
                SearchFacetValueWrapper wrapper = (SearchFacetValueWrapper) context.getBean(SearchFacetValueWrapper.class.getName());
                wrapper.wrapSummary(result, request);
                this.values.add(wrapper);
            }
        }
    }

    @Override
    public void wrapSummary(SearchFacetDTO model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<SearchFacetValueWrapper> getValues() {
        return values;
    }

    public void setValues(List<SearchFacetValueWrapper> values) {
        this.values = values;
    }
}
