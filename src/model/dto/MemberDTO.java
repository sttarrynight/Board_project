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
public class MemberDTO {

	@NonNull
	private Integer user_id; //PK
	@NonNull
	private String username;
	@NonNull
	private String password;
	@NonNull
	private String phone;
	@NonNull
	private Date created_at;
	
}
