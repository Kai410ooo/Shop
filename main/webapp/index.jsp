<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="refresh" content="12;url=LoginServlet">
    <title>トップ画面</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            overflow: hidden; /* スクロールバーを隠す */
            background-color: black;
        }
        
		h5 {
            font-family: 'Georgia', serif;
            font-size: 3em;
            color: white; /* 深みのある色で高級感 */
            text-align: center;
            position: absolute; /* 絶対位置に配置 */
            top: 40%;
            left: 50%;
            transform: translate(-50%, -50%); /* 中央に配置 */
            z-index: 9999; /* 他の要素より上に表示 */
            animation: fadeInOut2 12s infinite;
        }
        
                /* アニメーションの設定 */
        @keyframes fadeInOut2 {
            0%, 100% {
                opacity: 0;
            }
            10% {
                opacity: 1;
            }
            80% {
                opacity: 1;
            }
        }

        .fade-img-container1, .fade-img-container2, .fade-img-container3, .fade-img-container4, .fade-img-container5 {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            width: 35%;
            height: 35%;
        }

        /* それぞれの画像を少しずつずらして配置 */
        .fade-img-container2 {
            top: 25%;
            left: 25%;
        }
        .fade-img-container3 {
            top: 75%;
            left: 75%;
			width: 30%;
            height: 30%;
        }
        .fade-img-container4 {
            top: 70%;
            left: 30%;
			width: 30%;
            height: 30%;
        }
        .fade-img-container5 {
            top: 24%;
            left: 70%;
			width: 40%;
            height: 40%;
        }

        .fade-img {
            position: absolute;
            width: 100%;
            height: 100%;
            object-fit: cover;
            opacity: 0;
            animation: fadeInOut 15s infinite;
        }

        /* アニメーションの設定 */
        @keyframes fadeInOut {
            0%, 100% {
                opacity: 0;
            }
            10% {
                opacity: 1;
            }
            30% {
                opacity: 0;
            }
        }

        /* 各画像ごとのアニメーション遅延を設定 */
        .fade-img1 {
            animation-delay: 0s;
        }

        .fade-img2 {
            animation-delay: 2s;
        }

        .fade-img3 {
            animation-delay: 4s;
        }

        .fade-img4 {
            animation-delay: 6s;
        }

        .fade-img5 {
            animation-delay: 8s;
        }
    </style>
</head>
<body>

    <h5>くだもの屋さんへようこそ</h5>
    
    <div class="fade-img-container1">
        <img src="top_image/strawberry_tart.jpg" alt="Image 1" class="fade-img fade-img1">
    </div>
    <div class="fade-img-container2">
        <img src="top_image/fruit_4.jpg" alt="Image 2" class="fade-img fade-img2">
    </div>
    <div class="fade-img-container3">
        <img src="top_image/cherry_cake.jpg" alt="Image 3" class="fade-img fade-img3">
    </div>
    <div class="fade-img-container4">
        <img src="top_image/fruit_bowl.jpg" alt="Image 4" class="fade-img fade-img4">
    </div>
    <div class="fade-img-container5">
        <img src="top_image/fruit_3.jpg" alt="Image 5" class="fade-img fade-img5">
    </div>
</body>
</html>
