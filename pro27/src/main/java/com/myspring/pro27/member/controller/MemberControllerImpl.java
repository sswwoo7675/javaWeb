package com.myspring.pro27.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myspring.pro27.member.service.MemberService;
import com.myspring.pro27.member.vo.MemberVO;

@Controller("memberController")
public class MemberControllerImpl implements MemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberVO memberVO;
	private static final Logger logger = LoggerFactory.getLogger(MemberControllerImpl.class);
	
	@Override
	@RequestMapping(value = "/member/listMembers.do", method = RequestMethod.GET)
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("/member/listMembers");
		List membersList  = memberService.listMembers();
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
	@RequestMapping(value="/member/*Form.do", method = RequestMethod.GET)
	public ModelAndView form(@RequestParam(value="result", required = false) String result,
							HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView(getViewName(request)).addObject("result", result);
	}
	
	@Override
	@RequestMapping(value="/member/login.do", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("member") MemberVO member, RedirectAttributes rAttr, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		memberVO = memberService.login(member);
		if(memberVO != null) {
			HttpSession session = request.getSession();
			session.setAttribute("member", memberVO);
			session.setAttribute("isLogOn", true);
			mav.setViewName("redirect:/member/listMembers.do");
		} else {
			rAttr.addAttribute("result", "loginFailed");
			mav.setViewName("redirect:/member/loginForm.do");
		}
		return mav;
	}
	
	@RequestMapping(value="/member/logout.do", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		session.removeAttribute("member");
		session.removeAttribute("isLogOn");
		return new ModelAndView("redirect:/member/listMembers.do");
	}
	
	
	private String getViewName(HttpServletRequest request) throws Exception {
		String contextPath = request.getContextPath();
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		if (uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}

		int begin = 0;
		if (!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}

		int end;
		if (uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}

		String viewName = uri.substring(begin, end);
		if (viewName.indexOf(".") != -1) {
			viewName = viewName.substring(0, viewName.lastIndexOf("."));
		}
		if (viewName.lastIndexOf("/") != -1) {
			viewName = viewName.substring(viewName.lastIndexOf("/", 1), viewName.length());
		}
		return viewName;
	}


}
