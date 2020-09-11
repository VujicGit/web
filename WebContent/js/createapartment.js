$(document).ready(function () {
   
	$('.input-daterange').datepicker({});
    
    
    
    let forma = $("#myform");
    forma.submit(function(event){
    	event.preventDefault();
    	
    	var favorite = [];
    	
    	
        
       
    	
    	let i = 1;
    	$.each($("input[name='amenities']:checked"), function(){
    		dict = {
    			"id": i,
    			"name": $(this).val()
    		}
            favorite.push(dict);
    		i++;
        });
    
    	let data = {
    			"id": "apar4",
    	        "type": $("#aptype option:selected" ).text(),
    	        "numberOfRooms": $("#norooms").val(),
    	        "numberOfGuests": $("#noguests").val(),
    	        "location": {
    	            "latitude": $("#lat").val(),
    	            "longitude": $("#lon").val(),
    	            "address": {
    	                "street": $("#street").val(),
    	                "number": $("#num").val(),
    	                "place": $("#place").val(),
    	                "zipCode": $("#zip").val() 
    	            }
    	        },
    	        "datesForRent": null,
    	        "datesForIssue": null,
    	        "host": {
    	            "username": "",
    	            "password": "",
    	            "name": "",
    	            "surname": "",
    	            "gender": null
    	        },
    	        "comments": [{
    	            "guest": {
    	                "username": "",
    	                "password": "",
    	                "name": "",
    	                "surname": "",
    	                "gender": null
    	            },
    	            "apartment": {
    	                "id": null,
    	                "type": null,
    	                "numberOfRooms": 0,
    	                "numberOfGuests": 0,
    	                "location": null,
    	                "datesForRent": null,
    	                "datesForIssue": null,
    	                "host": null,
    	                "comments": null,
    	                "images": null,
    	                "price": 0.0,
    	                "amenities": null,
    	                "reservation": null,
    	                "checkoutDate": null,
    	                "checkInDate": null,
    	                "status": null
    	            },
    	            "content": "dadada",
    	            "rating": 2
    	        }],
    	        "images": [],
    	        "price": $("#price").val(),
    	        "amenities": favorite,
    	        "reservation": [{
    	            "apartment": {
    	                "id": null,
    	                "type": null,
    	                "numberOfRooms": 0,
    	                "numberOfGuests": 0,
    	                "location": null,
    	                "datesForRent": null,
    	                "datesForIssue": null,
    	                "host": null,
    	                "comments": null,
    	                "images": null,
    	                "price": 0.0,
    	                "amenities": null,
    	                "reservation": null,
    	                "checkoutDate": null,
    	                "checkInDate": null,
    	                "status": null
    	            },
    	            "reservationBegins": 0,
    	            "numberOfNights": 0,
    	            "price": 0,
    	            "message": "dada",
    	            "guest": {
    	                "username": "",
    	                "password": "",
    	                "name": "",
    	                "surname": "",
    	                "gender": null
    	            },
    	            "status": "ACCEPTED"
    	        }],
    	        "checkoutDate": null,
    	        "checkInDate": null,
    	        "status": $("#status option:selected" ).text()
    	};
    	
    	
    

    	$.ajax({
    		type: "POST",
			url: 'rest/apartments/save',
			data: JSON.stringify(data),
			contentType: 'application/json',
			dataType: "json",
			success: function(){
				$('#success').text('Novi proizvod uspešno kreiran.');
				$("#success").show().delay(3000).fadeOut();
				console.log(json);
				
				
				}
			});
    		

    });
    
    
});


