let container
$(document).ready(function(){
    container = document.getElementsByClassName("swiper-wrapper")[0]
    // getStudyMap()
})

function focusSearchbar() {
    let searchIcon = document.getElementById("search__icon");
    searchIcon.style.left = "1315px";
}

function focusoutSearchbar() {
    let searchIcon = document.getElementById("search__icon");
    searchIcon.style.left = "1310px";
}

function getStudyMap(){
    $(".swiper-wrapper").empty();
    let category = document.querySelector('input[name="category"]:checked').value
    let searchType = document.getElementById('searchType').value
    let searchTypeTitle = document.querySelector('option[value="title"]')
    let searchTypeCategory = document.querySelector('option[value="category"]')
    let keyword = document.getElementById('keyword').value;

    console.log("searchType: ", searchType);
    console.log("searchTypeTitle: ", searchTypeTitle);
    console.log("searchTypeCategory: ", searchTypeCategory);
    console.log("category: ", category);
    if(category === "s") {
        category = "road"
        searchType.disabled = false
    }
    else if(category === "c"){
        category = "career"
        searchType.disabled = true
        searchType = document.querySelector('option[value="title"]')
        searchType.selected = true
    }
    else return alert("카테고리를 설정해 주세요")

    if (searchType==="title") {
        $.ajax({
            url: "/roadmaps/roadTitle",
            type: "GET",
            dataType: "json",
            data: "&name="+keyword,
            success: function (data) {
                if (JSON.stringify(data) === "[]") noResult("검색결과가 없습니다.")
                else {
                    setSlide(category, data)
                    setSwiper()
                }
                console.log("실행 완료 data length-", data.length);
            },
            error: function (err) {
                noResult("오류가 발생하였습니다.")
                console.log("실행중 오류가 발생하였습니다. 에러:", err);
            }
        })
    } else {
        $.ajax({
            url: "/roadmaps/category",
            type: "GET",
            dataType: "json",
            data: "&name="+keyword,
            success: function (data) {
                if (JSON.stringify(data) === "[]") noResult("검색결과가 없습니다.")
                else {
                    setSlide(category, data)
                    setSwiper()
                }
                console.log("실행 완료 data length-", data.length);
            },
            error: function (err) {
                noResult("오류가 발생하였습니다.")
                console.log("실행중 오류가 발생하였습니다. 에러:", err);
            }
        })
    }
}

function findRoadMap(){
    $(".swiper-wrapper").empty()
    let category = document.querySelector('input[name="category"]:checked').value
    let searchType = document.getElementById("searchType")
    searchType = searchType.options[searchType.selectedIndex].value

    if(category === "s") category = "road"
    else if(category === "c") category = "career"

    if(category === "road" && searchType === "category") searchType = "NodeDataArray.nodeCategory"

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


function noResult(test){

    container.style.transform = "translate3d(0px, 0px, 0px)"
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

        if(category === "road" ){
            slide.setAttribute("road-id",result.roadId)

            // let setCategory = document.createElement('div')
            console.log("result: ",result)

            // setCategory.textContent = "["+result.roadNodeDataArray.nodeCategory+"]"
            // setCategory.className = "setCategory"
            // slide.appendChild(setCategory)
            slide.setAttribute("onclick","parent.document.location.href='/roadmaps/"+result.roadId+"'")
            slide.style = "background-position: center;" +
                "background-repeat: no-repeat; " +
                "background-size: 400px 400px; " +
                "background-image: url('http://localhost:9000/getRoadMapImage?roadId="+result.roadId+"')"

            let setTitle = document.createElement('div')
            console.log("setTitle");
            console.log(setTitle);
            setTitle.style = "background-image"
            setTitle.textContent = ++num+". "+result.roadTitle
            setTitle.className = "title"
            slide.appendChild(setTitle)

            let setCreated = document.createElement('div')
            setCreated.textContent = result.created.split("/")[0]
            setCreated.className = "created"
            slide.appendChild(setCreated)

        }else{
            slide.setAttribute("road-id",result.roadId)

            slide.setAttribute("onclick","parent.document.location.href='/roadmaps/"+result.roadId+"'")
            slide.style = "background-position: center;" +
                "background-repeat: no-repeat; " +
                "background-size: 400px 400px; " +
                "background-image: url('http://localhost:9000/getRoadMapImage?roadId="+result.roadId+"')"

            let setTitle = document.createElement('div')
            setTitle.textContent = ++num+". "+result.roadTitle
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
