package test.gjz.thread;

/**
 * <pre>
 * 计算
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/11/8 17:58
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class Calculator {

    /**
     * 计数斐波纳契数
     * @param number
     * @return
     */
    public static int fibo(int number) {
        if ( number < 2) {
            return 1;
        }

        int first = 0;
        int second = 1;
        int result = 1;
        for (int i = 2; i <= number; i++) {
            result = first + second;
            first = second;
            second = result;
        }

        return result;
    }

}

