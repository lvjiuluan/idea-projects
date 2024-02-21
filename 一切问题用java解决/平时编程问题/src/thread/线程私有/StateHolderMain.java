package thread.线程私有;

public class StateHolderMain {
}

class StateHolder {

    private final String state;

    StateHolder(String state) {
        this.state = state;
    }

    // 标准的构造函数和 getter

    public String getState() {
        return state;
    }
}

class ThreadState {
    public static final ThreadLocal<StateHolder> statePerThread = new ThreadLocal<StateHolder>() {
        @Override
        protected StateHolder initialValue() {
            return new StateHolder("active");

        }
    };

    public static StateHolder getState() {
        return statePerThread.get();
    }
}