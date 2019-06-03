package org.yejin.letter;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yejin.book.chap11.Member;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class LetterController {

	@Autowired
	LetterDao letterDao;
	
	
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
		int totalCount = letterDao.getLettersCount();
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("letterReceive", letterReceive);
	}
	
	/**
	 * 보낸 목록
	 */
	@GetMapping("/letter/send")
	public void letterSend(
			@RequestParam(value = "page", defaultValue = "1") int page,
			Model model) {

		// 페이지당 행의 수와 페이지의 시작점
		final int COUNT = 100;
		int offset = (page - 1) * COUNT;

		List<Letter> letterSend = letterDao.sendletters(offset, COUNT);
		int totalCount = letterDao.getLettersCount();
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("letterSend", letterSend);
	}
	
	/**
	 * 편지쓰기 화면
	 */
	@GetMapping("/letter/writeLetter")
	public String letterWriteLetter(HttpSession session) {
		/** // 세션에 MEMBER가 있는 지 확인
		Object memberObj = session.getAttribute("MEMBER");
		if (memberObj == null)
			// 세션에 MEMBER가 없으면 로그인 화면으로
			return "./login/loginForm";
			*/
		
		// 글쓰기 화면으로
		return "letter/writeLetter";
	}

	
}
