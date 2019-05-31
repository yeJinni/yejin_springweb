package org.yejin.article;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yejin.book.chap11.Member;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.dao.DuplicateKeyException;


@Controller
public class ArticleController {

	@Autowired
	ArticleDao articleDao;
	
	Logger logger = LogManager.getLogger();

	/**
	 * 글 목록
	 */
	@GetMapping("/article/list")
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

	/**
	 * 글 보기
	 */
	@GetMapping("/article/view")
	public void articleView(@RequestParam("articleId") String articleId,
			Model model) {
		Article article = articleDao.getArticle(articleId);
		model.addAttribute("article", article);
	}

	/**
	 * 글 등록 화면
	 */
	@GetMapping("/article/addForm")
	public String articleAddForm(HttpSession session) {
		/** // 세션에 MEMBER가 있는 지 확인
		Object memberObj = session.getAttribute("MEMBER");
		if (memberObj == null)
			// 세션에 MEMBER가 없으면 로그인 화면으로
			return "./login/loginForm";
			*/
		
		// 글쓰기 화면으로
		return "article/addForm";
	}

	/**
	 * 글 등록
	 */
	@PostMapping("/article/add")
	public String articleAdd(Article article, @SessionAttribute("MEMBER")Member member) {
		/** // 세션에 MEMBER가 있는 지 확인
		Object memberObj = session.getAttribute("MEMBER");
		if (memberObj == null)
			// 세션에 MEMBER가 없으면 로그인 화면으로
			return "./login/loginForm";
		*/

		// 아이디와 이름을 세션의 값으로 사용
		//Member member = (Member) memberObj;
		
		article.setUserId(member.getMemberId());
		article.setName(member.getName());
		articleDao.addArticle(article);
		return "redirect:/app/article/list";
	}
	
	/**
	 * 글 수정 화면
	 */
	@GetMapping("/article/updateForm")
	public String updateForm(@RequestParam(value="articleId") String articleId,
								@SessionAttribute("MEMBER") Member member, Model model) {
		Article article = articleDao.getArticle(articleId);
		
		//권한 체크: 세션의 멤버아이디와 글의 유저아이디를 비교
		if(!member.getMemberId().equals(article.getUserId()))
			return "redirect:/app/article/view?articleId="+articleId;
		model.addAttribute("article",article);
		
		// 글 수정 화면으로
		return "article/updateForm";
	}
	
	/**
	 * 글 수정
	 */
	@PostMapping("/article/update")
	public String revise(Article article,
			@SessionAttribute("MEMBER") Member member) {
		article.setUserId(member.getMemberId());
		int updatedRows = articleDao.updateArticle(article);

		// 권한 체크 : 글이 수정되었는지 확인
		if (updatedRows == 0)
			// 글이 수정되지 않음. 자신이 쓴 글이 아님
			throw new RuntimeException("No Authority!");

		return "redirect:/app/article/view?articleId=" + article.getArticleId();
	}
	
	/**
	 * 글 삭제
	 */
	@GetMapping("/article/delete")
	public String delete(
			@RequestParam(value="articleId") String articleId,
			@SessionAttribute("MEMBER") Member member)
	{
		Article article = articleDao.getArticle(articleId);
		if(!member.getMemberId().equals(article.getUserId()))
			return "redirect:/app/article/view?articleId="+articleId;
		articleDao.deleteArticle(articleId, member.getMemberId());
		return "redirect:/app/article/list";
}
	
}