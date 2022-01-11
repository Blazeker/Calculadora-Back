package calculadora.IASHandyman.services;

import calculadora.IASHandyman.model.Reportes;

import java.util.HashMap;
import java.util.List;

public interface IReporteService {
    List<Reportes> getAllTecnicos();

    Reportes agregarTecnico(Reportes reportes);
}
