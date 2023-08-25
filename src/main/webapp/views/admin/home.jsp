<%@ page
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta
	name="viewport"
	content="width=device-width, initial-scale=1.0">
<title>Admin page</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9"
	crossorigin="anonymous">

</head>
<body>
	<main class="px-4">
		<div class="add-user py-4">
			<button
				type="button"
				class="btn btn-success"
				data-bs-toggle="modal"
				data-bs-target="#create_user_form">Thêm mới</button>

			<!-- Modal -->
			<div
				class="modal fade"
				id="create_user_form"
				data-bs-backdrop="static"
				data-bs-keyboard="false"
				tabindex="-1"
				aria-labelledby="staticBackdropLabel"
				aria-hidden="true">
				<div class="modal-dialog modal-xl">
					<div class="modal-content">
						<div class="modal-header">
							<h1
								class="modal-title fs-5"
								id="staticBackdropLabel">Thêm mới</h1>
							<button
								type="button"
								class="btn-close"
								data-bs-dismiss="modal"
								aria-label="Close"></button>
						</div>
						<form
							action="${context}/admin-home"
							method="post">
							
							<!-- COMMAND -->
							<input
								type="hidden"
								name="command"
								value="CREATE">
							
							<!-- INFO -->
							<div class="modal-body">
								<div class="row row-cols-2">
									<div class="col">
										<label class="form-label">Họ và tên</label>
										<input
											type="text"
											name="fullname"
											class="form-control">
									</div>

									<div class="col">
										<label class="form-label">Email</label>
										<input
											type="email"
											name="email"
											class="form-control">
									</div>

									<div class="col">
										<label class="form-label">Số điện thoại</label>
										<input
											type="text"
											name="phone"
											class="form-control">
									</div>

									<div class="col">
										<label class="form-label">Địa chỉ</label>
										<input
											type="text"
											name="address"
											class="form-control">
									</div>

									<div class="col">
										<label class="form-label">Tài khoản</label>
										<input
											type="text"
											name="username"
											class="form-control">
									</div>

									<div class="col">
										<label class="form-label">Mật khẩu</label>
										<input
											type="password"
											name="password"
											class="form-control">
									</div>

									<div class="col">
										<label class="form-label"></label>
										<select class="form-select">
											<option selected>Chọn loại vai trò</option>
											<option value="admin">Admin</option>
											<option value="user">User</option>
										</select>
									</div>

								</div>
							</div>
							
							<!-- SUBMIT -->
							<div class="modal-footer">
								<button
									type="button"
									class="btn btn-secondary"
									data-bs-dismiss="modal">Đóng</button>
								<input
									type="submit"
									value="Lưu"
									class="btn btn-primary">
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>

		<!-- Show user -->
		<div class="dropdown pb-4">
			<div class="row g-0">
				<div class="col-sm-6 col-md-8">
					<form
						id="pagination_form"
						action="${context}/admin-home"
						method="get">
						<span>
							<input
								type="hidden"
								name="command"
								value="CHANGE_PAGE">

							<select
								id="current_page"
								name="page"
								class="btn border dropdown-toggle">
								<c:forEach
									var="pageNumber"
									begin="1"
									end="${TOTAL_PAGE}">
									<c:choose>
										<c:when test="${pageNumber == CURRENT_PAGE}">
											<option
												value="${pageNumber}"
												selected>${pageNumber}</option>
										</c:when>
										<c:otherwise>
											<option value="${pageNumber}">${pageNumber}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</span>
						<span class="mx-2">entries per page</span>
					</form>
					<!-- Create a action when user click to a option in select tag -->
					<script>
						document.addEventListener("DOMContentLoaded",
								function() {
									let selectElem = document
											.getElementById("current_page");
									let formElem = document
											.getElementById("pagination_form");

									selectElem.addEventListener("change",
											function() {
												formElem.submit();
											});
								});
					</script>
				</div>
				<div class="col-6 col-md-4">
					<form
						id="search_form"
						action="/"
						method="get">
						<input
							id="search"
							class="form-control"
							type="text"
							placeholder="Search">
					</form>
				</div>
			</div>
		</div>

		<!--  -->
		<table class="table table-bordered">
			<thead>
				<tr class="table-active">
					<th scope="col">Họ tên</th>
					<th scope="col">Email</th>
					<th scope="col">Số điện thoại</th>
					<th scope="col">Tài khoản</th>
					<th scope="col">Vai trò</th>
					<th scope="col">Trạng thái</th>
					<th scope="col">Hoạt động</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach
					var="entry"
					items="${ENTRIES_DATA}">
					<tr>
						<td>${entry.fullname}</td>
						<td>${entry.email}</td>
						<td>${entry.phoneNumber}</td>
						<td>${entry.username}</td>
						<c:choose>
							<c:when test="${entry.roleId == 1}">
								<td>admin</td>
							</c:when>
							<c:when test="${entry.roleId == 2}">
								<td>user</td>
							</c:when>
						</c:choose>
						<c:choose>
							<c:when test="${entry.status eq 'ACTIVE'}">
								<td class="text-success fw-bold">Hoạt động</td>
							</c:when>
							<c:when test="${entry.status eq 'BLOCK'}">
								<td class="text-danger fw-bold">Đã khóa</td>
							</c:when>
						</c:choose>
						<td>NULL</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<span class="py-2"> Showing ${CURRENT_PAGE} to ${TOTAL_PAGE} of
			${ENTRIES_DATA.size()} entries </span>
	</main>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
		crossorigin="anonymous"></script>
</body>
</html>