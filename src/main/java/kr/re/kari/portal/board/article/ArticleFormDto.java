package kr.re.kari.portal.board.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ArticleFormDto {
	private long parentArticleId;
	private long boardId;
	@NotBlank
	private String title;
	@NotBlank
	private String contents;
	private String noticeYn;
}
