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
	static final String RECEIVE_LETTERS = "select letterId, title, content, senderId, senderName, left(cdate,16) cdate from letter where receiverId=? order by letterId desc limit ?,?";
	//보낸
	static final String SEND_LETTERS = "select letterId, title, content, receiverId, receiverName, left(cdate,16) cdate from letter where senderId=? order by letterId desc limit ?,?";
	
	//받은 목록 건수
	static final String COUNT_LETTERS_RECEIVE = "select count(letterId) from letter where receiverId=?";	
	//보낸 목록 건수
	static final String COUNT_LETTERS_SEND = "select count(letterId) from letter where senderId=?";	
		
	//읽기
	static final String GET_LETTERS = "select letterId, title, content, senderId, senderName, left(cdate,16) receiverId, receiverName, left(cdate, 16) cdate from letter where letterId=? and (sendreId=? or receiverId=?)";
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
	public List<Letter> receiveLetters(String receiverId, int offset, int count) {
		return jdbcTemplate.query(RECEIVE_LETTERS, letterRowMapper, receiverId, offset,
				count);
	}
	
	/**
	 * 보낸 목록
	 */
	public List<Letter> sendLetters(String senderId, int offset, int count) {
		return jdbcTemplate.query(SEND_LETTERS, letterRowMapper, senderId, offset,
				count);
	}
	
	/**
	 * 받은 목록 건수
	 */
	public int countLettersReceive(String receiverId) {
		return jdbcTemplate.queryForObject(COUNT_LETTERS_RECEIVE, Integer.class, receiverId);
}
	
	/**
	 * 보낸 목록 건수
	 */
	public int countLettersSend(String senderId) {
		return jdbcTemplate.queryForObject(COUNT_LETTERS_SEND, Integer.class, senderId);
}
	
	/**
	 * 편지 읽기
	 */
	public Letter getLetters(String letterId, String memberId) {
		return jdbcTemplate.queryForObject(GET_LETTERS, letterRowMapper,
				letterId, memberId);
	}

	/**
	 * 편지 쓰기
	 */
	public int addLetters(Letter letter) {
		return jdbcTemplate.update(ADD_LETTERS, letter.getTitle(),
				letter.getContent(), letter.getSenderId(), letter.getSenderName(), letter.getReceiverId(), letter.getReceiverName());
	}
	
	/**
	 * 글 삭제
	 */
	public int deleteLetters(String letterId, String memberId) {
		return jdbcTemplate.update(DELETE_LETTERS, letterId, memberId, memberId);
	}
}

	