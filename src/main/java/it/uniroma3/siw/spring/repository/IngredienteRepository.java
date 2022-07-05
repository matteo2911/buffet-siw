package it.uniroma3.siw.spring.repository;
import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.spring.model.Ingrediente;
import java.util.List;

public interface IngredienteRepository extends CrudRepository<Ingrediente,Long>{
	
	public List<Ingrediente> findByNome(String nome);
	public List<Ingrediente> findByNomeAndDescrizione(String nome, String descrizione);
	public List<Ingrediente> findByNomeOrDescrizione(String nome, String descrizione);
}


