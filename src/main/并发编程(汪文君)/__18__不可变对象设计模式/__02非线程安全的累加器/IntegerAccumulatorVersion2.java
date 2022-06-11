package __18__不可变对象设计模式.__02非线程安全的累加器;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author wxg
 * @date 2022/1/10-11:57
 * @quotes 小不忍则乱大谋
 */
public class IntegerAccumulatorVersion2 {
    private int init;

    /**
     * 构造时传入初始值
     * @param init 等待传入
     */
    public IntegerAccumulatorVersion2(int init) {
        this.init = init;
    }

    public int add(int value) {
        init += value;
        return init;
    }

    /**
     * 返回当前的初始值
     * @return 初始值
     */
    public int getValue(){
        return init;
    }


    public static void main(String[] args) {
        // 定义累加器, 并且将设置初始值为0
        final IntegerAccumulatorVersion2 integerAccumulatorVersion2 = new IntegerAccumulatorVersion2(0);
        // 定义三个线程, 并且分别启动
        IntStream.range(0,3).forEach(i -> new Thread(() -> {
            int inc = 0;
            while (true) {
                int oldValue;
                int result;
                synchronized(IntegerAccumulatorVersion2.class) {
                    // 首先获得old value
                    oldValue = integerAccumulatorVersion2.getValue();
                    //  然后调用add方法计算
                    result = integerAccumulatorVersion2.add(inc);
                    System.out.println(oldValue + " + " + inc + " = " + result);
                    // 经过验证，如果不合理，则输出错误信息
                    if (inc + oldValue != result) {
                        System.err.println("ERROR: " + oldValue + " + " + inc + " = " + result);
                    }
                    inc++;
                    //  模拟延迟
                    slowly();
                }
            }
        }
        ).start());
    }

    private static void slowly() {
        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
