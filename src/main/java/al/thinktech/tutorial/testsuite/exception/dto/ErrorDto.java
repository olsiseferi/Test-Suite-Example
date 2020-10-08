package al.thinktech.tutorial.testsuite.exception.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class ErrorDto {
	@NonNull
	private Long errorCode;
	@NonNull
	private String message;
}
