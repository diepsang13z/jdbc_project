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
									<div class="col mb-3">
										<label class="form-label">Họ và tên</label>
										<input
											type="text"
											name="fullname"
											class="form-control">
									</div>

									<div class="col mb-3">
										<label class="form-label">Email</label>
										<input
											type="email"
											name="email"
											class="form-control">
									</div>

									<div class="col mb-3">
										<label class="form-label">Số điện thoại</label>
										<input
											type="text"
											name="phone_number"
											class="form-control">
									</div>

									<div class="col mb-3">
										<label class="form-label">Địa chỉ</label>
										<input
											type="text"
											name="address"
											class="form-control">
									</div>

									<div class="col mb-3">
										<label class="form-label">Tài khoản</label>
										<input
											type="text"
											name="username"
											class="form-control">
									</div>

									<div class="col mb-3">
										<label class="form-label">Mật khẩu</label>
										<input
											type="password"
											name="password"
											class="form-control">
									</div>

									<div class="col mb-3">
										<label class="form-label">Vai trò</label>
										<select
											name="role"
											class="form-select">
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

				<!-- Search bars -->
				<div class="col-6 col-md-4">
					<form
						id="search_form"
						action="${context}/admin-home"
						method="get">

						<input
							type="hidden"
							name="command"
							value="SEARCH">

						<input
							id="search"
							name="search_content"
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

					<!-- SHOW INFO -->
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
							<c:when test="${entry.status eq 'LOCK'}">
								<td class="text-danger fw-bold">Đã khóa</td>
							</c:when>
						</c:choose>
						<td>
							<div class="row row-cols-3 text-center m-1">
								<!-- Gửi -->
								<div class="p-1">
									<a
										href="#"
										class="w-100 col btn btn-success">Gửi</a>
								</div>

								<!-- Sửa -->
								<div class="p-1">
									<button
										type="button"
										class="w-100 col btn btn-primary"
										data-bs-toggle="modal"
										data-bs-target="#update_user_form_${entry.username}">Sửa</button>

									<!-- Modal -->
									<div
										class="modal fade text-start"
										id="update_user_form_${entry.username}"
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
														id="staticBackdropLabel">Cập nhật</h1>
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
														value="UPDATE" />

													<!-- Cập nhật nhanh hơn với id (primary key) -->
													<input
														type="hidden"
														name="id"
														value="${entry.id}" />

													<!-- INFO -->
													<div class="modal-body">
														<div class="row row-cols-2">
															<div class="col mb-3">
																<label class="form-label">Họ và tên</label>
																<input
																	type="text"
																	name="fullname"
																	class="form-control"
																	value="${entry.fullname}">
															</div>

															<div class="col mb-3">
																<label class="form-label">Email</label>
																<input
																	type="email"
																	name="email"
																	class="form-control"
																	value="${entry.email}">
															</div>

															<div class="col mb-3">
																<label class="form-label">Số điện thoại</label>
																<input
																	type="text"
																	name="phone_number"
																	class="form-control"
																	value="${entry.phoneNumber}">
															</div>

															<div class="col mb-3">
																<label class="form-label">Địa chỉ</label>
																<input
																	type="text"
																	name="address"
																	class="form-control"
																	value="${entry.address}">
															</div>

															<div class="col mb-3">
																<label class="form-label">Tài khoản</label>
																<input
																	type="text"
																	name="username"
																	class="form-control"
																	value="${entry.username}">
															</div>

															<div class="col mb-3">
																<label class="form-label">Vai trò</label>
																<select
																	name="role"
																	class="form-select">
																	<c:choose>
																		<c:when test="${entry.roleId == 1}">
																			<option
																				value="admin"
																				selected>Admin</option>
																			<option value="user">User</option>
																		</c:when>

																		<c:when test="${entry.roleId == 2}">
																			<option value="admin">Admin</option>
																			<option
																				value="user"
																				selected>User</option>
																		</c:when>
																	</c:choose>
																</select>
															</div>

														</div>
													</div>
													<!--  -->
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

								<!-- Chi tiết -->
								<div class="p-1">
									<a
										href="#"
										class="w-100 col btn btn-warning">Chi tiết</a>
								</div>

								<!-- Xóa -->
								<div class="p-1">
									<!-- SET URL AND COMMAND -->
									<c:url
										var="deleteUserLink"
										value="admin-home">
										<c:param
											name="command"
											value="DELETE" />
										<c:param
											name="userId"
											value="${entry.id}" />
									</c:url>

									<button
										type="button"
										class="w-100 col btn btn-danger"
										data-bs-toggle="modal"
										data-bs-target="#confirm_delete_user_${entry.username}">Xóa</button>

									<!-- Dialog -->
									<div
										class="modal fade"
										id="confirm_delete_user_${entry.username}"
										tabindex="-1"
										aria-labelledby="exampleModalLabel"
										aria-hidden="true">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header">
													<h1
														class="modal-title fs-5"
														id="exampleModalLabel">Bạn có chắc muốn xóa?</h1>
													<button
														type="button"
														class="btn-close"
														data-bs-dismiss="modal"
														aria-label="Close"></button>
												</div>

												<div class="modal-body text-start">
													<!-- User info -->
													Người dùng:
													<span>${entry.username}</span>
												</div>

												<div class="modal-footer">
													<button
														type="button"
														class="btn btn-secondary"
														data-bs-dismiss="modal">Close</button>
													<a
														href="${deleteUserLink}"
														class="btn btn-danger">Xóa</a>
												</div>
											</div>
										</div>
									</div>
								</div>

								<!-- SET URL AND COMMAND -->
								<c:url
									var="activeUserLink"
									value="admin-home">
									<c:param
										name="command"
										value="ACTIVE" />
									<c:param
										name="userId"
										value="${entry.id}" />
								</c:url>

								<c:url
									var="lockUserLink"
									value="admin-home">
									<c:param
										name="command"
										value="LOCK" />
									<c:param
										name="userId"
										value="${entry.id}" />
								</c:url>

								<!-- KHÓA, MỞ TÀI KHOẢN -->
								<c:choose>
									<c:when test="${entry.status eq 'ACTIVE'}">
										<!-- Khóa -->
										<div class="p-1">
											<a
												href="${lockUserLink}"
												class="w-100 col btn btn-danger">Khóa</a>
										</div>
									</c:when>

									<c:when test="${entry.status eq 'LOCK'}">
										<!-- Mở -->
										<div class="p-1">
											<a
												href="${activeUserLink}"
												class="w-100 col btn btn-success">Mở</a>
										</div>
									</c:when>
								</c:choose>
							</div>
						</td>
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