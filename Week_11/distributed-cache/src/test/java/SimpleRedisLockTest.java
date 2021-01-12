import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import com.geektime.redis.SimpleRedisLock;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
@Slf4j
public class SimpleRedisLockTest {
    @Autowired
    private SimpleRedisLock redisLock;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void simpleRedisLockTest() {
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        System.out.println("尝试加锁,结果:" + redisLock.tryLock("user1", 5));
        log.info("key:val ==> {}:{}", "user1", opsForValue.get("user1"));
        System.out.println(opsForValue.get("user1"));
        System.out.println("尝试加锁,结果:" + redisLock.tryLock("user1", 5));
        System.out.println("尝试解锁,结果:");
        redisLock.releaseLock("user1");
        System.out.println("尝试解锁,结果:");
        redisLock.releaseLock("user1");
    }

}
