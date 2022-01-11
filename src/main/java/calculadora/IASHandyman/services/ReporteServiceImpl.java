package calculadora.IASHandyman.services;


import calculadora.IASHandyman.model.Reportes;
import calculadora.IASHandyman.repository.IReporteServicioRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static java.lang.System.exit;

@Service
public class ReporteServiceImpl implements IReporteService {
    private final IReporteServicioRepository reporteServicioRepository;
    private final ICalculadoraHorasService calculadoraHorasService;

    public ReporteServiceImpl(IReporteServicioRepository reporteServicioRepository, ICalculadoraHorasService calculadoraHorasService) {
        this.reporteServicioRepository = reporteServicioRepository;
        this.calculadoraHorasService = calculadoraHorasService;
    }

    @Override
    public List<Reportes> getAllTecnicos() {
        return reporteServicioRepository.findAll();
    }


    @Override
    public Reportes agregarTecnico(Reportes reportes)
    {
        HashMap<Reportes, String> respuesta = new HashMap<>();

        if (reportes.getIdTecnico() == null ||
                reportes.getIdServicio() == null ||
                reportes.getFechaInicio() == null ||
                reportes.getFechaFinal() == null
        )
        {
            return (null);
        }

        if (reportes.getFechaFinal().isBefore(reportes.getFechaInicio()))
        {
            return (null);
        }
        if (reportes.getFechaInicio().isAfter(reportes.getFechaFinal()))
        {
            return (null);
        }
        return (reporteServicioRepository.save(reportes));

    }


}

