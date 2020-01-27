package core.repository;

public interface GenericRepository<E> {

    void save(E entity);
    E getSingle(String columnName, String value);
}
