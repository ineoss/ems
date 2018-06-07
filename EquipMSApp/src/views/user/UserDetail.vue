<template>
<div class="userdetail-wrapper">
	<mini-bar title="用户详细信息" back="true"></mini-bar>
	<list class="main">
		<refresh class="refresh" @refresh="onrefresh"  :display="refreshing ? 'show' : 'hide'">
      		<text class="indicator-text">刷新中 ...</text>
      		<loading-indicator class="indicator"></loading-indicator>
    	</refresh>
		<cell class="cell">
		<image :src="avatar" class="avatar"/>
		</cell>
		<cell class="cell">
		<div class="row">
			<text class="label">姓名：</text>
			<text class="input">{{user.truename}}</text>
		</div>
		<div class="row">
			<text class="label">性别：</text>
			<text class="input">{{user.gender=='m'?'男':'女'}}</text>
		</div>
		<div class="row">
			<text class="label">手机号：</text>
			<text class="input">{{user.phone}}</text>
		</div>
		<div class="row">
			<text class="label">身份证号：</text>
			<text class="input">{{user.idCard}}</text>
		</div>
		<div class="row">
			<text class="label">邮箱：</text>
			<text class="input">{{user.email}}</text>
		</div>
		<div class="row">
			<text class="label">生日：</text>
			<text class="input">{{user.birthday}}</text>
		</div>
		<div class="row">
			<text class="label">部门：</text>
			<text class="input">{{user.deptname}}</text>
		</div>
		<div class="row">
			<text class="label">角色：</text>
			<text class="input">{{user.rolename}}</text>
		</div>
		<div class="button-group" v-if="user.dept==userDept">
			<button><text @click="delUserAlert" class="util" style="background-color:rgba(255,0,0,0.5)">删除用户</text></button>
			<button><text @click="modifyUser" class="util" style="background-color:rgb(0, 191, 255)">修改用户</text></button>
		</div>
		</cell>
		<alert ref="delAlert" @dialogConfirmBtnClick="delUser" :single="false"></alert>
		<alert ref="delSuccessAlert" @dialogConfirmBtnClick="delSuccess"></alert>
		<alert ref="alert"></alert>
	</list>
</div>
</template>
<script>
import { MiniBar, Alert } from '@/views/components'
import config from '@/config'
let server_ip = config.server_ip
export default {
	data() {
		return {
			user: { avatar: '/avatar/default_avatar.jpg' },
			refreshing: false,
			userDept: ''
		}
	},
	methods: {
		onrefresh(event) {
			this.refreshing = true
			this.get('/getUserInfo', { id: this.user.id }, (result) => {
				if (result && result.state == 'success') {
					this.user = result.data
				}
				this.refreshing = false
			})
		},
		delUserAlert() {
			this.$refs['delAlert'].show('删除用户后不可恢复，确定要删除此用户吗？')
		},
		delUser() {
			this.post('/delUser', this.user, (result) => {
				if (result && result.state == 'success') {
					this.$refs['delSuccessAlert'].show('删除成功')
				} else {
					this.$refs['alert'].show('删除失败')
				}
			})
		},
		delSuccess() {
			this.toBack()
		},
		modifyUser() {
			this.jumpWithParams('ModifyUser', this.user)
		}
	},
	computed: {
		avatar() {
			return server_ip + this.user.avatar
		}
	},
	created() {
		let params = this.getParams()
		if (params && params.id) {
			this.get('/getUserInfo', { id: params.id }, (result) => {
				if (result && result.state == 'success') {
					this.user = result.data
				}
			})
		}
		this.getData(this.KEY_USER, (res) => {
			if (res) {
				this.userDept = JSON.parse(res).dept
			}
		})
	},
	components: {
		MiniBar,
		Alert
	}
}
</script>
<style scoped>
.iconfont {
  font-family: iconfont;
}
.userdetail-wrapper {
  background-color: #f0f0f0;
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  top: 0;
}
.main {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  top: 100px;
}
.cell {
  justify-content: center;
  align-items: center;
}
.avatar {
  height: 240px;
  width: 240px;
  border-radius: 120px;
  margin-bottom: 40px;
  margin-top: 40px;
}
.row {
  flex-direction: row;
  width: 750px;
  padding-left: 60px;
}
.label {
  height: 80px;
  width: 180px;
  text-align: center;
  line-height: 80px;
}
.input {
  height: 80px;
  width: 360px;
  border-radius: 4px;
  padding-left: 20px;
  padding-right: 20px;
  line-height: 80px;
}
.button-group {
  flex-direction: row;
  margin-top: 40px;
}
.util {
  height: 80px;
  width: 200px;
  text-align: center;
  line-height: 80px;
  border-radius: 8px;
  padding-left: 16px;
  padding-right: 16px;
	margin-left:10px;
	margin-right:10px;
  color: white;
}
.loading,
.refresh {
  width: 750;
  display: -ms-flex;
  display: -webkit-flex;
  display: flex;
  -ms-flex-align: center;
  -webkit-align-items: center;
  -webkit-box-align: center;
  align-items: center;
}
.indicator-text,
.more {
  color: #888888;
  margin-top: 10px;
  text-align: center;
}
.indicator {
  margin-top: 16px;
  height: 40px;
  width: 40px;
  color: blue;
}
</style>
