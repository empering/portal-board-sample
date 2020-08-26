package kr.re.kari.portal.board.comment;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("comment")
@Getter
@Setter
public class Comment {

  private long commentId;
  private long articleId;
  private String comment;
  private String useYn;
  private String registerId;
  private java.sql.Timestamp registerTimestamp;
  private String updateId;
  private java.sql.Timestamp updateTimestamp;

}
