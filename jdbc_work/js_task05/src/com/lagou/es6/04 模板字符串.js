/*
let str = `1
2
3`
console.log(str)
*/

/*
let name = "吕布"
let age = 25
let info = `我叫${name}，今年${age}岁`
console.log(info)*/

// 字符串中调用函数
function test() {
    return "吃喝玩乐"
}
let str = `悲惨的认识，从${test()}开始`
console.log(str)