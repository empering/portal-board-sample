package kr.re.kari.portal.board.controller;

import kr.re.kari.portal.board.mapper.BoardArticleMapper;
import kr.re.kari.portal.board.model.BoardArticle;
import kr.re.kari.portal.board.model.BoardArticleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/board/{boardId}/article")
@RequiredArgsConstructor
@Log4j2
public class BoardArticleController {

	private final BoardArticleMapper boardArticleMapper;


	@GetMapping
	public List<BoardArticle> getBoardArticleAll(@PathVariable Long boardId) {
		return boardArticleMapper.findAll(boardId);
	}

	@GetMapping("/{articleId}")
	public BoardArticle getBoardArticle(@PathVariable Long articleId) {
		return boardArticleMapper.findById(articleId);
	}

	@PostMapping
	public ResponseEntity<?> postBoardArticle(
			@PathVariable Long boardId,
			@RequestBody BoardArticleDto boardArticleDto) {

		boardArticleDto.setBoardId(boardId);
		boardArticleMapper.save(boardArticleDto);

		return ResponseEntity.ok().body(boardArticleMapper.findById(boardArticleDto.getArticleId()));
	}

	@PutMapping("/{articleId}")
	public ResponseEntity<?> putBoardArticle(@RequestBody BoardArticle boardArticle) {
		log.debug(boardArticle.toString());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{articleId}")
	public ResponseEntity<?> deleteBoardArticle(@RequestBody BoardArticle boardArticle) {
		log.debug(boardArticle.toString());
		return ResponseEntity.ok().build();
	}
}
