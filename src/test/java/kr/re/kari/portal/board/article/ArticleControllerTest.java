package kr.re.kari.portal.board.article;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.re.kari.portal.board.CustomRestDocumentationResultHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
// @AutoConfigureMockMvc
class ArticleControllerTest {

	private static final Long TEST_BOARD_ID = 1L;

	private static final Long TEST_ARTICLE_ID = 1L;

	private RestDocumentationResultHandler handler;

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

		this.handler = new CustomRestDocumentationResultHandler().getHandler();
	}

	@Test
	public void getArticleAll() throws Exception {
		ArticleSearchDto dto = new ArticleSearchDto();
		dto.setUseYn("Y");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(
				get("/boards/{boardId}/articles", TEST_BOARD_ID)
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaTypes.HAL_JSON)
		)
				.andExpect(status().isOk())
				.andDo(print())
				.andDo(this.handler.document(
						pathParameters(
								parameterWithName("boardId").description("게시판ID")
						),
						responseFields(
								subsectionWithPath("_embedded.articleList").description("<<get-article, 게시물 상세>>"),
								subsectionWithPath("_links").description("<<get-article-links, 게시물 상세>>").optional()
						),
						links(
								linkWithRel("self").description("<<get-article-all, 게시물 조회>>")
						)
				))
		;
	}

	@Test
	public void postArticle() throws Exception {
		ArticleFormDto dto = new ArticleFormDto();
		dto.setTitle("junit test title");
		dto.setContents("junit test contents");
		dto.setNoticeYn("N");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(
				post("/boards/{boardId}/articles", TEST_BOARD_ID)
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaTypes.HAL_JSON)
		)
				.andExpect(status().isCreated())
				.andDo(print())
				.andDo(this.handler.document(
						pathParameters(
								parameterWithName("boardId").description("게시판ID")
						),
						requestFields(
								fieldWithPath("noticeYn").type(JsonFieldType.STRING).description("공지여부"),
								fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
								fieldWithPath("contents").type(JsonFieldType.STRING).description("내용")
						),
						responseFields(
								fieldWithPath("articleId").type(JsonFieldType.NUMBER).description("게시물ID"),
								fieldWithPath("parentArticleId").type(JsonFieldType.NUMBER).description("부모게시물ID"),
								fieldWithPath("boardId").type(JsonFieldType.NUMBER).description("게시판ID"),
								fieldWithPath("noticeYn").type(JsonFieldType.STRING).description("공지여부"),
								fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
								fieldWithPath("contents").type(JsonFieldType.STRING).description("내용"),
								fieldWithPath("readCount").type(JsonFieldType.NUMBER).description("조회수"),
								fieldWithPath("useYn").type(JsonFieldType.STRING).description("사용여부"),
								fieldWithPath("registerId").type(JsonFieldType.STRING).description("등록자"),
								fieldWithPath("registerTimestamp").type(JsonFieldType.STRING).description("등록일시"),
								fieldWithPath("updateId").type(JsonFieldType.NULL).description("수정자"),
								fieldWithPath("updateTimestamp").type(JsonFieldType.NULL).description("수정일시"),
								subsectionWithPath("_links").description("<<get-article-all-link, 게시물 조회>>")
						),
						links(
								linkWithRel("self").description("<<get-article, 게시물 상세>>"),
								linkWithRel("articles").description("<<get-article-all, 게시물 조회>>")
						)
				))
		;
	}

	@Test
	public void getArticle() throws Exception {
		mockMvc.perform(
				get("/boards/{boardId}/articles/{articleId}",
						TEST_BOARD_ID, TEST_ARTICLE_ID)
						.accept(MediaTypes.HAL_JSON)
		)
				.andExpect(status().isOk())
				.andDo(print())
				.andDo(this.handler.document(
						pathParameters(
								parameterWithName("boardId").description("게시판ID"),
								parameterWithName("articleId").description("게시물ID")
						),
						responseFields(
								fieldWithPath("articleId").type(JsonFieldType.NUMBER).description("게시물ID"),
								fieldWithPath("parentArticleId").type(JsonFieldType.NUMBER).description("부모게시물ID"),
								fieldWithPath("boardId").type(JsonFieldType.NUMBER).description("게시판ID"),
								fieldWithPath("noticeYn").type(JsonFieldType.STRING).description("공지여부"),
								fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
								fieldWithPath("contents").type(JsonFieldType.STRING).description("내용"),
								fieldWithPath("readCount").type(JsonFieldType.NUMBER).description("조회수"),
								fieldWithPath("useYn").type(JsonFieldType.STRING).description("사용여부"),
								fieldWithPath("registerId").type(JsonFieldType.STRING).description("등록자"),
								fieldWithPath("registerTimestamp").type(JsonFieldType.STRING).description("등록일시"),
								fieldWithPath("updateId").type(JsonFieldType.VARIES).description("수정자").optional(),
								fieldWithPath("updateTimestamp").type(JsonFieldType.VARIES).description("수정일시").optional(),
								subsectionWithPath("_links").description("<<get-article-all-link, 게시물 조회>>")
						),
						links(
								linkWithRel("self").description("<<get-article, 게시물 상세>>"),
								linkWithRel("articles").description("<<get-article-all, 게시물 조회>>")
						)
				))
		;
	}

	@Test
	public void putArticle() throws Exception {
		ArticleFormDto dto = new ArticleFormDto();
		dto.setTitle("junit test title change");
		dto.setContents("junit test contents change");
		dto.setNoticeYn("Y");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(
				put("/boards/{boardId}/articles/{articleId}",
						TEST_BOARD_ID, TEST_ARTICLE_ID)
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaTypes.HAL_JSON)
		)
				.andExpect(status().isCreated())
				.andDo(print())
				.andDo(this.handler.document(
						pathParameters(
								parameterWithName("boardId").description("게시판ID"),
								parameterWithName("articleId").description("게시물ID")
						),
						requestFields(
								fieldWithPath("noticeYn").type(JsonFieldType.STRING).description("공지여부"),
								fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
								fieldWithPath("contents").type(JsonFieldType.STRING).description("내용")
						),
						responseFields(
								fieldWithPath("articleId").type(JsonFieldType.NUMBER).description("게시물ID"),
								fieldWithPath("parentArticleId").type(JsonFieldType.NUMBER).description("부모게시물ID"),
								fieldWithPath("boardId").type(JsonFieldType.NUMBER).description("게시판ID"),
								fieldWithPath("noticeYn").type(JsonFieldType.STRING).description("공지여부"),
								fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
								fieldWithPath("contents").type(JsonFieldType.STRING).description("내용"),
								fieldWithPath("readCount").type(JsonFieldType.NUMBER).description("조회수"),
								fieldWithPath("useYn").type(JsonFieldType.STRING).description("사용여부"),
								fieldWithPath("registerId").type(JsonFieldType.STRING).description("등록자"),
								fieldWithPath("registerTimestamp").type(JsonFieldType.STRING).description("등록일시"),
								fieldWithPath("updateId").type(JsonFieldType.STRING).description("수정자"),
								fieldWithPath("updateTimestamp").type(JsonFieldType.STRING).description("수정일시"),
								subsectionWithPath("_links").description("<<get-article-all-link, 게시물 조회>>")
						),
						links(
								linkWithRel("self").description("<<get-article, 게시물 상세>>"),
								linkWithRel("articles").description("<<get-article-all, 게시물 조회>>")
						)
				))
		;
	}


	@Test
	public void deleteArticle() throws Exception {
		mockMvc.perform(
				delete("/boards/{boardId}/articles/{articleId}",
						TEST_BOARD_ID, TEST_ARTICLE_ID)
						.accept(MediaType.APPLICATION_JSON)
		)
				.andExpect(status().isNoContent())
				.andDo(print())
				.andDo(this.handler.document(
						pathParameters(
								parameterWithName("boardId").description("게시판ID"),
								parameterWithName("articleId").description("게시물ID")
						)
				))
		;
	}
}