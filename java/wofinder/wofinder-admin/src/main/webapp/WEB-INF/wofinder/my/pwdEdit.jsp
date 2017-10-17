<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="changepasw">
	<div class="changewrap">
		<div class="form-group label-floating szpassword is-empty">
		   <label for="oldPassword" class="control-label">输入旧密码</label>
		   <input type="password" class="form-control" id="oldPassword">
		   <i class="iconfont icon-wf_yanjingbi" style="cursor:pointer"></i>
		   <span class="help-block error" id="oldPasswordError"></span>
		</div>
		<div class="form-group label-floating szpassword is-empty">
		   <label for="newPassword" class="control-label">输入新密码</label>
		   <input type="password" class="form-control" id="newPassword">
		   <i class="iconfont icon-wf_yanjingbi" style="cursor:pointer"></i>
		   <span class="help-block error" id="newPasswordError"></span>
		</div>
		<p><a href="javascript:;" class="btn msure" id="pawset">确定</a></p> 
	</div>
</div>
