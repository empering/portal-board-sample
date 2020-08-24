package kr.re.kari.portal.board.mapper;

import kr.re.kari.portal.board.model.BoardArticle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardArticleMapper extends BaseMapper<BoardArticle, Long> {
	List<BoardArticle> findAll(Long boardId);
}
