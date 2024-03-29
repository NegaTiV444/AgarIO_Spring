package negativ.agario.data;

import negativ.agario.data.entities.Record;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecordRepository extends CrudRepository<Record, String> {

    List<Record> findAllByOrderByScoreDesc();
}
