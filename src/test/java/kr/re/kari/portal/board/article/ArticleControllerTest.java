package kr.re.kari.portal.board.article;

import kr.re.kari.portal.board.ControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticleControllerTest extends ControllerTest {

	private static final Long TEST_BOARD_ID = 1L;

	private static final Long TEST_ARTICLE_ID = 1L;

	@BeforeEach
	public void setUp() {
	}

	@Test
	@DisplayName("게시물 조회")
	public void getArticleAll() throws Exception {
		ArticleSearchDto dto = new ArticleSearchDto();
		dto.setUseYn("Y");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(
				get("/articles")
						.queryParam("page", "2")
						.queryParam("size", "2")
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaTypes.HAL_JSON)
		)
				.andExpect(status().isOk())
				.andDo(print())
				.andDo(document("get-article-all",
						requestFields(
								fieldWithPath("page").type(JsonFieldType.NUMBER).description("페이지번호").optional(),
								fieldWithPath("size").type(JsonFieldType.NUMBER).description("페이지사이즈").optional()
						),
						responseFields(
								subsectionWithPath("_embedded.articleList").type(JsonFieldType.VARIES).description("<<get-article, 게시물 상세>>").optional(),
								subsectionWithPath("_links").type(JsonFieldType.VARIES).description("페이징"),
								subsectionWithPath("page").description("페이지정보")
						),
						links(
								linkWithRel("self").description("<<get-article-all, 게시물 조회>>"),
								linkWithRel("first").description("first page url"),
								linkWithRel("prev").description("prev page url").optional(),
								linkWithRel("next").description("next page url").optional(),
								linkWithRel("last").description("last page url")
						)
				))
		;
	}

	@Test
	@DisplayName("게시물 조회 조회조건 없는 경우")
	public void getArticleAll_withoutRequestBody() throws Exception {
		mockMvc.perform(
				get("/articles")
						.accept(MediaTypes.HAL_JSON)
		)
				.andExpect(status().isNotFound())
				.andDo(print())
		;
	}

	@Test
	@DisplayName("게시물 조회 조회조건에 페이징정보 경우")
	public void getArticleAll_withoutPagingInfo() throws Exception {
		ArticleSearchDto dto = new ArticleSearchDto();
		dto.setUseYn("Y");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(
				get("/articles")
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaTypes.HAL_JSON)
		)
				.andExpect(status().isOk())
				.andDo(print())
		;
	}

	@Test
	@DisplayName("게시물 등록")
	public void postArticle() throws Exception {
		ArticleFormDto dto = new ArticleFormDto();
		dto.setBoardId(TEST_BOARD_ID);
		dto.setTitle("junit test title");
		dto.setContents("junit test contents");
		dto.setNoticeYn("N");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(
				post("/articles")
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaTypes.HAL_JSON)
		)
				.andExpect(status().isCreated())
				.andDo(print())
				.andDo(document("post-article",
						requestFields(
								fieldWithPath("boardId").type(JsonFieldType.NUMBER).description("게시판ID"),
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
	@DisplayName("제목과 내용없이 게시물 등록")
	public void postArticleInvalidRequest() throws Exception {
		ArticleFormDto dto = new ArticleFormDto();
		dto.setBoardId(TEST_BOARD_ID);
		dto.setTitle(" ");
		dto.setNoticeYn("N");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(
				post("/articles")
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaTypes.HAL_JSON)
		)
				.andExpect(status().isBadRequest())
				.andDo(print())
		;
	}

	@Test
	@DisplayName("게시물 상세")
	public void getArticle() throws Exception {
		mockMvc.perform(
				get("/articles/{articleId}", TEST_ARTICLE_ID)
						.accept(MediaTypes.HAL_JSON)
		)
				.andExpect(status().isOk())
				.andDo(print())
				.andDo(document("get-article",
						pathParameters(
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
	@DisplayName("게시물 수정")
	public void putArticle() throws Exception {
		ArticleFormDto dto = new ArticleFormDto();
		dto.setTitle("junit test title change");
		dto.setContents("junit test contents change");
		dto.setNoticeYn("Y");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(
				put("/articles/{articleId}", TEST_ARTICLE_ID)
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaTypes.HAL_JSON)
		)
				.andExpect(status().isCreated())
				.andDo(print())
				.andDo(document("put-article",
						pathParameters(
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
	@DisplayName("제목과 내용없이 게시물 수정")
	public void purArticleInvalidRequest() throws Exception {
		ArticleFormDto dto = new ArticleFormDto();
		dto.setTitle(" ");
		dto.setNoticeYn("Y");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(
				put("/articles/{articleId}", TEST_ARTICLE_ID)
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaTypes.HAL_JSON)
		)
				.andExpect(status().isBadRequest())
				.andDo(print())
		;
	}

	@Test
	@DisplayName("게시물 삭제")
	public void deleteArticle() throws Exception {
		mockMvc.perform(
				delete("/articles/{articleId}", TEST_ARTICLE_ID)
						.accept(MediaType.APPLICATION_JSON)
		)
				.andExpect(status().isNoContent())
				.andDo(print())
				.andDo(document("delete-article",
						pathParameters(
								parameterWithName("articleId").description("게시물ID")
						)
				))
		;
	}

	@Test
	@DisplayName("존재하지않는 게시물ID로 게시물 삭제")
	public void deleteArticleNotExistsArticleId() throws Exception {
		mockMvc.perform(
				delete("/articles/{articleId}", -9999)
						.accept(MediaTypes.HAL_JSON)
		)
				.andExpect(status().isNotFound())
				.andDo(print())
		;
	}
}
