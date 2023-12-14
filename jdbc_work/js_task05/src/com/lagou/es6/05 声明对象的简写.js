/*
let name = `吕布`
let age =  28
let user1 = {
    name:name,
    age:age
}
console.log(user1)
let user2 = {name,age}
console.log(user2)*/

let user1 = {
    say:function () {
        console.log("大家好")
    }
}
user1.say()

let user2 = {
    say(){
        console.log("大家好")
    }
}
user2.say()