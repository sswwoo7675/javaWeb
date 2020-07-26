package com.myspring.pro30.board.service;

import java.util.Map;

import com.myspring.pro30.board.vo.ArticleVO;

public interface BoardService {
	public Map listArticles(Map<String, Integer> pagingMap) throws Exception;
	public int addNewArticle(Map articleMap) throws Exception;
	public ArticleVO viewArticle(int articleNO) throws Exception;
}
