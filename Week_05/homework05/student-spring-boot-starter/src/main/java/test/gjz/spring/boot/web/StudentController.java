package test.gjz.spring.boot.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.gjz.spring.boot.Student;

import javax.annotation.Resource;

/**
 * <pre>
 * 学生Controller
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/11/18 23:26
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@RestController
@RequestMapping(value = "student")
public class StudentController{

    @Resource
    Student student;

    @RequestMapping(value = "print")
    public String printStudentInfo(){
        return student.toString();
    }
}

