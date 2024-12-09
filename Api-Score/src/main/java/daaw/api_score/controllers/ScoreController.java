package daaw.api_score.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import daaw.api_score.persistence.model.Score;
import daaw.api_score.persistence.repo.ScoreRepository;

@CrossOrigin(origins = "http://localhost:5173") // Cambia esto por el origen de tu frontend
@RestController
@RequestMapping("/api/score")
public class ScoreController {

    @Autowired
    private ScoreRepository scoreRepository;

    // Agregar un nuevo puntaje
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Score addScore(@RequestBody Score newScore) {
        return scoreRepository.save(newScore);
    }

    @GetMapping("/getAllScores")
    public List<Score> getAllScores() {
        List<Score> scores = (List<Score>) scoreRepository.findAll();
        scores.sort((s1, s2) -> Integer.compare(s2.getScore(), s1.getScore())); // Orden descendente
        return scores;
    }

    @GetMapping("/getTopScores")
    public List<Score> getTopScores() {
        List<Score> scores = (List<Score>) scoreRepository.findAll();
        scores.sort((s1, s2) -> Integer.compare(s2.getScore(), s1.getScore())); // Orden descendente
        return scores.stream().limit(10).collect(Collectors.toList()); // Solo los 10 primeros
    }

    // Obtener puntajes por nombre
    @GetMapping("/getScore")
    public List<Score> getScore(@RequestParam String name) {
        return scoreRepository.findByName(name);
    }
}
