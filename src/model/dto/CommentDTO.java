package model.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
@ToString
public class CommentDTO {

	@NonNull
	private Integer comment_id; //PK
	@NonNull
	private String content;
	@NonNull
	private Date created_at;
	
	@NonNull
	private Integer board_id; //FK
	@NonNull
	private Integer user_id; //FK

	@NonNull
	private String username;
}
