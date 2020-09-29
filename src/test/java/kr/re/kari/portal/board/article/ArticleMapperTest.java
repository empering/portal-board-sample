package kr.re.kari.portal.board.article;

import kr.re.kari.portal.board.exception.ArticleDataNotFoundException;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@MybatisTest
class ArticleMapperTest {

	@Autowired
	ArticleMapper articleMapper;

	@Test
	public void findAll() {
		ArticleSearchDto article = new ArticleSearchDto();
		article.setBoardId(1L);

		List<Article> all = articleMapper.findAll(article, PageRequest.of(1, 2));
		assertThat(all).isNotEmpty();
		assertThat(all.size()).isEqualTo(1);
	}

	@Test
	public void count() {
		ArticleSearchDto article = new ArticleSearchDto();
		article.setBoardId(1L);

		int count = articleMapper.count(article);
		assertThat(count).isEqualTo(3);
	}

	@Test
	public void findById() {
		Optional<Article> byId = articleMapper.findById(1L);
		assertThat(byId).isNotEmpty();
		assertThat(byId.get().getBoardId()).isEqualTo(1L);
	}

	@Test
	public void findById_notExists() {
		Optional<Article> byId = articleMapper.findById(-999L);
		assertThat(byId).isEmpty();

		assertThatExceptionOfType(ArticleDataNotFoundException.class).isThrownBy(() -> byId.orElseThrow(ArticleDataNotFoundException::new));
	}

	@Test
	public void save() {
		Article article = new Article();
		article.setBoardId(1L);
		article.setNoticeYn("Y");
		article.setTitle("mapper test");
		article.setContents("mapper test contents");

		articleMapper.save(article);

		Optional<Article> byId = articleMapper.findById(article.getArticleId());
		assertThat(byId).isNotEmpty();

		Article savedArticle = byId.get();
		assertThat(savedArticle.getBoardId()).isEqualTo(article.getBoardId());
		assertThat(savedArticle.getArticleId()).isEqualTo(article.getArticleId());
		assertThat(savedArticle.getNoticeYn()).isEqualTo(article.getNoticeYn());
		assertThat(savedArticle.getTitle()).isEqualTo(article.getTitle());
		assertThat(savedArticle.getContents()).isEqualTo(article.getContents());
	}

	@Test
	public void update() {
		Article article = new Article();
		article.setArticleId(1L);
		article.setTitle("mapper test");

		articleMapper.update(article);

		Optional<Article> byId = articleMapper.findById(article.getArticleId());
		assertThat(byId).isNotEmpty();

		Article updatedArticle = byId.get();
		assertThat(updatedArticle.getArticleId()).isEqualTo(article.getArticleId());
		assertThat(updatedArticle.getTitle()).isEqualTo(article.getTitle());
	}

	@Test
	public void delete() {
		articleMapper.delete(1L);

		Optional<Article> byId = articleMapper.findById(1L);
		assertThat(byId).isNotEmpty();

		Article deletedArticle = byId.get();
		assertThat(deletedArticle.getArticleId()).isEqualTo(1L);
		assertThat(deletedArticle.getUseYn()).isEqualTo("N");
	}

}