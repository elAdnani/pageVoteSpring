package fr.but3.ctp;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.but3.ctp.Repository.QuestionRepository;
import fr.but3.ctp.model.Choix;
import fr.but3.ctp.model.Question;

@Controller
public class ControleurSondage {

	QuestionRepository repo;

	@Autowired
	public ControleurSondage(QuestionRepository repo) {
		this.repo = repo;
	}
	
	@GetMapping({"/", ""})
	public String index(Model model) {
		return "index";
	}
	
	@GetMapping("/private/activer")
	public String activer(Model model, Principal principal) {
		List<Question> questions = repo.findAll();
		
		Question vide = new Question();
		vide.setLibquest("Choisir aucune question");
		vide.setId(-1);
		questions.add(vide);

		model.addAttribute("activation", "");
		model.addAttribute("questions", questions);
		model.addAttribute("username", principal.getName());
		return "private/activer";
	}

	@PostMapping("/private/activer")
	public String postActiver(Model model, Principal principal,
			@RequestParam(name = "question", required = true) int question) {
		List<Question> questions = repo.findAll();

		for (Question uneQuestion : questions) {
			if (uneQuestion.getId() == question)
				uneQuestion.setActive(true);
			else
				uneQuestion.setActive(false);
		}
		repo.saveAll(questions);

		String jsp = activer(model, principal);

		model.addAttribute("activation", "la question vient d'être validé !");
		return jsp;
	}

	@GetMapping("/public/voter")
	public String voter(Model model, Principal principal) {
		Question question = questionChoisie();
		System.out.println(question);

		model.addAttribute("question", question != null ? question.getLibquest() : null);
		model.addAttribute("choix", question != null ? question.getChoix() : null);
		model.addAttribute("activation", null);
		model.addAttribute("username", principal.getName());

		return "public/voter";
	}

	private Question questionChoisie() {
		List<Question> questions = repo.findAll();
		for (Question question : questions)
			if (question.isActive())
				return question;
		return null;
	}

	@PostMapping("/public/voter")
	public String postVoter(Model model, Principal principal,
			@RequestParam(name = "choix", required = true) int choix) {
		Question question = questionChoisie();
		Choix choixObject = question.addChoice(choix);
		repo.save(question);

		String jsp = voter(model, principal);
		model.addAttribute("activation", "Votre choix, "+choixObject.getLibChoix()+", a bien été pris en compte !");
		return jsp;
	}

	@RequestMapping("/private/voir")
	public String voir(Model model, Principal principal) {
		Question question = questionChoisie();

		model.addAttribute("question", question != null ? question.getLibquest() : null);
		model.addAttribute("lesChoix", question != null ? question.getChoix() : null);
		model.addAttribute("username", principal.getName());
		return "private/voir";
	}

}
