package al.thinktech.tutorial.testsuite.repository.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "events")
public class EventModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_generator")
	@SequenceGenerator(name = "event_generator", sequenceName = "event_seq", allocationSize = 1, initialValue = 6)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", length = 500, nullable = false)
	private String name;

	@Column(name = "description", length = 5000)
	private String description;

	@Column(name = "startedAt")
	private Instant startedAt;

	@Column(name = "finishedAt")
	private Instant finishedAt;

}
