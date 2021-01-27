package com.artemis;

import annotations.Public;
import com.artemis.annotations.DelayedComponentRemoval;
import com.artemis.annotations.PooledWeaver;

@PooledWeaver
@DelayedComponentRemoval
@Public
public class Weapon extends Component {

    private int index;

    public Weapon() {
    }

    public Weapon(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    void setIndex(int index) {
        this.index = index;
    }
}
