
package com.wakacommerce.core.web.api.endpoint.customer;

import com.wakacommerce.core.web.api.endpoint.BaseEndpoint;
import com.wakacommerce.profile.core.service.CustomerService;

import javax.annotation.Resource;

/**
 * This endpoint depends on JAX-RS.  It should be extended by components that actually wish 
 * to provide an endpoint.  The annotations such as @Path, @Scope, @Context, @PathParam, @QueryParam, 
 * @GET, @POST, @PUT, and @DELETE are purposely not provided here to allow implementors finer control over 
 * the details of the endpoint.
 * <p/>
 * User: Kelly Tisdell
 * Date: 4/10/12
 */
public abstract class CustomerEndpoint extends BaseEndpoint {

    @Resource(name="blCustomerService")
    protected CustomerService customerService;

}
