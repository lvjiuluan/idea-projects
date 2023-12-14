// {
//     var a = 0 // 局部变量
//     let b = 0 // 块变量
//     c = 1 // 全局变量
// }
// function test(){
//     var d = 1 //局部变量
//     e = 1 // 全局变量
// }
// console.log(a)
// console.log(c)
// test()
// console.log(e)
//
// var a = 1
// var a = 1 // var可以重复声明
// let b = 1
// let b = 1 // let不可以重复声明

// 使用顺序
// console.log(a) 不报错，输出undefined
// var a = 1
// console.log(b) 报错
// b = 1
// console.log(c) 报错
// let c = 1

// function test() {
//     // let a = 1
//     b = 2
// }
// test()
// console.log(b)