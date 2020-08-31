package kr.re.kari.portal.board.exception;

public class DataNotFoundException extends RuntimeException {
	public DataNotFoundException() {
		super("데이터를 찾을 수 없습니다.");
	}

	public DataNotFoundException(String message) {
		super(message);
	}
}
