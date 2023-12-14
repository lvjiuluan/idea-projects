/*
function test(name,age=18) {
    console.log(`我叫${name}, 今年${age}岁`)
}
test("吕布",23)
test("吕布")
test("吕布","")
test("吕布",null)
test("吕布",undefined) // !!这个是变成18
*/
// 不定参数
function test(...arg) {
    console.log(`传入了${arg.length}个参数`)
    for (var i = 0; i < arg.length; i++) {
        console.log(arg[i])
    }
}
test()
test(1)
test(1,2)
test(1,2,3)
test(1,2,3,4)
