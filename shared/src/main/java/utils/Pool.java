package utils;

import com.artemis.utils.Bag;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.ReflectionException;

public class Pool<T extends Poolable> {
	private final Bag<T> cache;
	private Class<T> type;

	public Pool(Class<T> type) {
		this.type = type;
		cache = new Bag<T>(type);
	}

	@SuppressWarnings("unchecked")
	public <T extends Poolable> T obtain() {
		try {
			return (T) ((cache.size() > 0)
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
