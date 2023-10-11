package com.movie.web.join;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;


@Data
public class JoinDTO {
	
	
	
    @NotBlank(message = "아이디는 필수 항목입니다.")
    @Pattern(regexp = "^(?=.*[a-z]).{5,}$",
	   message = "아이디는 소문자를 포함하여 5글자 이상으로 입력해야합니다.")
    private String id;


    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{6,20}",
            message = "비밀번호는 영문 대,소문자와 숫자가 적어도 1개 이상씩 포함된 6자 ~ 20자의 비밀번호여야 합니다.")
    private String pw;
    
    
    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;
    
    @NotBlank(message = "닉네임은 필수 항목입니다.")
    @Size(max = 10, message = "닉네임은 10글자 이내로 입력해주세요.")
    private String nickName;
    
    @NotBlank(message = "성별은 필수 항목입니다.")
    private String gender;
    
    
    // 생년월일 관련 필드 추가
    @NotBlank(message = "년도는 필수 항목입니다.")
    @Pattern(regexp = "^(19[0-9][0-9]|200[0-7])$",
            message = "4자리 숫자를 입력해주세요. 가입은 1900년~2007년생까지 할 수 있습니다.")
    private String birthYear;

    @NotBlank(message = "월은 필수 항목입니다.")
    private String birthMonth;
    
    
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])$",
            message = "정확한 날짜를 입력해주세요. 한자리일 경우 앞에 0을 붙여주세요.")
    @NotBlank(message = "일은 필수 항목입니다.")
    private String birthDay;
    
    @NotBlank
    private String birth; // 합쳐진 생년월일을 저장할 필드

    
    // 생성자에서 생년월일을 합친 값을 설정
	public JoinDTO() { this.birth = this.birthYear+this.birthMonth+this.birthDay;
	}

    
}
    	

