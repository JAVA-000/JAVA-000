package test.gjz.spring.day1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <pre>
 * 写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）, 提交到 Github。
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/11/18 9:37
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class SpringBeanIocHomework {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // 根据bean id 查找容器中的对象
        School school1 = (School) context.getBean("school1");
        System.out.println("school1:" + school1);

        // 根据 bean name 查找容器的对象
        School school2 = (School) context.getBean("school2");
        System.out.println("school2:" + school2);

        // 通过类型查找bean, bean必须是唯一的，或指定默认的
        School school3 = context.getBean(School.class);
        System.out.println("school3:" + school3);

        //通过bean id查找容器中的对象，其中对象中又注入了School类的对象
        Student student1 = (Student) context.getBean("student1");
        System.out.println("student1:" + student1);

        // 通过bean id 查找容器中的对象，其中对象中又注入了List<Student>
        Grade grade = context.getBean(Grade.class);
        System.out.println("grade:" + grade);

        // 查找通过注解声明的对象
        Teacher teacher = context.getBean(Teacher.class);
        System.out.println("teacher:" + teacher);

    }

}

