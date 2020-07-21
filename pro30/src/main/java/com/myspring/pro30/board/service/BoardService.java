package com.myspring.pro30.board.service;

import java.util.Map;

public interface BoardService {
	public Map listArticles(Map<String, Integer> pagingMap) throws Exception;
}
