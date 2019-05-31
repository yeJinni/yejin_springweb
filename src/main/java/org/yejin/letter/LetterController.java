package org.yejin.letter;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yejin.article.Article;
import org.yejin.article.ArticleDao;
import org.yejin.book.chap11.Member;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.dao.DuplicateKeyException;

public class LetterController {

	@Autowired
	LetterDao letterDao;
	
	Logger logger = LogManager.getLogger();
	
	/**
	 * 받은 목록
	 */
	@GetMapping("/letter/receive")
	public void letterReceive(
			@RequestParam(value = "page", defaultValue = "1") int page,
			Model model) {

		// 페이지당 행의 수와 페이지의 시작점
		final int COUNT = 100;
		int offset = (page - 1) * COUNT;

		List<Letter> letterReceive = letterDao.receiveletters(offset, COUNT);
		int totalCount = letterDao.getArticlesCount();
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleList", articleList);
	}
	
	/**
	 * 보 목록
	 */
	@GetMapping("/article/send")
	public void articleList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			Model model) {

		// 페이지당 행의 수와 페이지의 시작점
		final int COUNT = 100;
		int offset = (page - 1) * COUNT;

		List<Article> articleList = articleDao.listArticles(offset, COUNT);
		int totalCount = articleDao.getArticlesCount();
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleList", articleList);
	}
	
}
