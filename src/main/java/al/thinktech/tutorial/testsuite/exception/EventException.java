package al.thinktech.tutorial.testsuite.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final static Long ERROR_CODE_1 = 1L;
	private final static Long ERROR_CODE_2 = 2L;
	private final EventExceptionType type;

	public EventException(EventExceptionType type) {
		super();
		this.type = type;
	}

	public enum EventExceptionType {
		EVENT_NOT_FOUND(ERROR_CODE_1, "Event not Found", HttpStatus.NOT_FOUND),

		FINISH_OPERATION_NOT_ALLOWED(ERROR_CODE_2, "Event needs to start before it finishes.", HttpStatus.BAD_REQUEST);

		private EventExceptionType(Long errorCode, String message, HttpStatus status) {
			this.errorCode = errorCode;
			this.message = message;
			this.status = status;
		}

		private Long errorCode;
		private String message;
		private HttpStatus status;

		public Long getCode() {
			return errorCode;
		}

		public String getMessage() {
			return message;
		}

		public HttpStatus getStatus() {
			return status;
		}

	}
}
