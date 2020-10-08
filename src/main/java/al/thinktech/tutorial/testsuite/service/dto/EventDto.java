package al.thinktech.tutorial.testsuite.service.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
	@NonNull
	private Long id;
	@NonNull
	private String name;
	private String description;
	private Instant startedAt;
	private Instant finishedAt;
}
