/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mycompany.controller.checkout;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.pricing.service.exception.PricingException;
import com.wakacommerce.core.web.checkout.model.BillingInfoForm;
import com.wakacommerce.core.web.checkout.model.GiftCardInfoForm;
import com.wakacommerce.core.web.checkout.model.OrderInfoForm;
import com.wakacommerce.core.web.checkout.model.ShippingInfoForm;
import com.wakacommerce.core.web.controller.checkout.BroadleafBillingInfoController;
import com.wakacommerce.core.web.order.CartState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class BillingInfoController extends BroadleafBillingInfoController {

    @RequestMapping(value="/checkout/billing", method = RequestMethod.POST)
    public String saveBillingAddress(HttpServletRequest request, HttpServletResponse response, Model model,
                                     @ModelAttribute("orderInfoForm") OrderInfoForm orderInfoForm,
                                     @ModelAttribute("shippingInfoForm") ShippingInfoForm shippingForm,
                                     @ModelAttribute("giftCardInfoForm") GiftCardInfoForm giftCardInfoForm,
                                     @ModelAttribute("billingInfoForm") BillingInfoForm billingForm,
                                     BindingResult result)
            throws PricingException, ServiceException {
        return super.saveBillingAddress(request, response, model, billingForm, result);
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
    }

}
