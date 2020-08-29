package kr.re.kari.portal.board.article;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Alias("article")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Article {

  private long articleId;
  private long parentArticleId;
  private long boardId;
  private String noticeYn;
  private String title;
  private String contents;
  private long readCount;
  private String useYn;
  private String registerId;
  private java.sql.Timestamp registerTimestamp;
  private String updateId;
  private java.sql.Timestamp updateTimestamp;

}
