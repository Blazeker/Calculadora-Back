package calculadora.IASHandyman.services;

import calculadora.IASHandyman.model.Reportes;
import calculadora.IASHandyman.model.ResponseCalculoHoras;

import java.util.HashMap;
import java.util.List;

public interface ICalculadoraHorasService {

     HashMap<String, Integer> calcularHoras(List <Reportes> reporteSemana);
     List<Reportes> resumenTotalHoras(String idTecnico);
     ResponseCalculoHoras horasSemanalesPorTecnico (String idTecnico, int semana);
}
