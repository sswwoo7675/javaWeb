package com.myspring.pro27.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.pro27.member.dao.MemberDAO;
import com.myspring.pro27.member.vo.MemberVO;

@Service("memberService")
@Transactional(propagation = Propagation.REQUIRED)
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDAO memberDAO;
	
	
	@Override
	public List listMembers() throws DataAccessException {
		List membersList = null;
		membersList = memberDAO.selectAllMemberList();
		return membersList;
	}

	@Override
	public void addMember(MemberVO memberVO) throws DataAccessException {
		memberDAO.insertMember(memberVO);
	}

	@Override
	public void removeMember(String id) throws DataAccessException {
		memberDAO.deleteMember(id);
	}
	
	@Override
	public MemberVO login(MemberVO memberVO) throws Exception {
		return memberDAO.loginById(memberVO);
	}

}
