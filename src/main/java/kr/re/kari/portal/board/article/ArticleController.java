package kr.re.kari.portal.board.article;

import kr.re.kari.portal.board.exception.ArticleDataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping("/articles")
@ExposesResourceFor(Article.class)
@RequiredArgsConstructor
@Log4j2
public class ArticleController {

	private final ArticleService articleService;

	private final ArticleModelAssembler assembler;

	private final ModelMapper modelMapper;

	@GetMapping()
	public PagedModel<EntityModel<Article>> getArticleAll(
			@RequestBody ArticleSearchDto searchDto, Pageable pageable,
			PagedResourcesAssembler<Article> pagedResourcesAssembler) {

		Article article = modelMapper.map(searchDto, Article.class);

		Page<Article> page = articleService.findAll(article, pageable);

		return pagedResourcesAssembler.toModel(page);
	}

	@PostMapping
	public ResponseEntity<?> postArticle(@RequestBody @Valid ArticleFormDto articleDto) {

		Article article = modelMapper.map(articleDto, Article.class);

		Article newArticle = articleService.save(article).orElseThrow(RuntimeException::new);

		EntityModel<Article> articleModel = assembler.toModel(newArticle);

		return ResponseEntity
				.created(articleModel.getRequiredLink(IanaLinkRelations.SELF).expand(newArticle.getArticleId()).toUri())
				.body(articleModel);
	}

	@GetMapping("/{articleId}")
	public EntityModel<Article> getArticle(@PathVariable Long articleId) {
		return assembler.toModel(articleService.findById(articleId).orElseThrow(ArticleDataNotFoundException::new));
	}

	@PutMapping("/{articleId}")
	public ResponseEntity<?> putArticle(@PathVariable Long articleId, @RequestBody @Valid ArticleFormDto articleDto) {

		Article article = articleService.findById(articleId).orElseThrow(ArticleDataNotFoundException::new);

		modelMapper.map(articleDto, article);
		Article newArticle = articleService.update(article).orElseThrow(RuntimeException::new);

		EntityModel<Article> articleModel = assembler.toModel(newArticle);

		return ResponseEntity
				.created(articleModel.getRequiredLink(IanaLinkRelations.SELF).expand(articleId).toUri())
				.body(articleModel);
	}

	@DeleteMapping("/{articleId}")
	public ResponseEntity<?> deleteArticle(@PathVariable Long articleId) {

		articleService.findById(articleId).orElseThrow(ArticleDataNotFoundException::new);

		articleService.delete(articleId);

		return ResponseEntity.noContent().build();
	}
}
