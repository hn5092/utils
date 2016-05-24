package base.google.eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * Created by xym on 2016/5/15.
 */
public class TestEventBus {
    @Subscribe
    public void reciveEvent(String a){
        System.out.println("recive info"+a);
    }

    @Subscribe
    public void reciveEvent1(Integer a){
        System.out.println("recive info"+a);
    }

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new TestEventBus());
        eventBus.post(11);
        eventBus.post("23123");

    }
}

