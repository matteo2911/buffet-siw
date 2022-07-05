package it.uniroma3.siw.spring.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name="findAllPiatto" , query="SELECT p FROM Piatto p")
public class Piatto {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(nullable=false)
	private String nome;
	
	@Column(nullable=false)
	private String descrizione;
	
	@ManyToMany(mappedBy="piatti")
	private List<Ingrediente> ingredienti;
	
	@ManyToOne
	private Buffet buffet;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public Buffet getBuffet() {
		return buffet;
	}

	public void setBuffet(Buffet buffet) {
		this.buffet = buffet;
	}
	
	public List<Ingrediente> getIngredienti() {
		return ingredienti;
	}

	public void setIngredienti(List<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
	}
	
	public void addIngrediente(Ingrediente i) {
		this.ingredienti.add(i);
	}
	
	public void rimuoviIngrediente(Ingrediente i) {
		this.ingredienti.remove(i);
	}


}
