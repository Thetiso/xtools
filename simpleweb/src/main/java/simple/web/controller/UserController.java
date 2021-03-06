package simple.web.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import simple.framework.annotation.Action;
import simple.framework.annotation.Autowired;
import simple.framework.annotation.Controller;
import simple.framework.bean.Data;
import simple.framework.bean.Param;
import simple.framework.bean.View;
import simple.framework.util.IOUtil;
import simple.web.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Action("get:/users")
	public Data getUsers() {
		return new Data(userService.getUsers(null));
	}
	
	@Action("get:/toUsers")
	public View toUsers(Param param) {
		View view = new View("user.jsp");
		view.addModel("users", userService.getUsers(null));
		return view;
	}
	
	@Action("post:/createUser")
	public Data createUser(Param param) throws FileNotFoundException {
		IOUtil.copy(param.getFile("file").getInputStream(), new FileOutputStream(new File("C:/Users/dell/Desktop/a.jpg")));
		return new Data(userService.createUser(param.getFieldMap()));
	}
	
}
