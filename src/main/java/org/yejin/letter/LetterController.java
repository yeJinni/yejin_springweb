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
			@SessionAttribute("MEMBER") Member member, Model model) {

		// 페이지당 행의 수와 페이지의 시작점
		final int COUNT = 100;
		int offset = (page - 1) * COUNT;

		List<Letter> letters = letterDao.receiveLetters(member.getMemberId(), offset, COUNT);
		int totalCount = letterDao.countLettersReceive(member.getMemberId());
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("letters", letters);
	}
	
	/**
	 * 보낸 목록
	 */
	@GetMapping("/letter/send")
	public void letterSend(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@SessionAttribute("MEMBER") Member member, Model model) {

		// 페이지당 행의 수와 페이지의 시작점
		final int COUNT = 100;
		int offset = (page - 1) * COUNT;

		List<Letter> letters = letterDao.sendLetters(member.getMemberId(), offset, COUNT);
		int totalCount = letterDao.countLettersSend(member.getMemberId());
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("letters", letters);
	}
	
	/**
	 * 편지 읽기
	 */
	@GetMapping("/letter/read")
	public void letterRead(@RequestParam("letterId") String letterId,
			@SessionAttribute("MEMBER") Member member, Model model) {
		// 자신의 편지 아닐 경우 EmptyResultDataAccessException 발생
		Letter letter = letterDao.getLetters(letterId, member.getMemberId());
		model.addAttribute("letter", letter);
	}
	

	
	/**
	 * 편지 쓰기
	 */
	@PostMapping("/letter/write")
	public String write(Letter letter, @SessionAttribute("MEMBER")Member member) {
		/** // 세션에 MEMBER가 있는 지 확인
		Object memberObj = session.getAttribute("MEMBER");
		if (memberObj == null)
			// 세션에 MEMBER가 없으면 로그인 화면으로
			return "./login/loginForm";
		*/

		// 아이디와 이름을 세션의 값으로 사용
		//Member member = (Member) memberObj;
		
		letter.setSenderId(member.getMemberId());
		letter.setSenderName(member.getName());
		letterDao.addLetters(letter);
		return "redirect:/app/letter/send";
	}
	
	/**
	 * 편지 삭제
	 */
	@GetMapping("/letter/deleteLetter")
	public String letterDeleteLetter(
			@RequestParam(value = "mode", required = false) String mode,
			@RequestParam(value="letterId") String letterId,
			@SessionAttribute("MEMBER") Member member)
	{
		int updatedRows = letterDao.deleteLetters(letterId,
				member.getMemberId());
		if (updatedRows == 0)
			// 자신의 편지가 아닐 경우 삭제되지 않음
			throw new RuntimeException("No Authority!");
		
		if ("SENT".equals(mode))
			return "redirect:/app/letter/letterSend";
		else
			return "redirect:/app/letter/letterReceive";
}

	
}
