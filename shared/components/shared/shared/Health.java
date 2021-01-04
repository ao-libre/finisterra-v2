package shared;

import com.artemis.Component;

public class Health extends Component {
    private int value;

    public Health() {}

    public Health(int value) {
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
