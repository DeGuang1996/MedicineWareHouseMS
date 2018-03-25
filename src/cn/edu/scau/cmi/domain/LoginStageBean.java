package cn.edu.scau.cmi.domain;

/*
 * 登陆界面数据类，登录使用的表是employee和roleemployee ,判定是不是药品仓库管理员
 * */
public class LoginStageBean {
	private int employeeId;
	private String employeeName;
	private int roleId;
	private String employeeNumber;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

}
