package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Chef;

public interface ChefRepository extends CrudRepository<Chef, Long> {

	public List<Chef> findByNome(String nome);

	public List<Chef> findByNomeAndCognome(String nome, String cognome);
	


	public List<Chef> findByNomeOrCognome(String nome, String cognome);
}