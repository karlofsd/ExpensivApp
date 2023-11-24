package core.interfaces;

import java.util.List;
import java.util.UUID;

public interface CRUDDAO<T> {
    void create(T object);
    void update(T object);
    void delete(T object);
    T get(UUID id);
    List<T> getAll();
}
