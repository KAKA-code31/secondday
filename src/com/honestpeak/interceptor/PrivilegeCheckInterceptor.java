package com.honestpeak.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.honestpeak.constant.AccountConstants;
import com.honestpeak.constant.PathConstants;
import com.honestpeak.shiro.ShiroUser;

/**
 * @ClassName: PrivilegeCheckInterceptor
 * @Description: URL权限验证拦截器
 * @author Jeabev
 * @date 2017年3月27日 下午5:52:03
 */
public class PrivilegeCheckInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 获取请求的url，并对路径URL进行处理，验证是否需要权限验证
		String url = initRequestUrl(request.getServletPath());
		
		System.out.println("访问路径信息：--->"+url);
		
		if (!this.isNeedPermit(url)) {
			// 如果不需要进行权限验证，放行
			return true;
		}
		
		//查看是否授权
		if (SecurityUtils.getSubject().isAuthenticated()) {
			//获取shiro User对象
			boolean permission = SecurityUtils.getSubject().isPermitted(url);
			//判断是否需要后台操作权限，验证user身份
			if(permission && isNeedBackAuth(url)){
				ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
				//无后台权限 (学生)
				if(user.adminType !=null){
//					if(user.adminType < AccountConstants.LOGIN_ADMIN){
//						permission = false;
//					}
				}
			}
			if(!permission){
				response.sendRedirect(request.getContextPath()+"/unauth");
			}
			return permission;
        }
		//执行这里表示用户身份需要认证，跳转登陆页面
		response.sendRedirect(request.getContextPath()+"/admin");
		
		//return false表示拦截，不向下执行
		//return true表示放行
		return false;
	}
	
	/**
	 * @Title: isNeedBackAuth
	 * @Description: 检查URL是否需要后台权限验证
	 * @param url
	 * @return
	 */
	private boolean isNeedBackAuth(String url) {
		//判断URL是否需要后台权限验证
        for (String method : PathConstants.PERMIBACK) {
            if (url.indexOf(method) > -1) {
                return true;
            }
        }
        
        return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
	/**
	 * @Title: isNeedPermit
	 * @Description: 判断 访问请求是否需要权限验证
	 * @param method 请求url
	 * @return
	 */
	private boolean isNeedPermit(String method) {
		
		//判断NameSpace 是否需要进行权限拦截
		for (String nameSpace : PathConstants.N_NAMESPACE) {
			if (method.indexOf(nameSpace) > -1) {
                return false;
            }
		}
		
		//判断方法名是否需要拦截
        for (String url : PathConstants.PERMITURL) {
            if (method.indexOf(url) > -1) {
                return true;
            }
        }
        
        return false;
    }
	
	/**
	 * @Title: initRequestUrl
	 * @Description: 初始化访问请求路径
	 * @param requestUrl
	 * @return
	 */
	private String initRequestUrl(String requestUrl) {
		//这里因为有的请求包含两个，但是都是同一个权限，所以在这里，对请求加以改进，将带UI的结尾去掉
		if(requestUrl.indexOf("UI") > -1){
			requestUrl = requestUrl.substring(0,requestUrl.lastIndexOf("UI"));
		} else {
			//这里因为采用了 restful风格的URL，所以这里要把参数给去掉
			String[] pattern = {"detail/","delete/"};
			for(String s : pattern){
				if(requestUrl.indexOf(s) > -1){
					requestUrl = requestUrl.substring(0,requestUrl.lastIndexOf("/"));
					break;
				}
			}
		}
		return requestUrl;
	}

}
