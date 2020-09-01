package kr.re.kari.portal.board.base;

import kr.re.kari.portal.board.exception.DataNotFoundException;
import kr.re.kari.portal.board.index.IndexController;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@ControllerAdvice
@Log4j2
public class BaseController {

	@ExceptionHandler
	@ResponseBody
	public ResponseEntity<?> handleException(Exception e) {
		log.error(e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<?> handleViolationException(MethodArgumentNotValidException e) {
		log.error(e);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(EntityModel.of(e.getBindingResult(),
						linkTo(IndexController.class).withRel("index")
				));
	}

	@ExceptionHandler(DataNotFoundException.class)
	@ResponseBody
	public ResponseEntity<?> handleDataNotFoundException(DataNotFoundException e) {
		log.error(e);
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(EntityModel.of(e.getMessage(),
						linkTo(IndexController.class).withRel("index")
				));
	}

}
