package club.leyvan.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object arg) throws Exception {
		Object object = request.getSession().getAttribute("loginUser");
		if(object==null){
			response.sendRedirect(request.getContextPath()+"/user/toLogin.action");
			return false;
		}
		return true;
	}
}
