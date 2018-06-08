import fetchjs from '@/config/fetch'
export default {
	data: () => ({
		KEY_USER: 'ems_user',
		KEY_DEPTLIST: 'ems_deptList',
		KEY_ROLELIST: 'ems_roleList',
		KEY_TOKEN: 'ems_token',
		KEY_EQUIPTYPE: 'ems_equipType',
		KEY_EQUIPSTATE: 'ems_equipState',
		KEY_FACTORYLIST: 'ems_factoryList',
		ROLE_ADMIN: '1001'
	}),
	methods: {
		checkMights(page, mights) {
			for (let i = 0; i < mights.length; i++) {
				if (page == mights[i]) {
					return true
				}
			}
			return false
		},
		redirect(page) {
			this.$router.replace({ name: page })
		},
		jumpTo(page) {
			if (WXEnvironment.platform == 'Web') {
				this.$router.push({ name: page })
			} else {
				let url = this.$getConfig().bundleUrl;
				url = url.split('/').slice(0, -1).join('/') + '/' + page + '.js';
				this.getModule('navigator').push({
					url: url,
					animated: "true"
				}, event => {
					//modal.toast({ message: 'callback: ' + event })
				})
			}
		},
		jumpWithParams(page, params) {
			if (WXEnvironment.platform === 'Web') {
				this.$router.push({ name: page, params: params })
			} else {
				let url = this.$getConfig().bundleUrl;
				url = url.split('/').slice(0, -1).join('/') + '/' + page + '.js';
				let q = this.createQuery(params)
				this.getModule('navigator').push({
					url: url + q,
					animated: "true"
				}, event => {
					//modal.toast({ message: 'callback: ' + event })
				})
			}
		},
		// object 转 URL 参数
		createQuery(obj) {
			let url = '?';
			for (let key in obj) {
				if (obj[key] !== null) {
					url += (key + '=' + encodeURIComponent(obj[key]) + '&');
				}
			}
			return url.substring(0, url.lastIndexOf('&'));
		},
		getParams() {
			if (WXEnvironment.platform === 'Web') {
				return this.$route.params;
			} else {
				let url = weex.config.bundleUrl
				url = url.substring(url.indexOf('.js?') + 3);
				let result = {};
				if (url.indexOf("?") != -1) {
					let str = url.substr(1);
					let strs = str.split("&");
					for (let i = 0; i < strs.length; i++) {
						result[strs[i].split("=")[0]] = decodeURIComponent(strs[i].split("=")[1]);
					}
				}
				return result;
			}
		},
		toBack() {
			if (WXEnvironment.platform === 'Web') {
				this.$router.back()
			} else {
				this.getModule('navigator').pop({ animated: "true" })
			}
		},
		getModule(name) {
			return weex.requireModule(name)
		},
		toast(msg, duration = 1) {
			this.getModule('modal').toast({
				message: msg,
				duration: duration
			})
		},
		alert(msg, duration = 1) {
			this.getModule('modal').alert({
				message: msg,
				duration: duration
			})
		},
		post(url, params, callback) {
			fetchjs.post(url, params, callback)
		},
		get(url, params, callback) {
			fetchjs.get(url, params, callback)
		},
		getImage(name) {
			if (WXEnvironment.platform === 'Web') {
				return `/static/images/${name}`
			} else if (WXEnvironment.platform === 'android') {
				let t = name.substring(0, name.lastIndexOf('.'));
				return `local:///${t}`;
			} else {
				return `local:///${name}`;
			}
		},
		setData(key, data) {
			this.getModule('storage').setItem(key, data)
		},
		getData(key, callback) {
			this.getModule('storage').getItem(key, (res) => {
				if (res.result == "success") {
					callback(res.data)
				} else callback(null)
			})
		},
		removeData(key) {
			this.getModule('storage').removeItem(key, (res) => { })
		}
	}
}
