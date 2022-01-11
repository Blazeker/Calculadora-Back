package calculadora.IASHandyman.model;

import lombok.Data;

import java.util.HashMap;
@Data
public class ResponseCalculoHoras {
    private HashMap<String, Integer> horas;
    private String error;

}
