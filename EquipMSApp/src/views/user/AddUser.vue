<template>
<div class="adduser-wrapper">
	<mini-bar title="添加用户" back="true"></mini-bar>
	<div class="main">
		<div class="row">
			<text class="label">姓名：</text>
			<input class="input" type="text" v-model="newUser.truename">
			<text class="required">*</text>
		</div>
		<div class="row">
			<text class="label">性别：</text>
			<div class="gender-box" @click="gSelect('m')">
				<text class="g-text" :class="[gActive('m')]">男</text>
				<text class="iconfont g-icon" :class="[gActive('m')]" v-if="this.newUser.gender=='m'">&#xe6ad;</text>
			</div>
    	<div class="gender-box" @click="gSelect('f')">
				<text class="g-text" :class="[gActive('f')]">女</text>
				<text class="iconfont g-icon" :class="[gActive('f')]" v-if="this.newUser.gender=='f'">&#xe6ad;</text>
			</div>
		</div>
		<div class="row">
			<text class="label">手机号：</text>
			<input class="input" type="number" v-model="newUser.phone">
			<text class="required">*</text>
		</div>
		<div class="row">
			<text class="label">身份证号：</text>
			<input class="input" type="text" v-model="newUser.idCard">
			<text class="required">*</text>
		</div>
		<div class="row">
			<text class="label">邮箱：</text>
			<input class="input" type="email" v-model="newUser.email">
		</div>
		<div class="row">
			<text class="label">部门：</text>
			<button @click="showSelectList('deptlist')" class="input" style="justify-content:center"><text>{{newUser.deptname}}</text></button>
			<text class="required">*</text>
		</div>
		<div class="row">
			<text class="label">生日：</text>
			<input class="input" type="date" v-model="newUser.birthday">
		</div>
		<div class="row">
			<text class="label">角色：</text>
			<button @click="showSelectList('rolelist')" class="input" style="justify-content:center"><text>{{newUser.rolename}}</text></button>
			<text class="required">*</text>
		</div>
		<button @click="submit" class="button"><text class="button-text">确认</text></button>
	</div>
	<list-select :list="deptList" title="选择部门" valStr="deptname" ref="deptlist" @listSelected="listSelected"></list-select>
	<list-select :list="roleList" title="选择角色" valStr="rolename" ref="rolelist" @listSelected="listSelected"></list-select>
	<alert ref="alert"></alert>
</div>
</template>
<script>
import { MiniBar, ListSelect, Alert } from '@/views/components'
export default {
	data: () => ({
		newUser: {
			truename: '',
			gender: 'm',
			birthday: '',
			phone: '',
			idCard: '',
			email: '',
			dept: '',
			role: '',
			deptname: '',
			rolename: ''
		},
		deptList: {},
		roleList: {},
		select_flag: 'deptlist',/*选择部门还是角色*/
	}),
	methods: {
		submit() {
			if (this.checkFormValue(this.newUser.truename, '请输入姓名')
				&& this.checkFormValue(this.newUser.phone, '请输入手机号')
				&& this.checkFormValue(this.newUser.idCard, '请输入身份证号')
				&& this.checkFormValue(this.newUser.dept, '请选择部门')
				&& this.checkFormValue(this.newUser.role, '请选择角色')) {
				this.post("/addUser", this.newUser, (result) => {
					if (result && result.state == 'success') {
						this.$refs.alert.show("添加成功")
						this.newUser.truename = this.newUser.phone = this.newUser.idCard
							= this.newUser.dept = this.newUser.role = this.newUser.email
							= this.newUser.birthday = this.newUser.deptname = this.newUser.rolename
							= ''
					} else if (result && result.state == 'error') {
						this.$refs.alert.show("添加失败，请重新添加")
					}
				})
			}

		},
		checkFormValue(value, msg) {
			if (value == '') {
				this.$refs.alert.show(msg)
				return false
			}
			return true
		},

		showSelectList(key) {
			this.select_flag = key
			this.$refs[key].show()
		},
		listSelected(e) {
			if (this.select_flag == 'deptlist') {
				this.newUser.deptname = e.obj.deptname
				this.newUser.dept = e.obj.id
			} else if (this.select_flag == 'rolelist') {
				this.newUser.rolename = e.obj.rolename
				this.newUser.role = e.obj.id
			}
		},
		gSelect(val) {
			this.newUser.gender = val
		},
		gActive(key) {
			return (this.newUser.gender == key) ? 'g-active' : ''
		}
	},
	created() {
		this.getData(this.KEY_DEPTLIST, (res) => {
			if (res) {
				this.deptList = JSON.parse(res)
			}
		})
		this.getData(this.KEY_ROLELIST, (res) => {
			if (res) {
				this.roleList = JSON.parse(res)
			}
		})
	},
	components: {
		MiniBar,
		ListSelect,
		Alert
	}
}
</script>

<style scoped>
.iconfont {
  font-family: iconfont;
}
.adduser-wrapper {
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
  top: 140px;
}
.row {
  flex-direction: row;
  background-color: #ffffff;
  padding-left: 60px;
  border-bottom-width: 1px;
  border-bottom-color: rgba(128, 128, 128, 0.5);
}
.label {
  height: 80px;
  width: 180px;
  text-align: center;
  line-height: 80px;
}
.input,
.button {
  height: 80px;
  width: 360px;
  border-radius: 4px;
  padding-left: 20px;
  padding-right: 20px;
}
.button {
  background-color: orangered;
  justify-content: center;
  align-items: center;
  margin-left: 195px;
  margin-top: 80px;
}
.button-text {
  color: #ffffff;
  font-size: 36px;
}
.gender-box {
  flex-direction: row;
  justify-content: center;
  width: 160px;
  height: 80px;
}
.g-text {
  height: 80px;
  line-height: 80px;
}
.g-icon {
  font-size: 48px;
  height: 80px;
  line-height: 80px;
}
.g-active {
  color: orangered;
}
.required {
  height: 80px;
  line-height: 80px;
  position: absolute;
  right: 60px;
  color: orangered;
}
</style>

