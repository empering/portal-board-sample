package kr.re.kari.portal.board.master;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("master")
@Getter
@Setter
public class Master {

  private long boardId;
  private String boardType;
  private String boardName;
  private String boardDescription;
  private long attachFileLimit;
  private String replyYn;
  private String commentYn;
  private String useYn;
  private String registerId;
  private java.sql.Timestamp registerTimestamp;
  private String updateId;
  private java.sql.Timestamp updateTimestamp;

}
