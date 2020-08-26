package kr.re.kari.portal.board.article;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/board/{boardId}/article")
@RequiredArgsConstructor
@Log4j2
public class ArticleController {

	private final ArticleMapper articleMapper;


	@GetMapping
	public List<Article> getArticleAll(@PathVariable Long boardId) {
		return articleMapper.findAll(boardId);
	}

	@GetMapping("/{articleId}")
	public Article getArticle(@PathVariable Long articleId) {
		return articleMapper.findById(articleId);
	}

	@PostMapping
	public ResponseEntity<?> postArticle(
			@PathVariable Long boardId,
			@RequestBody ArticleDto articleDto) {

		articleDto.setBoardId(boardId);
		articleMapper.save(articleDto);

		return ResponseEntity.ok().body(articleMapper.findById(articleDto.getArticleId()));
	}

	@PutMapping("/{articleId}")
	public ResponseEntity<?> putArticle(@RequestBody Article article) {
		log.debug(article.toString());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{articleId}")
	public ResponseEntity<?> deleteArticle(@RequestBody Article article) {
		log.debug(article.toString());
		return ResponseEntity.ok().build();
	}
}
