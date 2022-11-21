/**
 * 判断字符串是否为空
 * 如果空，则返回true
 * 非空，则返回false
 */
function isEmpty(str) {
    if (str == null || str.trim() == "") {
        return true;
    }
    return false;
}
