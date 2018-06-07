import config from './index'
let host = config.server_ip
function toParams(obj) {
	let param = ""
	for (let name in obj) {
		if (typeof obj[name] != 'function') {
			param += "&" + name + "=" + encodeURI(obj[name])
		}
	}
	return param.substring(1)
}
export default {
	get(url, params, callback) {
		let stream = weex.requireModule('stream')
		stream.fetch({
			method: 'GET',
			type: 'text',
			url: host + url + '?' + toParams(params)
		}, (res) => {
			if (res.ok) {
				callback(JSON.parse(res.data))
			} else {
				weex.requireModule('modal').toast({ message: '与服务器通信失败' })
				callback(null)
			}
		})
	},
	post(url, params, callback) {
		let stream = weex.requireModule('stream')
		stream.fetch({
			method: 'post',
			type: 'text',
			headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
			url: host + url,
			body: toParams(params)
		}, (res) => {
			if (res.ok) {
				callback(JSON.parse(res.data))
			} else {
				weex.requireModule('modal').toast({ message: '与服务器通信失败' })
				callback(null)
			}
		})
	}
}