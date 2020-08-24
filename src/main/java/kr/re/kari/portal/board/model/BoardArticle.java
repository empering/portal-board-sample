package kr.re.kari.portal.board.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardArticle {

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
