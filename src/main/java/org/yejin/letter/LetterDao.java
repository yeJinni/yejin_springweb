package org.yejin.letter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.yejin.letter.Letter;

@Repository
public class LetterDao {
	
	//받은
	static final String RECEIVE_LETTERS = "select letterId, title, content, senderId, senderName, left(cdate,16) cdate from letter where letterId=? and receiverId=?";
	//보낸
	static final String SEND_LETTERS = "select letterId, title, content, receiverId, receiverName, left(cdate,16) cdate from letter where letterId=? and senderId=?";
	//목록 건수
	static final String COUNT_LETTERS = "select count(articleId) from article";	
	//읽기
	static final String GET_LETTERS = "select letterId, title, content, senderId, senderName, left(cdate,16) cdate, udate from letter where letterId=?";
	//추가(쓰기)
	static final String ADD_LETTERS = "insert letter(title, content, senderId, senderName, receiverId, receiverName) values(?,?,?,?,?,?)";
	//삭제
	static final String DELETE_LETTERS = "delete from letter where letterId = ?";
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	RowMapper<Letter> letterRowMapper = new BeanPropertyRowMapper<>(
			Letter.class);

	/**
	 * 받은 목록
	 */
	public List<Letter> receiveletters(int offset, int count) {
		return jdbcTemplate.query(RECEIVE_LETTERS, letterRowMapper, offset,
				count);
	}
	
	/**
	 * 보낸 목록
	 */
	public List<Letter> sendletters(int offset, int count) {
		return jdbcTemplate.query(SEND_LETTERS, letterRowMapper, offset,
				count);
	}
	
	/**
	 * 목록 건수
	 */
	public int getLettersCount() {
		return jdbcTemplate.queryForObject(COUNT_LETTERS, Integer.class);
}
	
	/**
	 * 편지 읽기
	 */
	public Letter getletters(String letterId) {
		return jdbcTemplate.queryForObject(GET_LETTERS, letterRowMapper,
				letterId);
	}

	/**
	 * 편지 쓰기
	 */
	public int addletters(Letter letter) {
		return jdbcTemplate.update(ADD_LETTERS, letter.getTitle(),
				letter.getContent(), letter.getReceiverId(), letter.getReceiverName());
	}
	
	/**
	 * 글 삭제
	 */
	public int deleteletters(String letterId, String senderId) {
		return jdbcTemplate.update(DELETE_LETTERS, letterId, senderId);
	}
}

	