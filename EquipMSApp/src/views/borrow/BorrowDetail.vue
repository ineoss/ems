<!--   借用申请详情    -->
<template>
<div class="borrowdetail-wrapper">
	<mini-bar title="借用申请详情" back="true"></mini-bar>
	<list class="main">
		<refresh class="refresh" @refresh="onrefresh"  :display="refreshing ? 'show' : 'hide'">
      		<text class="indicator-text">刷新中 ...</text>
      		<loading-indicator class="indicator"></loading-indicator>
    	</refresh>
		<cell>
		<div class="row" style="margin-top:40px">
			<text class="label">设备名称：</text>
			<text class="input">{{borrow.equipName}}</text>
		</div>
		<div class="row">
			<text class="label">设备种类：</text>
			<text class="input">{{borrow.typeName}}</text>
		</div>
		<div class="row">
			<text class="label">申请人姓名：</text>
			<text class="input">{{borrow.truename}}</text>
		</div>
		<div class="row">
			<text class="label">申请人部门：</text>
			<text class="input">{{borrow.deptName}}</text>
		</div>
		<div class="row">
			<text class="label">申请日期：</text>
			<text class="input">{{borrow.applyDate}}</text>
		</div>
		<div class="row">
			<text class="label">是否同意：</text>
			<div class="gender-box" @click="gSelect(1)">
				<text class="g-text" :class="[gActive(1)]">同意</text>
				<text class="iconfont g-icon" :class="[gActive(1)]" v-if="this.agree==1">&#xe6ad;</text>
			</div>
    	<div class="gender-box" @click="gSelect(0)">
				<text class="g-text" :class="[gActive(0)]">不同意</text>
				<text class="iconfont g-icon" :class="[gActive(0)]" v-if="this.agree==0">&#xe6ad;</text>
			</div>
		</div>
		<div class="row" v-if="agree==0">
			<text class="label">原因：</text>
			<textarea class="textarea" rows="6" v-model="mark"></textarea>
			<text class="required">*</text>
		</div>
		<button @click="submit" class="button"><text class="button-text">确认</text></button>
		</cell>
		<alert ref="alert" @dialogConfirmBtnClick="back"></alert>
		<alert ref="warn"></alert>
	</list>
</div>
</template>
<script>
import { MiniBar, Alert } from '@/views/components'
export default {
	data() {
		return {
			borrow: {},
			agree: 1,
			mark: '',
			userid: '',
			refreshing: false
		}
	},
	methods: {
		submit() {
			if (this.agree == 0 && this.mark.trim() == '') {
				this.$refs.warn.show('请输入原因')
				return
			}
			this.borrow.mark = this.mark
			this.borrow.agree = this.agree
			this.borrow.auditor = this.userid
			this.post('/modifyBorrow', this.borrow, (result) => {
				if (result.state == "success") {
					this.$refs.alert.show('设置成功')
				} else {
					this.$refs.alert.show('设置失败！请重试')
				}
			})
		},
		back() {
			this.toBack()
		},
		onrefresh(event) {
			this.refreshing = true
			this.get('/getBorrowDetail', { id: this.borrow.id }, (result) => {
				if (result && result.state == 'success') {
					this.borrow = result.data
				}
				this.refreshing = false
			})
		},
		gSelect(val) {
			this.agree = val
		},
		gActive(key) {
			return (this.agree == key) ? 'g-active' : ''
		}
	},
	created() {
		let params = this.getParams()
		if (params && params.id) {
			this.get('/getBorrowDetail', { id: params.id }, (result) => {
				if (result && result.state == 'success') {
					this.borrow = result.data
				}
			})
		}
		this.getData(this.KEY_USER, (res) => {
			if (res) {
				this.userid = JSON.parse(res).id
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
.borrowdetail-wrapper {
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
  align-items: center;
}
.row {
  flex-direction: row;
  width: 750px;
  padding-left: 60px;
  background-color: #ffffff;
}
.label {
  height: 80px;
  width: 240px;
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
  height: 60px;
  line-height: 60px;
  border-radius: 8px;
  padding-left: 16px;
  padding-right: 16px;
  margin-left: 20px;
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
.textarea {
  width: 360px;
  border-width: 1px;
  border-color: cadetblue;
  border-radius: 4px;
  padding-left: 10px;
  padding-right: 10px;
  padding-top: 10px;
  padding-bottom: 10px;
}
.button {
  height: 80px;
  width: 360px;
  border-radius: 4px;
  background-color: orangered;
  justify-content: center;
  align-items: center;
  margin-left: 195px;
  margin-top: 80px;
}
.button-text {
  color: #ffffff;
  font-size: 36px;
  text-align: center;
}
</style>
