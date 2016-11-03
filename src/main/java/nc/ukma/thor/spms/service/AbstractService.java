package nc.ukma.thor.spms.service;

import org.springframework.beans.factory.annotation.Autowired;

import nc.ukma.thor.spms.repository.MyRepository;

public abstract class AbstractService<T> {
	
	@Autowired
	private MyRepository<T> repository;
	
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
