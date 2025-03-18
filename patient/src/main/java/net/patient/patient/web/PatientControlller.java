package net.patient.patient.web;

import jakarta.validation.Valid;
import net.patient.patient.entite.Patient;
import net.patient.patient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PatientControlller {
    @Autowired
    PatientRepository patientRepository;
    @GetMapping("/user/index")
    public String index(Model model , @RequestParam(name= "page",defaultValue = "0") int page ,
                        @RequestParam(name= "size",defaultValue = "5") int size,
                        @RequestParam(name= "keyword",defaultValue = "") String keyword){
        Page<Patient> pagePatients = patientRepository.findByNomContainsIgnoreCase(keyword,PageRequest.of(page , size));
        model.addAttribute("listPatients" , pagePatients.getContent());
        model.addAttribute("pages" , new int [pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("kayword",keyword);
        return "patients";
    }
    @GetMapping("/admin/deletePatient")
    public String deletePatient(@RequestParam(name = "id") Long id,
                                @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
                                @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        // Vérifier si le patient existe avant de le supprimer
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient non trouvé avec l'ID : " + id);
        }

        // Suppression du patient
        patientRepository.deleteById(id);

        // Redirection vers la liste des patients avec la pagination et le mot-clé conservé
        return "redirect:/user/index?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/admin/formPatients")
    public String formPatients(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatients";
    }
    @PostMapping(path="/admin/save")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "") String Keyword){
        if(bindingResult.hasErrors()) return "formPatients";
        //enregistrer le patient dans la base de donne
     patientRepository.save(patient);
     return "formPatients";
    }

    @GetMapping("/admin/editPatient")
    public String editPatients(Model model , Long id,String Keyword, int page){
        Patient patient = patientRepository.findById(id).orElse(null);
        if(patient==null) throw new RuntimeException("Patient introuvable");
        model.addAttribute("patient",patient);
        model.addAttribute("page",page);
        model.addAttribute("kayword",Keyword);
        return "editPatient";
    }
    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }


}
