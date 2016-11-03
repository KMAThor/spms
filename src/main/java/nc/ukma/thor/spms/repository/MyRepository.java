package nc.ukma.thor.spms.repository;

public interface MyRepository<T> {
	
	public void add(T entity);
	public void update(T entity);
	public void delete(T entity);
	
	public T getById(long entity);
}
