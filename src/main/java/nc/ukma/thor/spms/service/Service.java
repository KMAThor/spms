package nc.ukma.thor.spms.service;

public interface Service<T> {
	
	public void create(T entity);
	public void update(T entity);
	public void delete(T entity);
	
	public T getById(long id);

}
