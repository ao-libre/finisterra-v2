package utils;

import com.artemis.utils.Bag;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.ReflectionException;

public class Pool<T extends Poolable> {
    private final Bag<T> cache;
    private final Class<T> type;

    public Pool(Class<T> type) {
        this(type, 128);
    }

    public Pool(Class<T> type, int size) {
        this.type = type;
        cache = new Bag<>(type, size);
    }

    public T obtain() {
        try {
            return ((cache.size() > 0)
                    ? cache.removeLast()
                    : ClassReflection.newInstance(type));
        } catch (ReflectionException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void free(T component) {
        component.reset();
        cache.add(component);
    }

}
