package com.gitee.taven.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 先标记后踢出
 *
 */
public class MarkKickOutFilter extends KickOutFilter {

    @Override
    public boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response) {
        return false;
    }
}
