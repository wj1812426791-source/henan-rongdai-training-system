document.addEventListener('DOMContentLoaded', function() {
    const uploadForm = document.getElementById('uploadForm');
    const progressBar = document.getElementById('progressBar');
    const progressContainer = document.getElementById('progressContainer');

    uploadForm.addEventListener('submit', function(e) {
        // 展示进度条模拟上传过程
        progressContainer.classList.remove('d-none');
        let width = 0;
        const interval = setInterval(() => {
            if (width >= 90) {
                clearInterval(interval);
            } else {
                width += 10;
                progressBar.style.width = width + '%';
                progressBar.innerText = "正在传输视频文件...";
            }
        }, 300);
        
        // 允许表单提交
        return true;
    });
});