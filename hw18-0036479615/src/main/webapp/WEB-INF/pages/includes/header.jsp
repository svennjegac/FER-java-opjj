<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>Picture gallery</title>
		<meta charset="utf-8">
		
		<style type="text/css">
			
			body {
				background-color: rgb(192, 226, 255);
			}
		
			#tags {
				background-color: rgb(255, 250, 192);
				padding: 10px;
			}
			
			.tags {
				color: red;
			}
			
			.buttons {
				text-align: center;
			}
			
			#picturesnames {
				background-color: rgb(139, 247, 139);
			}
			
			.imageNames {
				text-align: center;
				padding: 10px;
			}
			
			#thumbnails {
				text-align: center;
			}
			
			.thumbns {
				background-color: rgb(204, 204, 255);
				padding: 10px;
			}
			
			#singlePicture {
				text-align: center;
			}
		
			.title {
				text-align: center;
				color: rgb(0, 0, 102);
			}
		</style>
		
		<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
		<script type="text/javascript"><!--
		  // vidi: http://www.w3schools.com/ajax/default.asp
		
		  $(document).ready(
				  initializeTags()
		  );
		
		  function initializeTags() {
			  $.ajax(
			  	{
			  		url: "tags",
			  		data: {},
			  		dataType: "json",
			  		success: function(data) {
			  			var tags = data;
			  			var html = "";
			  			
			  			if (tags.length == 0) {
			  				html = "<h3>No tags available</h3>"
			  			} else {
			  				html = "<div class='buttons'>";
			  				for (var i = 0; i < tags.length; i++) {
			  					if (i != 0 && i % 8 == 0) {
			  						html += "<br><br>"
			  					}
			  					
			  					html += "<button type='button' onclick='getTagThumbnails(" + "\"" + tags[i].name + "\"" + ")'>" + tags[i].name + "</button> ";
			  				}
			  				html += "</div>";
			  			}
			  			
			  			$("#tags").html(html);
			  		}
			  	}
			  );
		  }
		  
		  function getTagThumbnails(tag) {
			  countTagThumbnails(tag);
		  }
		  
		  function countTagThumbnails(tag) {
			  $.ajax(
			  	{
			  		url: "thumbnailcount",
			  		data: {
			  			tagName: tag
			  		},
			  		dataType: "json",
			  		success: function(data) {
			  			fetchImageNames(tag);
			  			fetchTagThumbnails(tag, data.number);
			  		}
			  	}
			  );
		  }
		  
		  function fetchImageNames(tag) {
			  $.ajax(
			  	{
			  		url: "imagenames",
			  		data: {
			  			tagName: tag
			  		},
			  		dataType: "json",
			  		success: function(data) {
			  			html = "<div class='imageNames'>";
			  			html += "<h3>Pictures names associated with tags</h3>"
			  			
			  			for (var i = 0; i < data.length; i++) {
			  				html += "<i>" + data[i].imageName + "</i>";
			  				
			  				if (i < data.length - 1) {
			  					html += "    |    ";
			  				}
			  			}
			  			
			  			html += "</div>";
			  			$("#picturesnames").html(html);
			  		}
			  	}
			  );
		  }
		  
		  function fetchTagThumbnails(tag, number) {
			  html = "";
			  html += "<div class='thumbns'>";
			  for (var i = 0; i < number; i++) {
				  if (i != 0 && i % 3 == 0) {
					  html += "<br><br>";
				  }
				  
				  html += "<img src='fetchthumbnail?tagName=" + tag + "&index=" + i +
						  "' alt='Nedostupna slika' onclick='fetchPicture(\"" + tag + "\", " + i + ")'> ";
			  }
			  html += "</div>";
			  
			  $("#thumbnails").html(html);
			  $("#singlePicture").html("");
		  }
		  
		  function fetchPicture(tag, index) {
			  $.ajax(
			  	{
			  		url: "fetchpicturedata",
			  		data: {
			  			tagName: tag,
			  			index: index
			  		},
			  		dataType: "json",
			  		success: function(data) {
			  			info = data;
			  			html = "<h3>" + info.name + "</h3>";
			  			html += "<h4>" + info.description + "</h4>";
			  			
			  			html += "<div class='tags'>"
			  			for (var i = 0; i < info.tags.length; i++) {
			  				html += "<b>" + info.tags[i] + "</b>";
			  				if (i < info.tags.length - 1) {
			  					html += " | ";
			  				}
			  			}
			  			html += "</div>";
			  			
			  			html += "<br>"
			  			
			  			html += "<img src='fetchpicture?tagName=" + tag + "&index=" + index + "' alt='Picture not available'>"
			  			
			  			$("#singlePicture").html(html);
			  		}
			  	}
			  );
		  }
		//--></script>
	</head>
	<body>