package com.artemis;

import annotations.Public;
import com.artemis.annotations.PooledWeaver;

@PooledWeaver
@Public
public class Armor extends Component {
    private int index;

    public Armor() {
    }

    public Armor(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
