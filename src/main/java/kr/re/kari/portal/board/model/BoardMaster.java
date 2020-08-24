package kr.re.kari.portal.board.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardMaster {

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
