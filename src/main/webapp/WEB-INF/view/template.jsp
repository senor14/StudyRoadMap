<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8" />
  <link rel="apple-touch-icon" sizes="76x76" href="/resources/assets/img/apple-icon.png">
  <link rel="icon" type="image/png" href="/resources/assets/img/favicon.png">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <title>
    Reminder
  </title>
  <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no' name='viewport' />
  <!--     Fonts and icons     -->
  <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700,200" rel="stylesheet" />
  <link href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css" rel="stylesheet">
  <!-- CSS Files -->
  <link href="/WEB-INF/resources/assets/css/bootstrap.min.css" rel="stylesheet" />
  <link href="/WEB-INF/resources/assets/css/paper-dashboard.css?v=2.0.1" rel="stylesheet" />
  <link rel="stylesheet" href="/WEB-INF/resources/scss/Button.css">
</head>

<body class="">
  <div class="wrapper ">
    <%@ include file="/WEB-INF/view/sidebar.jsp" %>
    <div class="main-panel">
      <!-- Navbar -->
      <nav class="navbar navbar-expand-lg navbar-absolute fixed-top navbar-transparent">
        <div class="container-fluid">
          <div class="navbar-wrapper">
            <div class="navbar-toggle">
              <button type="button" class="navbar-toggler" id="navbar-toggler">
                <span class="navbar-toggler-bar bar1"></span>
                <span class="navbar-toggler-bar bar2"></span>
                <span class="navbar-toggler-bar bar3"></span>
              </button>
            </div>
            <a class="navbar-brand" href="javascript:;">Reminder</a>
          </div>
        </div>
      </nav>
      <!-- End Navbar -->
      <div class="content">
      </div>
      <footer class="footer footer-black  footer-white ">
        <div class="container-fluid">
        </div>
      </footer>
    </div>
  </div>
  <!--   Core JS Files   -->
  <script src="/WEB-INF/resources/assets/js/core/jquery.min.js"></script>
  <script src="/WEB-INF/resources/assets/js/core/popper.min.js"></script>
  <script src="/WEB-INF/resources/assets/js/core/bootstrap.min.js"></script>
  <script src="/WEB-INF/resources/assets/js/plugins/perfect-scrollbar.jquery.min.js"></script>
  <!-- Chart JS -->
  <script src="/WEB-INF/resources/assets/js/plugins/chartjs.min.js"></script>
  <!--  Notifications Plugin    -->
  <script src="/WEB-INF/resources/assets/js/plugins/bootstrap-notify.js"></script>
  <!-- Control Center for Now Ui Dashboard: parallax effects, scripts for the example pages etc -->
  <script src="/WEB-INF/resources/assets/js/paper-dashboard.min.js?v=2.0.1" type="text/javascript"></script>
  <script>
    
    $("#navbar-toggler").on('click', function(){
    	if($(this).hasClass("toggled")){
    		$(this).removeClass("toggled");
    		$("html").first().removeClass("nav-open");
    	}else{
    		$(this).addClass("toggled");
    		$("html").first().addClass("nav-open");
    		
    	}
    	
    })
	
  </script>
</body>

</html>
