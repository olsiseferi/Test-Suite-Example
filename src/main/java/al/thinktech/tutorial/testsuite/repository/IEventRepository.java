package al.thinktech.tutorial.testsuite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import al.thinktech.tutorial.testsuite.repository.model.EventModel;

@Repository
public interface IEventRepository extends JpaRepository<EventModel, Long> {
}
