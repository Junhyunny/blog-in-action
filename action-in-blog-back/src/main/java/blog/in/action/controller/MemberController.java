package blog.in.action.controller;

import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import blog.in.action.annotation.TokenMember;
import blog.in.action.entity.Member;
import blog.in.action.service.MemberService;

@RestController
@RequestMapping(value = "/api/member")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@PostMapping("/sign-up")
	@Transactional(propagation = Propagation.REQUIRED)
	public void requestSignUp(@RequestBody Member member) {
		memberService.registMember(member);
	}

	@GetMapping("/user-info")
	public Member requestUserInfo(@RequestParam("id") String id) {
		return memberService.findById(id);
	}

	@GetMapping("/user-info-using-token")
	public Member requestUserInfoUsingToken(@TokenMember Member member) {
		return memberService.findById(member.getId());
	}

	@PostMapping(value = "/upload/profile-img")
	public @ResponseBody String requestUploadFile(@RequestParam("fileList") List<MultipartFile> fileList) {
		try {
			for (MultipartFile multipartFile : fileList) {
				FileOutputStream writer = new FileOutputStream("./images/" + multipartFile.getOriginalFilename());
				writer.write(multipartFile.getBytes());
				writer.close();
			}
		} catch (Exception e) {
			return "upload fail";
		}
		return "upload success";
	}
}