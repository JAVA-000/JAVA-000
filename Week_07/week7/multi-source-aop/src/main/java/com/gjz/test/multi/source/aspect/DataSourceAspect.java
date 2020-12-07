package com.gjz.test.multi.source.aspect;

import com.gjz.test.multi.source.holder.DynamicDataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 数据源切换拦截
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/7 15:11
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Aspect
@Component
@Slf4j
public class DataSourceAspect {


    /**
     * 只读：
     * 不是Master注解的对象或方法  && select开头的方法  ||  get开头的方法
     */
    @Pointcut("!@annotation(com.gjz.test.multi.source.annotation.WriterData) " +
            "&& (execution(* com.gjz.test.multi.source.service..*.select*(..)) " +
            "|| execution(* com.gjz.test.multi.source.service..*.list*(..)) " +
            "|| execution(* com.gjz.test.multi.source.service..*.get*(..)))")
    public void readPointcut() {

    }

    /**
     * 写：
     * Master注解的对象或方法 || insert开头的方法  ||  add开头的方法 || update开头的方法
     * || edlt开头的方法 || delete开头的方法 || remove开头的方法
     */
    @Pointcut("@annotation(com.gjz.test.multi.source.annotation.WriterData) " +
            "|| execution(* com.gjz.test.multi.source.service..*.insert*(..)) " +
            "|| execution(* com.gjz.test.multi.source.service..*.add*(..)) " +
            "|| execution(* com.gjz.test.multi.source.service..*.update*(..)) " +
            "|| execution(* com.gjz.test.multi.source.service..*.delete*(..)) " +
            "|| execution(* com.gjz.test.multi.source..*.remove*(..))")
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read() {
        log.info("{}, read, 读从库方法========》" , "【数据源切面】");
        DynamicDataSourceHolder.markSalve();
    }

    @Before("writePointcut()")
    public void write() {
        log.info("{}, write, 写主库方法========》" , "【数据源切面】");
        DynamicDataSourceHolder.markMaster();
    }

}

