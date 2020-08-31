package kr.re.kari.portal.board.index;

import kr.re.kari.portal.board.article.ArticleController;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/")
public class IndexController {
	@GetMapping
	public RepresentationModel<?> index() {
		RepresentationModel<?> indexModel = new RepresentationModel<>();

		indexModel.add(
				linkTo(methodOn(IndexController.class).index()).withSelfRel(),
				linkTo(ArticleController.class).withRel("articles")
		);

		return indexModel;
	}
}
