<html>
<head>
<title>中国石油浙江销售分公司经营分析系统</title>
<link rel="stylesheet" href="${resource(dir:'jarvis/css',file:'AdminLTE.min.css')}">
<script type="text/javascript" src="${resource(dir:'jarvis/js',file:'bootstrap.min.js') }"></script>
<script type="text/javascript" src="${resource(dir:'jarvis/js',file:'icheck.min.js') }"></script>
<script type="text/javascript" src="${resource(dir:'jarvis/js',file:'jQuery-2.1.4.min.js') }"></script>
<link rel="stylesheet" href="http://cdn.staticfile.org/twitter-bootstrap/3.3.1/css/bootstrap.css">
<link rel="stylesheet" href="${resource(dir:'jarvis/css',file:'blue.css')}">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
  <!-- Ionicons -->
<link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
<script src="http://cdn.staticfile.org/twitter-bootstrap/3.3.1/js/bootstrap.min.js"></script>
<style type="text/css">
  
</style>
</head>

<body class="hold-transition login-page">
	<div class="login-box">
		<div class="login-logo" style="width: 110%;">
			<span>中国石油浙江销售分公司经营分析系统</span>
		</div>
		<!-- /.login-logo -->
		<div class="login-box-body">
			<p class="login-box-msg" style="font-size: 16px;">请登录</p>
             <g:if test='${flash.message}'>
				<div class='login_message' style="color: red;">
					${flash.message}
				</div>
			</g:if>
			<form action='${postUrl}' method='POST' id='loginForm'>
				<div class="form-group has-feedback">
					<input type="text" class="form-control" placeholder="用户名" name='j_username' id='username'>
					<span class="glyphicon glyphicon-user form-control-feedback"></span>
				</div>
				<div class="form-group has-feedback">
					<input type="password" class="form-control" placeholder="用户密码" name='j_password' id='password'>
					<span class="glyphicon glyphicon-lock form-control-feedback"></span>
				</div>
				<div class="row">
					<div class="col-xs-8">
						<div class="checkbox icheck">
							<label> <input type="checkbox" name='${rememberMeParameter}' id='remember_me'
						<g:if test='${hasCookie}'>checked='checked'</g:if>> 记住我
							</label>
						</div>
					</div>
					<!-- /.col -->
					<div class="col-xs-4">
						<button type="submit" class="btn btn-primary btn-block btn-flat"  id="submit">登录</button>
					</div>
					<!-- /.col -->
				</div>
			</form>

			<div class="social-auth-links text-center">
				<p>- - -</p>
				<div style="width:100%; background-color: #DD4B39; color: white; height: 7%;" >
				   <span style="text-align: center; height: 60px; line-height: 60px; cursor: none;">欢迎进入销售分析系统</span>
				</div>
			</div>
			<!-- /.social-auth-links -->
		</div>
		<!-- /.login-box-body -->
	</div>
	<!-- /.login-box -->
	<script>
		$(function() {
			$('input').iCheck({
				checkboxClass : 'icheckbox_square-blue',
				radioClass : 'iradio_square-blue',
				increaseArea : '20%' // optional
			});
		});
	</script>
</body>
</html>
