package negativ.agario.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="Records")
@NoArgsConstructor
@AllArgsConstructor
public class Record {

    @Id
    private String name;
    private float score;
}
