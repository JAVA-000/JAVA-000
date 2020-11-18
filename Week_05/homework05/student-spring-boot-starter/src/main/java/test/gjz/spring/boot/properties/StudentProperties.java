package test.gjz.spring.boot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <pre>
 *
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/11/18 22:48
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@ConfigurationProperties(prefix = "school.student")
@Data
public class StudentProperties {
    int id;
    String name;
}

