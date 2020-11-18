import com.github.qingyi.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootApplication
@Component
public class StudentStarterTest {

    @Autowired
    private Student student;

    @Test
    public void test() {
        System.out.println(student.toString());
    }

}