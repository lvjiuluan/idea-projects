var f1 = function (a) {
    return a*10
}
console.log(f1(10))
//es6
let f2 = a=>a*10
console.log(f2(100))

let f3 = (a,b)=>a*b*10
console.log(f3(100,200))
let f4 = (a,b,c)=>{
    a = a*b*10
    return a + c
}
console.log(f4(100,200,300))