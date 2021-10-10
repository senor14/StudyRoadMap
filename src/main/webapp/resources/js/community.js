let container
$(document).ready(function(){
    container = document.getElementsByClassName("swiper-wrapper")[0]
    getStudyMap()
})

function getStudyMap(){
    $(".swiper-wrapper").empty();
    let category = document.querySelector('input[name="category"]:checked').value
    let searchType = document.querySelector('option[value="category"]')

    if(category === "s") {
        category = "road_"
        searchType.disabled = false
    }
    else if(category === "c"){
        category = "career_"
        searchType.disabled = true
        searchType = document.querySelector('option[value="title"]')
        searchType.selected = true
    }
    else return alert("카테고리를 설정해 주세요")

    $.ajax({
        url: "/getStudyMap.do",
        type:"GET",
        dataType : "json",
        data : {category : category},
        success : function(data) {
            if(JSON.stringify(data) === "[]") noResult("검색결과가 없습니다.")
            else{
                setSlide(category, data)
                setSwiper()
            }
            console.log("실행 완료 data length-",data.length);
        },
        error : function(err) {
            noResult("오류가 발생하였습니다.")
            console.log("실행중 오류가 발생하였습니다. 에러:",err);
        }
    })
}

function findRoadMap(){
    $(".swiper-wrapper").empty()
    let category = document.querySelector('input[name="category"]:checked').value
    let searchType = document.getElementById("searchType")
    searchType = searchType.options[searchType.selectedIndex].value

    if(category === "s") category = "road_"
    else if(category === "c") category = "career_"

    if(category === "road_" && searchType === "category") searchType = "nodeDataArray.node_category"

    $.ajax({
        url: "/findRoadMap.do",
        type:"GET",
        dataType : "json",
        data : {
            category: category,
            searchType: category+searchType,
            keyWord : document.querySelector('input[name="keyWord"]').value
        },
        success : function(data) {
            if(JSON.stringify(data) === "[]") noResult("검색결과가 없습니다.")
            else {
                setSlide(category, data)
                setSwiper()
            }
            console.log("실행 완료 data length-",data.length);
        },
        error : function(err) {
            noResult("오류가 발생하였습니다.")
            console.log("실행중 오류가 발생하였습니다. 에러:",err);
        }
    })
}

function copyStudyMap(){}

function noResult(test){

    let noResult = document.createElement("div")
    noResult.className = "noResult"
    noResult.textContent = test
    container.appendChild(noResult)

    document.getElementsByClassName("swiper-button-next")[0].classList.add("hide")
    document.getElementsByClassName("swiper-button-prev")[0].classList.add("hide")
}

function setSlide(category, data){
    let num = 0;
    data.forEach(result => {
        let slide = document.createElement('div')
        slide.className = "swiper-slide"
        container.appendChild(slide)

        if(category === "road_" ){
            slide.setAttribute("road-id",result.road_id)

            let setCategory = document.createElement('div')
            setCategory.textContent = "["+result.road_node.node_category+"]"
            setCategory.className = "setCategory"
            slide.appendChild(setCategory)

            let setTitle = document.createElement('div')
            setTitle.textContent = ++num+". "+result.road_title
            setTitle.className = "title"
            slide.appendChild(setTitle)

            let setCreated = document.createElement('div')
            setCreated.textContent = result.created.split("/")[0]
            setCreated.className = "created"
            slide.appendChild(setCreated)

        }else{
            slide.setAttribute("road-id",result.user_uuid)

            let setTitle = document.createElement('div')
            setTitle.textContent = ++num+". "+result.career_title
            setTitle.className = "title"
            slide.appendChild(setTitle)

            let setCreated = document.createElement('div')
            setCreated.textContent = result.created.split("/")[0]
            setCreated.className = "created"
            slide.appendChild(setCreated)
        }
    })
}

function setSwiper(){
    var swiper = new Swiper(".mySwiper", {
        slidesPerView: 5,
        spaceBetween: 10,
        slidesPerGroup: 5,
        loop: true,
        loopFillGroupWithBlank: true,
        pagination: {
            el: ".swiper-pagination",
            clickable: true,
        },
        navigation: {
            nextEl: ".swiper-button-next",
            prevEl: ".swiper-button-prev",
        },
    });
}
