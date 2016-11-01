package nc.ukma.thor.spms.repository;

public abstract class AbstractRepository<T> {
	
	public abstract void add(T t);
	public abstract void update(T t);
	public abstract void delete(T t);
	public abstract T getById(long id);

}
