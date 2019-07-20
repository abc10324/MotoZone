var showAdInterval1 = setInterval(showAd1,5000);
var picCount1 = 1;
var maxCount1 = 6;
var cat1 = 1;

var picCount2 = 1;
var maxCount2 = 11;
var cat2 = 6;

function showAd1(){
    var queryStr1 = "#left-ad a:nth-child(" + picCount1 + ") img";
    var queryStr2 = "#right-ad a:nth-child(" + picCount2 + ") img";
    
    $(queryStr1).fadeIn(700,function(){
        $(queryStr1).delay(3600).fadeOut(700);
    });
    
    $(queryStr2).fadeIn(700,function(){
        $(queryStr2).delay(3600).fadeOut(700);
    });

    picCount1++;
    picCount2++;
    

    if(picCount1 == 6){
        picCount1 = 1;
    }
    
    if(picCount2 == 6){
        picCount2 = 1;
    }
}

$(document).ready(function(){
	
	clearInterval(showAdInterval1);
    showAdInterval = setInterval(showAd1,5000);
    showAd1();
	
});

