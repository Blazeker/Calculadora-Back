package calculadora.IASHandyman.controller;

import calculadora.IASHandyman.model.Reportes;


import calculadora.IASHandyman.services.IReporteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("/tecnicos")
public class ReportServicioController {

    private final IReporteService reporteServicio;

    public ReportServicioController(IReporteService IReporteService) {
        this.reporteServicio = IReporteService;

    }

    @GetMapping("/tecnicos")
    public ResponseEntity<List<Reportes>> getAllTecnicos() {
        return ResponseEntity.ok(reporteServicio.getAllTecnicos());
    }

    @PostMapping(value = "/tecnicos/insertar")
    public ResponseEntity<Reportes> save(@RequestBody Reportes reportes) {
        return ResponseEntity.ok(reporteServicio.agregarTecnico(reportes));
    }
}
