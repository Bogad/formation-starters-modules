package ma.omnishore.clients.api.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ma.co.omnidata.framework.services.lock.exceptions.AlreadyLockedException;
import ma.co.omnidata.framework.services.lock.exceptions.LockException;
import ma.co.omnidata.framework.services.lock.exceptions.WrongUnlockException;

@ControllerAdvice
public class ExceptionHandlers {

	private static final Logger log = LoggerFactory.getLogger(ExceptionHandlers.class);

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public ResponseError handleAccessDeniedException(final AccessDeniedException ex) {
		return new ResponseError("ACCESS.DENIED", ex.getMessage());
	}

	@ExceptionHandler(LockException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ResponseError handleLockException(final LockException ex) {
		log.error("LOCK.EXCEPTION", ex);
		return new ResponseError("LOCK.EXCEPTION", ex.getMessage());
	}

	@ExceptionHandler(AlreadyLockedException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ResponseError handleAlreadyLockedException(final AlreadyLockedException ex) {
		log.error("ALREADY.LOCKED.EXCEPTION", ex);
		return new ResponseError("ALREADY.LOCKED.EXCEPTION", ex.getMessage());
	}

	@ExceptionHandler(WrongUnlockException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ResponseError handleWrongUnlockException(final WrongUnlockException ex) {
		log.error("WRONG.UNLOCK.EXCEPTION", ex);
		return new ResponseError("WRONG.UNLOCK.EXCEPTION", ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseError handleThrowable(final Exception ex) {
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