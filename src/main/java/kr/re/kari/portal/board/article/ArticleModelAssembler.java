package kr.re.kari.portal.board.article;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ArticleModelAssembler implements RepresentationModelAssembler<Article, EntityModel<Article>> {

	@Override
	public EntityModel<Article> toModel(Article article) {
		return EntityModel.of(article,
				linkTo(methodOn(ArticleController.class).getArticle(article.getArticleId())).withSelfRel().expand(article.getBoardId()),
				linkTo(methodOn(ArticleController.class).getArticleAll(null)).withRel("articles").expand(article.getBoardId())
		);
	}

	@Override
	public CollectionModel<EntityModel<Article>> toCollectionModel(Iterable<? extends Article> articles) {
		return CollectionModel.of(
				StreamSupport.stream(articles.spliterator(), false)
						.map(this::toModel)
						.collect(Collectors.toList()),
				linkTo(methodOn(ArticleController.class).getArticleAll(null)).withSelfRel()
		);
	}

	public CollectionModel<EntityModel<Article>> toCollectionModel(Iterable<? extends Article> articles, Long boardId) {
		return CollectionModel.of(
				StreamSupport.stream(articles.spliterator(), false)
						.map(this::toModel)
						.collect(Collectors.toList()),
				linkTo(methodOn(ArticleController.class).getArticleAll(null)).withSelfRel().expand(boardId)
		);
	}
}
