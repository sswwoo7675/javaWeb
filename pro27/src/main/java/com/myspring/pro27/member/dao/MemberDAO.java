package com.myspring.pro27.member.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.myspring.pro27.member.vo.MemberVO;

public interface MemberDAO {
	public List selectAllMemberList() throws DataAccessException;
	public void insertMember(MemberVO memberVO) throws DataAccessException ;
	public void deleteMember(String id) throws DataAccessException;
	public MemberVO loginById(MemberVO memberVO) throws DataAccessException;
}
