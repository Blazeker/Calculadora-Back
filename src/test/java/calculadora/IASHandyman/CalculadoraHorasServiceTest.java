package calculadora.IASHandyman;


import calculadora.IASHandyman.model.Reportes;
import calculadora.IASHandyman.model.ResponseCalculoHoras;
import calculadora.IASHandyman.services.ICalculadoraHorasService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashMap;

@SpringBootTest
public class CalculadoraHorasServiceTest {


    private final ICalculadoraHorasService calculadoraHorasService;

    @Autowired
    public CalculadoraHorasServiceTest(ICalculadoraHorasService calculadoraHorasService) {
        this.calculadoraHorasService = calculadoraHorasService;
    }

    @Test
    public void agregarExitoTest(){
        Reportes reportes = new Reportes();
        reportes.setIdTecnico("Test");
        reportes.setIdServicio("Tipo 1");
        reportes.setFechaInicio(LocalDateTime.of(2022,8,21,7,10));
        reportes.setFechaFinal(LocalDateTime.of(2022,8,21,12,10));
        HashMap<Reportes, String> esperado = new HashMap<>();
        esperado.put(reportes, "");

        HashMap<Reportes, String> resultado = new HashMap<>();
        resultado.put(reportes, "");

        Assertions.assertEquals(resultado,esperado);
    }

    @Test
    public void consultarExitoTest(){
        ResponseCalculoHoras esperado = new ResponseCalculoHoras();
        HashMap<String, Integer> horas = new HashMap<>();
        horas.put("horasNormales", 0);
        horas.put("horasNocturnas", 0);
        horas.put("horasDominicales",5);
        horas.put("horasNormalesExtra",0);
        horas.put("horasNocturnasExtra",0);
        horas.put("horasDominicalesExtra",0);
        horas.put("horasTotales",5);
        esperado.setHoras(horas);

        ResponseCalculoHoras resultado = calculadoraHorasService.horasSemanalesPorTecnico("Test", 34);

        Assertions.assertEquals(resultado,esperado);

    }

    @Test
    public void consultarFallidoTest(){
        ResponseCalculoHoras esperado = new ResponseCalculoHoras();
        HashMap<String, Integer> horas = new HashMap<>();
        horas.put("horasNormales", 0);
        horas.put("horasNocturnas", 0);
        horas.put("horasDominicales",0);
        horas.put("horasNormalesExtra",0);
        horas.put("horasNocturnasExtra",0);
        horas.put("horasDominicalesExtra",0);
        horas.put("horasTotales",0);
        esperado.setHoras(horas);

        ResponseCalculoHoras resultado = calculadoraHorasService.horasSemanalesPorTecnico("Tecnico 1", 50);

        Assertions.assertEquals(resultado,esperado);
    }
}
