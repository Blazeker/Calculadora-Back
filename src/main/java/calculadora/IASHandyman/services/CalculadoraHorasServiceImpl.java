package calculadora.IASHandyman.services;

import calculadora.IASHandyman.model.Reportes;
import calculadora.IASHandyman.model.ResponseCalculoHoras;
import calculadora.IASHandyman.repository.IReporteServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.IsoFields;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CalculadoraHorasServiceImpl implements ICalculadoraHorasService {

    @Autowired
    IReporteServicioRepository reporteServicioRepository;

    @Override
    public List<Reportes> resumenTotalHoras(String idTecnico) {
        return reporteServicioRepository.findAllById(idTecnico);
    }

    public boolean validarHoraExtra(int totalHoras)
    {
        return totalHoras >= 48;
    }

    public void  almacenarHora(HashMap<String, Integer> total, String tipo){
            total.replace(tipo, total.get(tipo)+1);
    }

    @Override
    public HashMap<String, Integer> calcularHoras(List <Reportes> reporteSemana) {
        HashMap<String, Integer> resultado = new HashMap<>();
        resultado.put("horasNormales", 0);
        resultado.put("horasNocturnas", 0);
        resultado.put("horasDominicales",0);
        resultado.put("horasNormalesExtra",0);
        resultado.put("horasNocturnasExtra",0);
        resultado.put("horasDominicalesExtra",0);
        resultado.put("horasTotales",0);
        LocalTime inicioJornada = LocalTime.of(7,0);
        LocalTime finJornada = LocalTime.of(20,0);
        LocalTime mediaNoche = LocalTime.of(0,0);

        reporteSemana.forEach(data -> {
            Duration p = Duration.between(data.getFechaInicio(), data.getFechaFinal());
            long horas = Math.abs(p.toHours());
            IntStream.range(0,(int) horas).forEach(i -> {
                LocalDateTime horasAEvaluar = data.getFechaInicio().plusHours(i);
                if(validarHoraExtra(resultado.get("horasTotales"))){
                    if(horasAEvaluar.getDayOfWeek().toString().equalsIgnoreCase("sunday")){
                        almacenarHora(resultado,"horasDominicalesExtra");
                    }else if(( horasAEvaluar.isAfter(LocalDateTime.of(horasAEvaluar.toLocalDate(),inicioJornada))  ||
                            horasAEvaluar.isEqual(LocalDateTime.of(horasAEvaluar.toLocalDate(),inicioJornada))) &&
                            horasAEvaluar.isBefore(LocalDateTime.of(horasAEvaluar.toLocalDate(),finJornada))){
                        almacenarHora(resultado,"horasNormalesExtra");
                    }else if(horasAEvaluar.isAfter(LocalDateTime.of(horasAEvaluar.toLocalDate(),finJornada)) ||
                            horasAEvaluar.isEqual(LocalDateTime.of(horasAEvaluar.toLocalDate(),finJornada)) &&
                                    horasAEvaluar.isBefore(LocalDateTime.of(horasAEvaluar.toLocalDate().plusDays(1),inicioJornada)) ||
                            (horasAEvaluar.isAfter(LocalDateTime.of(horasAEvaluar.toLocalDate(),mediaNoche)) &&
                                    horasAEvaluar.isBefore(LocalDateTime.of(horasAEvaluar.toLocalDate(),inicioJornada)))
                    ){
                        almacenarHora(resultado,"horasNocturnasExtra");
                    }
                }else if(horasAEvaluar.getDayOfWeek().toString().equalsIgnoreCase("sunday")){
                    //Hora dominical
                    almacenarHora(resultado,"horasDominicales");

                } else if( ( horasAEvaluar.isAfter(LocalDateTime.of(horasAEvaluar.toLocalDate(),inicioJornada)) ||
                        horasAEvaluar.isEqual(LocalDateTime.of(horasAEvaluar.toLocalDate(),inicioJornada))) &&
                        horasAEvaluar.isBefore(LocalDateTime.of(horasAEvaluar.toLocalDate(),finJornada)) ){
                    almacenarHora(resultado,"horasNormales");

                } else if((horasAEvaluar.isAfter(LocalDateTime.of(horasAEvaluar.toLocalDate(),finJornada)) ||
                        horasAEvaluar.isEqual(LocalDateTime.of(horasAEvaluar.toLocalDate(),finJornada))) &&
                        horasAEvaluar.isBefore(LocalDateTime.of(horasAEvaluar.toLocalDate().plusDays(1),inicioJornada)) ||(
                        horasAEvaluar.isAfter(LocalDateTime.of(horasAEvaluar.toLocalDate(),mediaNoche)) &&
                                horasAEvaluar.isBefore(LocalDateTime.of(horasAEvaluar.toLocalDate(),inicioJornada)))
                ){
                    almacenarHora(resultado,"horasNocturnas");
                }
                almacenarHora(resultado,"horasTotales");
            });
        });
        return resultado;
        }

    @Override
    public ResponseCalculoHoras horasSemanalesPorTecnico (String idTecnico, int semana) {
        ResponseCalculoHoras respuesta = new ResponseCalculoHoras();
        if(idTecnico.equalsIgnoreCase("null")){
            respuesta.setError("No se aceptan campos nulos");
            return respuesta;
        }else if ((semana == 0) || semana > 53 ){
            respuesta.setError("Revisar semana");
            return respuesta;
        }
        respuesta.setHoras( listarPorSemana(resumenTotalHoras(idTecnico), semana));
        return respuesta;
    }

    public HashMap<String, Integer> listarPorSemana(List <Reportes> servicios, int semana){
        servicios.stream().forEach(a -> System.out.println((LocalDate.of(
                a.getFechaFinal().getYear()
                , a.getFechaInicio().getMonthValue()
                , a.getFechaInicio().getDayOfMonth())
                .get(IsoFields.WEEK_OF_WEEK_BASED_YEAR))));
        List <Reportes> serviciosSemana =
                servicios.stream()
                        .filter(a -> (LocalDate.of(
                                a.getFechaFinal().getYear()
                                , a.getFechaInicio().getMonthValue()
                                , a.getFechaInicio().getDayOfMonth())
                                .get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == semana-1))
                        .sorted(Comparator.comparing(Reportes::getFechaInicio))
                        .collect(Collectors.toList());
        return calcularHoras(serviciosSemana);
    }



}


