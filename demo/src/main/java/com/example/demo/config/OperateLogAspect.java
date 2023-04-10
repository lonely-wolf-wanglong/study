package com.example.demo.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.example.demo.annotation.SysLog;
import com.example.demo.entity.R;
import com.example.demo.entity.SysOperateLog;
import com.example.demo.service.ISysOperateLogService;
import com.example.demo.utils.HttpUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Aspect
@Component
public class OperateLogAspect {

    @Autowired
    private ISysOperateLogService iSysOperateLogService;

    /**
     * 设置操作日志切入点   在注解的位置切入代码
     */
    @Pointcut("@annotation(com.example.demo.annotation.SysLog)")
    public void operLogPoinCut() {
    }

    /**
     * 记录操作日志
     * @param joinPoint 方法的执行点
     * @param result  方法返回值
     * @throws Throwable
     */
    @AfterReturning(returning  = "result", value = "operLogPoinCut()")
    public void saveOperLog(JoinPoint joinPoint, Object result) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        try {

            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();

            SysLog sysLog = method.getAnnotation(SysLog.class);
            if (sysLog != null) {
                SysOperateLog sysOperateLog = new SysOperateLog();
                R map = (R) result;
                sysOperateLog.setModuleName(sysLog.operModule());
                sysOperateLog.setMenuName(sysLog.operMenu());
                sysOperateLog.setOperName(sysLog.operName());
                sysOperateLog.setUserId("111");
                sysOperateLog.setOperTime(LocalDateTime.now());
                sysOperateLog.setOperIp(HttpUtil.getIpAddr(request));
                sysOperateLog.setOperMethod(method.getName());

                String className = joinPoint.getTarget().getClass().getName();
                String methodName = joinPoint.getSignature().getName();
                sysOperateLog.setOperMethod(className + "." + methodName + "()");
                sysOperateLog.setRequestMethod(getRequestAttributes().getRequest().getMethod());
                sysOperateLog.setOperStatus(String.valueOf(map.getCode()));
                sysOperateLog.setOperResult(map.getMsg());
                setRequestValue(joinPoint, sysOperateLog);
                iSysOperateLogService.save(sysOperateLog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 获取请求的参数，放到log中
     * @param sysOperateLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint,SysOperateLog sysOperateLog) throws Exception {
        String requsetMethod = sysOperateLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requsetMethod) || HttpMethod.POST.name().equals(requsetMethod)) {
            String parsams = argsArrayToString(joinPoint.getArgs());
            sysOperateLog.setOperParam(StrUtil.sub(parsams,0,2000));
        } else {
            Map<?,?> paramsMap = (Map<?,?>) getRequestAttributes().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, 0);
            sysOperateLog.setOperParam(StrUtil.sub(paramsMap.toString(),0,2000));
        }
    }
    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuffer params = new StringBuffer();
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object object : paramsArray) {
                // 不为空 并且是不需要过滤的 对象
                if (object != null&& !isFilterObject(object)) {
                    Object jsonObj = JSONUtil.toJsonStr(object);
                    params.append(jsonObj.toString());
                }
            }
        }
        return params.toString();
    }
    /**
     * 判断是否需要过滤的对象。
     * @param object 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object object) {
        Class<?> clazz = object.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) object;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) object;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return object instanceof MultipartFile || object instanceof HttpServletRequest
                || object instanceof HttpServletResponse || object instanceof BindingResult;
    }
}
