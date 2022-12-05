package fr.leo.patientsmvc.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.RequestContext;

import fr.leo.patientsmvc.entities.Patient;
import fr.leo.patientsmvc.repositories.PatientRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class PatientController {

	// @Autowired <= solution qui marche bien, mais un constructeur
	// avec parametre est plus recommandé !
	private PatientRepository patientRepository;

	// Meme cela ce n'est plus utile car on a utilisé Lombok
//	public PatientController(PatientRepository patientRepository) {
//		this.patientRepository = patientRepository;
//	}

//	@GetMapping(path ="/index")
//	public String patients(Model model, 
//							@RequestParam(name = "page", defaultValue = "0")   int page, 
//							@RequestParam(name = "size", defaultValue = "5" ) int size
//							)
//		{
//		Page<Patient> pagePatients = patientRepository.findAll(PageRequest.of(page, size));
//		model.addAttribute("listPatients", pagePatients.getContent());
//		model.addAttribute("pages", new int[pagePatients.getTotalPages()] );
//		model.addAttribute("cuurentPage", page);
//		return "patients"; // la vue va se nommer 'patients.html' car on utilise Thymeleaf
//	}	

	@GetMapping(path = "/index")
	public String patients(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "keyword", defaultValue = "") String keyword) {
		Page<Patient> pagePatients = patientRepository.findByNomContains(keyword, PageRequest.of(page, size));
		model.addAttribute("listPatients", pagePatients.getContent());
		model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
		model.addAttribute("cuurentPage", page);
		model.addAttribute("keyword", keyword);
		return "patients"; // la vue va se nommer 'patients.html' car on utilise Thymeleaf
	}

	@GetMapping(path="/delete")
	public String delete( Long id,int page, String keyword) {
							patientRepository.deleteById(id);
							return "redirect:/index?page=" + page + "&keyword=" + keyword;
	}
	
	@GetMapping(path="/")
	public String home() {
		return "redirect:/index";
	}
	
	@ResponseBody
	@GetMapping(path="/patients")
	public List<Patient> listPatients(){
		return patientRepository.findAll();
	}
	
	@GetMapping(path="/formPatients")
	public String formPatients(Model model) {		
		// Ci dessous, ainsi le formulaire va aficher des valeurs par défaut !
		model.addAttribute("patient", new Patient());
		return "formPatients";
	}
	
	@PostMapping(path="/save")
	public String save( @Valid Patient patient, BindingResult bindingResult,
			@RequestParam(name = "page", defaultValue = "0")   int page, 
			@RequestParam(name = "keyword", defaultValue = "" ) String keyword,
			Model model) {

		if (bindingResult.hasErrors()) 
		{
			System.out.println("Identifiant : " + patient.getId());
			if (patient.getId() == null) {
				model.addAttribute("page",page);
				model.addAttribute("keyword",keyword);
				return "formPatients";
			}else {				
				model.addAttribute("page",page);
				model.addAttribute("keyword",keyword);
				return "editPatient";
			}
		}	
		
		// Si le patient est connu (id != null) alors il fait update,
		// sinon il fait insert !
		System.out.println("Le patient : " + patient);
		patientRepository.save(patient);
		return "redirect:/index?page=" + page + "&keyword=" + keyword;
		 //return "formPatients"; // on pourrait aller vers une autre page comme quoi le patient a été bien enregistré !
	}
	
	@GetMapping(path="/editPatient")
	public String editPatient( Model model, Long id, int page, String keyword) {
		// Tu recherche le patient, si il n'existe pas retourne 'null' 
		Patient patient =  patientRepository.findById(id).orElse(null);
		if (patient==null) {
			model.addAttribute("error", "Patient introuvable !");
			return "error"; 
			//throw new RuntimeException("Patient introuvable !");			
		}
		model.addAttribute("patient", patient);
		model.addAttribute("page",page);
		model.addAttribute("keyword", keyword);
		return "editPatient"; 
	}
}
