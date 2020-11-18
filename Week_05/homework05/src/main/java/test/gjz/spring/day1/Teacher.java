package test.gjz.spring.day1;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 教师
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/11/18 10:11
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Component
@Data
public class Teacher {
    String name;

    @Autowired
    School school;
}

