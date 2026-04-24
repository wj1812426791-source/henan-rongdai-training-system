/**
 * 视频播放与进度追踪逻辑
 */
document.addEventListener('DOMContentLoaded', function() {
    const video = document.getElementById('courseVideo');
    const courseIdInput = document.getElementById('hiddenCourseId');
    const userIdInput = document.getElementById('hiddenUserId');
    
    // 防御性编程：确保页面上有这些元素才执行逻辑
    if (video && courseIdInput && userIdInput) {
        const courseId = courseIdInput.value;
        const userId = userIdInput.value;
        let isVideoFinished = false;

        console.log("视频追踪器已启动，当前课程ID:", courseId);

        // 监听视频播放结束事件
        video.addEventListener('ended', function() {
            if (!isVideoFinished) {
                isVideoFinished = true;
                console.log("视频播放结束，准备发送进度到后台...");
                sendProgressToBackend(100, 1);
            }
        });

        // 封装 Ajax 请求 (Fetch API) 向后台汇报进度
        function sendProgressToBackend(progress, isFinished) {
            let formData = new FormData();
            formData.append('userId', userId);
            formData.append('courseId', courseId);
            formData.append('progress', progress);
            formData.append('isFinished', isFinished);

            fetch('/student/api/progress', {
                method: 'POST',
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                if (data.justFinished) {
                    alert("🎉 恭喜！您已完成课程学习，学分已发放到您的账户！\n快去【荣誉榜】看看排名吧！");
                } else {
                    console.log("进度已保存（非首次完成）。");
                }
            })
            .catch(error => console.error('进度保存失败:', error));
        }
    }
});