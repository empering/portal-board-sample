package kr.re.kari.portal.board.article;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController()
@RequestMapping("/board/{boardId}/article")
@RequiredArgsConstructor
@Log4j2
public class ArticleController {

	private final ArticleMapper articleMapper;

	private final ModelMapper modelMapper;

	Long boardId;

	@InitBinder
	public void getBoardId(@PathVariable Long boardId) {
		this.boardId = boardId;
	}

	@GetMapping
	public CollectionModel<EntityModel<Article>> getArticleAll(@RequestBody ArticleSearchDto dto) {
		List<EntityModel<Article>> articles = articleMapper.findAll(boardId).stream()
				.map(article ->
						EntityModel.of(article,
								linkTo(methodOn(ArticleController.class).getArticle(article.getArticleId())).withSelfRel(),
								linkTo(methodOn(ArticleController.class).getArticleAll(null)).withRel("article")
						)).collect(Collectors.toList());

		return CollectionModel.of(articles,
				linkTo(methodOn(ArticleController.class).getArticleAll(dto)).withRel("article"));
	}

	@GetMapping("/{articleId}")
	public EntityModel<Article> getArticle(@PathVariable Long articleId) {
		// return articleMapper.findById(articleId);
		return EntityModel.of(articleMapper.findById(articleId),
				linkTo(methodOn(ArticleController.class).getArticle(articleId)).withSelfRel(),
				linkTo(methodOn(ArticleController.class).getArticleAll(null)).withRel("article")
		);
	}

	@PostMapping
	public ResponseEntity<?> postArticle(@RequestBody Article article) {

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

		return ResponseEntity.noContent().build();
	}
}
