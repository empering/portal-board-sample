package kr.re.kari.portal.board.base;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class BaseSearchDto {
	private String searchKey;
	private String searchWord;
	private int pageSize = 10;
	@Min(1)
	private int pageIndex = 1;
	private int countPerPage = 10;
}
