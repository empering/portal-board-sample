package kr.re.kari.portal.board.exception;

public class ArticleDataNotFoundException extends DataNotFoundException {
	public ArticleDataNotFoundException() {
		super("게시물 데이터를 찾을 수 없습니다.");
	}
}
