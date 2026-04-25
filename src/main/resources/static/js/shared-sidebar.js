document.addEventListener('DOMContentLoaded', function() {
    var currentPath = location.pathname;
    var roleInput = document.getElementById('userRole');
    var currentRole = roleInput ? roleInput.value : 'student';

    var menuItems = [];
    var roleTitle = '';

    if (currentRole === 'teacher') {
        roleTitle = '👨‍🏫 教师端';
        menuItems = [
            { href: '/teacher/index', icon: '🏠', text: '首页概览' },
            { href: '/teacher/upload', icon: '📤', text: '上传课程' },
            { href: '/teacher/manage', icon: '🛠️', text: '课程管理' },
            { href: '/teacher/plan', icon: '📅', text: '下发计划' },
            { href: '/teacher/categories', icon: '📁', text: '分类管理' },
            { href: '/teacher/audit', icon: '🎓', text: '培训审核' },
            { href: '/student/ranking', icon: '🏆', text: '荣誉榜' }
        ];
    } else if (currentRole === 'admin') {
        roleTitle = '⚙️ 管理端';
        menuItems = [
            { href: '/admin/teachers', icon: '👨‍🏫', text: '教师管理' },
            { href: '/admin/students', icon: '👨‍🎓', text: '学员管理' },
            { href: '/admin/audit', icon: '📋', text: '课程审核' },
            { href: '/student/ranking', icon: '🏆', text: '荣誉榜' }
        ];
    } else {
        roleTitle = '👨‍🎓 学员端';
        menuItems = [
            { href: '/student/index', icon: '🏠', text: '学员首页' },
            { href: '/student/public-courses', icon: '🌍', text: '全员公开课' },
            { href: '/student/dept-courses', icon: '🎯', text: '部门必修课' },
            { href: '/student/ranking', icon: '🏆', text: '荣誉榜' }
        ];
    }

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
