

package com.wakacommerce.common.payment.dto;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @ hui
 */
public class LineItemDTO {

    protected PaymentRequestDTO parent;

    protected Map<String, Object> additionalFields;
    protected String name;
    protected String description;
    protected String shortDescription;
    protected String systemId;
    protected String category;
    protected String quantity;
    protected String amount;
    protected String tax;
    protected String itemTotal;
    protected String total;

    public PaymentRequestDTO done(){
        parent.lineItems.add(this);
        return parent;
    }

    public LineItemDTO(PaymentRequestDTO parent) {
        this.additionalFields = new HashMap<String, Object>();
        this.parent = parent;
    }

    public LineItemDTO additionalField(String key, Object value) {
        additionalFields.put(key, value);
        return this;
    }

    public LineItemDTO name(String name) {
        this.name = name;
        return this;
    }

    public LineItemDTO description(String description) {
        this.description = description;
        return this;
    }

    public LineItemDTO shortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public LineItemDTO systemId(String systemId) {
        this.systemId = systemId;
        return this;
    }

    public LineItemDTO category(String category) {
        this.category = category;
        return this;
    }

    public LineItemDTO quantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public LineItemDTO amount(String amount) {
        this.amount = amount;
        return this;
    }

    public LineItemDTO tax(String tax) {
        this.tax = tax;
        return this;
    }

    public LineItemDTO itemTotal(String itemTotal) {
        this.itemTotal = itemTotal;
        return this;
    }

    public LineItemDTO total(String total) {
        this.total = total;
        return this;
    }

    public Map<String, Object> getAdditionalFields() {
        return additionalFields;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getSystemId() {
        return systemId;
    }

    public String getCategory() {
        return category;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getAmount() {
        return amount;
    }

    public String getTax() {
        return tax;
    }

    public String getItemTotal() {
        return itemTotal;
    }

    public String getTotal() {
        return total;
    }
}
