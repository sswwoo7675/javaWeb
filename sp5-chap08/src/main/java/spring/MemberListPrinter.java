package spring;

import java.util.Collection;

public class MemberListPrinter {

	private MemberDAO memberDao;
	private MemberPrinter printer;

	public MemberListPrinter(MemberDAO memberDao, MemberPrinter printer) {
		this.memberDao = memberDao;
		this.printer = printer;
	}

	public void printAll() {
		Collection<Member> members = memberDao.selectAll();
		members.forEach(m -> printer.print(m));
	}
}
