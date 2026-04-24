document.addEventListener('DOMContentLoaded', function() {
    var currentPath = location.pathname;
    var roleInput = document.getElementById('userRole');
    var currentRole = roleInput ? roleInput.value : 'student';

    var menuItems = [];

    if (currentRole === 'teacher') {
        menuItems = [
            { href: '/teacher/upload', icon: '📤', text: '上传课程' },
            { href: '/teacher/manage', icon: '🛠️', text: '课程管理' },
            { href: '/student/ranking', icon: '🏆', text: '荣誉榜' }
        ];
    } else if (currentRole === 'admin') {
        menuItems = [
            { href: '/admin/teachers', icon: '👨‍🏫', text: '教师管理' },
            { href: '/admin/students', icon: '👨‍🎓', text: '学员管理' },
            { href: '/student/ranking', icon: '🏆', text: '荣誉榜' },
            { href: '/admin/dashboard', icon: '📊', text: '管理看板' }
        ];
    } else {
        menuItems = [
            { href: '/student/courses', icon: '📚', text: '课程列表' },
            { href: '/student/ranking', icon: '🏆', text: '荣誉榜' }
        ];
    }

    var roleTitle = '👨‍🎓 学员端';
    if (currentRole === 'teacher') roleTitle = '👨‍🏫 教师端';
    else if (currentRole === 'admin') roleTitle = '⚙️ 管理端';

    var sidebarHtml = '<h4>' + roleTitle + '</h4>';
    for (var i = 0; i < menuItems.length; i++) {
        var item = menuItems[i];
        var isActive = currentPath === item.href;
        sidebarHtml += '<a href="' + item.href + '"' + (isActive ? ' class="active"' : '') + '>' + item.icon + ' ' + item.text + '</a>';
    }
    sidebarHtml += '<a href="/logout" class="mt-5">🚪 退出登录</a>';

    var sidebarEl = document.querySelector('.sidebar');
    if (sidebarEl) {
        sidebarEl.innerHTML = sidebarHtml;
    }
});
