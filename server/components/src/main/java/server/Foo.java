package server;

import com.artemis.Component;

public class Foo extends Component {
    private int value;

    public Foo() {}

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
