
package com.wakacommerce.core.offer.service;

import com.wakacommerce.core.offer.dao.CustomerOfferDao;
import com.wakacommerce.core.offer.dao.OfferCodeDao;
import com.wakacommerce.core.offer.dao.OfferDao;
import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.core.offer.domain.OfferCode;
import com.wakacommerce.core.offer.service.discount.domain.PromotableItemFactory;
import com.wakacommerce.core.offer.service.processor.FulfillmentGroupOfferProcessor;
import com.wakacommerce.core.offer.service.processor.ItemOfferProcessor;
import com.wakacommerce.core.offer.service.processor.OrderOfferProcessor;
import com.wakacommerce.core.offer.service.workflow.VerifyCustomerMaxOfferUsesActivity;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.OrderService;
import com.wakacommerce.core.pricing.service.exception.PricingException;
import com.wakacommerce.profile.core.domain.Customer;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

/**
 *
 * @ hui
 */
public interface OfferService {

    public List<Offer> findAllOffers();

    public Offer save(Offer offer);

    public OfferCode saveOfferCode(OfferCode offerCode);

    public Offer lookupOfferByCode(String code);

    public OfferCode findOfferCodeById(Long id);

    public OfferCode lookupOfferCodeByCode(String code);

    public Order applyAndSaveOffersToOrder(List<Offer> offers, Order order) throws PricingException;

    @Deprecated
    public void applyOffersToOrder(List<Offer> offers, Order order) throws PricingException;

    public List<Offer> buildOfferListForOrder(Order order);

    public List<OfferCode> buildOfferCodeListForCustomer(Customer customer);

    public CustomerOfferDao getCustomerOfferDao();

    public void setCustomerOfferDao(CustomerOfferDao customerOfferDao);

    public OfferCodeDao getOfferCodeDao();

    public void setOfferCodeDao(OfferCodeDao offerCodeDao);

    public OfferDao getOfferDao();

    public void setOfferDao(OfferDao offerDao);

    public OrderOfferProcessor getOrderOfferProcessor();

    public void setOrderOfferProcessor(OrderOfferProcessor orderOfferProcessor);

    public ItemOfferProcessor getItemOfferProcessor();

    public void setItemOfferProcessor(ItemOfferProcessor itemOfferProcessor);

    public FulfillmentGroupOfferProcessor getFulfillmentGroupOfferProcessor();

    public void setFulfillmentGroupOfferProcessor(FulfillmentGroupOfferProcessor fulfillmentGroupOfferProcessor);
    
    public Order applyAndSaveFulfillmentGroupOffersToOrder(List<Offer> offers, Order order) throws PricingException;

    @Deprecated
    public void applyFulfillmentGroupOffersToOrder(List<Offer> offers, Order order) throws PricingException;

    public PromotableItemFactory getPromotableItemFactory();

    public void setPromotableItemFactory(PromotableItemFactory promotableItemFactory);

    public boolean verifyMaxCustomerUsageThreshold(@Nonnull Customer customer, @Nonnull Offer offer);

    public boolean verifyMaxCustomerUsageThreshold(@Nonnull Customer customer, @Nonnull OfferCode code);

    public Set<Offer> getUniqueOffersFromOrder(Order order);

    public Map<Offer, OfferCode> getOffersRetrievedFromCodes(List<OfferCode> codes, Set<Offer> appliedOffers);

    public Map<Offer, OfferCode> getOffersRetrievedFromCodes(Order order);

    public OrderService getOrderService();

    public void setOrderService(OrderService orderService);
}
