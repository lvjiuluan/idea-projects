new Vue({
	el: "#app",
	data: {
		citycode: "",
		city: "",
		weatherList: []
	},
	mounted() {
		const that = this
		$.getJSON("./resources/citycode.json", function(data) {
			that.citycode = data
		})
	},
	methods: {
		getCityCode: function(city_name) {
			const c = this.citycode.find(item => item.city_name == city_name)
			if (c) {
				return c.city_code
			} else {
				return "City not Found"
			}
		},
		searchWeather: function() {
			console.log("天气查询")
			console.log(this.city)
			// 查询城市代码
			city_code = this.getCityCode(this.city)
			console.log(city_code)
			// 将外部的this传到axios中
			const that = this
			// 调用查询添加的接口
			axios.get("http://162.14.110.208:8080/hello_maven/ajax?city_code=" + city_code)
				.then(function(res) {
						that.weatherList = res.data.data.forecast.slice(0,5)
					},
					function(error) {
						console.log(error)
					})
		}
	}
})