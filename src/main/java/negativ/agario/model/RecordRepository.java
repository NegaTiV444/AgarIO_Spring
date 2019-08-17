package negativ.agario.model;

import negativ.agario.entities.Record;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecordRepository extends CrudRepository<Record, String> {

    List<Record> findAllByOrderByScore();
}
