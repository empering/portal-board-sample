package kr.re.kari.portal.board.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardArticleDto extends BoardArticle {
	private String searchKey;
	private String searchWord;
	private int pageSize = 10;
	private int pageIndex = 1;
	private int countPerPage = 10;
}
