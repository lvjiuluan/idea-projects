let user1 = {
    name:"项羽",
    age:25
}
let user2 = {...user1}
console.log(user2)
let user3 = {
    head:"诸葛亮"
}
let user4 = {...user1,...user3}
console.log(user4)