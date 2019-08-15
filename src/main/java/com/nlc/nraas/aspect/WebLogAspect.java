package com.nlc.nraas.aspect;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

@Aspect
@Order(5)
@Component
public class WebLogAspect {

	private final Logger logger = LoggerFactory.getLogger(getClass());

//	ThreadLocal<Long> startTime = new ThreadLocal<>();

	/**
     * 定义一个切入点.
     * 解释下：
     *
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 任意包名
     * ~ 第三个 * 代表任意方法.
     * ~ 第四个 * 定义在web包或者子包
     * ~ 第五个 * 任意方法
     * ~ .. 匹配任意数量的参数.
     */
//	@Pointcut("execution(public * com.nlc.nraas.controller.*.*(..)) || target(org.springframework.data.repository.CrudRepository)")
	@Pointcut("execution(public * com.nlc.nraas.controller.*.*(..))")
	public void webLog() {
	}
	
	@Around("webLog()")
	public Object advice(ProceedingJoinPoint joinPoint) throws Throwable{
		
		long beginTime = System.currentTimeMillis();
		
	    try {
	    	// 接收到请求，记录请求内容
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();

			// 记录下请求内容
//			logger.info("URL : " + request.getRequestURL().toString());
//			logger.info("HTTP_METHOD : " + request.getMethod());
//			logger.info("IP : " + getClientIpAddr(request));
//			logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
//					+ joinPoint.getSignature().getName());
//			logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
			String beanName = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();
            String uri = request.getRequestURI();
            String remoteAddr = getClientIpAddr(request);
//            String sessionId = request.getSession().getId();
            String user = (String) request.getSession().getAttribute("user");
            String method = request.getMethod();
			String params = "";
            if ("POST".equals(method)) {
                Object[] paramsArray = joinPoint.getArgs();
                params = argsArrayToString(paramsArray);
            } else {
                Map<?, ?> paramsMap = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                params = paramsMap.toString();
            }
            
            logger.debug("uri=" + uri + "; beanName=" + beanName + "; remoteAddr=" + remoteAddr + "; user=" + user
                    + "; methodName=" + methodName + "; params=" + params);
            
	        Object ret = joinPoint.proceed();
	        
	        // 处理完请求，返回内容
	 		logger.debug("RESPONSE : " + ret);
	 		logger.debug("SPEND TIME(millisecond) : " + (System.currentTimeMillis() - beginTime));
	 		
	 		return ret;
	    } catch (Throwable e) {
//	    	logger.error("*** Exception ***", e);
//	        e.printStackTrace();
//	        return null;
	    	// 处理完请求，返回内容
	 		logger.debug("RESPONSE with exception : " + e.getMessage());
	 		logger.debug("SPEND TIME(millisecond) : " + (System.currentTimeMillis() - beginTime));
	    	throw e;
	    }
	}

//	@Before("webLog()")
//	public void doBefore(JoinPoint joinPoint) throws Throwable {
//		startTime.set(System.currentTimeMillis());
//
//		// 接收到请求，记录请求内容
//		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request = attributes.getRequest();
//
//		// 记录下请求内容
//		logger.info("URL : " + request.getRequestURL().toString());
//		logger.info("HTTP_METHOD : " + request.getMethod());
//		logger.info("IP : " + request.getRemoteAddr());
//		logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
//				+ joinPoint.getSignature().getName());
//		logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
//
//	}
//
//	@AfterReturning(returning = "ret", pointcut = "webLog()")
//	public void doAfterReturning(Object ret) throws Throwable {
//		// 处理完请求，返回内容
//		logger.info("RESPONSE : " + ret);
//		logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
//	}
	 /**
     * 获取登录用户远程主机ip地址
     * 
     * @param request
     * @return
     */
    private String getClientIpAddr(HttpServletRequest request) {
    	
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        
        return ip;
    }

    /**
     * 请求参数拼装
     * 
     * @param paramsArray
     * @return
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
//                Object jsonObj = JSON.toJSON(paramsArray[i]);
//                params += jsonObj.toString() + " ";
            	params += paramsArray[i] + " ";
            }
        }
        return params.trim();
    }

}
