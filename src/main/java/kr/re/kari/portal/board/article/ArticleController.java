package kr.re.kari.portal.board.article;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/board/{boardId}/article")
@RequiredArgsConstructor
@Log4j2
public class ArticleController {

	private final ArticleMapper articleMapper;

	private final ModelMapper modelMapper;

	@GetMapping
	public List<Article> getArticleAll(
			@PathVariable Long boardId,
			@RequestBody ArticleSearchDto dto) {
		return articleMapper.findAll(boardId);
	}

	@GetMapping("/{articleId}")
	public Article getArticle(@PathVariable Long articleId) {
		return articleMapper.findById(articleId);
	}

	@PostMapping
	public ResponseEntity<?> postArticle(
			@PathVariable Long boardId,
			@RequestBody Article article) {

		article.setBoardId(boardId);
		articleMapper.save(article);

		return ResponseEntity.ok().body(articleMapper.findById(article.getArticleId()));
	}

	@PutMapping("/{articleId}")
	public ResponseEntity<?> putArticle(
			@PathVariable Long articleId,
			@RequestBody Article article) {

		Article targetArticle = articleMapper.findById(articleId);

		if (targetArticle == null) {
			return ResponseEntity.badRequest().build();
		}

		article.setArticleId(articleId);
		articleMapper.update(article);

		return ResponseEntity.ok().body(articleMapper.findById(article.getArticleId()));
	}

	@DeleteMapping("/{articleId}")
	public ResponseEntity<?> deleteArticle(@PathVariable Long articleId) {

		Article article = articleMapper.findById(articleId);

		if (article == null) {
			return ResponseEntity.badRequest().build();
		}

		articleMapper.delete(articleId);

		return ResponseEntity.ok().build();
	}
}
