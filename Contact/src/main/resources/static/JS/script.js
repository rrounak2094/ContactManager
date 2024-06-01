console.log("This is script file");

const toggleSidebar = () =>{
	if($(".sidebar").is(":visible")){
		//close the side bar
		$(".sidebar").css("display","none");
		$(".content").css("margin-left","0%");
	}
	else{
		//open the side bar
		$(".sidebar").css("display","block");
		$(".content").css("margin-left","20%");
	}
};

//search funtinality

const search=()=>{
	//console.log("searching.....");
	
	let query=$("#search-input").val();
	console.log(query);
	
	if(query==""){
		$(".search-result").hide();
	}
	else{
		console.log(query);
		
		//sending data to the server
		let url=`http://localhost:1218/search/${query}`;
		
		fetch(url).then((response)=>{
			return response.json();
		})
		.then((data)=>{
			//access the data
			console.log(data);
			
			let text=`<div class='list-group'>`;
			
			data.forEach((contact)=>{
				text +=`<a href='/user/contact/${contact.conids}' class='list-group-item list-group-item-action'> ${contact.name} </a>`;
			});
			
			text +=`</div>`;
			
			$(".search-result").html(text);
			$(".search-result").show();
		});
		
		$(".search-result").show();
	}
	
};