package negativ.agario.web;

import negativ.agario.entities.Record;
import negativ.agario.model.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private RecordRepository recordRepository;

    @GetMapping({"/", "/home"})
    public String home() {
        return "home";
    }

    @GetMapping("/records")
    public @ResponseBody Iterable<Record> records() {
        return recordRepository.findAllByOrderByScore();
    }
}
