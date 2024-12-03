package daaw.api_score.persistence.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import daaw.api_score.persistence.model.Score;

@Repository
public interface ScoreRepository extends CrudRepository<Score, Long>{
    List<Score> findByName(String name);
}
