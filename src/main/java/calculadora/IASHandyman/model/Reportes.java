package calculadora.IASHandyman.model;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter @Setter @NoArgsConstructor
public class Reportes
{
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idReporte;
    @NotNull
    private String idTecnico;
    @NotNull
    private String idServicio;
    @NotNull
    LocalDateTime fechaInicio;
    @NotNull
    LocalDateTime fechaFinal;


}

