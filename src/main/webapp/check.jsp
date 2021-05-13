<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	</head>
	<body>
		<style>
			body {
				background-color: #2b2f34;
			}
			.root {
				background-color: white;
				height: 100%;
			}
			#form_check {
				margin-top: 5vh;
				margin-bottom: 10vh
			}
			#preloader {
				width: 10rem; 
				height: 10rem;
				display: none;
			}
			.card-item {
				border: 1px solid #ced4da;
				padding: 10px;
				margin-top: 1.5vh;
			}
			.card-item__status {
				font-weight: bold;
			}
		</style>
		<div class="container" style="background-color: red;">
			<div class="row">
				<div class="col">dfgs</div>
			</div>
		</div>
		
		<div class="container root">
			<div class="row">
				<div class="col">
					<form id="form_check">
						<div class="form-group">
							<button class="col btn btn-success btn-lg" type="submit">OK</button>
						</div>
						<div class="form-group">
    						<input type="text" class="col form-control form-control-lg" placeholder="Фамилия / Nachname">
  						</div>	
					</form>
				</div>
			</div>
			<div class="row">
				<div class="col text-center">
					<div id="preloader" class="spinner-border text-success" role="status" disabled>
						<span class="sr-only">Loading...</span>
					</div>
				</div>
				
			</div>
			<div class="row ">
				<div class="col">
					<div class="card-item">
						<div class="card-item__status">
							Письмо вернулось
						</div> 
						<div class="card-item__name">Ivanov Ivan</div>
						<div class="card-item__adresse">df adf adf agr arg tgbsabgv s</div>
					</div>
				</div>
				<div class="col">
					<div class="card-item">
						<div class="card-item__status">
							<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="red" class="bi bi-circle-fill" viewBox="0 0 16 16">
							  <circle cx="8" cy="8" r="8"/>
							</svg>
							Отказ
						</div>  
						<div class="card-item__name">Petrov Petr</div> 
						<div class="card-item__adresse">01855 Heiaflr fkdsjdfkgjnsv 25</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
