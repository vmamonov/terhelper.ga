<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, user-scalable=yes">
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.6/js/standalone/selectize.min.js" integrity="sha256-+C0A5Ilqmu4QcSPxrlGpaZxJ04VjsRjKu+G82kl5UJk=" crossorigin="anonymous"></script>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.6/css/selectize.bootstrap3.min.css" integrity="sha256-ze/OEYGcFbPRmvCnrSeKbRTtjG4vGLHXgOqsyLFTRjg=" crossorigin="anonymous" />
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
			.search-result-item {
				border: 1px solid #ced4da;
				padding: 10px;
				margin-top: 1.5vh;
			}
			.search-result-item__status {
				font-weight: bold;
			}
			.search-result-item__name {
				font-size: 1.5rem;
			}
			.DoNotCall {
				border-color: red;
				background-color: #eae1e1;
			}
			svg {
				display: none;
			}
			svg.DoNotCall {
				margin-right: 5px;
				display: inline;
			}
			.date_upd {
				color: white;
			}
			.date_upd:active {
				color: black;
			}
		</style>
		
		<div class="container root">
			<div class="row">
				<div class="col date_upd">
					<%= request.getAttribute("dateUpdate") %> <br>	
				</div>
			</div>
			<div class="row">
				<div class="col">
					<form id="form_check">
						<div class="form-group">
							<select id="search-field" class="col form-control form-control-lg" placeholder="Введите данные"></select>
  						</div>	
					</form>
				</div>
			</div>
			<div id="search-result" class="row"></div>
		</div>
		
		<script type="text/javascript">
			Window.dataRaw = <%= request.getAttribute("data") %>;
			if (typeof Window.dataRaw === 'object') {
				Window.dataRaw.sort((a, b) => a.name > b.name ? 1 : -1);
			}
			
			$('.date_upd').append('Записей: ' + Window.dataRaw.length);
			$('#search-result').hide();
			$('#search-field').change(function() {
				resultSearchRebuild(this);		
			});
		
			$(document).ready(function (dataRaw) {
			      $('select').selectize({
					maxItems: 1,
					valueField: 'name',
					labelField: 'name',
					searchField: 'name',
					options: Window.dataRaw,			 
					create: false
			      });
			  });
				
			function resultSearchRebuild(el) {
				$('#search-result').hide();
				$('.search-result-item__box').remove();
				Window.dataRaw.forEach (row => {	
					if (row.name == $(el).val()) {
						var status = ("DoNotCall" == row.status) ? "Отказ" : "Письмо вернулось";
						$('#search-result').append(	
							"<div class='search-result-item__box col-12 col-md-6'>" +
								"<div class='"+row.status+" search-result-item'>" +
									"<div class='search-result-item__status'>"+
										"<svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' fill='red' class='"+row.status+" bi bi-circle-fill' viewBox='0 0 16 16'>"+
										  "<circle cx='8' cy='8' r='8'/>"+
										"</svg>"
										+status+
									"</div>" +
									"<div class='search-result-item__name'>"+row.name+"</div>"+
									"<div class='search-result-item__address'>"+row.address+"</div>"+
								"</div>"+
							"</div>"
						);
					}
					
				});
				$('#search-result').append();
				$('#search-result').slideDown('fast');
			}
		</script>	
		<script async src="https://www.googletagmanager.com/gtag/js?id=G-87SHFLMBS5"></script>
		<script>
		  window.dataLayer = window.dataLayer || [];
		  function gtag(){dataLayer.push(arguments);}
		  gtag('js', new Date());
		
		  gtag('config', 'G-87SHFLMBS5');
		</script>	
	</body>
</html>
