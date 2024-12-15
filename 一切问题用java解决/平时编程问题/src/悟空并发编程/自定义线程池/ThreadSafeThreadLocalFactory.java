package 悟空并发编程.自定义线程池;

import java.util.Objects;

public class ThreadSafeThreadLocalFactory {

    public static interface Supply<T>{
        T get();
    }
    public static class ThreadLocalSupply<T> extends ThreadLocal<T>{
        private Supply<T> supply;

        public ThreadLocalSupply(Supply supply) {
            this.supply = Objects.requireNonNull(supply);
        }

        @Override
        protected T initialValue() {
            return supply.get();
        }
    }
}
