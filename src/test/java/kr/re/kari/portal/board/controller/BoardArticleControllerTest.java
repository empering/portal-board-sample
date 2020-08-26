package kr.re.kari.portal.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.re.kari.portal.board.model.BoardArticleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
// @AutoConfigureMockMvc
class BoardArticleControllerTest {

	private static final Long TEST_BOARD_ID = 1L;

	private static final Long TEST_ARTICLE_ID = 1L;

	/*	MockMvc @Autowired 하려면 @AutoConfigureMockMvc 사용
		RestDocumentationExtension 사용을 위해 수동 주입

		// @Autowired
		// MockMvc mockMvc;
	*/
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext,
					  RestDocumentationContextProvider restDocumentationContextProvider) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(documentationConfiguration(restDocumentationContextProvider))
				.build();
	}

	@Test
	public void getBoardArticleAll() throws Exception {
		mockMvc.perform(
				get("/board/{boardId}/article", TEST_BOARD_ID))
				.andExpect(status().isOk())
				.andDo(print())
				.andDo(document("getBoardArticleAll"))
		;
	}

	@Test
	public void getBoardArticle() throws Exception {
		mockMvc.perform(
				get("/board/{boardId}/article/{articleId}",
						TEST_BOARD_ID, TEST_ARTICLE_ID))
				.andExpect(status().isOk())
				.andDo(print())
				.andDo(document("getBoardArticle"))
		;
	}

	@Test
	void postBoardArticle() throws Exception {
		BoardArticleDto dto = new BoardArticleDto();
		dto.setTitle("junit test title");
		dto.setContents("junit test contents");
		dto.setNoticeYn("N");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(
				post("/board/{boardId}/article", TEST_BOARD_ID)
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
		)
				.andExpect(status().isOk())
				.andDo(print())
				.andDo(document("postBoardArticle"))
		;
	}

}