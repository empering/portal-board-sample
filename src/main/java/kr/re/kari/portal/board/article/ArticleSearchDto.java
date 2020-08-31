package kr.re.kari.portal.board.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.re.kari.portal.board.base.BaseSearchDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ArticleSearchDto extends BaseSearchDto {
	private String boardId;
	private String parentArticleId;
	private String title;
	private String contents;
	private String useYn = "Y";
}
