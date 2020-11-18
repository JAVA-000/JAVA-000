package test.gjz.spring.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.gjz.spring.boot.properties.StudentProperties;

/**
 * <pre>
 * Starter核心类
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/11/18 22:43
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Configuration
@ConditionalOnClass({Klass.class, Student.class})
@ConditionalOnProperty(prefix = "school.student",value = "enable",matchIfMissing = true)
@EnableConfigurationProperties(StudentProperties.class)
public class StudentAutoConfiguration {

    @Autowired
    StudentProperties studentProperties;

    @Bean(value = "student")
    @ConditionalOnMissingBean
    public Student getStudent(){
        return new Student(studentProperties.getId(), studentProperties.getName());
    }
}

