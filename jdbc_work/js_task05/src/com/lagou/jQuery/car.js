$(".jia").click(function () {
    num = $(this).prev().text()
    num = Number(num) + 1
    $(this).prev().text(num)
    price = $(this).parent().prev().text()
    total = Number(num) * Number(price)
    $(this).parent().next().text(total)
    getTotal()
})
$(".jian").click(function () {
    num = $(this).next().text()
    num = Number(num) - 1
    if(num == 0){
        if(confirm("是否删除该商品？")){
            $(this).parents("tr").remove()
        }
    }else{
        $(this).next().text(num)
        price = $(this).parent().prev().text()
        total = Number(num) * Number(price)
        $(this).parent().next().text(total)
    }
    getTotal()
})
// 计算所有商品的总价
function getTotal() {
    var sum = 0
    // var arr = $("tr:not(tr:first)").find("td:last")
    // for (var i = 0; i < arr.length; i++) {
    //     sum += Number(arr[i].innerHTML)
    // }
    $("tr:not(tr:first)").find("td:last").each(function () {
        sum += Number($(this).text())
    })
    $("b").text(sum)
}