package kr.re.kari.portal.board.controller;

import kr.re.kari.portal.board.mapper.BoardArticleMapper;
import kr.re.kari.portal.board.model.BoardArticle;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/board/{boardId}/article")
@RequiredArgsConstructor
@Log4j2
public class BoardController {

	private final BoardArticleMapper boardArticleMapper;

	@GetMapping
	public List<BoardArticle> getBoardAll(@PathVariable Long boardId) {
		return boardArticleMapper.findAll(boardId);
	}

	@GetMapping("/{articleId}")
	public BoardArticle getBoard(@PathVariable Long articleId) {
		return boardArticleMapper.findById(articleId);
	}

	@PostMapping()
	public void postBoard(BoardArticle board) {

	}
}
