package calculadora.IASHandyman.repository;

import calculadora.IASHandyman.model.Reportes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReporteServicioRepository extends JpaRepository<Reportes, Long> {

    @Query(value = "SELECT * FROM Reportes WHERE Reportes.id_tecnico = ?1", nativeQuery = true)
    List<Reportes> findAllById(String idTecnico);

    // List<Reportes> findAllByidTecnico(String idTecnico)





}
