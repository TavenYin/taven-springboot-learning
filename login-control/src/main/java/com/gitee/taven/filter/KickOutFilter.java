package com.gitee.taven.filter;

import com.gitee.taven.pojo.CurrentUser;
import com.gitee.taven.pojo.UserBO;
import com.gitee.taven.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class KickOutFilter implements Filter {

    public final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RedissonClient redissonClient;

    @Autowired
    public UserService userService;

    public static final String PREFIX = "uni_token_";

    public static final String PREFIX_LOCK = "uni_token_lock_";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        try {
            if (checkToken(request, response) && isAccessAllowed(request, response)) {
                filterChain.doFilter(req, resp);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 当前用户是否允许访问
     *
     * @param request
     * @param response
     * @return
     */
    public abstract boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 检查是否携带token 以及token是否失效
     *
     * @param request
     * @param response
     * @return
     */
    public boolean checkToken(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            sendJsonResponse(response, 401, "你无权访问");
            return false;
        }

        // 校验token是否存在
        RBucket<UserBO> rBucket = redissonClient.getBucket(token);
        UserBO userBO = rBucket.get();

        if (userBO == null) {
            sendJsonResponse(response, 403, "无效令牌");
            return false;
        }

        CurrentUser.put(userBO);
        return true;
    }

    public static void sendJsonResponse(HttpServletResponse resp, int code, String message) {
        sendJsonResponse(resp, String.format(jsonTemplate(), code, message));
    }

    public static String jsonTemplate() {
        return "{\"code\":%s,\"msg\":\"%s\",\"data\":null,\"errors\":null}";
    }

    public static void sendJsonResponse(HttpServletResponse response, String json) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
