package __08__线程池.__26线程池实现.接口和基本类;

/**
 * @author wxg
 * @date 2022/1/4-18:50
 * @quotes 小不忍则乱大谋
 */
public class TaskDenyException extends RuntimeException{
    public TaskDenyException(String message){
        super(message);
    }
}
