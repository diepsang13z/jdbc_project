<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>

<!DOCTYPE html>
<html>

<head>
<title>DirEngine</title>

<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- template link -->
<%@ include file="/common/web/link.jsp"%>

<!-- specific link -->
<link rel="stylesheet" href="${webviews}/css/structure.css">
<link rel="stylesheet" href="${webviews}/css/form.css">

</head>

<body>

	<div class="page-container">
		<div id="content-wrap">
			<!-- all other page content -->

			<!-- nav -->
			<%@ include file="/common/web/nav.jsp"%>

			<!-- header -->
			<div class="hero-wrap js-fullheight"
				style="background-image: url('${webtemplate}/images/bg_2.jpg');">
				<div class="overlay"></div>
				<div class="container">
					<div
						class="row no-gutters slider-text js-fullheight align-items-center justify-content-center"
						data-scrollax-parent="true">
						<div class="col-md-9 ftco-animate text-center"
							data-scrollax=" properties: { translateY: '70%' }">
							<p class="breadcrumbs"
								data-scrollax="properties: { translateY: '30%', opacity: 1.6 }">
								<span class="mr-2"><a href="index.html">Register</a></span> <span>Login</span>
							</p>
							<h1 class="mb-3 bread"
								data-scrollax="properties: { translateY: '30%', opacity: 1.6 }">Register</h1>
						</div>
					</div>
				</div>
			</div>

			<!-- main -->
			<div class="container show fullscreen">
				<div class="form">
					<h1>Đăng ký</h1>

					<form id="register" action="/" method="post" class="card">
						<input type="hidden" name="action" value="formsubmit" />

						<div class="form-item">
							<label for="email">Email</label> <br> <input id="email"
								name="email" type="text" /> <br> <span
								class="form-message"></span>
						</div>

						<div class="form-item">
							<label for="fullname">Họ và tên</label> <br> <input
								id="fullname" name="fullname" type="text" /> <br> <span
								class="form-message"></span>
						</div>

						<div class="form-item">
							<label for="username">Tên đăng nhập</label> <br> <input
								id="username" name="username" type="text" /> <br> <span
								class="form-message"></span>
						</div>

						<div class="form-item">
							<label for="password">Mật khẩu</label> <br> <input
								id="password" name="password" type="password" /> <br> <span
								class="form-message"></span>
						</div>

						<div class="form-item">
							<label for="confirm_password">Nhập lại mật khẩu</label> <br>
							<input id="confirm_password" name="confirm_password"
								type="password" /> <br> <span class="form-message"></span>
						</div>

						<div class="form-item">
							<label for="phone">Số điện thoại</label> <br> <input
								id="phone" name="phone" type="text" /> <br> <span
								class="form-message"></span>
						</div>

						<div class="form-item">
							<input class="btn" type="submit" value="Register" />
						</div>
					</form>

					<div class="singuplink">
						<span>Login now?</span> <a href="${webviews}/register.jsp">Login</a>
					</div>
				</div>
			</div>

		</div>

		<div style="clear: both"></div>
		<!-- footer -->
		<%@ include file="/common/web/footer.jsp"%>
		<!-- loader -->
		<%@ include file="/common/web/loader.jsp"%>
	</div>
	<!-- template script -->
	<%@ include file="/common/web/script.jsp"%>

</body>

</html>