package zlei.bysj.cslk;

/**
 * Created by zlei1 on 2018/3/31.
 */

public class MessageEvent {
    private int message;

    public MessageEvent(int message) {
        this.message = message;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }
}
