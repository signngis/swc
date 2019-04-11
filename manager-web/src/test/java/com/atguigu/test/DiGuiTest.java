package com.atguigu.test;


public class DiGuiTest {
	
//	private List<Menu> selectChildMenu;
//
//	public void test01() {
//		Menu menu = new Menu(1,"父菜单",0);
//		selectChildMenu = selectChildMenu(menu);
//	}
//	
//	//查詢子菜單
//	//  最底层的子菜单
//	public List<Menu> selectChildMenu(Menu menu) {
//			//当前菜单没有子菜单结束
//			List<Menu> menus = selectChildMenu(menu);
//			
//			
//			
//			if(menus==null||menus.size()==0) {
//				return null;
//			}
//			
//			//子菜单  每次把自己的东西已保存即可
//			return menu.setChild(menus);
//	}
	
	

}

class Menu{
	private Integer id;
	private String name;
	private Integer pid;
	
	
	
	public Menu(Integer id, String name, Integer pid) {
		super();
		this.id = id;
		this.name = name;
		this.pid = pid;
	}

	public Menu() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	
}

	