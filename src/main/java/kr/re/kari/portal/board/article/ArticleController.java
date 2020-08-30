package kr.re.kari.portal.board.article;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController()
@RequestMapping("/boards/{boardId}/articles")
@RequiredArgsConstructor
@Log4j2
public class ArticleController {

	private final ArticleMapper articleMapper;

	private final ArticleModelAssembler assembler;

	private final ModelMapper modelMapper;

	Long boardId;

	@InitBinder
	public void getBoardId(@PathVariable Long boardId) {
		this.boardId = boardId;
	}

	@GetMapping()
	public CollectionModel<EntityModel<Article>> getArticleAll(@RequestBody ArticleSearchDto dto) {
		return assembler.toCollectionModel(articleMapper.findAll(boardId), boardId);
	}

	@PostMapping
	public ResponseEntity<?> postArticle(@RequestBody ArticleFormDto articleDto) {

		articleDto.setBoardId(boardId);
		Article article = modelMapper.map(articleDto, Article.class);

		articleMapper.save(article);

		EntityModel<Article> articleModel = assembler.toModel(articleMapper.findById(article.getArticleId()));

		return ResponseEntity
				.created(articleModel.getRequiredLink(IanaLinkRelations.SELF).expand(article.getArticleId()).toUri())
				.body(articleModel);
	}

	@GetMapping("{articleId}")
	public EntityModel<Article> getArticle(@PathVariable Long articleId) {
		return assembler.toModel(articleMapper.findById(articleId));
	}

	@PutMapping("/{articleId}")
	public ResponseEntity<?> putArticle(@PathVariable Long articleId, @RequestBody ArticleFormDto articleDto) {

		articleDto.setBoardId(boardId);
		Article article = articleMapper.findById(articleId);

		if (article == null) {
			return ResponseEntity.badRequest().build();
		}

		modelMapper.map(articleDto, article);
		articleMapper.update(article);

		EntityModel<Article> articleModel = assembler.toModel(articleMapper.findById(article.getArticleId()));

		return ResponseEntity
				.created(articleModel.getRequiredLink(IanaLinkRelations.SELF).expand(articleId).toUri())
				.body(articleModel);
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
