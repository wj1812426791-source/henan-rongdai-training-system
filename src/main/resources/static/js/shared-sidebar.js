document.addEventListener('DOMContentLoaded', function() {
    var currentPath = location.pathname;
    var currentRole = window.currentUserRole || sessionStorage.getItem('userRole') || 'student';
    if (window.currentUserRole) {
        sessionStorage.setItem('userRole', window.currentUserRole);
    }
    currentRole = sessionStorage.getItem('userRole') || currentRole;

    var menuItems = [];
    if (currentPath.indexOf('/teacher/') === 0) {
        menuItems = [
            { href: '/teacher/upload', icon: '📤', text: '上传课程' },
            { href: '/teacher/manage', icon: '🛠️', text: '课程管理' },
            { href: '/student/ranking', icon: '🏆', text: '荣誉榜' }
        ];
    } else if (currentPath.indexOf('/admin/') === 0) {
        menuItems = [
            { href: '/admin/teachers', icon: '👨‍🏫', text: '教师管理' },
            { href: '/admin/students', icon: '👨‍🎓', text: '学员管理' },
            { href: '/student/ranking', icon: '🏆', text: '荣誉榜' }
            
        ];
    } else {
        menuItems = [
            { href: '/student/courses', icon: '📚', text: '课程列表' },
            { href: '/student/ranking', icon: '🏆', text: '荣誉榜' }
        ];
    }

    var roleTitle = '👨‍🎓 学员端';
    if (currentPath.indexOf('/teacher/') === 0) roleTitle = '👨‍🏫 教师端';
    else if (currentPath.indexOf('/admin/') === 0) roleTitle = '⚙️ 管理端';

    var sidebarHtml = '<h4>' + roleTitle + '</h4>';
    for (var i = 0; i < menuItems.length; i++) {
        var item = menuItems[i];
        var isActive = currentPath === item.href || currentPath.indexOf(item.href) === 0;
        sidebarHtml += '<a href="' + item.href + '"' + (isActive ? ' class="active"' : '') + '>' + item.icon + ' ' + item.text + '</a>';
    }
    sidebarHtml += '<a href="/" class="mt-5">🏠 返回首页</a>';

    var sidebarEl = document.querySelector('.sidebar');
    if (sidebarEl) {
        sidebarEl.innerHTML = sidebarHtml;
    }
});
