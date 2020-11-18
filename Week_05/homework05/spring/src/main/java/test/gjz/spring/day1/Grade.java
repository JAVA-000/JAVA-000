package test.gjz.spring.day1;

import lombok.Data;

import java.util.List;

/**
 * <pre>
 * 班级
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/11/18 10:33
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Data
public class Grade {
    List<Student> students;
}

