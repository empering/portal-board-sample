package kr.re.kari.portal.board.article;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArticleDto extends Article {
	private String searchKey;
	private String searchWord;
	private int pageSize = 10;
	private int pageIndex = 1;
	private int countPerPage = 10;
}
