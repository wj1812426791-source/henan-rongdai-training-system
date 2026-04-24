/**
 * 课程列表页面交互逻辑
 */
document.addEventListener('DOMContentLoaded', function() {
    console.log("课程列表页面已加载");

    // 获取所有课程卡片
    const courseCards = document.querySelectorAll('.course-card');

    courseCards.forEach(card => {
        card.addEventListener('mouseenter', function() {
            this.style.boxShadow = "0 8px 15px rgba(52, 152, 219, 0.3)";
        });

        card.addEventListener('mouseleave', function() {
            this.style.boxShadow = "0 4px 6px rgba(0,0,0,0.1)";
        });
    });
});