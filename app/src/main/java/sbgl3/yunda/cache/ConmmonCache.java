package sbgl3.yunda.cache;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

public interface ConmmonCache {
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<String>> getUsers(Observable<String> oUsers);
}
