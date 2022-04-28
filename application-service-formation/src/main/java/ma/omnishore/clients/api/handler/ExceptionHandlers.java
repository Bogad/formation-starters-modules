package ma.omnishore.clients.api.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlers {

	private static final Logger log = LoggerFactory.getLogger(ExceptionHandlers.class);

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseError handleThrowable(final Throwable ex) {
		log.error("An unexpected internal server error occured", ex);
		return new ResponseError("INTERNAL.SERVER.ERROR", ex.getMessage());
	}

	public static class ResponseError {
		private final String code;
		private final String message;

		public ResponseError(String code, String message) {
			super();
			this.code = code;
			this.message = message;
		}

		public String getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

	}
}