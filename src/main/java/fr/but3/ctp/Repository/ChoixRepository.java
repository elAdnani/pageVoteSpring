package fr.but3.ctp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.but3.ctp.model.Choix;

@Repository
public interface ChoixRepository extends JpaRepository<Choix, Long> {
}
