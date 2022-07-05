package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.repository.BuffetRepository;
@Service
public class BuffetService {


	@Autowired
	private BuffetRepository buffetRepository; 

	@Autowired
	private ChefService chefService;

	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private PiattoService piattoService;

	public ChefService getChefService() {
		return chefService;
	}

	@Transactional
	public Buffet inserisci(Buffet buffet) {
		return buffetRepository.save(buffet);
	}

	@Transactional
	public List<Buffet> buffetPerNome(String nome) {
		return buffetRepository.findByNome(nome);
	}

	@Transactional
	public List<Buffet> tutti() {
		return (List<Buffet>) buffetRepository.findAll();
	}

	@Transactional
	public Buffet buffetPerId(Long id) {
		Optional<Buffet> optional = buffetRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Buffet buffet) {
		List<Buffet> buffets = this.buffetRepository.findByNome(buffet.getNome());
		if (buffets.size() > 0)
			return true;
		else 
			return false;
	}


	@Transactional
	public List<Buffet> filtraLista(List<Buffet> lista) {
		List<Buffet> buffets=this.tutti();
		for(Buffet b:lista) {	//rimuovo opere che appartengono già alla collezione
			buffets.remove(b);
		}
		return buffets;
	}

	@Transactional
	public void eliminaBuffet(Buffet b) {
		buffetRepository.delete(b);
	}

	public CredentialsService getCredentialsService() {
		return credentialsService;
	}

	public void setCredentialsService(CredentialsService credentialsService) {
		this.credentialsService = credentialsService;
	}

	public void setChefService(ChefService chefService) {
		this.chefService = chefService;
	}
		
	public void setPiattoService(PiattoService piattoService) {
		this.piattoService = piattoService;
	}

	public PiattoService getPiattoService() {
		return piattoService;
	}
	
	@Transactional
	public List<Piatto> getPiattoBuffet(Buffet b){
		
		
		List<Piatto> lista = new ArrayList<>();
		
		for(Piatto p: piattoService.tutti()) {
			if(p.getBuffet()== b)
				lista.add(p);
		}
		return lista;
	}
}


	


