package it.uniroma3.siw.spring.service;
import it.uniroma3.siw.spring.repository.PiattoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Ingrediente;
import it.uniroma3.siw.spring.model.Piatto;





@Service
public class PiattoService {
	
	
	@Autowired
	private PiattoRepository piattoRepository; 

	@Autowired
	private BuffetService buffetService;

	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private IngredienteService ingredienteService;

	@Transactional
	public Piatto inserisci(Piatto piatto) {
		return piattoRepository.save(piatto);
	}
	
	@Transactional
	public List<Piatto> piattoPerNome(String nome) {
		return piattoRepository.findByNome(nome);
	}
	
	@Transactional
	public List<Piatto> tutti() {
		return (List<Piatto>) piattoRepository.findAll();
	}
	
	@Transactional
	public Piatto piattoPerId(Long id) {
		Optional<Piatto> optional = piattoRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Piatto piatto) {
		List<Piatto> piatti = this.piattoRepository.findByNome(piatto.getNome());
		if (piatti.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public List<Piatto> filtraLista(List<Piatto> lista) {
		List<Piatto> piatti=this.tutti();
		for(Piatto p:lista) {	//rimuovo opere che appartengono già alla collezione
			piatti.remove(p);
		}
		return piatti;
	}

	@Transactional
	public void eliminaPiatto(Piatto p) {
		piattoRepository.delete(p);
	}
	
	public CredentialsService getCredentialsService() {
		return credentialsService;
	}

	public void setCredentialsService(CredentialsService credentialsService) {
		this.credentialsService = credentialsService;
	}

	public BuffetService getBuffetService() {
		return buffetService;
	}
	

	public List<Piatto> getPiattiFiltered(Buffet b) {
		List<Piatto> filtrato=new ArrayList<Piatto>();
		for(Piatto s : this.tutti()) {
			if(!b.getPiatti().contains(s))
				filtrato.add(s);
		}
		
		return filtrato;			
	}
	
	
	@Transactional
	public List<Piatto> getPiattiFiltered(){
		List<Piatto> filtrato=new ArrayList<>();
		for(Piatto p: this.tutti()) {
			if(p.getBuffet()==null)
				filtrato.add(p);
		}
		return filtrato;
	}
	
	
	public IngredienteService getIngredienteService() {
		return ingredienteService;
	}


	@Transactional
	public List<Ingrediente> getIngredientePiatto(Piatto p){
		
		
		List<Ingrediente> lista = new ArrayList<>();
		
		for(Ingrediente i: ingredienteService.tutti()) {
			if(i.getPiatti()== p)
				lista.add(i);
		}
		return lista;
	}
}

