<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 86158
  Date: 2020-2-19
  Time: 21:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String cssPath = application.getContextPath()+"/homeS/css/";
	String jsPath = application.getContextPath()+"/homeS/js/";
	String path = application.getContextPath()+"/";
	String imagesPath =application.getContextPath()+"/homeS/images/";
	String dbimgPath =application.getContextPath()+"/images/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" id="searchJsp">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>职位搜索-高级搜索</title>
	<link type="text/css" rel="stylesheet" href=<%=cssPath+"searchJob.css"%>/>
	<link rel="stylesheet" href="<%=path+"layui/css/layui.css"%>" media="all">
	<link type="text/css" rel="stylesheet" href=<%=cssPath+"jobTable.css"%>/>
</head>

<body>
<div class="topDiv">
	<div class="topL">
		<div class="logo">
			<img src=<%=imagesPath+"LOGO.png"%>/>
			<img src=<%=imagesPath+"view_logo40.png"%> class="img1"/>
		</div>
		<img src=<%=imagesPath+"spirit_40.png"%> />
	</div>
	<div class="topR">
		<div class="topR0 topR1" style=" background: url(<%=imagesPath+"4.png"%>) no-repeat;width: 55px;padding-left: 25px;cursor: pointer;"><a href="zhuceCompany.html">企业招聘</a></div>
		<div class="topR0 topR2"><a href="zhuce.html">注册</a></div>
		<div class="topR0 topR3" style="background: url(<%=imagesPath+"5.png"%>) no-repeat;width: 62px;padding-left: 25px;cursor: pointer;"><a href="login.html">个人登录</a></div>
		<div class="topR0 topR4" style="background: url(<%=imagesPath+"6.png"%>) no-repeat;width: 72px;padding-left: 25px;cursor: pointer; margin-right:20px"><a href="javascript:void()">手机找工作</a></div>
		<div class="clear"></div>
	</div>
</div>
<div class="menuDiv">
	<div class="mainWarp">
		<ul>
			<li><a href=<%=path+"jump/home/index"%>>首页</a></li>
			<li><a href="personal.html">个人中心</a></li>
			<li class="onmenu"><a href=<%=path+"jump/home/searchJob"%>>职位搜索</a></li>
			<li><a href="meetingJob.html">招聘会</a></li>
			<li><a href="jobnews.html">就业资讯</a></li>
			<li><a href="download.html">文档下载</a></li>
			<li><a href="helpJob.html">求职互助</a></li>
			<div class="clear"></div>
		</ul>
	</div>
</div>
<div class="clear"></div>
<div class="menuLittle">
	<div class="menuselect"><a href="searchJob.html" class="on">高级搜索</a></div>
	<div class="menuselect"><a href="searchJob_map.html">地图搜索</a></div>
	<div class="menuselect"><a href="searchJob_new.html">最新职位</a></div>
	<div class="menuselect"><a href="searchJob_hot.html">热门搜索</a></div>
	<div class="menuselect"><a href="searchJob_jianzhi.html">兼职招聘</a></div>
	<div class="clear"></div>
</div>
<div class="clear"></div>
<div class="searchDiv">
	<form class="layui-form">
	<div class="search1">
		<div class="sea1_1">
			<input name='postion' value=
				<c:if test="${text != null}">
					${text}
				</c:if>
				<c:if test="${text == null or text == ''}">
					""
				</c:if>
			  type="text" placeholder="请填写关键词或选择职位" class="keyword" style="color: #898989;padding-left: 35px;background: url(<%=imagesPath+"7.png"%>) 8px 8px no-repeat;line-height: 33px;height: 33px;width: 275px;cursor: auto" id="searchJobText"/>
			<span class="jiantou" style="background: url(<%=imagesPath+"8.png"%>) 5px 13px no-repeat;width: 29px;height: 35px;float: right;"></span>
		</div>
<%--		<div class="sea1_3">--%>
<%--			<input name="" type="text" placeholder="请选择行业" class="hytxt" style="background:url(<%=imagesPath+"8.png"%>)  right 13px no-repeat; width:203px;height: 35px; padding-left:15px"/>--%>
<%--		</div>--%>
		<div class="sea1_4">
			<a href="" style="width: 112px;"><button class="layui-btn" lay-submit lay-filter="searchBtn" id="searchBtn" style="width: 116px;">搜工作</button></a>
		</div>
	</div>

			<div class="layui-inline">
				<div class="layui-form-item" pane="">
					<label class="layui-form-label">工作地点 ：</label>
					<div class="layui-input-inline">
						<select name="gzAddress" lay-search="" lay-verify="required" lay-filter="gzAddress" id="gzAddress">
							<option value="0">~</option>
							<option value="1">福州</option>
							<option value="4">泉州</option>
							<option value="3">漳州</option>
							<option value="2">厦门</option>
							<option value="5">杭州</option>
							<option value="7">温州</option>
							<option value="8">绍兴</option>
							<option value="6">宁波</option>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-form-item" pane="">
				<label class="layui-form-label">月薪范围 ：</label>
				<div class="layui-input-inline">
					<select name="salaryLow" lay-search="" lay-verify="salaryLow" lay-filter="salaryLow" id="salaryLow">
						<option value="0">~</option>
						<option value="1000">1K</option>
						<option value="3000">3K</option>
						<option value="5000">5K</option>
						<option value="6000">6K</option>
						<option value="7000">7K</option>
						<option value="8000">8K</option>
						<option value="10000">10K</option>
						<option value="15000">15K</option>
						<option value="20000">20K</option>
						<option value="21">面议</option>
					</select>
				</div>
				<div class="layui-input-inline">
					<select name="salaryHigh" lay-search="" lay-verify="salaryHigh" lay-filter="salaryHigh" id="salaryHigh">
						<option value="0">~</option>
						<option value="1000">1K</option>
						<option value="3000">3K</option>
						<option value="5000">5K</option>
						<option value="6000">6K</option>
						<option value="7000">7K</option>
						<option value="8000">8K</option>
						<option value="10000">10K</option>
						<option value="15000">15K</option>
						<option value="20000">20K</option>
					</select>
				</div>

				<label class="layui-form-label">学历要求 ：</label>
				<div class="layui-input-inline">
					<select name="eduction" lay-search="" lay-verify="eduction" lay-filter="eduction" id="eduction">
						<option value="0">~</option>
						<option value="小学">小学及以上</option>
						<option value="初中">初中及以上</option>
						<option value="高中">高中、中技、中专及以上</option>
						<option value="大专">大专及以上</option>
						<option value="本科">本科及以上</option>
						<option value="硕士">硕士及以上</option>
						<option value="博士">博士及以上</option>
						<option value="不限">不限</option>
					</select>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">工作年限 ：</label>
				<div class="layui-input-inline">
					<select name="workTime" lay-search="" lay-verify="workTime" lay-filter="workTime" id="workTime">
						<option value="0">~</option>
						<option value="不限">不限</option>
						<option value="应届">应届毕业生</option>
						<option value="1">1~2年</option>
						<option value="3">3~5年</option>
						<option value="6">6~10年</option>
						<option value="10">10年以上</option>
					</select>
				</div>

				<label class="layui-form-label">工作性质 ：</label>
				<div class="layui-input-inline">
					<select name="workState" lay-search="" lay-verify="workState" lay-filter="workState" id="workState">
						<option value="0">~</option>
						<option value="全职">全职</option>
						<option value="兼职">兼职</option>
						<option value="实习">实习</option>
						<option value="不限">不限</option>
					</select>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">企业规模 ：</label>
				<div class="layui-input-inline">
					<select name="qyPeople" lay-search="" lay-verify="qyPeople" lay-filter="qyPeople" id="qyPeople">
						<option value="0">~</option>
						<option value="50人">少于50人</option>
						<option value="100人">50~100人</option>
						<option value="200人">101~200人</option>
						<option value="500人">201~500人</option>
						<option value="1000人">501~1000人</option>
						<option value="1001人">1001人以上</option>
					</select>
				</div>

				<label class="layui-form-label">企业答复率 ：</label>
				<div class="layui-input-inline">
					<select name="replyRate" lay-search="" lay-verify="replyRate" lay-filter="replyRate" id="replyRate">
						<option value="0">~</option>
						<option value="50">50%</option>
						<option value="60">60%</option>
						<option value="70">70%</option>
						<option value="80">80%</option>
						<option value="90">90%</option>
						<option value="100">100%</option>
					</select>
				</div>
			</div>

			<div class="layui-form-item" >
				<label class="layui-form-label">福利待遇：</label>
				<div class="layui-input-block">
					<input name="baoxian" title="五险" type="checkbox"  lay-skin="primary">
					<input name="gjijin" title="公积金" type="checkbox" lay-skin="primary">
					<input name="jiangjin" title="奖金提成"type="checkbox" lay-skin="primary">
					<input name="shuangxiu" title="双休"  type="checkbox" lay-skin="primary">
					<input name="baxiaoshi" title="八小时工作制"  type="checkbox" lay-skin="primary">
					<input name="zhusu" title="提供住宿"  type="checkbox" lay-skin="primary">
				</div>
			</div>
			<button class="layui-btn" lay-submit lay-filter="tijiao">确定</button>
	</form>



</div>
<div class="clear"></div>
<div class="seachlist">
	<div class="listtop">

		<div class="tj2">
			<input name="" type="checkbox" value="" />
			<span>只显示在线 </span>
		</div>
		<div class="tj2">
			<input name="" type="checkbox" value="" />
			<span>只显示有环境照片 </span>
		</div>

	</div>
	<div class="clear"></div>

	<table class="layui-table" lay-skin="nob" lay-even="searchJob" id="searchJob" id="searchJob"></table>
</div>
<div class="clear"></div>
<div class="footer">
	<p><a href="">**人才</a>旗下<img src=<%=imagesPath+"logo_foot.gif"%> />蝶飞人才网版权所有 <a href="">京ICP证0******9号</a><a href=""></a>本网站所有招聘信息，未经书面授权不得转载 投诉电话：400-8**-****</p>
</div>

<script type="text/javascript" src=<%=path+"layui/jquery-3.4.1.js"%>></script>
<script src=<%=path + "layui/layui.js"%>></script>
<script type="text/javascript" src=<%=jsPath+"searchJob.js"%>></script>
</body>
</html>

