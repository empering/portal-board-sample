package kr.re.kari.portal.board.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardComment {

  private long commentId;
  private long articleId;
  private String comment;
  private String useYn;
  private String registerId;
  private java.sql.Timestamp registerTimestamp;
  private String updateId;
  private java.sql.Timestamp updateTimestamp;

}
