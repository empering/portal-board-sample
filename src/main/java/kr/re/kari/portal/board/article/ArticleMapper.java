package kr.re.kari.portal.board.article;

import kr.re.kari.portal.board.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article, Long> {
	List<Article> findAll(Long boardId);
}
