package org.yejin.letter;

public class Letter {
		int letterId;
		String title;
		String content;
		int senderId;
		int receiverId;
		String senderName;
		String receiverName;
		String cdate;
		public int getLetterId() {
			return letterId;
		}
		public void setLetterId(int letterId) {
			this.letterId = letterId;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public int getSenderId() {
			return senderId;
		}
		public void setSenderId(int senderId) {
			this.senderId = senderId;
		}
		public String getSenderName() {
			return senderName;
		}
		public void setSenderName(String senderName) {
			this.senderName = senderName;
		}
		public int getReceiverId() {
			return receiverId;
		}
		public void setReceiverId(int receiverId) {
			this.receiverId = receiverId;
		}
		public String getReceiverName() {
			return receiverName;
		}
		public void setReceiverName(String receiverName) {
			this.receiverName = receiverName;
		}
		public String getCdate() {
			return cdate;
		}
		public void setCdate(String cdate) {
			this.cdate = cdate;
		}
		
		/**
		 * \n를 <br/>
		 * 로 바꾼다.
		 */
		public String getContentHtml() {
			if (content != null)
				return content.replace("\n", "<br/>");
			return null;
		}

		@Override
		public String toString() {
			return "\nArticle [senderId=" + senderId + ", title=" + title
					+ ", content=" + content + ", receiverId=" + receiverId + ", senderName="
					+ senderName + ", receiverName=" + receiverName+ ", cdate=" + cdate + "]";
		}
		
}
