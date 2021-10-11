<%--
  Created by IntelliJ IDEA.
  User: 2chan
  Date: 2021-10-10
  Time: 오후 9:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../../../resources/css/comment.css">
    <script src="../../../resources/js/jquery.min.js"></script>
    <script src="../../../resources/js/comment.js"></script>
</head>
<body>
<!-- comments container -->
<div class="comment_block">
    <!-- new comment -->
    <div class="new_comment">
        <!-- build comment -->
        <ul class="user_comment">
            <!-- COMMENTS -->
<%--            <li>
                <div class="comment_body">
                    <div class="replied_to">
                            <span class="user">John Smith:</span>
                            Gastropub cardigan jean shorts, kogi Godard PBR&B lo-fi locavore. Organic chillwave vinyl Neutra. Bushwick Helvetica cred freegan, crucifix Godard craft beer deep v mixtape cornhole Truffaut master cleanse pour-over Odd Future beard. Portland polaroid iPhone.
                    </div>
                    <div class="comment_details">
                        <ul>
                            <li><i class="fa fa-calendar"></i> 04/01/2015</li>
                            <li><i class="fa fa-clock-o"></i> 14:59</li>
                            <li><i class="fa fa-pencil"></i> <span class="user">Simon Gregor</span></li>
                            <li class="update"><i class="fa fa-comment"></i>수정</li>
                            <li class="delete"><i class="fa fa-trash"></i>삭제</li>
                        </ul>
                    </div>
                </div>
            </li>--%>

<%--            <li>
                <div class="comment_body my_comment">
                    <div class="replied_to">
                            <span class="user">Sarah Walkman:</span>
                            Pork belly migas flexitarian messenger bag Brooklyn gluten-free. Tilde kitsch skateboard Helvetica, lumbersexual four loko direct trade pour-over. Cronut deep v keffiyeh cornhole food truck
                    </div>
                    </p>
                    <div class="comment_details my_comment">
                        <ul>
                            <li class="delete"><i class="fa fa-trash"></i>삭제</li>
                            <li class="update"><i class="fa fa-comment"></i>수정</li>
                            <li><i class="fa fa-calendar"></i> 14/01/2015</li>
                            <li><i class="fa fa-clock-o"></i> 19:23</li>
                            <li><i class="fa fa-pencil"></i>Blake Anderson</li>
                        </ul>
                    </div>
                </div>
            </li>--%>

        </ul>
    </div>
</div>

<div class="create_new_comment">
    <div class="input_comment">
        <input class="comment" type="text" placeholder="댓글을 입력해주세요" onKeyDown="javascript: if (event.keyCode == 13) {insertComment()}" onsubmit="return false" >
    </div>
</div>
</body>
</html>
