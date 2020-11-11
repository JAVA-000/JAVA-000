package src.homework1;

import java.util.concurrent.Callable;

public class CallAbleSum implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        return sum();
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}
