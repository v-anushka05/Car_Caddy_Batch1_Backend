package org.infosys.repository;

import org.infosys.model.Car;
import org.infosys.model.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    Optional<Maintenance> findTopByCarOrderByDateDesc(Car car);

}
