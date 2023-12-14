function addUser(name) {
    return `保存${name}成功`
}
function removeUser(name) {
    return `删除${name}成功`
}
/*
module.exports = {
    save:addUser,
    delete:removeUser
}*/
module.exports = {
    addUser,
    removeUser
}