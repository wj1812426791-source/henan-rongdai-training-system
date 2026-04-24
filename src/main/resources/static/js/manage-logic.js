function deleteCourse(id) {
    if (confirm('⚠️ 确定要永久删除这门课程吗？对应的视频文件也将从服务器移除。')) {
        fetch('/teacher/delete/' + id, {
            method: 'POST'
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('删除成功！');
                location.reload(); // 刷新页面
            } else {
                alert('删除失败，请检查该课程是否有关联的学习记录。');
            }
        });
    }
}