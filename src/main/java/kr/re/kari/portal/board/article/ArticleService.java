package kr.re.kari.portal.board.article;

import kr.re.kari.portal.board.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService implements BaseService<Article, Long> {

	private final ArticleMapper articleMapper;

	@Override
	public Page<Article> findAll(Article entity, Pageable pageable) {
		List<Article> articles = articleMapper.findAll(entity, pageable);
		return new PageImpl<>(articles, pageable, articleMapper.count(entity));
	}

	@Override
	public Optional<Article> findById(Long id) {
		return articleMapper.findById(id);
	}

	@Override
	public <S extends Article> Optional<Article> save(S entity) {
		articleMapper.save(entity);
		return articleMapper.findById(entity.getArticleId());
	}

	@Override
	public <S extends Article> Optional<Article> update(S entity) {
		articleMapper.update(entity);
		return articleMapper.findById(entity.getArticleId());
	}

	@Override
	public void delete(Long id) {
		articleMapper.delete(id);
	}
}
