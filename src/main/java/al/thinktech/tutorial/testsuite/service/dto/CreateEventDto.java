package al.thinktech.tutorial.testsuite.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventDto {
	@NotBlank(message = "You must specify a name for the Event!")
	@NonNull
	@Size(max = 500, message = "Maximum size for the name is 500 characters.")
	private String name;
	@NonNull
	@Size(max = 5000, message = "Maximum size for the description is 5000 characters.")
	private String description;
}
