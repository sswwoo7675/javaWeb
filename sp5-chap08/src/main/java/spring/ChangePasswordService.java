package spring;

public class ChangePasswordService {
	private MemberDAO memberDao;
	
	public void setMemberDao(MemberDAO memberDao) {
		this.memberDao = memberDao;
	}
	
	public void changePassword(String email, String oldPwd, String newPwd) {
		Member member = memberDao.selectByEmail(email);
		if(member==null)
			throw new MemberNotFoundException();
		member.changePassword(oldPwd, newPwd);
		memberDao.update(member);
	}
}
