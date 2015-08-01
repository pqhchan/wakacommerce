package com.wakacommerce.common.web;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filters和Spring interceptors的通用处理接口. 要注意的是, 
 * 作为参数传进来的request应该是 {@link NativeWebRequest} 的子类.
 * 可以看一下下面的例子:
 * 
 * <pre>
 *   public class SomeServletFilter extends GenericFilterBean {
 *      &#064;Resource(name="blCustomerStateRequestProcessor")
 *      protected WakaWebRequestProcessor processor;
 *      
 *      public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
 *          processor.process(new ServletWebRequest(request, response));
 *      }
 *   }
 * </pre>
 * 
 * <p>要尽可能多的往 {@link WebRequest} 里填充信息. 像上面的例子，我们同时用了
 * {@link HttpServletRequest} 和 {@link HttpServletResponse} 来初始化 {@link ServletWebRequest}
 * 
 * @see {@link NativeWebRequest}
 * @see {@link ServletWebRequest}
 * @see {@link PortletWebRequest}
 * @see {@link WakaRequestFilter}
 */
public interface WakaWebRequestProcessor {

    /**
     * 处理当期请求. 比如说在该请求上设置当期登录的customer 
     * 
     * @param request
     */
    public void process(WebRequest request);
    
    /**
     * 请求处理完后，如果还有工作要做，这时候可以调用该方法
     * 
     * @param request
     */
    public void postProcess(WebRequest request);

}
