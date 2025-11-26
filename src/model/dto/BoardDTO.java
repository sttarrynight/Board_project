package model.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
public class BoardDTO {

	@NonNull
	private Integer board_id; //PK
	@NonNull
	private String title;
	@NonNull
	private String content;
	@NonNull
	private Date created_at;
	private Date updated_at;
	@NonNull
	private Integer user_id; //FK
}
