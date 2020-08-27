package kr.re.kari.portal.board.article;

import kr.re.kari.portal.board.common.BaseSearchDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArticleSearchDto extends BaseSearchDto {
	private String parentArticleId;
	private String title;
	private String contents;
	private String useYn = "Y";
}
