/************************************************************
Copyright (C), 1988-1999, Huawei Tech. Co., Ltd.
FileName: test.cpp
Author: Version : Date:
Description: // 模块描述
Version: // 版本信息
Function List: // 主要函数及其功能
1. -------
History: // 历史修改记录
<author> <time> <version > <desc>
David 96/10/12 1.0 build this moudle
 ***********************************************************/

package wzd.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import wzd.pageModel.Json;
import wzd.pageModel.SessionInfo;
import wzd.pageModel.User;
import wzd.service.IUserService;
import wzd.util.ConfigUtil;
import wzd.util.UtilValidate;

import com.opensymphony.xwork2.ModelDriven;

@Namespace("/")
@Action(value = "userAction")
public class UserAction extends BaseAction implements ModelDriven<User> {
	private User user = new User();

	@Override
	public User getModel() {
		return user;
	}

	private static final Logger logger = Logger.getLogger(UserAction.class);

	private IUserService userService;

	public IUserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void checkUserOnSession() {
		SessionInfo sessionInfo = null;
		File file = new File("e:/user_info.txt");
		if(file.exists()){
			BufferedReader br;
	        String temp = null;
			try {
				br = new BufferedReader(new FileReader(file));
				temp = br.readLine();
		        br.close();
		        file.delete();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			User u = userService.getUserNameFromPi(temp);
			if(null!=u){
				sessionInfo = new SessionInfo();
				sessionInfo.setName(u.getName());
				sessionInfo.setId(u.getId());
				ServletActionContext.getRequest().getSession().setAttribute(ConfigUtil.getSessionInfoName(), sessionInfo);
			}
		}else{
			Object obj = ServletActionContext.getRequest().getSession().getAttribute(ConfigUtil.getSessionInfoName());
			if(null!=obj){
				sessionInfo = (SessionInfo)obj;
			}
		}
		Json j = new Json();
		if(null!=sessionInfo&&UtilValidate.isNotEmpty(sessionInfo.getId())){
			j.setMsg(sessionInfo.getId()+"_"+sessionInfo.getName());
		}else{
			j.setMsg("");
		}
		super.writeJson(j);
	}
	
	public void saveUserNameToSession() {
		File file=new File("e:/user_info.txt");
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			FileOutputStream out;
			out = new FileOutputStream(file,true);
			StringBuffer sb=new StringBuffer();
	        sb.append(user.getName());
	        out.write(sb.toString().getBytes("utf-8"));
	        out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Json j = new Json();
		j.setMsg(user.getName());
		super.writeJson(j);
	}
	
	public void CheckIn() {
		Json j = new Json();
		User u = userService.login2(user);
		if(UtilValidate.isNotEmpty(u.getId())){
			SessionInfo sessionInfo = new SessionInfo();
			sessionInfo.setName(u.getName());
			sessionInfo.setId(u.getId());
			ServletActionContext.getRequest().getSession().setAttribute(ConfigUtil.getSessionInfoName(), sessionInfo);
		}
		if(null!=u&&UtilValidate.isNotEmpty(u.getId())){
			j.setMsg(u.getId()+"_"+u.getName());
			j.setSuccess(true);
		}else{
			j.setSuccess(false);
		}
		super.writeJson(j);
	}
	
	public void datagrid() {
		super.writeJson(userService.datagrid(user));
	}

	public void add() {
		Json j = new Json();
		User u = userService.save(user);
		j.setSuccess(true);
		j.setMsg("添加成功！");
		j.setObj(u);
		super.writeJson(j);

	}

	public void remove() {
		userService.remove(user.getIds());
		Json j = new Json();
		j.setSuccess(true);
		j.setMsg("删除成功！");
		super.writeJson(j);
	}

	public void checkDuplicateUser() {
		String name = user.getName();
		boolean checkResult = userService.checkDuplicateUser(name);
		Json j = new Json();
		if (checkResult) {
			j.setSuccess(true);
			j.setMsg("帐号重复");
		}
		super.writeJson(j);
	}

	public void getUserRoleList() {
		super.writeJson(userService.getUserRoleList(user.getName()));
	}

	public void edit() {
		Json j = new Json();
		userService.edit(user);
		j.setSuccess(true);
		j.setMsg("编辑成功！");
		super.writeJson(j);
	}
	
	public void getUserMenus() {
		SessionInfo session = (SessionInfo)ServletActionContext.getRequest().getSession().getAttribute(ConfigUtil.getSessionInfoName());
		super.writeJson(userService.getUserMenuFromUserId(session.getId()));
	}
	
	
}
