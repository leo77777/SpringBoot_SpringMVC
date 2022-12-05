package fr.leo.patientsmvc.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Entiy : pour que cette classe soit une entité Jpa 
 * @Data : pour les getters et setter
 * La notation @Data, génère un constructeur sans paramètres,
 *  mais celui ci est protégé, donc il faut quand meme ajouté la 
 *  notation "NoArgConstructor" parce que une entité Jpa exige un
 *  constructeur sans paramètre qui est public !
 * @Temporal(TemporalType.DATE) : pour avoir la date au format Date et
 *  non pas au format TimeStamP ! 
 */
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Patient {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	@Size(min = 4, max = 40)
	private String nom;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateNaissance;	
	private boolean malade;
	@DecimalMin("100")
	private int score;

}
