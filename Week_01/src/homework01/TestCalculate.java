package src.homework01;

/**
 * @author Jiang Jining
 * @date 2020/10/18 23:09
 */
public class TestCalculate {
    public static void main(String[] args) {
        int i = 1;
        int j = 2;
        int k = 3;
        int sum = 0;
        System.out.println("i + j + k = " + (i + j + k));
        System.out.println("i * j * k = " + (i * j * k));
        for (int l = 0; l < 100; l++) {
            sum += l;
        }
        System.out.println("1 + ... + 100 = " + sum);
    }
}
