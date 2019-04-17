package com.gitee.taven.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * 先标记后踢出
 *
 */
public class MarkKickOutFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {

    }
}
