package net.patient.patient;

import net.patient.patient.entite.Patient;
import net.patient.patient.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class PatientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientApplication.class, args);
	}


	@Bean
	public CommandLineRunner start(PatientRepository patientRepository){
		return args -> {
			//sauvegarder les patient dans la base de donne
			//premier solution pour creer des objet
			for (int i = 1; i <= 15; i++) { // Ajoute 30 patients
				Patient patient1 = new Patient();// @NoArgsCostructeur
				patient1.setNom("Bah");
				patient1.setPrenom("Mody");
				patient1.setScore(100);
				patient1.setMalade(false);
				patient1.setDateNaissance(new Date());


				//dexieme solution pour creer des objet
				Patient patient2 = new Patient(null, "Sow", "Oumar", new Date(), 1200, false);//AllArgsConstructeur

				//  troisieme solution pour creer des objet
				Patient patient3 = Patient.builder()//Builder
						.nom("Mohamde")
						.prenom("Sidi")
						.dateNaissance(new Date())
						.score(1300)
						.malade(true)
						.build();
				patientRepository.save(patient1);
				patientRepository.save(patient2);
				patientRepository.save(patient3);

			}

			//on va tester pour voir tout es enregistrer avec findAll return une list
			List<Patient> patients = patientRepository.findAll();
			patients.forEach(p->{
				System.out.println(p.toString());
			});
        };

}

}
