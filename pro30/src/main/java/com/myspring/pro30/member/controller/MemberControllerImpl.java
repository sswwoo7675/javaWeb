package com.myspring.pro30.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myspring.pro30.member.service.MemberService;
import com.myspring.pro30.member.vo.MemberVO;

@Controller("memberController")
public class MemberControllerImpl implements MemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberVO memberVO;
	private static final Logger logger = LoggerFactory.getLogger(MemberControllerImpl.class);

	@RequestMapping(value = { "/", "/main.do" }, method = RequestMethod.GET)
	private ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("main");
	}

	@Override
	@RequestMapping(value = "/member/listMembers.do", method = RequestMethod.GET)
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("/member/listMembers");
		List membersList = memberService.listMembers();
		mav.addObject("membersList", membersList);
		return mav;
	}

	@Override
	@RequestMapping(value = "/member/addMember.do", method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("member") MemberVO member) {
		memberService.addMember(member);
		return new ModelAndView("redirect:/member/listMembers.do");
	}

	@Override
	@RequestMapping(value = "/member/removeMember.do", method = RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id) {
		memberService.removeMember(id);
		return new ModelAndView("redirect:/member/listMembers.do");
	}

	@Override
	@RequestMapping(value = "/member/*Form.do", method = RequestMethod.GET)
	public ModelAndView form(@RequestParam(value = "action", required = false) String action,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String viewName = (String) request.getAttribute("viewName");
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView(viewName);
		return mav;
	}

	@Override
	@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity login(@ModelAttribute("member") MemberVO member, RedirectAttributes rAttr,
			HttpServletRequest request) throws Exception {

		memberVO = memberService.login(member);
		
		String message = "";
		HttpHeaders resHeader = new HttpHeaders();
		resHeader.add("Content-Type", "text/html; charset=utf-8");
		
		if (memberVO != null) {
			HttpSession session = request.getSession();
			
			session.setAttribute("member", memberVO);
			session.setAttribute("isLogOn", true);
			
			String action = (String) session.getAttribute("action");
			session.removeAttribute("action");
			
			String url = "";
			if (action != null) { // 다른곳에서 로그인이 유도
				url = request.getContextPath() + action;
			} else {
				url = request.getContextPath() + "/main.do";
			}
			message = "<script>";
			message += "alert('로그인에 성공하였습니다.');";
			message += "location.href='" + url + "';";
			message += "</script>";
		} else {
			message = "<script>";
			message += "alert('로그인에 실패하였습니다. 아이디나 비밀번호를 확인하세요.');";
			message += "location.href='" + request.getContextPath() + "/member/loginForm.do';";
			message += "</script>";
		}

		return new ResponseEntity(message, resHeader, HttpStatus.OK);

	}

	@RequestMapping(value = "/member/logout.do", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("member");
		session.removeAttribute("isLogOn");
		return new ModelAndView("redirect:/member/listMembers.do");
	}

}
