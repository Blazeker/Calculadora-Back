package calculadora.IASHandyman.controller;

import calculadora.IASHandyman.model.ResponseCalculoHoras;
import calculadora.IASHandyman.services.ICalculadoraHorasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ReporteHorasController {
    private final ICalculadoraHorasService calculadoraHorasService;

    public ReporteHorasController(ICalculadoraHorasService calculadoraHorasService) {
        this.calculadoraHorasService = calculadoraHorasService;
    }

    @GetMapping("/horas/{idTecnico}/{semana}")
    public ResponseEntity<ResponseCalculoHoras> consultarHoras(@PathVariable String idTecnico, @PathVariable() int semana)
    {
        return ResponseEntity.ok(calculadoraHorasService.horasSemanalesPorTecnico(idTecnico, semana));
    }


}
