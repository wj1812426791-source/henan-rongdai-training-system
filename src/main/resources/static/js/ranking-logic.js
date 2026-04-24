/**
 * 荣誉排行榜 - 前端交互逻辑
 */
document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.getElementById('searchInput');
    const rankingTable = document.getElementById('rankingTable');

    if (searchInput && rankingTable) {
        searchInput.addEventListener('keyup', function() {
            let filter = this.value.toUpperCase();
            let rows = rankingTable.getElementsByTagName('tr');
            
            for (let i = 0; i < rows.length; i++) {
                // 同时搜索姓名(第2列)和部门(第3列)
                let nameCol = rows[i].cells[1] ? rows[i].cells[1].textContent.toUpperCase() : "";
                let deptCol = rows[i].cells[2] ? rows[i].cells[2].textContent.toUpperCase() : "";
                
                if (nameCol.indexOf(filter) > -1 || deptCol.indexOf(filter) > -1) {
                    rows[i].style.display = "";
                } else {
                    rows[i].style.display = "none";
                }
            }
        });
    }
});