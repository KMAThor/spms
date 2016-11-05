package nc.ukma.thor.spms.service;

import nc.ukma.thor.spms.repository.MyRepository;

public abstract class AbstractService<T> implements Service<T>{

	private MyRepository<T> repository;
	
	public AbstractService(MyRepository<T> repository){
		this.repository = repository;
	}
	
	public void create(T t){
		repository.add(t);
	}
	
	public void update(T t){
		repository.update(t);
	}

	public void delete(T t){
		repository.delete(t);
	}
	
	public T getById(long id){
		return repository.getById(id);
	}
}
