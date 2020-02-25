package com.cykj.net.controller;

import com.cykj.net.javabean.Adminrole;
import com.cykj.net.javabean.Alluserinfo;
import com.cykj.net.javabean.Schoolinfo;
import com.cykj.net.javabean.Table;
import com.cykj.net.javabean.admin.Admin;
import com.cykj.net.service.AdminroleService;
import com.cykj.net.service.SchoolService;
import com.cykj.net.service.admin.AdminService;
import com.google.gson.Gson;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Controller
@RequestMapping("/school")
public class SchoolController
{
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private AdminroleService adminroleService;




	//注册－－－－－－－－－－－－－－－－－－－－－－－－
	/**
	 * 该功能只记录reg1当中的账户密码到session
	 * @param schoolinfo
	 * @param request
	 * @return
	 */
	@RequestMapping("/reg1")
	@ResponseBody
	public String screg1(Schoolinfo schoolinfo, HttpServletRequest request){
		System.out.println("学校注册１启动。。。");
		Schoolinfo sc=schoolService.findSchoolinfo(schoolinfo.getScAccount());
		if(sc!=null){
			//该账户存在的情况下，不给注册
			System.out.println("不给注册");
			return "0";
		}else{
			//该账户不存在的情况下，下一步
			//将数据存在session里面方便后面信息填写
			System.out.println("给注册");
			request.getSession().setAttribute("reg1need",schoolinfo);
		}
			return "1";
	}
	@RequestMapping("/returnreg2")
	@ResponseBody
	public ModelAndView returnreg2(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/WEB-INF/school/reg2");
		mv.addObject("weizhi",1);
		return mv;
	}
	/**
	 * 该功能真正实现注册，并插入管理员的表以及高校的表
	 * @param schoolinfo
	 * @param request
	 * @return
	 */
	@RequestMapping("/reg2")
	@ResponseBody
	public String screg2(Schoolinfo schoolinfo, HttpServletRequest request,@RequestParam("file") MultipartFile file) throws IOException
	{
		System.out.println("学校注册２启动。。。");
		//session里面取值
		Schoolinfo sess= (Schoolinfo) request.getSession().getAttribute("reg1need");
		//注入账户密码
		schoolinfo.setScAccount(sess.getScAccount());
		schoolinfo.setPassword(sess.getPassword());
		//查找这个账户存在不，是否被其他管理员在这个期间注册了
		Schoolinfo scnamecheck=schoolService.findSchoolinfo(schoolinfo.getScAccount());
		//查找手机号是否唯一
		Schoolinfo scphonecheck=schoolService.findphone(schoolinfo.getScPhone());
		//查找信用代码是否唯一
		Schoolinfo scdaimachenk=schoolService.finddaima(schoolinfo.getXinyongDaima());
		if(scnamecheck!=null){
			//被其他管理员注册了，注册失败
			return "0";
		}else if(scphonecheck!=null){
			//手机不唯一
			return "-1";
		}else if(scdaimachenk!=null){
			//社会信用代码不唯一
			return "-2";
		} else if(schoolinfo.getScAccount()==null||schoolinfo.getPassword()==null){
			return "erro";
		}
		else{
			//插入注册时间
			schoolinfo.setRegTime(new Timestamp(System.currentTimeMillis()));
			//插入图片路径
			String path=request.getServletContext().getRealPath("/WEB-INF/school/cunchu");
			//判断logo目录的是否存在
			File pathlogo=new File(path+"\\"+"logo");
			if(!pathlogo.exists()){
				pathlogo.mkdir();
			}
			//判断以这个账号为命名目录的是否存在
			File logoacc=new File(path+"\\"+"logo"+"\\"+schoolinfo.getScAccount());
			if(!logoacc.exists()){
				logoacc.mkdir();
			}
			System.out.println(path+"\\"+"logo"+"\\"+schoolinfo.getScAccount());
			String filename = file.getOriginalFilename();
			file.transferTo(new File(path+"\\"+"logo"+"\\"+schoolinfo.getScAccount()+"\\"+ filename));
			schoolinfo.setScpicture("\\"+"logo"+"\\"+schoolinfo.getScAccount()+"\\"+ filename);
			schoolinfo.setScState(0);
			Admin a=new Admin();
			a=adminService.findAdmin(schoolinfo.getScAccount());
			if(a!=null){
				return "0";
			}else{
				//插入管理员表
				Admin admin=new Admin();
				admin.setAccount(schoolinfo.getScAccount());
				admin.setPassword(schoolinfo.getPassword());
				admin.setRegistertime(schoolinfo.getRegTime());
				admin.setName(schoolinfo.getScName());
				adminService.regAdmin(admin);
				//插入管理角色表
				Adminrole adminrole=new Adminrole();
				adminrole.setAccount(schoolinfo.getScAccount());
				adminrole.setRoid(4);
				adminroleService.regAdminRole(adminrole);
				int i=schoolService.inserSchoolinfo(schoolinfo);
				if(i>0){
					System.out.println("学校表插入成功");
				}

			}


		}
		return "1";
	}
	//修改高校信息－－－－－－－－－－－－－－－－－－－－－－－－
	/**
	 * 跳转到修改信息页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/changeinfo")
	public ModelAndView changeinfo(HttpServletRequest request){
		Admin admin= (Admin) request.getSession().getAttribute("admin");
		Schoolinfo scinfo=schoolService.findSchoolinfo(admin.getAccount());
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/WEB-INF/school/changeinfo");
		mv.addObject("scinfo",scinfo);
		return mv;
	}
	/**
	 * 修改高校信息，有修改图片
	 * @param sc
	 * @param request
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/changeInfo1")
	@ResponseBody
	public String changeInfo1(Schoolinfo sc,HttpServletRequest request,@RequestParam("file") MultipartFile file) throws IOException
	{
		Admin admin= (Admin) request.getSession().getAttribute("admin");
		sc.setScAccount(admin.getAccount());
			Schoolinfo ssc=schoolService.findSchoolinfo(admin.getAccount());
			//判断电话号码是否唯一
			int a=panduanphone(admin.getAccount(),sc.getScPhone());
			if(a==1){}else{return "-1";}
			//判断信用代码是否唯一
			int b=panduandaima(admin.getAccount(),sc.getXinyongDaima());
			if(b==1){}else{return "-2";}
			//插入图片路径
			String path=request.getServletContext().getRealPath("/WEB-INF/school/cunchu");
			File logoacc=new File(path+"\\"+"logo"+"\\"+admin.getAccount());
			//删除这个用户文件夹下的所有文件
			File[] files = logoacc.listFiles();
			//遍历删除文件
			for(File f: files){
				f.delete();
			}
			String filename = file.getOriginalFilename();
			file.transferTo(new File(path+"\\"+"logo"+"\\"+admin.getAccount()+"\\"+ filename));
			sc.setScpicture("\\"+"logo"+"\\"+admin.getAccount()+"\\"+ filename);
			int i=schoolService.updateSchoolinfo(sc);
			if(i>0)
			{
				return "1";
			}

		return "0";
	}

	/**
	 * 修改高校信息，不修改图片
	 * @param sc
	 * @param request
	 * @return
	 */
	@RequestMapping("/changeInfo2")
	@ResponseBody
	public String changeInfo2(Schoolinfo sc,HttpServletRequest request)
	{
		Admin admin= (Admin) request.getSession().getAttribute("admin");
		sc.setScAccount(admin.getAccount());
		//判断电话号码是否唯一
		int a=panduanphone(admin.getAccount(),sc.getScPhone());
		if(a==1){}else{return "-1";}
		//判断信用代码是否唯一
		int b=panduandaima(admin.getAccount(),sc.getXinyongDaima());
		if(b==1){}else{return "-2";}
		int i=schoolService.updateSchoolinfo(sc);
		if(i>0)
		{
			return "1";
		}
		return "0";
	}

	//修改密码－－－－－－－－－－－－－－－－－－－－－－－－

	/**
	 * 跳转到修改密码页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/changepassword")
	public ModelAndView changepassword(HttpServletRequest request){
		Admin admin= (Admin) request.getSession().getAttribute("admin");
		Schoolinfo scinfo=schoolService.findSchoolinfo(admin.getAccount());
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/WEB-INF/school/changepwd");
		mv.addObject("scinfo",scinfo);
		return mv;
	}
	/**
	 *修改密码
	 * @param request
	 */
	@RequestMapping("/pwd")
	@ResponseBody
	public String changepwd(Schoolinfo sc,String newpass,HttpServletRequest request){
		Admin admin= (Admin) request.getSession().getAttribute("admin");
		if(admin!=null){
			if(schoolService.findSchoolinfo(admin.getAccount()).getPassword().equals(sc.getPassword())){
				sc.setScAccount(admin.getAccount());
				sc.setPassword(newpass);
				int i=schoolService.updateSchoolinfo(sc);
				admin.setPassword(newpass);
				int k=adminService.changeAdminPassword(admin);
				if(i>0&&k>0){
					return "1";
				}
			}
		}else{
			return "erro";
		}
		return "0";
	}


	//人才信息

	/**
	 * 跳转到人才信息页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/rencaiinfo")
	public ModelAndView rencaiinfo(HttpServletRequest request){
		Admin admin= (Admin) request.getSession().getAttribute("admin");
		Schoolinfo scinfo=schoolService.findSchoolinfo(admin.getAccount());
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/WEB-INF/school/rencaiinfo");
		mv.addObject("scinfo",scinfo);
		return mv;
	}

	/**
	 * 分页查询人才信息
	 *
	 */
	@RequestMapping("/rencaiinfoquery")
	public void rencaiquery(HttpServletRequest request,HttpServletResponse response,String time,String name,String zy,String page,String limit)throws Exception{
		utf8(request,response);
		Admin admin= (Admin) request.getSession().getAttribute("admin");
		String lasttime="";
		String nowtime="";
		if(time!=null&&time!=""){
			String arr[]=time.split("to");
			lasttime=arr[0].trim();
			nowtime=arr[1].trim();
			System.out.println(lasttime);
			System.out.println(nowtime);
		}
		int page1 = Integer.valueOf(page);
		int limit1=Integer.valueOf(limit);
		RowBounds rowBounds = new RowBounds(page1-1, limit1);
		int count=schoolService.fenyecount1(admin.getAccount(),name,zy,lasttime,nowtime);
		System.out.println(count);
		List<Alluserinfo> list=schoolService.fenyeshuju1(admin.getAccount(),name,zy,lasttime,nowtime,rowBounds);
		Table t=new Table();
		t.setCode(0);
		t.setCount(count);
		t.setMsg("");
		t.setData(list);
		gsonbean(t,response);
	}












































	public int panduanphone(String account,String phone){
		Schoolinfo ssc=schoolService.findSchoolinfo(account);
		if(ssc.getScPhone().equals(phone)){}else{
			//查找手机号是否唯一
			System.out.println("yyy"+ssc.getScPhone());
			System.out.println("xxx"+phone);
			Schoolinfo scphonecheck=schoolService.findphone(phone);
			if(scphonecheck!=null){return 0;}
		}
		return 1;
	}
	public int panduandaima(String account,String daima){
		Schoolinfo ssc=schoolService.findSchoolinfo(account);
		if(ssc.getXinyongDaima().equals(daima)){}else{
			//查找手机号是否唯一
			Schoolinfo scdaimacheck=schoolService.finddaima(daima);
			if(scdaimacheck!=null){return 0;}
		}
		return 1;
	}
	//时间
	public static String getTime(){
		Date date = new Date();
		SimpleDateFormat s = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
		String format = s.format(date);
		return format;
	}
	//判断文档类型
	public String panduantype(String filename){
		String[] arr=filename.split("\\.");
		if(arr.length>0){
			System.out.println(arr[1]);
			return arr[1];
		}
		return "无类型";
	}

	//传值为bean类型
	public void gsonbean(Object object,HttpServletResponse response){
		Gson g=new Gson();
		try
		{
			response.getWriter().write(g.toJson(object));
			response.setContentType("text/html; charset =utf-8");
			response.getWriter().flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public void utf8(HttpServletRequest request, HttpServletResponse response){
		try
		{
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
	}
}

