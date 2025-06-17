<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<button id="scrollToTopBtn" title="ページのトップへ戻る">↑</button>

<script>
    // スクロールして「トップに戻る」ボタンの表示/非表示を切り替え
    window.onscroll = function() {
        let scrollToTopBtn = document.getElementById("scrollToTopBtn");
        if (document.body.scrollTop > 100 || document.documentElement.scrollTop > 100) {
            scrollToTopBtn.style.display = "block";
        } else {
            scrollToTopBtn.style.display = "none";
        }
    };

    // ボタンをクリックするとトップに戻る
    document.getElementById("scrollToTopBtn").onclick = function() {
        window.scrollTo({ top: 0, behavior: 'smooth' });
    };
</script>