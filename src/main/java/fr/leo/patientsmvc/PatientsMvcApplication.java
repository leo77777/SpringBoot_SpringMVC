package fr.leo.patientsmvc;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.leo.patientsmvc.entities.Patient;
import fr.leo.patientsmvc.repositories.PatientRepository;

@SpringBootApplication
public class PatientsMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientsMvcApplication.class, args);
	}
	
	/**
	 * @Bean : cette fonction va se lancer au démarrage !
	 *         La notation @Bean signifie : "au démarrage lance moi cette méthode" !
	 */
	//@Bean
	CommandLineRunner commandLineRunner(PatientRepository patientRepository) {
		return args->{
			patientRepository.save(new Patient(null, "rere", new Date(), false, 100));
			patientRepository.save(new Patient(null, "lala", new Date(), true, 110));
			patientRepository.save(new Patient(null, "vyvy", new Date(), false, 140));
			patientRepository.save(new Patient(null, "bibi", new Date(), true, 230));
			
			patientRepository.findAll().forEach(p->{
				System.out.print(p.getId() +"\t");
				System.out.print(p.getNom() +"\t");
				System.out.println(p.getDateNaissance());
			});
		};
	}
}
