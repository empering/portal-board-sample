package kr.re.kari.portal.board.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ArticleFormDto {
	private long parentArticleId;
	private long boardId;
	@NotNull
	private String title;
	@NotNull
	private String contents;
	private String noticeYn;
}
