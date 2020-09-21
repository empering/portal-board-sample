package kr.re.kari.portal.board.article;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ArticleModelAssembler implements RepresentationModelAssembler<Article, EntityModel<Article>> {

	@Override
	public EntityModel<Article> toModel(Article article) {
		return EntityModel.of(article,
				linkTo(methodOn(ArticleController.class).getArticle(article.getArticleId())).withSelfRel().expand(article.getBoardId()),
				linkTo(ArticleController.class).withRel("articles").expand(article.getBoardId())
		);
	}

}
