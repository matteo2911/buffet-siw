package it.uniroma3.siw.spring.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Ingrediente;
import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.service.BuffetService;
import it.uniroma3.siw.spring.service.IngredienteService;
import it.uniroma3.siw.spring.service.PiattoService;

@Controller
public class PiattoController {
	
	@Autowired
	private PiattoService piattoService;
	
    @Autowired
    private PiattoValidator piattoValidator;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    

    @RequestMapping(value="/admin/addPiatto", method = RequestMethod.GET)
    public String addBuffet(Model model) {
    	
    	model.addAttribute("piatto", new Piatto());
    	model.addAttribute("buffet", piattoService.getBuffetService().tutti());
        return "piattoForm.html";
    }

    @RequestMapping(value = "/piatto/{id}", method = RequestMethod.GET)
    public String getPiatto(@PathVariable("id") Long id, Model model) {
    	Piatto p =this.piattoService.piattoPerId(id);
    	model.addAttribute("piatto", p);
    	model.addAttribute("ingrediente", new Ingrediente());
    	model.addAttribute("ingredienti",piattoService.getIngredienteService().getIngredientiFiltered(p));
    	model.addAttribute("ingredientiPiatto",p.getIngredienti());
    	
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.piattoService.getCredentialsService().getCredentials(userDetails.getUsername());
    	
    	model.addAttribute("credentials", credentials);

    	return "piatto.html";
    }
    
    
    @RequestMapping(value = "/piatto", method = RequestMethod.GET)
    public String getBuffets(Model model) {
    		model.addAttribute("piatti", this.piattoService.tutti());
    		return "piatti.html";
    }
    
    @RequestMapping(value = "/admin/piatto", method = RequestMethod.POST)
    public String newPiatto(@ModelAttribute("piatti") Piatto piatto, 
    									Model model, BindingResult bindingResult) {
    	this.piattoValidator.validate(piatto, bindingResult);
        if (!bindingResult.hasErrors()) {
        	
        	this.piattoService.inserisci(piatto);
            model.addAttribute("piatti", this.piattoService.tutti());
            return "piatti.html";
        }
        model.addAttribute("piatto", new Piatto());
        model.addAttribute("buffets", piattoService.getBuffetService().tutti());
        return "piattoForm.html";
    }
    
    @RequestMapping(value = "/admin/addIngredienteAPiatto/{id}", method = RequestMethod.POST)
    public String aggiungiIngrediente(@RequestParam("ingrediente") Long idIngrediente, 
    									Model model, @PathVariable("id") Long idPiatto) {
    	
    	Ingrediente i=piattoService.getIngredienteService().ingredientePerId(idIngrediente);
    	Piatto p  = this.piattoService.piattoPerId(idPiatto);
    	if(!i.getPiatti().contains(p)) {
    			i.addPiatto(p);
    			piattoService.getIngredienteService().inserisci(i);
    	}
    	model.addAttribute("piatto", this.piattoService.piattoPerId(idPiatto));
    	model.addAttribute("ingredienti",piattoService.getIngredienteService().getIngredientiFiltered(p));
    	model.addAttribute("ingredientiPiatto",p.getIngredienti());
    	
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.piattoService.getCredentialsService().getCredentials(userDetails.getUsername());
    	
    	model.addAttribute("credentials", credentials);
    	return "piatto.html";
    }
    
    @RequestMapping(value = "/admin/rimuoviIngrediente/{id}", method = RequestMethod.POST)
    public String rimuoviIngrediente(@RequestParam("ingrediente") Long idIngrediente, 
    									Model model, @PathVariable("id") Long idPiatto) {
    	Piatto p = this.piattoService.piattoPerId(idPiatto);
    	Ingrediente i=piattoService.getIngredienteService().ingredientePerId(idIngrediente);
    	if(i.getPiatti().contains(p)) {
    		
    		i.removePiatto(p);
    		piattoService.getIngredienteService().inserisci(i);
    	}
    	model.addAttribute("piatto", p);
    	model.addAttribute("ingredienti",piattoService.getIngredienteService().getIngredientiFiltered(p));
    	model.addAttribute("ingredientiPiatto",p.getIngredienti());
    	
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.piattoService.getCredentialsService().getCredentials(userDetails.getUsername());
    	
    	model.addAttribute("credentials", credentials);
    	return "piatto.html";
    }
     
    @RequestMapping(value = "/admin/eliminaPiatto/{id}", method = RequestMethod.POST)
    public String eliminaPiatto(Model model, @PathVariable("id") Long idPiatto) {
    		
    		Piatto p= piattoService.piattoPerId(idPiatto);
    		
    		List<Ingrediente> ingredientiP = new ArrayList<>( p.getIngredienti());
    		
    		for(Ingrediente i: ingredientiP) {
    			
    			p.rimuoviIngrediente(i);
    			i.removePiatto(p);		
    		}
    		
    		piattoService.eliminaPiatto(p);
    		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	Credentials credentials = this.piattoService.getCredentialsService().getCredentials(userDetails.getUsername());
        	
        	model.addAttribute("credentials", credentials);
    		return "home.html";
    }
}
