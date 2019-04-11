package com.atguigu.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.atguigu.constant.AppConstant;

/**
 * 检查用户的登陆状态
 * 
 * @author lfy
 *
 */
public class LoginCheckIntercetor implements HandlerInterceptor {

	// 到达目标页面之前进行检查
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		Object user = request.getSession().getAttribute(AppConstant.LOGIN_USER);
		// 返回true放行，false就拦截；
		if (user != null) {
			return true;
		}
		request.setAttribute("msg", "没有权限，请先登陆");
		request.getRequestDispatcher("/login.html").forward(request, response);
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
