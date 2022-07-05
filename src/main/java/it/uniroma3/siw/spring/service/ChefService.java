package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.repository.ChefRepository;

@Service
public class ChefService {
	
	@Autowired
	private ChefRepository chefRepository; 
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private BuffetService buffetService;
	
	public ChefRepository getArtistaRepository() {
		return chefRepository;
	}

	public void setArtistaRepository(ChefRepository artistaRepository) {
		this.chefRepository = chefRepository;
	}

	public CredentialsService getCredentialsService() {
		return credentialsService;
	}

	public void setCredentialsService(CredentialsService credentialsService) {
		this.credentialsService = credentialsService;
	}

	@Transactional
	public Chef inserisci(Chef chef) {
		return chefRepository.save(chef);
	}
	
	@Transactional
	public List<Chef> chefsPerNomeAndCognome(String nome, String cognome) {
		return chefRepository.findByNomeAndCognome(nome, cognome);
	}
	@Transactional
	public Chef chefPerNomeAndCognome(String nome, String cognome) {
		return chefRepository.findByNomeAndCognome(nome, cognome).get(0);
	}
	@Transactional
	public void eliminaChef(Chef c) {
		chefRepository.delete(c);
     }
	
	@Transactional
	public List<Chef> tutti() {
		return (List<Chef>) chefRepository.findAll();
	}

	@Transactional
	public Chef chefPerId(Long id) {
		Optional<Chef> optional = chefRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Chef chef) {
		List<Chef> chefs = this.chefRepository.findByNomeAndCognome(chef.getNome(), chef.getCognome());
		if (chefs.size() > 0)
			return true;
		else 
			return false;
	}
	
	
	public BuffetService getBuffetService() {
		return buffetService;
	}
	
	
	@Transactional
	public List<Buffet> getBuffetChef(Chef c){
		
		
		List<Buffet> lista = new ArrayList<>();
		
		for(Buffet b: buffetService.tutti()) {
			if(b.getChef()== c)
				lista.add(b);
		}
		return lista;
	}
	
}
