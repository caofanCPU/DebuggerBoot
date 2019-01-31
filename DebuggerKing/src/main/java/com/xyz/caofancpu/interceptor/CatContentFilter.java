package com.xyz.caofancpu.interceptor;

import com.dianping.cat.Cat;
import com.xyz.caofancpu.model.CatContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * FileName: CatContentFilter
 * CAT请求封装, 若调用方接入了CAT, 则把调用方传来的CAT_msgId信息织入成树, 继续向下层服务传递
 *
 * @author: caofanCPU
 * @date: 2019/1/24 15:09
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "CATFilter")
public class CatContentFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {
        
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String catFlag = "cat";
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String messageId = httpServletRequest.getHeader(catFlag + Cat.Context.CHILD);
        String rootId = httpServletRequest.getHeader(catFlag + Cat.Context.ROOT);
        String parentId = httpServletRequest.getHeader(catFlag + Cat.Context.PARENT);
        if (StringUtils.isNotEmpty(messageId) && StringUtils.isNotEmpty(rootId) && StringUtils.isNotEmpty(parentId)) {
            CatContext catContext = new CatContext();
            catContext.addProperty(Cat.Context.ROOT, rootId);
            catContext.addProperty(Cat.Context.PARENT, parentId);
            catContext.addProperty(Cat.Context.CHILD, messageId);
            Cat.logRemoteCallServer(catContext);
        }
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
    
    }
    
}
