//function to get element by id
function _id(name) {
	return document.getElementById(name);
}

//function to get element by class
function _class(name) {
	return document.getElementsByClassName(name);
}

//js for search book
const search = () => {
	let query = _id("search-input").value;
	let res = _id("search-result");
	let act = _id("actual");

	if (query == "") {
		res.innerHTML = "";
		act.style.display = "";
	} else {
		//sending request to server
		let url = "http://localhost:8282/search/" + query;

		fetch(url).then((Response) => {
			return Response.json();
		}).then((data) => {
			let text = `<thead class='thead-light'>
					<tr>
					<th class="table-success bg-success"> Sr. No. </th>
						<th class="table-success bg-success"> Book title </th>
						<th class="table-success bg-success"> Author </th>
						<th class="table-success bg-success"> ISBN </th>
						<th class="table-success bg-success"> Quantity </th>
						<th class="table-success bg-success"> Available </th>
						<th class="table-success bg-success"> Action </th>`;
			let i = 1;
			data.forEach(book => {
				text += `<tr  data-bs-toggle="modal" data-bs-target="#issued${book.bookId}">
					<td class="table-success"> ${i++}</td>
					<td class="table-success"> ${book.bookTitle}</td>
					<td class="table-success"> ${book.author}</td>
					<td class="table-success"> ${book.isbn}</td>
					<td class="table-success"> ${book.quantity}</td>
					<td class="table-success"> ${book.available}</td>
					<td class="table-success action-cell-unclickable">
						<button class="btn btn-outline-primary" data-bs-toggle="modal"
							 data-bs-target="#exampleModal${book.bookId}">
							<i class="bi bi-pen-fill"></i>
						</button>
						<button class="btn btn-outline-danger" onclick="deleteRecord(${book.bookId})">
							<i class="bi bi-trash-fill"></i>
						</button>
					</td>
				</tr>`;
			});

			text += `</tr>
				</thead>`;

			res.innerHTML = text;
			act.style.display = "none";
		});
	}
};
//end js for book search

//js for search user
const searchUser = () => {
	let query = _id("search-input-user").value;
	let res = _id("search-result-user");
	let act = _id("actual-user");

	if (query == "") {
		res.innerHTML = "";
		act.style.display = "";
	} else {
		//sending request to server
		let url = "http://localhost:8282/search-user/" + query;

		fetch(url).then((Response) => {
			return Response.json();
		}).then((data) => {
			let text = `<thead class='thead-light'>
					<tr>
					<th class="table-success bg-success"> Sr. No. </th>
						<th class="table-success bg-success"> Name </th>
						<th class="table-success bg-success"> Phone Number </th>
						<th class="table-success bg-success"> Email </th>
						<th class="table-success bg-success"> Subscription Last Date </th>
						<th class="table-success bg-success"> Action </th>`;
			let i = 1;
			data.forEach(user => {
				text += `<tr data-bs-toggle="modal" data-bs-target="#issued${user.userId}">
					<td class="table-success"> ${i++}</td>
					<td class="table-success"> ${user.name}</td>
					<td class="table-success"> ${user.phoneNumber}</td>
					<td class="table-success"> ${user.email}</td>
					<td class="table-success"> ${user.subscribtionValidation}</td>
					<td class="table-success action-cell-unclickable">
						<button class="btn btn-outline-primary" data-bs-toggle="modal"
							 data-bs-target="#exampleModal${user.userId}">
							<i class="bi bi-pen-fill"></i>
						</button>
						<button class="btn btn-outline-danger" onclick="deleteRecord(${user.userId})">
							<i class="bi bi-trash-fill"></i>
						</button>
					</td>
				</tr>`;
			});

			text += `</tr>
				</thead>`;

			res.innerHTML = text;
			act.style.display = "none";
		});
	}
};
//end js for user search

//sweet alert for confirmation of deletion of book
function deleteRecord(bid) {
	Swal.fire({
		title: "Are you sure?",
		text: "You won't be able to revert this!",
		icon: "warning",
		showCancelButton: true,
		confirmButtonColor: "#3085d6",
		cancelButtonColor: "#d33",
		confirmButtonText: "Yes, delete it!"
	}).then((result) => {
		if (result.isConfirmed) {
			Swal.fire({
				title: "Deleted!",
				text: "Your file has been deleted.",
				icon: "success",
				confirmButtonColor: "#3085d6",
				confirmButtonText: "Ok"
			}).then((res) => {
				if (res.isConfirmed) {
					window.location = "/delete-record/" + bid;
				}
			});
		}
	});
}

//sweet alert for confirmation of deletion of user
function deleteRecordUser(uid) {
	Swal.fire({
		title: "Are you sure?",
		text: "You won't be able to revert this!",
		icon: "warning",
		showCancelButton: true,
		confirmButtonColor: "#3085d6",
		cancelButtonColor: "#d33",
		confirmButtonText: "Yes, delete it!"
	}).then((result) => {
		if (result.isConfirmed) {
			Swal.fire({
				title: "Deleted!",
				text: "The user has been deleted.",
				icon: "success",
				confirmButtonColor: "#3085d6",
				confirmButtonText: "Ok"
			}).then((res) => {
				if (res.isConfirmed) {
					window.location = "/delete-record-user/" + uid;
				}
			});
		}
	});
}

//sweet alert for confirmation of deletion of issue record
function deleteRecordIssue(iid, ibt, u) {
	Swal.fire({
		title: "Are you sure?",
		text: "You won't be able to revert this!" +
			" Click yes only if the book " + ibt +
			" has been returned by " + u + ".",
		icon: "warning",
		showCancelButton: true,
		confirmButtonColor: "#3085d6",
		cancelButtonColor: "#d33",
		confirmButtonText: "Yes"
	}).then((result) => {
		if (result.isConfirmed) {
			Swal.fire({
				title: "Returned!",
				text: "The book " + ibt + " has been returned.",
				icon: "success",
				confirmButtonColor: "#3085d6",
				confirmButtonText: "Ok"
			}).then((res) => {
				if (res.isConfirmed) {
					window.location = "/delete-record-issue/" + iid;
				}
			});
		}
	});
}