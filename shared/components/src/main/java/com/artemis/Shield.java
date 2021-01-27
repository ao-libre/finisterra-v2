package com.artemis;

import annotations.Public;
import com.artemis.annotations.DelayedComponentRemoval;
import com.artemis.annotations.PooledWeaver;

@PooledWeaver
@DelayedComponentRemoval
@Public
public class Shield extends Component {

    private int index;

    public Shield() {
    }

    public Shield(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
