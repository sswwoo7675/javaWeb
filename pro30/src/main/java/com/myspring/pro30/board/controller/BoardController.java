package com.myspring.pro30.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface BoardController {
	public ModelAndView listArticles(@RequestParam(value = "section", required = false) String _section,
			@RequestParam(value = "pageNum", required = false) String _pageNum) throws Exception;

	public ModelAndView viewArticle(@RequestParam("articleNO") int articleNO, HttpServletRequest request) throws Exception;
}