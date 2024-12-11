package daaw.api_score.controllers;

import java.util.List;
import java.util.Map;
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

@CrossOrigin(origins = "*") // Cambia esto por el origen de tu frontend
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
    
        // Agrupar les puntuacions pel nom del jugador i seleccionar la millor puntuació de cada grup
        Map<String, Score> bestScoresByPlayer = scores.stream()
            .collect(Collectors.toMap(
                Score::getName, // Clau: nom del jugador
                score -> score,       // Valor: l'objecte Score
                (existing, replacement) -> existing.getScore() > replacement.getScore() ? existing : replacement // Mantenir la puntuació més alta
            ));
    
        // Ordenar les millors puntuacions de cada jugador de forma descendent i limitar a les 10 primeres
        return bestScoresByPlayer.values().stream()
            .sorted((s1, s2) -> Integer.compare(s2.getScore(), s1.getScore())) // Ordenació descendent
            .limit(10) // Limitar a les 10 millors puntuacions
            .collect(Collectors.toList());
    }
    

    // Obtener puntajes por nombre
    @GetMapping("/getScore")
    public List<Score> getScore(@RequestParam String name) {
        return scoreRepository.findByName(name);
    }
}
