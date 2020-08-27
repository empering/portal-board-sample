package kr.re.kari.portal.board.article;

import kr.re.kari.portal.board.common.BaseFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArticleFormDto extends BaseFormDto {
	private long articleId;
	private long parentArticleId;
	private long boardId;
	private String noticeYn;
	private String title;
	private String contents;
	private long readCount;
	private String useYn;
}
