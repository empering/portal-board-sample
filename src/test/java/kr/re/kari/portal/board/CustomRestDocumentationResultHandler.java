package kr.re.kari.portal.board;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

public class CustomRestDocumentationResultHandler {

	private final RestDocumentationResultHandler handler;

	public CustomRestDocumentationResultHandler() {
		this.handler = document("{method-name}",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())
		);
	}

	public RestDocumentationResultHandler getHandler() {
		return this.handler;
	}
}
